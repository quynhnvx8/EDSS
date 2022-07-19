
package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;

/**
 * 	BOM Product/Component Model
 *	
 */
public class MFactoriesLine extends X_M_FactoriesLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3431041011059529621L;

	
	public MFactoriesLine (Properties ctx, int M_FactoriesLine_ID, String trxName)
	{
		super (ctx, M_FactoriesLine_ID, trxName);
		
	}	//	MBOMProduct

	
	public MFactoriesLine (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MBOMProduct

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		
		String sql = "SELECT COUNT(1) FROM M_BOM WHERE M_Product_ID = ? AND M_Warehouse_ID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(getM_Product_ID());
		params.add(getM_Warehouse_ID());
		int count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count <= 0) {
			log.saveError("Error", "Sản phẩm và phân xưởng không khớp với thiết lập định mức");
			return false;
		}
		
		sql = "SELECT COUNT(1) FROM M_FactoriesLine WHERE M_Factories_ID = ? AND OrderNo = ? AND M_FactoriesLine_ID != ?";
		params = new ArrayList<Object>();
		params.add(getM_Factories_ID());
		params.add(getOrderNo());
		params.add(getM_FactoriesLine_ID());
		count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count > 0) {
			log.saveError("Error", "Số thứ tự không được trùng nhau");
			return false;
		}
		
		return true;
	}	//	beforeSave
	
	
}	//	MBOMProduct
