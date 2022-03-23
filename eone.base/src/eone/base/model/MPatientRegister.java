package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.Env;

public class MPatientRegister extends X_HM_PatientRegister implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MPatientRegister (Properties ctx, int HM_PatientRegister_ID, String trxName)
	{
		super (ctx, HM_PatientRegister_ID, trxName);
		
	}	//	MAssetUse

	
	public static MPatientRegister get (Properties ctx, int HM_PatientRegister_ID)
	{
		return get(ctx,HM_PatientRegister_ID,null);
	}
	
	

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		return true;
	}

	public static List<MPatientRegisterLine> getLines(Properties ctx, int HM_PatientRegister_ID, String trxName, String status ) {
		String whereClause = " HM_PatientRegister_ID = ? ";
		if (status != null) {
			whereClause = whereClause + " AND COALESCE(PerformStatus,'NU') =  '"+ status +"'";
		}
			
		List<MPatientRegisterLine> retValue = new Query(ctx, X_HM_PatientRegisterLine.Table_Name, whereClause, trxName)
				.setParameters(HM_PatientRegister_ID)
				.list();
		return retValue;
	}

	public static MPatientRegister get (Properties ctx, int HM_PatientRegister_ID, String trxName)
	{
		final String whereClause = "HM_PatientRegister_ID=? AND AD_Client_ID=?";
		MPatientRegister retValue = new Query(ctx,I_HM_PatientRegister.Table_Name,whereClause,trxName)
		.setParameters(HM_PatientRegister_ID,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}
	
	
	public static boolean saveLine(ArrayList<MPatientRegisterLine> data) {
		
		return true;
	}
	
	

	public MPatientRegister (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetUse


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info ("afterSave");
		
		//Lưu lại lịch sử bệnh nhân
		MPatient patient = MPatient.get(getCtx(), getHM_Patient_ID());
		patient.setHistoryPatient(getHistoryPatient());
		patient.setPrehistory(getPrehistory());
		patient.save();
		
		
		return success;
		 
		
	}	//	afterSave
	

	//Implements DocAction
	protected String		m_processMsg = null;
	private String action;
	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		this.action = action;
		return engine.processIt (action, getDocStatus());
	}

	@Override
	public String completeIt() {
		if(getDebt().compareTo(Env.ZERO) <= 0) {
			m_processMsg = "Tiền thanh toán phải lớn hơn 0 !";
			return DocAction.STATUS_Drafted;
		}
		if (getDebt().compareTo(getAmount()) < 0) {
			m_processMsg = "Thanh toán chưa hết";
			return DocAction.STATUS_Drafted;
		}
		setProcessed(true);
		if (DocAction.STATUS_Inprogress.equals(action))
			return DocAction.STATUS_Inprogress;
		
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
		
		return true;
	}
	/*
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE HM_PatientRegisterLine SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE HM_PatientRegister_ID=").append(getHM_PatientRegister_ID());
		int noLine = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
	}
	*/

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
		
		return "";
	}


	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}


}
