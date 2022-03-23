
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


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
		return "";
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
