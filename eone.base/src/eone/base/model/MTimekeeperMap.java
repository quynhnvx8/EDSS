package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eone.util.CCache;
import eone.util.Env;
import eone.util.Msg;

public class MTimekeeperMap extends X_HR_TimekeeperMap
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MTimekeeperMap (Properties ctx, int HR_TimekeeperMap_ID, String trxName)
	{
		super (ctx, HR_TimekeeperMap_ID, trxName);
		
	}	//	MAssetUse

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(getPercentage().compareTo(Env.ZERO) < 0) {
			log.saveError("Error", "Percentage must be great than zero!");
			return false;
		}
		
		if (getValueNumber().compareTo(Env.ZERO) < 0 || getValueNumber().compareTo(Env.ONE) > 0) {
			log.saveError("Error", "ValueNumber must be between 0 and 1 !");
			return false;
		}
		
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_Name, getName());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_HR_TimekeeperMap_ID, getHR_TimekeeperMap_ID(), get_TrxName());
		dataColumn = null;
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + getName());
			return false;
		}
		
		return true;
	}


	public MTimekeeperMap (Properties ctx, ResultSet rs, String trxName)
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

	private static CCache<String,MTimekeeperMap> s_cache = new CCache<String,MTimekeeperMap>(Table_Name, 5);

	public static Map<String, MTimekeeperMap> getAllItems (Properties ctx, String trxName)
	{
		Map<String, MTimekeeperMap> list = new HashMap<String, MTimekeeperMap>();
		String whereClause = "IsActive = 'Y'";
		
		List<MTimekeeperMap> retValue = new Query(ctx, Table_Name,whereClause,trxName)
				.list();
		for(int i = 0; i < retValue.size(); i++) {
			list.put(retValue.get(i).getName(), retValue.get(i));
		}
		return list;
	}
	
	public static MTimekeeperMap get (Properties ctx, String name, String trxName)
	{
		if(s_cache.containsKey(name))
			return s_cache.get(name);
		
		final String whereClause = "Name=? And AD_Client_ID = ? And IsActive = 'Y'";
		List<MTimekeeperMap> retValue = new Query(ctx, Table_Name,whereClause,trxName)
		.setParameters(name, Env.getAD_Client_ID(ctx))
		.list();
		for(int i = 0; i < retValue.size(); i++) {
			s_cache.put(retValue.get(i).getName(), retValue.get(i));
		}
		return s_cache.get(name);
	}
	
	
	
	//Làm ngày thường
	public boolean isWorkingDayNormal() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_HaveASalary))
			return true;
		return false;
	}
	
	
	
	/*
	 * Ham kiem tra nghi ốm hoặc chăm người ốm
	 */
	public boolean isInsurancePaidSalary() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_InsuranceSalary))
			return true;
		return false;
	}
	
	/*
	 * Ham kiem tra nghi thai san
	 */
	public boolean isMeternitySalary() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_MeternityTime))
			return true;
		return false;
	}
	
	/*
	 * Ham kiem tra di lam them ngay thuong
	 */
	public boolean isOverWorkingWeekday() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_OvertimeOnWeekdays))
			return true;
		return false;
	}
	
	/*
	 * Ham kiem tra di lam them ngay thuong
	 */
	public boolean isOverWorkingEvening() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_Overnight))
			return true;
		return false;
	}
	
	/*
	 * Ham kiem tra di lam them ngay thuong
	 */
	public boolean isOverWorkingNormal() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_OvertimeOnNormal))
			return true;
		return false;
	}
	
	/*
	 * Ham kiem tra di lam them ngay le
	 */
	public boolean isOverWorkingHoliday() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_OvertimeOnHoliday))
			return true;
		return false;
	}
	
	public boolean isWorkdayProduct() {
		if (getTypeTimeKeeper().equalsIgnoreCase(TYPETIMEKEEPER_WorkdayProduct))
			return true;
		return false;
	}
}
