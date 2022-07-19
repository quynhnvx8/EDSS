
package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


public class MWarehouse extends X_M_Warehouse
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3065089372599460372L;

	
	public static MWarehouse get (Properties ctx, int M_Warehouse_ID)
	{
		return get(ctx, M_Warehouse_ID, null);
	}
	

	public static MWarehouse get (Properties ctx, int M_Warehouse_ID, String trxName)
	{
		String key = String.valueOf(M_Warehouse_ID);
		MWarehouse retValue = (MWarehouse)s_cache.get(key);
		if (retValue != null)
			return retValue;
		//
		final String whereClause = "M_Warehouse_ID=?";
		retValue = new Query(ctx,I_M_Warehouse.Table_Name,whereClause,trxName)
		.setParameters(M_Warehouse_ID)
		.firstOnly();
		s_cache.put (key, retValue);
		return retValue;
	}	//	get


	public static MWarehouse getDefault (Properties ctx, String trxName)
	{
		int AD_Org_ID = Env.getAD_Org_ID(ctx);
		String key = "Default_" + AD_Org_ID;
		
		MWarehouse retValue = (MWarehouse) s_cache.get(key);
		if (retValue != null) {
			return retValue;
		}
		String whereClause = " IsDefault = 'Y' And AD_Org_ID = ?";
		retValue = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(AD_Org_ID)
				.firstOnly();
		if (retValue == null) {
			whereClause = " IsDefault = 'Y' ";
			retValue = new Query(ctx, Table_Name, whereClause, trxName, true)
					.firstOnly();
		}
		s_cache.put(key, retValue);
		return retValue;
	}
	
	public static MWarehouse[] getForOrg (Properties ctx, int AD_Org_ID)
	{
		final String whereClause = "AD_Org_ID=?";
		List<MWarehouse> list = new Query(ctx, Table_Name, whereClause, null)
										.setParameters(AD_Org_ID)
										.setOnlyActiveRecords(true)
										.setOrderBy(COLUMNNAME_M_Warehouse_ID)
										.list();
		return list.toArray(new MWarehouse[list.size()]);
	}	//	get
	

	public static MWarehouse getDefaultForOrg (Properties ctx, int AD_Org_ID)
	{
		final String whereClause = "IsDefault=? AND AD_Org_ID=?";
		MWarehouse list = new Query(ctx, Table_Name, whereClause, null)
										.setParameters("Y", AD_Org_ID)
										.setOnlyActiveRecords(true)
										.setOrderBy(COLUMNNAME_M_Warehouse_ID)
										.first();
		return list;
	}	//	get
	
	/**	Cache					*/
	protected static CCache<String,MWarehouse> s_cache = new CCache<String,MWarehouse>(Table_Name, 50 );	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_Warehouse_ID id
	 *	@param trxName transaction
	 */
	public MWarehouse (Properties ctx, int M_Warehouse_ID, String trxName)
	{
		super(ctx, M_Warehouse_ID, trxName);
		
	}	//	MWarehouse

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWarehouse (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWarehouse

	/**
	 * 	Organization Constructor
	 *	@param org parent
	 */
	public MWarehouse (MOrg org)
	{
		this (org.getCtx(), 0, org.get_TrxName());
		setValue (org.getValue());
		setName (org.getName());
		
	}	//	MWarehouse

	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_Value, getValue());
		dataColumn.put(COLUMNNAME_AD_Client_ID, Env.getAD_Client_ID(getCtx()));
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_M_Warehouse_ID, getM_Warehouse_ID(), get_TrxName());
		dataColumn = null;
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + getValue());
			return false;
		}
		
		if (isDefault()) {
			List<MWarehouse> relValue = new Query(getCtx(), Table_Name, "AD_Org_ID = ? And IsDefault = 'Y' And M_Warehouse_ID != ? and M_Warehouse_ID > 0", get_TrxName())
					.setParameters(getAD_Org_ID(), getM_Warehouse_ID())
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", "Warehouse default exists !");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 	After Save	
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		
		
		return success;
	}	//	afterSave

	
	public static int createWarehouse(int AD_Org_ID, int AD_Client_ID, String Code, String Name, boolean isInsert) {
		Object [] params = {Code, Name};
		if (isInsert)
		{	
			MWarehouse wh = new MWarehouse(Env.getCtx(), 0, null);
			wh.setValue(Code);
			wh.setName(Name);
			wh.setAD_Client_ID(AD_Client_ID);
			wh.setAD_Org_ID(AD_Org_ID);
			wh.setIsAutoCreate(true);
			wh.setIsDefault(true);
			wh.save();
				
			return wh.getM_Warehouse_ID();
		} else {
			
			String sql = "DELETE FROM M_Warehouse WHERE IsAutoCreate = 'Y' AND Value = ?";
			params = new Object [] {Code};
			int no = DB.executeUpdate(sql, params, true, null);
			return no > 0 ? 0 : -1;
		}
	}
	
}	//	MWarehouse
