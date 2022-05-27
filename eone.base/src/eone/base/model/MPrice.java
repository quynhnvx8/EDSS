package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;

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
			MPrice data = getPriceCurrent(getCtx(), get_TrxName(), getM_Product_ID());
			BigDecimal pricePO = getPricePO();
			BigDecimal priceSO = getPriceSO();
			if(data != null) {
				pricePO = data.getPricePO();
				priceSO = data.getPriceSO();
			}
			
			updateProduct(pricePO, priceSO);
		}
		return true;
	}

	public static MPrice getPriceCurrent(Properties ctx, String trxName, int M_Product_ID) {
		String whereClause = " M_Product_ID = ? ";
		List<MPrice> retValue = new Query(ctx,I_M_Price.Table_Name,whereClause,trxName)
		.setParameters(M_Product_ID)
		.setOrderBy(" M_Product_ID, ValidFrom DESC")
		.list();
		if (retValue.size() > 0)
			return retValue.get(0);
		return null;
	}
	
	public static MPrice getPriceByDate(Properties ctx, String trxName, int M_Product_ID, Timestamp dateAcct) {
		String whereClause = " M_Product_ID = ? AND ValidFrom <= ? AND (ValidTo >= ? OR ValidTo IS NULL) ";
		List<MPrice> retValue = new Query(ctx,I_M_Price.Table_Name,whereClause,trxName)
		.setParameters(M_Product_ID, dateAcct, dateAcct)
		.setOrderBy(" M_Product_ID, ValidFrom DESC")
		.list();
		if (retValue.size() > 0)
			return retValue.get(0);
		return null;
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
