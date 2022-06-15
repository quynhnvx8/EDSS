package eone.base.model;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eone.util.Env;
import eone.util.Msg;

public class MPackagePrice extends X_AD_PackagePrice
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MPackagePrice (Properties ctx, int AD_PackagePrice_ID, String trxName)
	{
		super (ctx, AD_PackagePrice_ID, trxName);
		
	}	//	MAssetUse

	
	public static MPackagePrice get (Properties ctx, int AD_PackagePrice_ID, String trxName)
	{
		return (MPackagePrice) MTable.get(ctx, Table_ID).getPO(AD_PackagePrice_ID, trxName);
	}
	
	

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_Price, getPrice());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_PackagePrice_ID, getAD_PackagePrice_ID(), get_TrxName());
		
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_Price);
			return false;
		}
		
		
		if(getMaxValue().compareTo(Env.ZERO) < 0) {
			log.saveError("Error", "Số người tối đa lớn hơn 0");
			return false;
		}
		
		MCurrency curr = MCurrency.get(getCtx(), getC_Currency_ID());
		DecimalFormat formatter = null;
		if ("VND".equalsIgnoreCase(curr.getISO_Code()))
			formatter = new DecimalFormat("#,###");
		else
			formatter = new DecimalFormat("#,###.00");
		
		String name = "Gói cước: " + formatter.format(getPrice()) + " " + curr.getISO_Code() + " dùng cho lượng người dùng tối đa " + getMaxValue();
		
		setName(name);
		
		return true;
	}


	public MPackagePrice (Properties ctx, ResultSet rs, String trxName)
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
