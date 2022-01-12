package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;


public class MFlowLine extends X_AD_FlowLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1685512419870004665L;

	
	
	public MFlowLine(Properties ctx, int AD_AppendSign_ID, String trxName)
	{
		super (ctx, AD_AppendSign_ID, trxName);

	}	

	
	
	public MFlowLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	
	
	
	protected boolean beforeSave (boolean newRecord)
	{
		return true;	
	}	//	beforeSave

	
}	
