
package eone.base.callout;

import java.math.BigDecimal;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.I_M_ProductionInput;
import eone.base.model.I_M_ProductionOutput;
import eone.base.model.MProductionOutput;

/**
 *	Production Callouts
 *	
 
 */
public class CalloutProduction extends CalloutEngine
{
	
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		
		return "";
	}   //  product

	//eone.base.callout.CalloutProduction.qtys
	public String qtys (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Object productionOutput = mTab.getValue(I_M_ProductionOutput.COLUMNNAME_M_ProductionOutput_ID);
		int M_ProductionOutput_ID = 0;
		if (productionOutput != null) {
			M_ProductionOutput_ID = Integer.parseInt(productionOutput.toString());
		}
		MProductionOutput out = MProductionOutput.get(ctx, M_ProductionOutput_ID, null);
		
		BigDecimal qtyOne = (BigDecimal)value;
		if (qtyOne != null)
			mTab.setValue(I_M_ProductionInput.COLUMNNAME_Qty, out.getQtyBook().multiply(qtyOne));
		
		return "";
	}
	
}	//	CalloutProduction
