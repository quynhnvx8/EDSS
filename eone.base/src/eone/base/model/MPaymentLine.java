package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;

public class MPaymentLine extends X_C_PaymentLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MPaymentLine (Properties ctx, int C_PaymentLine_ID, String trxName)
	{
		super (ctx, C_PaymentLine_ID, trxName);
		
	}	


	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		updateHeader(true);
		return true;
	}


	@Override
	protected boolean beforeDelete() {
		updateHeader(false);
		return true;
	}


	private void updateHeader(boolean isAdd) {
		String sql = "SELECT SUM(Amount) FROM C_PaymentLine WHERE C_Payment_ID = ? AND PerformStatus != 'NO'";
		List<Object> params = new ArrayList<Object>();
		params.add(getC_Payment_ID());
		BigDecimal amt = DB.getSQLValueBD(get_TrxName(), sql, params);
		if (amt == null) {
			amt = Env.ZERO;
		}
		if (isAdd) {
			if ("NO".equals(getPerformStatus()))
				amt = amt.compareTo(Env.ZERO) > 0 ? amt.subtract(getAmount()) : Env.ZERO;				
			else
				amt = amt.add(getAmount());
		} else {
			amt = amt.compareTo(Env.ZERO) > 0 ? amt.subtract(getAmount()) : Env.ZERO;
		}
		String sqlUpdate = "UPDATE C_Payment set Amount = ? WHERE C_Payment_ID = ?";
		DB.executeUpdate(sqlUpdate, new Object [] {amt, getC_Payment_ID()}, true, get_TrxName() );
	}
	
	public MPaymentLine (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetUse


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		if (!success)
			return success;
		
		
		
		return success;
		 
		
	}	//	afterSave



}
