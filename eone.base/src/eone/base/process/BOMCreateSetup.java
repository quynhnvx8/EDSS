
package eone.base.process;

import java.util.List;

import eone.base.model.MBOMProduct;
import eone.base.model.MProductionInput;
import eone.base.model.MProductionOutput;
import eone.base.model.MWarehouse;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.base.model.X_M_BOMProduct;

/**
 * 	Validate BOM
 *	
 *  @author Client
 */
public class BOMCreateSetup extends SvrProcess
{
	
	protected void prepare ()
	{
		
	}	//	prepare

	/**
	 * 	Process
	 *	@return Info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		MProductionOutput out = MProductionOutput.get(getCtx(), getRecord_ID(), get_TrxName());
		String whereClause = " M_BOM_ID IN (SELECT M_BOM_ID FROM M_BOM WHERE M_Product_ID = ?)";
		List<MBOMProduct> data = new Query(getCtx(), X_M_BOMProduct.Table_Name, whereClause, get_TrxName(), true)
				.setParameters(out.getM_Product_ID())
				.list();
		MProductionInput input = null;
		for (int i = 0; i < data.size(); i++) {
			input = new MProductionInput(getCtx(), 0, get_TrxName());
			PO.copyValues(data.get(i), input);
			input.setM_Product_ID(data.get(i).getM_Product_ID());
			input.setQtyOne(data.get(i).getBOMQty());
			input.setQty(input.getQtyOne().multiply(out.getQtyBook()));
			input.setM_ProductionOutput_ID(out.getM_ProductionOutput_ID());
			input.setM_Warehouse_ID(MWarehouse.getDefaultForOrg(getCtx(), data.get(i).getAD_Org_ID()).getM_Warehouse_ID());
			input.save();
		}
		return "Success!";
	}	//	doIt
	
}	//	BOMCreateSetup
