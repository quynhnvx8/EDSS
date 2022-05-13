package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;


public class MProductGroup extends X_M_ProductGroup
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 285926961771269935L;

	
	public static MProductGroup get (Properties ctx, int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID <= 0)
		{
			return null;
		}
		Integer key = Integer.valueOf(M_ProductGroup_ID);
		MProductGroup retValue = (MProductGroup) s_cache.get (key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MProductGroup (ctx, M_ProductGroup_ID, null);
		if (retValue.get_ID () != 0)
		{
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get
	
	public static Object [] get (Properties ctx, String code)
	{
		if (s_cacheCode.containsKey(code))
			return s_cacheCode.get(code);
		String sql = "Select M_Product_ID, C_UOM_ID From M_ProductGroup Where Value = ?";
		Object [] data = DB.getObjectValuesEx(null, sql, 2, code);
		if (data != null)
		{
			s_cacheCode.put(code, data);
			return data;
		}
		return null;
	}

	
	public static MProductGroup[] get (Properties ctx, String whereClause, String trxName)
	{
		List<MProductGroup> list = new Query(ctx, Table_Name, whereClause, trxName)
								.setClient_ID()
								.list();
		return list.toArray(new MProductGroup[list.size()]);
	}	//	get


	
	
	
	
	/**	Cache						*/
	private static CCache<Integer,MProductGroup> s_cache	= new CCache<Integer,MProductGroup>(Table_Name, 40, 5);	//	5 minutes
	
	private static CCache<String,Object []> s_cacheCode	= new CCache<String,Object []>(Table_Name, 40, 5);
	
	public MProductGroup (Properties ctx, int M_ProductGroup_ID, String trxName)
	{
		super (ctx, M_ProductGroup_ID, trxName);		
	}	

	/**
	 * 	Load constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProductGroup (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProduct


	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MProduct[");
		sb.append(get_ID()).append("-").append(getValue())
			.append("]");
		return sb.toString();
	}	//	toString

	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	//	beforeSave


	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		
		return success;
	}	//	afterSave

	@Override
	protected boolean beforeDelete ()
	{
		
		return true; 
	}	//	beforeDelete
	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(X_AD_Tree.TREETYPE_CustomTable);
		return success;
	}	//	afterDelete
	
	
	
}	//	MProductGroup
