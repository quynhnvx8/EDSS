
package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;

/**
 * 	BOM Product/Component Model
 *	
 */
public class MBOMProduct extends X_M_BOMProduct
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3431041011059529621L;

	/**
	 * 	Get Products of BOM
	 *	@param bom bom
	 *	@return array of BOM Products
	 */
	public static MBOMProduct[] getOfBOM (MBOM bom) 
	{
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		String whereClause = "M_BOM_ID=?";
		List <MBOMProduct> list = new Query(bom.getCtx(), I_M_BOMProduct.Table_Name, whereClause, bom.get_TrxName()) 
		.setParameters(bom.getM_BOM_ID())
		.setOrderBy("SeqNo")
		.list(); 
		
		MBOMProduct[] retValue = new MBOMProduct[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfProduct

	/**	Logger	*/
	@SuppressWarnings("unused")
	private static CLogger s_log = CLogger.getCLogger (MBOMProduct.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_BOMProduct_ID id
	 *	@param trxName trx
	 */
	public MBOMProduct (Properties ctx, int M_BOMProduct_ID, String trxName)
	{
		super (ctx, M_BOMProduct_ID, trxName);
		if (M_BOMProduct_ID == 0)
		{
			//setBOMProductType (BOMPRODUCTTYPE_StandardProduct);	// S
			setBOMQty (Env.ONE);
		
		}
	}	//	MBOMProduct

	/**
	 * 	Parent Constructor
	 *	@param bom product
	 */
	public MBOMProduct (MBOM bom)
	{
		this (bom.getCtx(), 0, bom.get_TrxName());
	}	//	MBOMProduct

	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MBOMProduct (Properties ctx, ResultSet rs, String trxName)
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
		
		
		//	Set Line Number
		if (getLine() == 0)
		{
			String sql = "SELECT NVL(MAX(Line),0)+10 FROM M_BOMProduct WHERE M_BOM_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getM_BOM_ID());
			setLine (ii);
		}

		return true;
	}	//	beforeSave
	
	
}	//	MBOMProduct
