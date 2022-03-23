package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;
import eone.util.Msg;

public class MOrg extends X_AD_Org
{
	private static final long serialVersionUID = -5604686137606338725L;


	public static MOrg[] getOfClient (PO po)
	{
		List<MOrg> list = new Query(po.getCtx(), Table_Name, "AD_Client_ID=?", null)
								.setOrderBy(COLUMNNAME_Value)
								.setOnlyActiveRecords(true)
								.setParameters(po.getAD_Client_ID())
								.list();
		for (MOrg org : list)
		{
			s_cache.put(org.get_ID(), org);
		}
		return list.toArray(new MOrg[list.size()]);
	}	//	getOfClient
	
	public static MOrg get (Properties ctx, int AD_Org_ID)
	{
		MOrg retValue = s_cache.get (AD_Org_ID);
		if (retValue != null)
			return retValue;
		retValue = new MOrg (ctx, AD_Org_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (AD_Org_ID, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer,MOrg>	s_cache	= new CCache<Integer,MOrg>(Table_Name, 50);
	
	
	public MOrg (Properties ctx, int AD_Org_ID, String trxName)
	{
		super(ctx, AD_Org_ID, trxName);
		if (AD_Org_ID == 0)
		{
			setIsSummary (false);
		}
	}	//	MOrg

	public MOrg (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrg

	/**
	 * 	Parent Constructor
	 *	@param client client
	 *	@param name name
	 */
	public MOrg (MClient client, String value, String name)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setAD_Client_ID (client.getAD_Client_ID());
		setValue (value);
		setName (name);
	}	//	MOrg

		
	
	
	/**
	 * 	After Save
	 *	@param newRecord new Record
	 *	@param success save success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			MRoleOrgAccess.createForOrg (this);
			//MRole  role = MRole.getDefault(getCtx(), true);	//	reload
			//role.set_TrxName(get_TrxName());
			//role.loadAccess(true); // reload org access within transaction
		}
		
		if (newRecord || is_ValueChanged("Value") || is_ValueChanged("Name") || isCreateBPartner()) {
			if (!isSummary()) {
				boolean ok = MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Org_ID(), "AD_Org", "ORG", true);
				if (!ok)
				{
					log.saveError("Error!", "Create BPartner false!");
					return false;
				}
			}
		}
		
		if (
				(isSummary() && is_ValueChanged("IsSummary")) || 
				(!isCreateBPartner() && is_ValueChanged("IsCreateBPartner")) ||
				(!isActive() && is_ValueChanged("IsActive"))
			)
		{
			MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Org_ID(), "AD_Org", "ORG", false);
		}
		
		if (is_ValueChanged("IsCreateWarehouse")) {
			int m_warehouse_id = 0;
			
			if (isCreateWarehouse()) {
				m_warehouse_id = MWarehouse.createWarehouse(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), true);
			} else {
				MWarehouse.createWarehouse(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), false);
			}
			
			String sqlUpdate = "UPDATE AD_Org Set M_Warehouse_ID = ?";
			DB.executeUpdate(sqlUpdate, m_warehouse_id, get_TrxName());
		}
		
		//this.save();
		
		return true;
	}	//	afterSave
	
	@Override
	protected boolean beforeDelete() {
		MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Org_ID(), "AD_Org", "ORG", false);
		return super.beforeDelete();
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (newRecord || is_ValueChanged("Value") || isActive() || is_ValueChanged("Name")) {
			List<MOrg> relValue = new Query(getCtx(), Table_Name, "AD_Org_ID != ? And (Value = ? Or Name = ?) And AD_Client_ID = ? And IsActive = 'Y'", get_TrxName())
					.setParameters(getAD_Org_ID(), getValue(), getName(), getAD_Client_ID())
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", Msg.getMsg(getCtx(), "ValueOrNameExists"));//ValueExists, NameExists
				return false;
			}

		}
		return true;
	}

	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(MTree.TREETYPE_CustomTable);
		return success;
	}	//	afterDelete


}	//	MOrg
