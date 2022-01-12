
package eone.base.callout;

import java.util.List;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.MPatient;
import eone.base.model.MPatientRegister;

//eone.base.callout.CalloutPatient.getInfoExams
//eone.base.callout.CalloutPatient.getDocExams

public class CalloutPatient extends CalloutEngine
{

	public String getInfoExams (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		if (value == null)
			return "";
		Integer m_HM_Patient_ID = Integer.valueOf(value.toString());
		List<MPatientRegister> register = MPatient.getRegisters(ctx, m_HM_Patient_ID.intValue(), null);
		if (register.size() == 1) {
			mTab.setValue("HM_PatientRegister_ID", register.get(0).getHM_PatientRegister_ID());
			mTab.setValue("DocumentNo", register.get(0).getDocumentNo());
		} else {
			mTab.setValue("HM_PatientRegister_ID", 0);
			mTab.setValue("DocumentNo", null);
		}
		return "";
	}
	
	
	public String getDocExams (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		if (value == null)
			return "";
		Integer HM_PatientRegister_ID = Integer.valueOf(value.toString());
		Object objPatient = mTab.getValue("HM_Patient_ID");
		Integer m_HM_Patient_ID = 0;
		if (objPatient != null) {
			m_HM_Patient_ID = Integer.parseInt(objPatient.toString());
		}
		List<MPatientRegister> register = MPatient.getRegisters(ctx, m_HM_Patient_ID.intValue(), null);
		for(int i =0; i < register.size(); i++) {
			if (register.get(i).getHM_PatientRegister_ID() == HM_PatientRegister_ID) {
				mTab.setValue("DocumentNo", register.get(i).getDocumentNo());
			}
		}
		
		return "";
	}
}	//	CalloutInOut