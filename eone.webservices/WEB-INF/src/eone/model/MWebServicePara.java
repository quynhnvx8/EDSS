

package eone.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.base.model.X_WS_WebService_Para;

/**
 *	Web Services Parameters Model
 *	
 *  @author Carlos Ruiz
 */
public class MWebServicePara extends X_WS_WebService_Para
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3561409141850981248L;

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param WS_WebService_Para_ID
	 *	@param trxName transaction
	 */
	public MWebServicePara (Properties ctx, int WS_WebService_Para_ID, String trxName)
	{
		super (ctx, WS_WebService_Para_ID, trxName);
		/** if (WS_WebService_Para_ID == 0)
        {
			setName (null);
			setValue (null);
			WS_WebService_Para_ID (0);
        } */
	}	//	MWebServicePara

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWebServicePara (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWebServicePara

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (   "Filter".equalsIgnoreCase(getParameterName())
			&& PARAMETERTYPE_Free.equals(getParameterType())) {
			log.saveError("Error", "Type Free not allowed for parameter Filter (security issue)"); // IDEMPIERE-1784
			return false;
		}
		return true;
	}

}	//	MWebServicePara
