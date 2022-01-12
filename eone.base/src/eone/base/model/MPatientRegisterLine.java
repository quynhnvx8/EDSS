package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

public class MPatientRegisterLine extends X_HM_PatientRegisterLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MPatientRegisterLine (Properties ctx, int HM_PatientRegisterLine_ID, String trxName)
	{
		super (ctx, HM_PatientRegisterLine_ID, trxName);
		
	}	//	MAssetUse

	
	public static MPatientRegisterLine get (Properties ctx, int HM_PatientRegisterLine_ID)
	{
		return get(ctx,HM_PatientRegisterLine_ID,null);
	}
	
	

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (is_ValueChanged("ResultCLS")) {
			setHR_Employee_ID(Env.getContextAsInt(getCtx(), "#HR_Employee_ID"));
		}
		return true;
	}

	

	public static MPatientRegisterLine get (Properties ctx, int HM_PatientRegisterLine_ID, String trxName)
	{
		final String whereClause = "HM_PatientRegisterLine_ID=? AND AD_Client_ID=?";
		MPatientRegisterLine retValue = new Query(ctx,I_HM_PatientRegisterLine.Table_Name,whereClause,trxName)
		.setParameters(HM_PatientRegisterLine_ID,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}
	
	
	public static boolean saveLine() {
		
		return true;
	}
	
	

	public MPatientRegisterLine (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetUse


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info ("afterSave");
		if (!success)
			return success;
		
		return success;
		 
		
	}	//	afterSave
	
}
