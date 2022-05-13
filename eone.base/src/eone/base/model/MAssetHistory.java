package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import eone.util.CCache;

public class MAssetHistory extends X_A_Asset_History
{
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		//updateHeader();
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return super.beforeDelete();
	}

	
	@Override
	protected boolean afterDelete(boolean success) {
		//updateHeader();
		return super.afterDelete(success);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1371478760221357780L;


	
	private static CCache<String,MAssetHistory> s_cache = new CCache<String,MAssetHistory>(Table_Name, 10, 0);
	
	
	public static MAssetHistory get (Properties ctx, int A_Asset_History_ID)
	{
		if (A_Asset_History_ID <= 0)
			return null;
		MAssetHistory o = s_cache.get(String.valueOf(A_Asset_History_ID));
		if (o != null)
			return o;
		o = new MAssetHistory(ctx, A_Asset_History_ID, null);
		if (o.get_ID() > 0) {
			s_cache.put(String.valueOf(A_Asset_History_ID), o);
			return o;
		}
		return null;
	}
	
	public static MAssetHistory get (Properties ctx, int A_Asset_ID, Timestamp date)
	{
		if (A_Asset_ID <= 0 || date == null)
			return null;
		
		String whereClause = " A_Asset_ID = ? And ChangeDate = ? ";
		MAssetHistory o = new Query(ctx, Table_Name, whereClause, null)
				.setParameters(A_Asset_ID, date)
				.setApplyAccessFilter(true)
				.firstOnly();
		
		return o;
	}

	public static MAssetHistory get (Properties ctx, Object id)
	{
		if (id == null)
			return null;
		return get(ctx, ((Number)id).intValue());
	}
	
	/** Standard Constructor */
	public MAssetHistory (Properties ctx, int A_Asset_History_ID, String trxName)
	{
		super (ctx, A_Asset_History_ID, trxName);
	}
	
	/** Load Constructor */
	public MAssetHistory (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}
	


}	// class MAssetType
