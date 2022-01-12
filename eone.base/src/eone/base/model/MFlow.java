package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;


public class MFlow extends X_AD_Flow implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1685512419870004665L;

	
	
	public MFlow(Properties ctx, int AD_Flow_ID, String trxName)
	{
		super (ctx, AD_Flow_ID, trxName);

	}	

	
	
	public MFlow(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	
	
	
	public String toString()
	{
		return "";
	}	//	toString

	
	protected boolean beforeSave (boolean newRecord)
	{
		return true;
	}

	
	protected String		m_processMsg = null;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID);
		return engine.processIt (action, getDocStatus());
	}


	@Override
	public String completeIt() {
		m_processMsg = null;
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;
		setProcessed(true);
		//setProcessedLine(true);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		if(!super.reActivate())
			return false;
		setProcessed(false);
		return true;
	}


	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}
	
	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		return sb.toString();
	}



	@Override
	public int getAD_Window_ID() {
		return 0;
	}
}	
