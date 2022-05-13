package eone.base.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;
import eone.util.Msg;


public class MProduct extends X_M_Product
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 285926961771269935L;

	
	public static MProduct get (Properties ctx, int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}
		Integer key = Integer.valueOf(M_Product_ID);
		MProduct retValue = (MProduct) s_cache.get (key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MProduct (ctx, M_Product_ID, null);
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
		String sql = "Select M_Product_ID, C_UOM_ID From M_Product Where Value = ?";
		Object [] data = DB.getObjectValuesEx(null, sql, 2, code);
		if (data != null)
		{
			s_cacheCode.put(code, data);
			return data;
		}
		return null;
	}

	
	public static MProduct[] get (Properties ctx, String whereClause, String trxName)
	{
		List<MProduct> list = new Query(ctx, Table_Name, whereClause, trxName)
								.setClient_ID()
								.list();
		return list.toArray(new MProduct[list.size()]);
	}	//	get


	
	
	
	
	/**	Cache						*/
	private static CCache<Integer,MProduct> s_cache	= new CCache<Integer,MProduct>(Table_Name, 40, 5);	//	5 minutes
	
	private static CCache<String,Object []> s_cacheCode	= new CCache<String,Object []>(Table_Name, 40, 5);
	
	public MProduct (Properties ctx, int M_Product_ID, String trxName)
	{
		super (ctx, M_Product_ID, trxName);
		if (M_Product_ID == 0)
		{
		
		}
	}	//	MProduct

	/**
	 * 	Load constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProduct (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProduct


	/**	UOM Precision			*/
	private Integer		m_precision = null;
	
	/**
	 * 	Get UOM Standard Precision
	 *	@return UOM Standard Precision
	 */
	public int getUOMPrecision()
	{
		if (m_precision == null)
		{
			int C_UOM_ID = getC_UOM_ID();
			if (C_UOM_ID == 0)
				return 0;	//	EA
			m_precision = Integer.valueOf(MUOM.getPrecision(getCtx(), C_UOM_ID));
		}
		return m_precision.intValue();
	}	//	getUOMPrecision
	
	
	
	
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
		if (newRecord || is_ValueChanged("Value") || isActive()  || is_ValueChanged("Name")) {
			List<MProduct> relValue = new Query(getCtx(), Table_Name, "M_Product_ID != ? And (Value = ? Or Name = ?) And AD_Client_ID = ? And IsActive = 'Y'", get_TrxName())
					.setParameters(getM_Product_ID(), getValue(), getName(), getAD_Client_ID())
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", Msg.getMsg(getCtx(), "ValueOrNameExists"));//ValueExists, NameExists
				return false;
			}

		}
		
		MProductGroup group = MProductGroup.get(getCtx(), getM_ProductGroup_ID());
		
		if (newRecord || is_ValueChanged(X_M_Product.COLUMNNAME_M_ProductGroup_ID)) {
			if (X_M_ProductGroup.CATEGORYTYPE_Medical.equalsIgnoreCase(group.getCategoryType())
					|| X_M_ProductGroup.CATEGORYTYPE_Pharmaceuticals.equalsIgnoreCase(group.getCategoryType())
					) {
				setIsMedical(true);
			} else {
				setIsMedical(false);
			}
			
			if (X_M_ProductGroup.CATEGORYTYPE_Service.equalsIgnoreCase(group.getCategoryType())) {
				setIsService(true);
			} else {
				setIsService(false);
			}
			
			if (X_M_ProductGroup.CATEGORYTYPE_Goods.equalsIgnoreCase(group.getCategoryType())) {
				setIsSold(true);
			} else {
				setIsSold(false);
			}
			
		}
		
		return true;
	}	//	beforeSave


	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		
		//Xóa cấu hình cũ
		if (is_ValueChanged(X_M_Product.COLUMNNAME_M_ProductGroup_ID)) {
			String sql = "DELETE C_Account WHERE M_Product_ID = ?";
			DB.executeUpdate(sql, getM_Product_ID(), get_TrxName());
		}
		//Insert cấu hình mới
		if (newRecord || is_ValueChanged(X_M_Product.COLUMNNAME_M_ProductGroup_ID)) {
			List<MAccount> lists = MAccount.getListAcctProductGroup(getM_ProductGroup_ID());
			for (int i = 0; i < lists.size(); i++) {
				MAccount acc = new MAccount(getCtx(), 0, get_TrxName());
				acc.setM_Product_ID(getM_Product_ID());
				acc.setAccount_ID(lists.get(i).getAccount_ID());
				acc.setIsDefault(lists.get(i).isDefault());
				acc.setTypeAccount(lists.get(i).getTypeAccount());
				acc.save();
			}
		}
		
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
		return success;
	}	//	afterDelete
	
	public static Map<String, BigDecimal> getPrice_SOPO(Timestamp date, int M_Product_ID) {
		String sql = "";
		Map<String, BigDecimal> data = new HashMap<String, BigDecimal>();
    	if (DB.isOracle() ) {
    		sql = "select PriceSO, PricePO from (select Coalesce(PriceSO,0) PriceSO, Coalesce(PricePO,0) PricePO from M_Price where ? between ValidFrom And ValidTo And M_Product_ID = ? order by ValidTo desc)b where ROWNUM <= 1";
    	} else {
    		sql = "select PriceSO, PricePO from (select Coalesce(PriceSO,0) PriceSO, Coalesce(PricePO,0) PricePO, COUNT(*) OVER ( ORDER BY ValidTo Desc) as ROWNUM from M_Price where ? between ValidFrom And ValidTo And M_Product_ID = ? order by ValidTo desc)b where ROWNUM <= 1";
    	}
    	PreparedStatement ps = DB.prepareCall(sql);
    	ResultSet rs = null;
    	try {
			ps.setTimestamp(1, date);
			ps.setInt(2, M_Product_ID);
			rs = ps.executeQuery();
			while(rs.next()) {
				data.put("PricePO", rs.getBigDecimal("PricePO"));
				data.put("PriceSO", rs.getBigDecimal("PriceSO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.close(rs, ps);
			rs = null;
			ps = null;
		}
    	
    	return data;
	}
	
	
	
}	//	MProduct
