package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class MRegister extends X_AD_Register
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MRegister (Properties ctx, int AD_Register_ID, String trxName)
	{
		super (ctx, AD_Register_ID, trxName);
		
	}	//	MAssetUse

	
	public static MRegister get (Properties ctx, int AD_Register_ID, String trxName)
	{
		return (MRegister) MTable.get(ctx, Table_ID).getPO(AD_Register_ID, trxName);
	}
	
	private static CCache<Integer, MRegister> s_cache = new CCache<Integer, MRegister>(Table_Name, 5);
	
	public static MRegister getAllDomain (Properties ctx, String trxName, int AD_Register_ID)
	{
		if (!s_cache.containsKey(AD_Register_ID))
		{
			final String whereClause = "";
			MRegister retValue = new Query(ctx,I_AD_Register.Table_Name,whereClause,trxName)
					.setParameters(AD_Register_ID)
					.setOrderBy(COLUMNNAME_AD_Register_ID)
					.first();
			
			s_cache.put(AD_Register_ID, retValue);
		}
		return s_cache.get(AD_Register_ID);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		dataColumn.put(COLUMNNAME_Domain, getDomain());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_Domain);
			return false;
		}
		
		dataColumn.clear();
		dataColumn.put(COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		dataColumn.put(COLUMNNAME_TaxID, getTaxID());
		check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_TaxID);
			return false;
		}
		
		return true;
	}


	public MRegister (Properties ctx, ResultSet rs, String trxName)
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
