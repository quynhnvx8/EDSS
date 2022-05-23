
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;


public class MDocTypeSub extends X_C_DocTypeSub
{
	
	private static final long serialVersionUID = -6556521509479670059L;

	
	static public MDocTypeSub get (Properties ctx, int C_DocTypeSub_ID)
	{
		MDocTypeSub retValue = (MDocTypeSub)s_cache.get(C_DocTypeSub_ID);
		if (retValue == null)
		{
			retValue = new MDocTypeSub (ctx, C_DocTypeSub_ID, null);
			s_cache.put(C_DocTypeSub_ID, retValue);
		}
		return retValue; 
	} 	//	get
	
	/**	Cache					*/
	static private CCache<Integer,MDocTypeSub>	s_cache = new CCache<Integer,MDocTypeSub>(Table_Name, 20);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_DocType_ID id
	 *	@param trxName transaction
	 */
	public MDocTypeSub(Properties ctx, int C_DocType_ID, String trxName)
	{
		super(ctx, C_DocType_ID, trxName);
		if (C_DocType_ID == 0)
		{
			setIsDefault (false);
		}
	}	//	MDocType

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDocTypeSub(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDocType

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param DocBaseType document base type
	 *	@param Name name
	 *	@param trxName transaction
	 */
	public MDocTypeSub (Properties ctx, String DocBaseType, String Name, String trxName)
	{
		this (ctx, 0, trxName);
		setAD_Org_ID(0);
		setName (Name);
		
	}	//	MDocType

	
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MDocType[");
		sb.append(get_ID()).append("-").append(getName())
			.append("]");
		return sb.toString();
	}	//	toString

	
	
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		String sql = "Select count(1) From C_DocTypeSub Where C_DocType_ID = ? And C_DocTypeSub_ID != ? And IsDefault = 'Y'";
		Object [] params = {getC_DocType_ID(), getC_DocTypeSub_ID()};
		int no = DB.getSQLValue(get_TrxName(), sql, params);
		if (no > 1) {
			log.saveError("Error", "Exists set default");
			return false;
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
	
	/**
	 * 	Executed before Delete operation.
	 *
	 *	@return true if delete is a success
	 */
	protected boolean beforeDelete ()
	{
		return true;
	}   //  beforeDelete


}	//	MDocType
