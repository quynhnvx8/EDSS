package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;


public class MProductionOutput extends X_M_ProductionOutput {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720901152312853611L;

	protected MProduction productionParent;

	private static CCache<Integer, MProductionOutput> s_cache = new CCache<Integer, MProductionOutput>(Table_Name, 60);

	public static MProductionOutput get(Properties ctx, int M_ProductionOutput_ID, String trxName) {
		if (s_cache.containsKey(M_ProductionOutput_ID))
			return s_cache.get(M_ProductionOutput_ID);
		MProductionOutput retValue = new Query(ctx, Table_Name, "IsActive = 'Y'", trxName, true)
				.firstOnly();
		s_cache.put(M_ProductionOutput_ID, retValue);
		return s_cache.get(M_ProductionOutput_ID);
	}
	
	public MProductionOutput (Properties ctx, int M_ProductionOutput_ID, String trxName)
	{
		super (ctx, M_ProductionOutput_ID, trxName);
	}	// MProductionLine
	
	public MProductionOutput (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductionLine
	
	/**
	 * Parent Constructor
	 * @param plan
	 */
	public MProductionOutput( MProduction header ) {
		super( header.getCtx(), 0, header.get_TrxName() );
		setM_Production_ID( header.get_ID());
		setAD_Client_ID(header.getAD_Client_ID());
		setAD_Org_ID(header.getAD_Org_ID());
		productionParent = header;
	}
	
	public MProductionOutput( MProductionPlan header ) {
		super( header.getCtx(), 0, header.get_TrxName() );
		setM_ProductionPlan_ID( header.get_ID());
		setAD_Client_ID(header.getAD_Client_ID());
		setAD_Org_ID(header.getAD_Org_ID());
	}
	

	

	public String toString() {
		if ( getM_Product_ID() == 0 )
			return ("No product defined for production line ");
		MProduct product = new MProduct(getCtx(),getM_Product_ID(), get_TrxName());
		return ( "Production Out:" + product.getValue() + " -- " + getQtyBook());
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		if (productionParent == null && getM_Production_ID() > 0)
			productionParent = new MProduction(getCtx(), getM_Production_ID(), get_TrxName());
		if (newRecord || is_ValueChanged(COLUMNNAME_M_Warehouse_ID)) {
			String sql = "SELECT COUNT(1) FROM M_ProductionOutput WHERE M_Production_ID = ? AND M_Warehouse_ID = ? AND M_ProductionOutput_ID != ?";
			List<Object> params = new ArrayList<Object>();
			params.add(getM_Production_ID());
			params.add(getM_Warehouse_ID());
			params.add(getM_ProductionOutput_ID());
			int count = DB.getSQLValue(get_TrxName(), sql, params);
			if (count > 1) {
				log.saveError("Error!", "Mỗi lệnh sản xuất là 1 nhóm sản phẩm tại từng phân xưởng, công đoạn!");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		
		return true;
	}

	
}
