
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


public class MInOutLine extends X_M_InOutLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8630611882798722864L;

	
	public MInOutLine (Properties ctx, int M_InOutLine_ID, String trxName)
	{
		super (ctx, M_InOutLine_ID, trxName);
		getParent();
		
	}	//	MInOutLine


	public MInOutLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInOutLine

	/**
	 *  Parent Constructor
	 *  @param inout parent
	 */
	public MInOutLine (MInOut inout)
	{
		this (inout.getCtx(), 0, inout.get_TrxName());
		setM_InOut_ID (inout.getM_InOut_ID());
		m_parent = inout;
	}	//	MInOutLine

	
	private MInOut			m_parent = null;

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MInOut getParent()
	{
		if (m_parent == null)
			m_parent = new MInOut (getCtx(), getM_InOut_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	protected boolean beforeSave (boolean newRecord)
	{
		MInOut inout = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());
		MDocType mDocyType = MDocType.get(getCtx(), inout.getC_DocType_ID());
		boolean check = mDocyType.getDocType().equals(MDocType.DOCTYPE_Output);
		//Kiem tra so luong ton kho khi lam lenh xuat. 
		//Bat buoc check tu bang M_Storage vi cau truc logic cua he thong.
		
		//Neu la xuat va co cau hinh check so luong ton kho trong bang M_Storage (Voi dieu kien bang nay du lieu phai input day du)
		//De input day du can kiem tra nghiep vu: CO, RA va UpdateOpenBalanceWarehouse.java
		if (check && Env.checkRemainQty()) {
			BigDecimal qtyRemain = MStorage.getQtyRemain(getM_Product_ID(), getM_Warehouse_Cr_ID(), inout.getDateAcct());
			if (qtyRemain.compareTo(getQty()) < 0)
			{
				log.saveError("Error!", "Số lượng không đủ, số lượng còn lại là " + qtyRemain);
				return false;
			}			
		}
		log.fine("");
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "M_InOutLine"));
			return false;
		}
		
		if (getQty().compareTo(Env.ZERO) < 0) {
			log.saveError("Error!", "Quantity must be than zero!");
			return false;
		}
		
		if (getM_Product_ID() > 0 && getM_Warehouse_Dr_ID() > 0) {
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			if (product.isManufactured() && !checkSetupBOM(getM_Product_ID(), getM_Warehouse_Dr_ID())) {
				log.saveError("Error!", "Sản phẩm và phân xương/Công đoạn không trùng với thiết lập định mức!");
				return false;
			}
		}
		
		if (getM_Product_Cr_ID() > 0 && getM_Warehouse_Cr_ID() > 0) {
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			if (product.isManufactured() && !checkSetupBOM(getM_Product_Cr_ID(), getM_Warehouse_Cr_ID())) {
				log.saveError("Error!", "Sản phẩm và phân xương/Công đoạn không trùng với thiết lập định mức!");
				return false;
			}
		}
		
		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+1 FROM M_InOutLine WHERE M_InOut_ID=?";
			int ii = DB.getSQLValueEx (get_TrxName(), sql, getM_InOut_ID());
			setLine (ii);
		}
		//	UOM
		if (getC_UOM_ID() == 0)
			setC_UOM_ID (Env.getContextAsInt(getCtx(), "#C_UOM_ID"));
		
		return true;
	}	//	beforeSave

	protected boolean afterSave (boolean newRecord, boolean success)
	{
		String sql = "UPDATE M_InOut o"
				+ " SET (Amount, AmountConvert, TaxAmt, TaxBaseAmt) ="
				+ "(SELECT Sum(Amount), Sum(AmountConvert), Sum(TaxAmt), Sum(TaxBaseAmt)"
				+ " FROM M_InOutLine ol WHERE ol.M_InOut_ID=o.M_InOut_ID) "
				+ "WHERE M_InOut_ID=?";
		DB.executeUpdateEx(sql, new Object[] {getM_InOut_ID()}, get_TrxName());
		
		return true;
	}	
	@Override
	protected boolean afterDelete(boolean success) {
		String sql = "UPDATE M_InOut o"
				+ " SET (Amount, AmountConvert, TaxAmt, TaxBaseAmt) ="
				+ "(SELECT Sum(Amount), Sum(AmountConvert), Sum(TaxAmt), Sum(TaxBaseAmt)"
				+ " FROM M_InOutLine ol WHERE ol.M_InOut_ID=o.M_InOut_ID) "
				+ "WHERE M_InOut_ID=?";
		int no = DB.executeUpdateEx(sql, new Object[] {getM_InOut_ID()}, get_TrxName());
		if (no < 0)
			return false;
		return true;
	}

	
	private boolean checkSetupBOM(int M_Product_ID, int M_Warehouse_ID) {
		String sql = "SELECT COUNT(1) FROM M_BOM b INNER JOIN M_Product p ON b.M_Product_ID = p.M_Product_ID"
				+ " WHERE b.M_Product_ID = ? AND b.M_Warehouse_ID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(M_Product_ID);
		params.add(M_Warehouse_ID);
		int count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count > 0)
			return true;
		return false;
	}
	/**
	 * 	Before Delete
	 *	@return true if drafted
	 */
	protected boolean beforeDelete ()
	{
		if (! getParent().getDocStatus().equals(MInOut.DOCSTATUS_Drafted)) {
			log.saveError("Error", Msg.getMsg(getCtx(), "CannotDelete"));
			return false;
		}
		
		return true;
	}	//	beforeDelete

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MInOutLine[").append (get_ID())
			.append(",M_Product_ID=").append(getM_Product_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MInOutLine
