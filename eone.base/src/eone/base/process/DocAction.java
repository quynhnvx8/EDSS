
package eone.base.process;

import java.util.Properties;

import eone.base.model.SystemIDs;
import eone.exceptions.EONEException;
import eone.util.CLogger;


public interface DocAction
{
	public static final String STATUS_ReActivate = "RA";
	public static final String STATUS_Drafted = "DR";
	public static final String STATUS_Inprogress = "IP";
	public static final String STATUS_Completed = "CO";
	public static final String STATUS_Pending = "PE";
	public static final String STATUS_Reject = "RE";
	
	public static final String SIGNSTATUS_None 		= "NON";
	public static final String SIGNSTATUS_Approved 	= "APP";
	public static final String SIGNSTATUS_Cancel 	= "CAN";
	
	public static final int AD_REFERENCE_ID = SystemIDs.REFERENCE_DOCUMENTACTION;
	
	public static final int AD_REFERENCE_SIGN_ID = SystemIDs.REFERENCE_SIGNER;
	
	
	public void setDocStatus (String newStatus);

	public String getDocStatus();
	
	public boolean processIt (String action, int AD_Window_ID) throws Exception;
	
	
	public String completeIt();
	
	public boolean reActivateIt();
		
	public String getProcessMsg ();
	
	public void setProcessMsg(String text);
	
	public void saveEx() throws EONEException;
	
	public Properties getCtx();
	
	public int get_ID();
	
	public int get_Table_ID();
	
	public CLogger get_Logger();

	public String get_TrxName();
	
	public String getSummary();
	
	public int getAD_Window_ID();
	
}	//	DocAction
