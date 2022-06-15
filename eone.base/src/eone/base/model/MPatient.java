
package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.CCache;
import eone.util.Env;
import eone.util.Msg;

public class MPatient extends X_HM_Patient implements DocAction
{
	private static final long serialVersionUID = -5604686137606338725L;


	public static List<MPatientRegister> getRegisters(Properties ctx, int HM_Patient_ID, String trxNam ) {
		String whereClause = " HM_Patient_ID = ? AND  HM_PatientRegister_ID IN (SELECT HM_PatientRegister_ID FROM HM_PatientRegisterLine WHERE PerformStatus = 'NU')";
			
		List<MPatientRegister> retValue = new Query(ctx, X_HM_PatientRegister.Table_Name, whereClause, trxNam)
				.setParameters(HM_Patient_ID)
				.list();
		return retValue;
	}

	public static MPatient get (Properties ctx, int HM_Patient_ID)
	{
		MPatient retValue = s_cache.get (HM_Patient_ID);
		if (retValue != null)
			return retValue;
		retValue = new MPatient (ctx, HM_Patient_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (HM_Patient_ID, retValue);
		return retValue;
	}	//	get

	private static CCache<Integer,MPatient>	s_cache	= new CCache<Integer,MPatient>(Table_Name, 50);
	
	
	public MPatient (Properties ctx, int HM_Patient_ID, String trxName)
	{
		super(ctx, HM_Patient_ID, trxName);
		
	}	

	public MPatient (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrg

	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			
		}
		
		return true;
	}	//	afterSave
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (newRecord 
				|| is_ValueChanged(COLUMNNAME_Name) 
				|| is_ValueChanged(COLUMNNAME_HM_Province_ID)
				|| is_ValueChanged(COLUMNNAME_HM_District_ID)
				|| is_ValueChanged(COLUMNNAME_HM_Wards_ID)
				|| is_ValueChanged(COLUMNNAME_BirthYear)
			) 
		{
			Map<String, Object> dataColumn = new HashMap<String, Object>();
			dataColumn.put(COLUMNNAME_Name, getName());
			dataColumn.put(COLUMNNAME_HM_Province_ID, getHM_Province_ID());
			dataColumn.put(COLUMNNAME_HM_District_ID, getHM_District_ID());
			dataColumn.put(COLUMNNAME_HM_Wards_ID, getHM_Wards_ID());
			dataColumn.put(COLUMNNAME_BirthYear, getBirthYear());
			boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_HM_Patient_ID, getHM_Patient_ID(), get_TrxName());
			if (!check) {
				log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": Name, Province, District, Wards, BirthYear!");
				return false;
			}
			
		}
		return true;
	}

	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(MTree.TREETYPE_CustomTable);
		return success;
	}	//	afterDelete


	protected String		m_processMsg = null;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
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
		// TODO Auto-generated method stub
		return 0;
	}
	
}
