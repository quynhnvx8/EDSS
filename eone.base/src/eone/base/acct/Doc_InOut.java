
package eone.base.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.MDocType;
import eone.base.model.MElementValue;
import eone.base.model.MInOut;
import eone.base.model.MInOutLine;
import eone.util.Env;

public class Doc_InOut extends Doc
{

	public Doc_InOut (ResultSet rs, String trxName)
	{
		super (MInOut.class, rs, trxName);
	}   //  DocInOut



	protected String loadDocumentDetails()
	{
		MInOut inout = (MInOut)getPO();
		p_lines = loadLines(inout);
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails


	private DocLine[] loadLines(MInOut inout)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MInOutLine[] lines = inout.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MInOutLine line = lines[i];
			if (line.getM_Product_ID() == 0)
			{
				if (log.isLoggable(Level.FINER)) log.finer("Ignored: " + line);
				continue;
			}

			DocLine docLine = new DocLine (line, this);
			list.add (docLine);
		}

		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	
	
	public ArrayList<Fact> createFacts ()
	{
		Fact fact = new Fact(this, Fact.POST_Actual);
		MInOut header = (MInOut)getPO();
		
		
		MDocType dt = MDocType.get(getCtx(), header.getC_DocType_ID());
		
		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine docLine = p_lines[i];
			MInOutLine line = (MInOutLine) docLine.getPO();
			
			//Bán hàng
			if (MDocType.DOCBASETYPE_ExportWarehouseForSell.equals(dt.getDocBaseType())) {
				if (!output_COGS(fact, docLine, header, line))
				{
					return null;
				}
				
				if (!output_REV(fact, docLine, header, line))
				{
					return null;
				}
				
				//Nếu có tiền thuế thì hạch toán thuế
				if (line.getTaxAmt().compareTo(Env.ZERO) != 0) {
					if (!output_TAX(fact, docLine, header, line))
					{
						return null;
					}
				}
			}
			
			//Nhập mua
			if (MDocType.DOCBASETYPE_PurchaseInputWarehouse.equals(dt.getDocBaseType())) {
				if (!insert_Data(fact, docLine, header, line))
				{
					return null;
				}
				
				if (line.getTaxAmt().compareTo(Env.ZERO) != 0) {
					if (!input_TAX(fact, docLine, header, line))
					{
						return null;
					}
				}
			}
			//Nhập nội bộ
			if (MDocType.DOCBASETYPE_WarehouseImportUse.equals(dt.getDocBaseType()) 
					
					//Xuất điều chuyển nội bộ
					|| MDocType.DOCBASETYPE_ExportTransferWarehouse.equals(dt.getDocBaseType())
					
					//Xuất sử dụng
					|| MDocType.DOCBASETYPE_WarehoseExportUse.equals(dt.getDocBaseType())
					
					//Kiểm kê thừa thiếu
					|| MDocType.DOCBASETYPE_15InventoryMiss.equals(dt.getDocBaseType())
					|| MDocType.DOCBASETYPE_15InventoryRedundant.equals(dt.getDocBaseType())
					
					//Dư đầu kỳ
					|| MDocType.DOCBASETYPE_WarehouseOpenBalance.equals(dt.getDocBaseType())
					
					//Xuất sản xuất
					|| MDocType.DOCBASETYPE_154Debit.equals(dt.getDocBaseType())
					
					//Xuất sản xuất
					|| MDocType.DOCBASETYPE_154Credit.equals(dt.getDocBaseType())
					
					//Nhập thành phẩm
					|| MDocType.DOCBASETYPE_155Debit.equals(dt.getDocBaseType())
					) 
			{
				if (!insert_Data(fact, docLine, header, line))
				{
					return null;
				}
			}
		}//End for lines
		
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	//Post giá vốn
	private boolean output_COGS(Fact fact, DocLine docLine, MInOut header, MInOutLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), header.getAccount_COGS_ID());
		cr = MElementValue.get(getCtx(), line.getAccount_Cr_ID());
		BigDecimal amount = line.getPricePO().multiply(line.getQty());
		
		FactLine f = fact.createLine(docLine, dr, cr, amount, amount);
		if (f == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		
		f.setPrice(line.getPricePO());
		f.setQty(line.getQty());
		f.setC_BPartner_Dr_ID(header.getC_BPartner_Dr_ID());
		f.setC_BPartner_Cr_ID(header.getC_BPartner_Cr_ID());
		f.setM_Product_ID(line.getM_Product_ID());
		if (line.getM_Product_Cr_ID() > 0)
			f.setM_Product_Cr_ID(line.getM_Product_Cr_ID());
		else
			f.setM_Product_Cr_ID(line.getM_Product_ID());
		f.setM_Warehouse_Dr_ID(line.getM_Warehouse_Dr_ID());
		f.setM_Warehouse_Cr_ID(line.getM_Warehouse_Cr_ID());
		return true;
	}
	
	//Post doanh thu
	private boolean output_REV(Fact fact, DocLine docLine, MInOut header, MInOutLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), line.getAccount_Dr_ID());
		cr = MElementValue.get(getCtx(), header.getAccount_REV_ID());
		BigDecimal amount = line.getAmount().subtract(line.getDiscountAmt());
		
		FactLine f = fact.createLine(docLine, dr, cr, amount, amount);
		if (f == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		
		f.setPrice(line.getPrice());
		f.setQty(line.getQty());
		f.setC_BPartner_Dr_ID(header.getC_BPartner_Dr_ID());
		f.setC_BPartner_Cr_ID(header.getC_BPartner_Cr_ID());
		f.setM_Product_ID(line.getM_Product_ID());
		if (line.getM_Product_Cr_ID() > 0)
			f.setM_Product_Cr_ID(line.getM_Product_Cr_ID());
		else
			f.setM_Product_Cr_ID(line.getM_Product_ID());
		f.setM_Warehouse_Dr_ID(line.getM_Warehouse_Dr_ID());
		f.setM_Warehouse_Cr_ID(line.getM_Warehouse_Cr_ID());
		return true;
	}
	
	//Post thuế (nếu có)
	private boolean output_TAX(Fact fact, DocLine docLine, MInOut header, MInOutLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), line.getAccount_Dr_ID());
		cr = MElementValue.get(getCtx(), header.getAccount_Tax_ID());
		BigDecimal amount = line.getTaxAmt();
		
		FactLine f1 = fact.createLine(docLine, dr, cr, amount, amount);
		if (f1 == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		f1.setC_BPartner_Cr_ID(header.getC_BPartner_Cr_ID());
		f1.setC_BPartner_Dr_ID(header.getC_BPartner_Dr_ID());
		f1.setC_Tax_ID(header.getC_Tax_ID());
		f1.setInvoiceNo(header.getInvoiceNo());
		f1.setInvoiceSymbol(header.getInvoiceSymbol());
		f1.setDateInvoiced(header.getDateInvoiced());
		return true;
	}
	
	/**
	 * 
	 */
	
	//Hàm dùng chung cho nhập và xuất thông thường
	private boolean insert_Data(Fact fact, DocLine docLine, MInOut header, MInOutLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), line.getAccount_Dr_ID());
		cr = MElementValue.get(getCtx(), line.getAccount_Cr_ID());
		BigDecimal amount = line.getAmount();
		
		FactLine f = fact.createLine(docLine, dr, cr, amount, amount);
		if (f == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		
		f.setPrice(line.getPrice());
		f.setQty(line.getQty());
		f.setC_BPartner_Dr_ID(header.getC_BPartner_Dr_ID());
		f.setC_BPartner_Cr_ID(header.getC_BPartner_Cr_ID());
		f.setM_Product_ID(line.getM_Product_ID());
		if (line.getM_Product_Cr_ID() > 0)
			f.setM_Product_Cr_ID(line.getM_Product_Cr_ID());
		else
			f.setM_Product_Cr_ID(line.getM_Product_ID());
		f.setM_Warehouse_Dr_ID(line.getM_Warehouse_Dr_ID());
		f.setM_Warehouse_Cr_ID(line.getM_Warehouse_Cr_ID());
		return true;
	}
	
	
	//Post thuế (nếu có)
	private boolean input_TAX(Fact fact, DocLine docLine, MInOut header, MInOutLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), header.getAccount_Tax_ID());
		cr = MElementValue.get(getCtx(), line.getAccount_Cr_ID());
		BigDecimal amount = line.getTaxAmt();
		
		FactLine f1 = fact.createLine(docLine, dr, cr, amount, amount);
		if (f1 == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		f1.setC_BPartner_Cr_ID(header.getC_BPartner_Cr_ID());
		f1.setC_BPartner_Dr_ID(header.getC_BPartner_Dr_ID());
		f1.setC_Tax_ID(header.getC_Tax_ID());
		f1.setInvoiceNo(header.getInvoiceNo());
		f1.setInvoiceSymbol(header.getInvoiceSymbol());
		f1.setDateInvoiced(header.getDateInvoiced());
		return true;
	}
}   //  Doc_InOut
