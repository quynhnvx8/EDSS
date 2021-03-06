package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;


public class MDepreciationExp extends X_A_Depreciation_Exp
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6731366890875525147L;
	//private static CLogger s_log = CLogger.getCLogger(MDepreciationExp.class);
	//private CLogger log = CLogger.getCLogger(this.getClass());
	
	/** Standard Constructor */
	public MDepreciationExp(Properties ctx, int A_Depreciation_Exp_ID, String trxName)
	{
		super (ctx, A_Depreciation_Exp_ID, trxName);
		
	}
	
	/** Load Constructor */
	public MDepreciationExp (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}
	
	/** Gets depreciation expense 
	 *	@param ctx	context
	 *	@param A_Depreciation_Exp_ID	depreciation expense id
	 *	@return depreciation expense or null if A_Depreciation_Exp_ID=0 or not found
	 */
	public static MDepreciationExp get(Properties ctx, int A_Depreciation_Exp_ID) {
		if (A_Depreciation_Exp_ID <= 0) {
			return null;
		}
		MDepreciationExp depexp = new MDepreciationExp(ctx, A_Depreciation_Exp_ID, null);
		if (depexp.get_ID() != A_Depreciation_Exp_ID) {
			depexp = null;
		}
		return depexp;
	}
	
	
	
	protected boolean beforeDelete()
	{
		
		return true;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		//updateHeader();
		return super.afterSave(newRecord, success);
	}

	

	protected boolean afterDelete(boolean success)
	{
		//updateHeader();
		return true;
	}
	
	
}
