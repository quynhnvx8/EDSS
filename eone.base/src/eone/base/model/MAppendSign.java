package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.CCache;
import org.compiere.util.DB;


public class MAppendSign extends X_AD_AppendSign
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1685512419870004665L;

	
	public static MAppendSign get (Properties ctx, int AD_Table_ID, int Record_ID)
	{
		return get(ctx, AD_Table_ID, Record_ID, (String)null);
	}
	
	static private CCache<String, List<X_AD_Signer>> s_cache = new CCache<String, List<X_AD_Signer>>(X_AD_Signer.Table_Name, 30, 60);
	static private CCache<String, MAppendSign> s_appendSign = new CCache<String,MAppendSign>(X_AD_AppendSign.Table_Name, 30, 60);
	
	public static MAppendSign get (Properties ctx, int AD_Table_ID, int Record_ID, String trxName)
	{
		if (s_appendSign.containsKey(AD_Table_ID + "_" + Record_ID))
			return s_appendSign.get(AD_Table_ID + "_" + Record_ID);
		final String whereClause = X_AD_AppendSign.COLUMNNAME_AD_Table_ID+"=? AND "+X_AD_AppendSign.COLUMNNAME_Record_ID+"=?";
		MAppendSign retValue = new Query(ctx,X_AD_AppendSign.Table_Name,whereClause, trxName)
		.setParameters(AD_Table_ID, Record_ID)
		.first();
		s_appendSign.put(AD_Table_ID + "_" + Record_ID, retValue);
		return retValue;
	}	//	get
	
	public static List<X_AD_Signer> getListSign (Properties ctx, int AD_Table_ID, int Record_ID, String trxName) {
		MAppendSign append = MAppendSign.get(ctx, AD_Table_ID, Record_ID);
		if (append != null) {
			if (s_cache.containsKey(AD_Table_ID + "_" + Record_ID))
				return s_cache.get(AD_Table_ID + "_" + Record_ID);
			List<X_AD_Signer> query = new Query(ctx, X_AD_Signer.Table_Name, " AD_AppendSign_ID = ?", trxName)
					.setParameters(append.getAD_AppendSign_ID())
					.setOrderBy("SeqNo")
					.list();
			if (query.size() > 0) {
				s_cache.put(AD_Table_ID + "_" + Record_ID, (ArrayList<X_AD_Signer>)query);
			}
			return query;
		}
		else 
			return null;
	}
	
	public MAppendSign(Properties ctx, int AD_AppendSign_ID, String trxName)
	{
		super (ctx, AD_AppendSign_ID, trxName);

	}	//	MAttachment

	
	public MAppendSign(Properties ctx, int AD_Table_ID, int Record_ID, String trxName)
	{
		this (ctx, MAppendSign.getID(AD_Table_ID, Record_ID) > 0 ? MAppendSign.getID(AD_Table_ID, Record_ID) : 0, trxName);
		if (get_ID() == 0) {
			setAD_Table_ID (AD_Table_ID);
			setRecord_ID (Record_ID);
		}
	}	//	MAttachment

	
	public MAppendSign(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttachment
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MAppendSign[");
		sb.append(getAD_AppendSign_ID());
		sb.append("]");
		return sb.toString();
	}	//	toString

	
	protected boolean beforeSave (boolean newRecord)
	{
		return true;		//	save in BinaryData
	}	//	beforeSave

	
	
	public static int getID(int Table_ID, int Record_ID) {
		String sql="SELECT AD_AppendSign_ID FROM AD_AppendSign WHERE AD_Table_ID=? AND Record_ID=?";
		int attachid = DB.getSQLValue(null, sql, Table_ID, Record_ID);
		return attachid;
	}

}	//	MAttachment
