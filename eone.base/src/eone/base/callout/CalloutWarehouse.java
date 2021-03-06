
package eone.base.callout;

import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.MWarehouse;
import eone.util.Env;

//eone.base.model.CalloutWarehouse.setWarehouseDefault
public class CalloutWarehouse extends CalloutEngine
{

	public String setWarehouseDefault (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		int p_AD_Org_ID = Env.getAD_Org_ID(ctx);
		MWarehouse relValue = MWarehouse.getDefaultForOrg(ctx, p_AD_Org_ID);
		if (relValue != null) {
			mTab.setValue("M_Warehouse_ID", relValue.getM_Warehouse_ID());
			mTab.setValue("M_Warehouse_Dr_ID", relValue.getM_Warehouse_ID());
			mTab.setValue("M_Warehouse_Cr_ID", relValue.getM_Warehouse_ID());
		}
		return "";
	}   //  product

}	//	CalloutProduction
