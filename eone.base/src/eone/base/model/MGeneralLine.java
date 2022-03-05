
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;


public class MGeneralLine extends X_C_GeneralLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8630611882798722864L;

	
	public MGeneralLine (Properties ctx, int C_GeneralLine_ID, String trxName)
	{
		super (ctx, C_GeneralLine_ID, trxName);
		getParent();
		
	}	//	MInOutLine


	public MGeneralLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInOutLine

	/**
	 *  Parent Constructor
	 *  @param inout parent
	 */
	public MGeneralLine (MGeneral parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg (parent);
		setC_General_ID (parent.getC_General_ID());
		m_parent = parent;
	}	//	MInOutLine

	/**	Product					*/
	private MProduct 		m_product = null;
	
	private MGeneral			m_parent = null;

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MGeneral getParent()
	{
		if (m_parent == null)
			m_parent = new MGeneral (getCtx(), getC_General_ID(), get_TrxName());
		return m_parent;
	}	//	getParent

	
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product = MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct

	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID (m_product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			setC_UOM_ID (0);
		}
	}	//	setProduct

	/**
	 * 	Set M_Product_ID
	 *	@param M_Product_ID product
	 *	@param setUOM also set UOM from product
	 */
	public void setM_Product_ID (int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID (M_Product_ID);
	}	//	setM_Product_ID

	/**
	 * 	Set Product and UOM
	 *	@param M_Product_ID product
	 *	@param C_UOM_ID uom
	 */
	public void setM_Product_ID (int M_Product_ID, int C_UOM_ID)
	{
		if (M_Product_ID != 0)
			super.setM_Product_ID (M_Product_ID);
		super.setC_UOM_ID(C_UOM_ID);
		m_product = null;
	}	//	setM_Product_ID

	
	
	
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	//	beforeSave

	protected boolean afterSave (boolean newRecord, boolean success)
	{
		String sql = "UPDATE C_General o"
				+ " SET (Amount, AmountConvert) ="
				+ "(SELECT Sum(Amount), Sum(AmountConvert)"
				+ " FROM C_GeneralLine ol WHERE ol.C_General_ID=o.C_General_ID) "
				+ "WHERE C_General_ID=?";
		DB.executeUpdateEx(sql, new Object[] {getC_General_ID()}, get_TrxName());
		
		return true;
	}	
	@Override
	protected boolean afterDelete(boolean success) {
		String sql = "UPDATE C_General o"
				+ " SET (Amount, AmountConvert) ="
				+ "(SELECT Sum(Amount), Sum(AmountConvert)"
				+ " FROM C_GeneralLine ol WHERE ol.C_General_ID=o.C_General_ID) "
				+ "WHERE C_General_ID=?";
		DB.executeUpdateEx(sql, new Object[] {getC_General_ID()}, get_TrxName());
		
		return true;
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

	/**
	 * 	Get Base value for Cost Distribution
	 *	@param CostDistribution cost Distribution
	 *	@return base number
	 */
	public BigDecimal getBase (String CostDistribution)
	{
		
		return Env.ZERO;
	}	//	getBase

	
}	//	MInOutLine
