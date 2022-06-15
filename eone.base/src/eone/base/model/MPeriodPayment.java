package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.CCache;

public class MPeriodPayment extends X_C_PeriodPayment
{
	
	private static final long serialVersionUID = -1247516669047870893L;
	
	static private CCache<String,MPeriodPayment> s_cache = new CCache<String,MPeriodPayment>(Table_Name, 30, 60);

	public MPeriodPayment (Properties ctx, int C_PeriodPayment, String trxName)
	{
		super (ctx, C_PeriodPayment, trxName);
		
	}	//	MAssetUse

	
	public static MPeriodPayment get (Properties ctx, int C_PeriodPayment)
	{
		return get(ctx,C_PeriodPayment,null);
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		return true;
	}

	

	public static MPeriodPayment get (Properties ctx, int C_PeriodPayment, String trxName)
	{
		final String whereClause = "C_PeriodPayment_ID=? ";
		MPeriodPayment retValue = new Query(ctx,Table_Name,whereClause,trxName)
		.setParameters(C_PeriodPayment)
		.firstOnly();
		return retValue;
	}
	
	public static MPeriodPayment get (Properties ctx, String typePeriod, String trxName)
	{
		if (s_cache.containsKey(typePeriod))
			return s_cache.get(typePeriod);
		final String whereClause = "TYPEPERIOD = ? ";
		MPeriodPayment retValue = new Query(ctx,Table_Name,whereClause,trxName)
		.setParameters(typePeriod)
		.firstOnly();
		s_cache.put(typePeriod, retValue);
		return retValue;
	}
	
	
	public MPeriodPayment (Properties ctx, ResultSet rs, String trxName)
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
