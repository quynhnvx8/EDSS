package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MPrice extends X_M_Price
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MPrice (Properties ctx, int M_Price_ID, String trxName)
	{
		super (ctx, M_Price_ID, trxName);
		
	}	//	MAssetUse

	
	public static MPrice get (Properties ctx, int M_Price_ID)
	{
		return get(ctx,M_Price_ID,null);
	}
	
	
	private Map<Integer, MPrice> data = null;
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (is_ValueChanged(X_M_Price.COLUMNNAME_ValidFrom) || is_ValueChanged(X_M_Price.COLUMNNAME_ValidTo)) {
			String sql = "Select count(1) From M_Price Where ((? between ValidFrom And ValidTo) Or (? between ValidFrom And ValidTo)) And M_Price_ID > 0 And M_Price_ID != ? And M_Product_ID = ?";
			List<Object> params = new ArrayList<Object>();
			params.add(getValidFrom());
			params.add(getValidTo());
			params.add(getM_Price_ID());
			params.add(getM_Product_ID());
			int no = DB.getSQLValue(null, sql, params);
			if (no > 0) {
				log.saveError("Save Error!", "Thời gian không hợp lệ");
				return false;
			}
		}
		
		if (is_ValueChanged("Processed") ) {
			data = getPriceCurrent(getCtx(), get_TrxName(), getM_Product_ID(), getM_Price_ID());
			BigDecimal pricePO = getPricePO();
			BigDecimal priceSO = getPriceSO();
			if(data.size() > 0) {
				pricePO = data.get(getM_Product_ID()).getPricePO();
				priceSO = data.get(getM_Product_ID()).getPriceSO();
			}
			
			updateProduct(pricePO, priceSO);
		}
		return true;
	}

	public static Map<Integer, MPrice> getPriceCurrent(Properties ctx, String trxName, int M_Product_ID, int M_Price_ID) {
		String whereClause = " AD_Client_ID=? AND M_Product_ID = ? AND Processed = 'Y'";
		if(M_Price_ID > 0) {
			whereClause = whereClause + " AND M_Price_ID != " + M_Price_ID;
		}
		List<MPrice> retValue = new Query(ctx,I_M_Price.Table_Name,whereClause,trxName)
		.setParameters(Env.getAD_Client_ID(ctx), M_Product_ID)
		.setOrderBy(" M_Product_ID, ValidFrom DESC")
		.list();
		Map<Integer, MPrice> data = new HashMap<Integer, MPrice>();
		Timestamp validFrom = null;
		Timestamp validCurr = null;
		for(int i = 0; i < retValue.size(); i++) {
			if(data.containsKey(M_Product_ID)) {
				validCurr = data.get(M_Product_ID).getValidFrom();
				validFrom = retValue.get(i).getValidFrom();
				if(validFrom.compareTo(validCurr) > 0) {
					data.put(M_Product_ID, retValue.get(i));					
				}
			} else {
				data.put(M_Product_ID, retValue.get(i));
				validFrom = null;
				validCurr = null;
			}
		}
		return data;
	}

	public static MPrice get (Properties ctx, int M_Price_ID, String trxName)
	{
		final String whereClause = "M_Price_ID=? AND AD_Client_ID=?";
		MPrice retValue = new Query(ctx,I_M_Price.Table_Name,whereClause,trxName)
		.setParameters(M_Price_ID,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}
	
	private void updateProduct(BigDecimal pricePO, BigDecimal priceSO) {
		String sql = "UPDATE M_Product SET PriceSO = ?, PricePO = ? WHERE M_Product_ID = ?";
		Object [] params = {priceSO, pricePO, getM_Product_ID()};
		DB.executeUpdate(sql, params, true, get_TrxName());
	}
	

	public MPrice (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetUse


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info ("afterSave");
		if (!success)
			return success;
		
		return success;
		 
		
	}	//	afterSave
		
}
