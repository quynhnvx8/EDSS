package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.CCache;
import eone.util.Msg;

public class MProductCategory extends X_M_Product_Category
{

	private static final long serialVersionUID = 1239249591584452179L;


	public static MProductCategory get (Properties ctx, int M_Product_Category_ID)
	{
		Integer ii = Integer.valueOf(M_Product_Category_ID);
		MProductCategory retValue = (MProductCategory)s_cache.get(ii);
		if (retValue != null)
			return retValue;
		retValue = new MProductCategory (ctx, M_Product_Category_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (M_Product_Category_ID, retValue);
		return retValue;
	}	//	get
	

	
	/**	Categopry Cache				*/
	private static CCache<Integer,MProductCategory>	s_cache = new CCache<Integer,MProductCategory>(Table_Name, 20);

	public MProductCategory (Properties ctx, int M_Product_Category_ID, String trxName)
	{
		super(ctx, M_Product_Category_ID, trxName);
		if (M_Product_Category_ID == 0)
		{
			setIsDefault (false);
		}
	}	//	MProductCategory

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProductCategory(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductCategory


	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord || is_ValueChanged("CategoryType") || isActive()) {
			List<MProduct> relValue = new Query(getCtx(), Table_Name, "M_Product_Category_ID != ? And CategoryType = ? And AD_Client_ID = ? And IsActive = 'Y'", get_TrxName())
					.setParameters(getM_Product_Category_ID(), getCategoryType(), getAD_Client_ID())
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", Msg.getMsg(getCtx(), "CategoryType"));//ValueExists, NameExists
				return false;
			}

		}
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		

		return success;
	}	//	afterSave

	
}	//	MProductCategory