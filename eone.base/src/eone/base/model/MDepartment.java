
package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.CCache;


public class MDepartment extends X_AD_Department
{
	
	@Override
	protected boolean beforeDelete() {
		MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Department_ID(), "AD_Department", "ORG", false);
		return super.beforeDelete();
	}

	private static final long serialVersionUID = -5604686137606338725L;


	
	public static MDepartment get (Properties ctx, int AD_Department_ID)
	{
		MDepartment retValue = s_cache.get (AD_Department_ID);
		if (retValue != null)
			return retValue;
		final String whereClause = "AD_Department_ID=?";
		retValue = new Query(ctx,I_AD_Department.Table_Name,whereClause,null)
		.setParameters(AD_Department_ID)
		.firstOnly();
		s_cache.put (AD_Department_ID, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer,MDepartment>	s_cache	= new CCache<Integer,MDepartment>(Table_Name, 50);
	
	
	
	public MDepartment (Properties ctx, int AD_Department_ID, String trxName)
	{
		super(ctx, AD_Department_ID, trxName);
		
	}	

	
	public MDepartment (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	

	public MDepartment (MClient client, String value, String name)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setValue (value);
		setName (name);
	}	



	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if (newRecord || is_ValueChanged("Value") || is_ValueChanged("Name")) {
			List<MDepartment> relValue = new Query(getCtx(), Table_Name, "AD_Department_ID != ? And (Value = ? Or Name = ?) And AD_Client_ID = ? And IsActive = 'Y' And IsSummary = 'N'", get_TrxName())
					.setParameters(getAD_Department_ID(), getValue(), getName(), getAD_Client_ID())
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", "Value Or Name exists");
				return false;
			}

		}
		
		
		return true;
	}


	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord || !isSummary() || !isActive() || is_ValueChanged("Name") || is_ValueChanged("Value")) {
			if ( isCreateBPartner()) {
				boolean ok = MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Department_ID(), "AD_Department", "DEP", true);
				if (!ok) {
					log.saveError("Error", "Create BPartner false!");
					return false;
				}			
			}
		} 
		
		//Lúc update
		if (!newRecord) {
			if((!isCreateBPartner() && is_ValueChanged("IsCreateBPartner"))	|| 
					(isSummary() && is_ValueChanged("IsSummary")) ||
					(!isActive() && is_ValueChanged("IsActive"))) {
				MBPartner.createBPartner(getAD_Org_ID(), getAD_Client_ID(), getValue(), getName(), getAD_Department_ID(), "AD_Department", "DEP", false);
			}
		}
		
		
		return true;
	}	//	afterSave
	
	
	protected boolean afterDelete (boolean success)
	{
		return success;
	}	//	afterDelete


	
	
}	
