
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.CCache;
import eone.util.DB;
import eone.util.Env;


public class MInOut extends X_M_InOut implements DocAction
{

	private static final long serialVersionUID = 1226522383231204912L;

	private static CCache<Integer,MInOut>		s_cache	= new CCache<Integer,MInOut>(Table_Name, 5);

	public static MInOut get(Properties ctx, int M_InOut_ID) {
		MInOut inout = null;
		if (s_cache.containsKey(M_InOut_ID)) {
			return s_cache.get(M_InOut_ID);
		}
		inout = new Query(ctx, Table_Name, "M_InOut_ID = ?", null)
				.setParameters(M_InOut_ID)
				.first();
		s_cache.put(M_InOut_ID, inout);
		return inout;
	}
	
	
	//Nếu có đơn đặt hàng thì tạo line tự động từ đơn đặt hàng
	public static void createOrderLine(MOrder order, int C_Tax_ID, Properties ctx, int M_InOut_ID, String trxName) {
		BigDecimal taxRate = Env.ZERO;
		if(C_Tax_ID > 0) {
			MTax tax = MTax.get(ctx, C_Tax_ID);
			taxRate = tax.getRate();
		}
		MOrderLine [] line = order.getLines(" And Coalesce(QtyDelivered,0) < Qty", "");
		MInOutLine ioline = null;
		for(int i = 0; i < line.length; i++) {
			ioline = new MInOutLine(ctx, 0, trxName);
			ioline.setLine(line[i].getLine());
			ioline.setM_Product_ID(line[i].getM_Product_ID());
			ioline.setPricePO(line[i].getPrice());
			ioline.setQty(line[i].getQty().subtract(line[i].getQtyDelivered()));
			ioline.setAmount(ioline.getPricePO().multiply(ioline.getQty()));
			ioline.setTaxBaseAmt(ioline.getAmount().multiply((Env.ONE.add(taxRate))));
			ioline.setTaxAmt(ioline.getTaxBaseAmt().subtract(ioline.getAmount()));
			ioline.setM_InOut_ID(M_InOut_ID);
			ioline.setC_OrderLine_ID(line[i].getC_OrderLine_ID());
			ioline.save();
		}
			
	}
	
	private void updateQtyOrderDelivered(boolean aproved) {
		String sql = "UPDATE C_OrderLine ol SET QtyDelivered = Coalesce(ol.QtyDelivered,0) + "
				+ " (SELECT coalesce(io.Qty,0) FROM M_InOutLine io WHERE  ol.C_OrderLine_ID = io.C_OrderLine_ID AND io.M_InOut_ID = ?)";
		if (!aproved) {
			sql = "UPDATE C_OrderLine ol SET QtyDelivered = Coalesce(ol.QtyDelivered,0) - "
					+ " (SELECT coalesce(io.Qty,0) FROM M_InOutLine io WHERE  ol.C_OrderLine_ID = io.C_OrderLine_ID AND io.M_InOut_ID = ?)";
		}
		DB.executeUpdate(sql, getM_InOut_ID(), get_TrxName());
	}

	public MInOut (Properties ctx, int M_InOut_ID, String trxName)
	{
		super (ctx, M_InOut_ID, trxName);
		if (M_InOut_ID == 0)
		{
			setDocStatus (DOCSTATUS_Drafted);
			//
			super.setProcessed (false);
		}
	}	//	MInOut

	
	public MInOut (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInOut

	public MInOut (MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate)
	{
		this (order.getCtx(), 0, order.get_TrxName());
		
	}	//	MInOut

	
	public MInOut (MInOut original, int C_DocTypeShipment_ID, Timestamp movementDate)
	{
		this (original.getCtx(), 0, original.get_TrxName());
		
		if (C_DocTypeShipment_ID == 0)
			setC_DocType_ID(original.getC_DocType_ID());
		else
			setC_DocType_ID (C_DocTypeShipment_ID);

		setDateOrdered(original.getDateOrdered());
		setDescription(original.getDescription());
	}	//	MInOut


	/**	Lines					*/
	protected MInOutLine[]	m_lines = null;
	
	
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	//	getDocStatusName

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else{
			StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
			setDescription(msgd.toString());
		}	
	}	//	addDescription

	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MInOut[")
			.append (get_ID()).append("-").append(getDocumentNo())
			.append(",DocStatus=").append(getDocStatus())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		StringBuilder msgreturn = new StringBuilder().append(dt.getName()).append(" ").append(getDocumentNo());
		return msgreturn.toString();
	}	//	getDocumentInfo

	
	protected MInvoiceTax[]	m_linesTax = null;
	public MInvoiceTax[] getLinesTax (boolean requery)
	{
		String sqlWhere = " 1 = 1";
		if (!X_M_InOut.INCLUDETAXTAB_TAXS.equalsIgnoreCase(getIncludeTaxTab())) {
			sqlWhere = " 1=2 ";
		}
		if (m_linesTax != null && !requery) {
			set_TrxName(m_linesTax, get_TrxName());
			return m_linesTax;
		}
		List<MInvoiceTax> list = new Query(getCtx(), I_C_InvoiceTax.Table_Name, sqlWhere + "And M_InOut_ID=?", get_TrxName())
		.setParameters(getM_InOut_ID())
		.list();
		//
		m_linesTax = new MInvoiceTax[list.size()];
		list.toArray(m_linesTax);
		return m_linesTax;
	}
	
	public MInOutLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MInOutLine> list = new Query(getCtx(), I_M_InOutLine.Table_Name, "M_InOut_ID=?", get_TrxName())
		.setParameters(getM_InOut_ID())
		.setOrderBy(MInOutLine.COLUMNNAME_Line)
		.list();
		//
		m_lines = new MInOutLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	//	getMInOutLines

	/**
	 * 	Get Lines of Shipment
	 * 	@return lines
	 */
	public MInOutLine[] getLines()
	{
		return getLines(false);
	}	//	getLines


	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE M_InOutLine SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE M_InOut_ID=").append(getM_InOut_ID());
		int noLine = DB.executeUpdate(sql.toString(), get_TrxName());
		m_lines = null;
		if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
	}	//	setProcessed
	

	protected boolean beforeSave (boolean newRecord)
	{
		return true;
	}	//	beforeSave

	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;

		if (is_ValueChanged("AD_Org_ID"))
		{
			final String sql = "UPDATE M_InOutLine ol"
					+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM M_InOut o WHERE ol.M_InOut_ID=o.M_InOut_ID) "
					+ "WHERE M_InOut_ID=?";
			int no = DB.executeUpdateEx(sql, new Object[] {getM_InOut_ID()}, get_TrxName());
			if (log.isLoggable(Level.FINE)) log.fine("Lines -> #" + no);
		}
		if (is_ValueChanged(X_M_InOut.COLUMNNAME_IncludeTaxTab) ) {
			BigDecimal taxRate = Env.ZERO;
			if (getC_Tax_ID() > 0) { 
				MTax tax = MTax.get(Env.getCtx(), getC_Tax_ID());
				if (tax != null) 
					taxRate = tax.getRate();
			}
			Object [] params = null;
			String sqlUpdateLine = "";
			if (!"NONE".equals(getIncludeTaxTab())) {
				sqlUpdateLine = "Update M_InOutLine r set (Amount, TaxAmt, TaxBaseAmt) = " +
						"( Select l.Qty * l.Price, l.Qty * l.Price * (1 + ?) - l.Qty * l.Price, l.Qty * l.Price * (1 + ?) From M_InOutLine l Where r.M_InOutLine_ID = l.M_InOutLine_ID) "+
						"Where  M_InOut_ID = ?";
				params = new Object [] {taxRate, taxRate, getM_InOut_ID()};
			} else {
				sqlUpdateLine = "Update M_InOutLine r set (Amount, TaxAmt, TaxBaseAmt) = " +
						"( Select l.Qty * l.Price, 0, 0 From M_InOutLine l Where r.M_InOutLine_ID = l.M_InOutLine_ID) "+
						"Where M_InOut_ID = ?";
				params = new Object [] {getM_InOut_ID()};
			}
			
			DB.executeUpdateEx(sqlUpdateLine, params, get_TrxName());
			
			//Update Header
			
			String sql = "UPDATE M_InOut o"
					+ " SET (Amount, AmountConvert, TaxAmt, TaxBaseAmt) ="
					+ "(SELECT Sum(Amount), Sum(Amount), Sum(TaxAmt), Sum(TaxBaseAmt)"
					+ " FROM M_InOutLine ol WHERE ol.M_InOut_ID=o.M_InOut_ID) "
					+ "WHERE M_InOut_ID=?";
			DB.executeUpdateEx(sql, new Object[] {getM_InOut_ID()}, get_TrxName());
		}
		
		if (getC_Order_ID() > 0) {
			MOrder order = MOrder.get(getCtx(), getC_Order_ID());
			createOrderLine(order, getC_Tax_ID(), getCtx(), getM_InOut_ID(), get_TrxName()); 
		}
		
		return true;
	}	

	String action;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		this.action = action;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, true);
		return engine.processIt (action, getDocStatus());
	}

	/**	Process Message 			*/
	protected String		m_processMsg = null;


	public String completeIt()
	{
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Drafted;
		}
		
		MInOutLine[] lines = getLines(true);
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Drafted;
		}
		
		if (getC_Order_ID() > 0) {
			updateQtyOrderDelivered(true);
		}
		
		//Insert vào bảng MStorage phục vụ tính giá xuất khi CO
		MStorage.insertOrUpdateStorage(true, getCtx(), getM_InOut_ID(), getC_DocType_ID(), get_TrxName());
		
		setProcessed(true);
		if (DocAction.STATUS_Inprogress.equals(action))
			return DocAction.STATUS_Inprogress;
		return DocAction.STATUS_Completed;
	}	//	completeIt


	/**
	 * 	Re-activate
	 * 	@return false
	 */
	public boolean reActivateIt()
	{
		
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return false;
		}
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!super.reActivate())
			return false;
		
		//Xóa dữ liệu bảng MStorage phục vụ tính giá xuất khi RA
		MStorage.insertOrUpdateStorage(false, getCtx(), getM_InOut_ID(), getC_DocType_ID(), get_TrxName());
		
		if (getC_Order_ID() > 0) {
			updateQtyOrderDelivered(false);
		}
		
		setProcessed(false);
		
		return true;
	}	//	reActivateIt


	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		sb.append(":")
			.append(" (#").append(getLines(false).length).append(")");
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	
	public String getProcessMsg()
	{
		if (m_processMsg != null) {
			setProcessed(false);
			
		}
		return m_processMsg;
	}	//	getProcessMsg

	
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}
	

	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds);
	}	//	isComplete


	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}	
