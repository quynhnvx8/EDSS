/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import eone.util.Env;

/** Generated Model for HM_PatientRegister
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_PatientRegister extends PO implements I_HM_PatientRegister, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210917L;

    /** Standard Constructor */
    public X_HM_PatientRegister (Properties ctx, int HM_PatientRegister_ID, String trxName)
    {
      super (ctx, HM_PatientRegister_ID, trxName);
      /** if (HM_PatientRegister_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_PatientRegister (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_HM_PatientRegister[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address.
		@param Address Address	  */
	public void setAddress (String Address)
	{
		throw new IllegalArgumentException ("Address is virtual column");	}

	/** Get Address.
		@return Address	  */
	public String getAddress () 
	{
		return (String)get_Value(COLUMNNAME_Address);
	}

	/** Set Age.
		@param Age Age	  */
	public void setAge (String Age)
	{
		throw new IllegalArgumentException ("Age is virtual column");	}

	/** Get Age.
		@return Age	  */
	public String getAge () 
	{
		return (String)get_Value(COLUMNNAME_Age);
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmountPrescription.
		@param AmountPrescription AmountPrescription	  */
	public void setAmountPrescription (BigDecimal AmountPrescription)
	{
		set_Value (COLUMNNAME_AmountPrescription, AmountPrescription);
	}

	/** Get AmountPrescription.
		@return AmountPrescription	  */
	public BigDecimal getAmountPrescription () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountPrescription);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Approved.
		@param Approved Approved	  */
	public void setApproved (String Approved)
	{
		set_Value (COLUMNNAME_Approved, Approved);
	}

	/** Get Approved.
		@return Approved	  */
	public String getApproved () 
	{
		return (String)get_Value(COLUMNNAME_Approved);
	}

	/** Set BirthYear.
		@param BirthYear BirthYear	  */
	public void setBirthYear (int BirthYear)
	{
		throw new IllegalArgumentException ("BirthYear is virtual column");	}

	/** Get BirthYear.
		@return BirthYear	  */
	public int getBirthYear () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BirthYear);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BloodPressure.
		@param BloodPressure BloodPressure	  */
	public void setBloodPressure (int BloodPressure)
	{
		set_Value (COLUMNNAME_BloodPressure, Integer.valueOf(BloodPressure));
	}

	/** Get BloodPressure.
		@return BloodPressure	  */
	public int getBloodPressure () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BloodPressure);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BloodVessel.
		@param BloodVessel BloodVessel	  */
	public void setBloodVessel (int BloodVessel)
	{
		set_Value (COLUMNNAME_BloodVessel, Integer.valueOf(BloodVessel));
	}

	/** Get BloodVessel.
		@return BloodVessel	  */
	public int getBloodVessel () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BloodVessel);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canceled.
		@param Canceled Canceled	  */
	public void setCanceled (String Canceled)
	{
		set_Value (COLUMNNAME_Canceled, Canceled);
	}

	/** Get Canceled.
		@return Canceled	  */
	public String getCanceled () 
	{
		return (String)get_Value(COLUMNNAME_Canceled);
	}

	/** Set CancelExam.
		@param CancelExam CancelExam	  */
	public void setCancelExam (String CancelExam)
	{
		set_Value (COLUMNNAME_CancelExam, CancelExam);
	}

	/** Get CancelExam.
		@return CancelExam	  */
	public String getCancelExam () 
	{
		return (String)get_Value(COLUMNNAME_CancelExam);
	}

	/** Set CardID.
		@param CardID CardID	  */
	public void setCardID (String CardID)
	{
		throw new IllegalArgumentException ("CardID is virtual column");	}

	/** Get CardID.
		@return CardID	  */
	public String getCardID () 
	{
		return (String)get_Value(COLUMNNAME_CardID);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		throw new IllegalArgumentException ("DateFinish is virtual column");	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateIssue.
		@param DateIssue DateIssue	  */
	public void setDateIssue (Timestamp DateIssue)
	{
		set_Value (COLUMNNAME_DateIssue, DateIssue);
	}

	/** Get DateIssue.
		@return DateIssue	  */
	public Timestamp getDateIssue () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateIssue);
	}

	/** Set DateNext.
		@param DateNext DateNext	  */
	public void setDateNext (Timestamp DateNext)
	{
		set_Value (COLUMNNAME_DateNext, DateNext);
	}

	/** Get DateNext.
		@return DateNext	  */
	public Timestamp getDateNext () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateNext);
	}

	/** Set DateService.
		@param DateService DateService	  */
	public void setDateService (Timestamp DateService)
	{
		set_Value (COLUMNNAME_DateService, DateService);
	}

	/** Get DateService.
		@return DateService	  */
	public Timestamp getDateService () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateService);
	}

	/** Set DayExam.
		@param DayExam DayExam	  */
	public void setDayExam (int DayExam)
	{
		set_Value (COLUMNNAME_DayExam, Integer.valueOf(DayExam));
	}

	/** Get DayExam.
		@return DayExam	  */
	public int getDayExam () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayExam);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DayUse.
		@param DayUse DayUse	  */
	public void setDayUse (int DayUse)
	{
		set_Value (COLUMNNAME_DayUse, Integer.valueOf(DayUse));
	}

	/** Get DayUse.
		@return DayUse	  */
	public int getDayUse () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayUse);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Debt.
		@param Debt Debt	  */
	public void setDebt (BigDecimal Debt)
	{
		set_Value (COLUMNNAME_Debt, Debt);
	}

	/** Get Debt.
		@return Debt	  */
	public BigDecimal getDebt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Debt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DefineService.
		@param DefineService DefineService	  */
	public void setDefineService (String DefineService)
	{
		set_Value (COLUMNNAME_DefineService, DefineService);
	}

	/** Get DefineService.
		@return DefineService	  */
	public String getDefineService () 
	{
		return (String)get_Value(COLUMNNAME_DefineService);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Diagnostic.
		@param Diagnostic Diagnostic	  */
	public void setDiagnostic (String Diagnostic)
	{
		set_Value (COLUMNNAME_Diagnostic, Diagnostic);
	}

	/** Get Diagnostic.
		@return Diagnostic	  */
	public String getDiagnostic () 
	{
		return (String)get_Value(COLUMNNAME_Diagnostic);
	}

	/** Set DiagnosticCLS.
		@param DiagnosticCLS DiagnosticCLS	  */
	public void setDiagnosticCLS (String DiagnosticCLS)
	{
		set_Value (COLUMNNAME_DiagnosticCLS, DiagnosticCLS);
	}

	/** Get DiagnosticCLS.
		@return DiagnosticCLS	  */
	public String getDiagnosticCLS () 
	{
		return (String)get_Value(COLUMNNAME_DiagnosticCLS);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Reject = RE */
	public static final String DOCSTATUS_Reject = "RE";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EndExam.
		@param EndExam EndExam	  */
	public void setEndExam (String EndExam)
	{
		set_Value (COLUMNNAME_EndExam, EndExam);
	}

	/** Get EndExam.
		@return EndExam	  */
	public String getEndExam () 
	{
		return (String)get_Value(COLUMNNAME_EndExam);
	}

	/** Set Examination.
		@param Examination Examination	  */
	public void setExamination (String Examination)
	{
		set_Value (COLUMNNAME_Examination, Examination);
	}

	/** Get Examination.
		@return Examination	  */
	public String getExamination () 
	{
		return (String)get_Value(COLUMNNAME_Examination);
	}

	/** Set Export Medicine.
		@param ExportMedicine Export Medicine	  */
	public void setExportMedicine (String ExportMedicine)
	{
		set_Value (COLUMNNAME_ExportMedicine, ExportMedicine);
	}

	/** Get Export Medicine.
		@return Export Medicine	  */
	public String getExportMedicine () 
	{
		return (String)get_Value(COLUMNNAME_ExportMedicine);
	}

	/** Female = F */
	public static final String GENDER_Female = "F";
	/** Male = M */
	public static final String GENDER_Male = "M";
	/** Set Gender.
		@param Gender Gender	  */
	public void setGender (String Gender)
	{

		throw new IllegalArgumentException ("Gender is virtual column");	}

	/** Get Gender.
		@return Gender	  */
	public String getGender () 
	{
		return (String)get_Value(COLUMNNAME_Gender);
	}

	/** Set Height.
		@param Height Height	  */
	public void setHeight (BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	/** Get Height.
		@return Height	  */
	public BigDecimal getHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Height);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set historyNo.
		@param historyNo historyNo	  */
	public void sethistoryNo (String historyNo)
	{
		set_Value (COLUMNNAME_historyNo, historyNo);
	}

	/** Get historyNo.
		@return historyNo	  */
	public String gethistoryNo () 
	{
		return (String)get_Value(COLUMNNAME_historyNo);
	}

	/** Set HistoryPatient.
		@param HistoryPatient HistoryPatient	  */
	public void setHistoryPatient (String HistoryPatient)
	{
		set_Value (COLUMNNAME_HistoryPatient, HistoryPatient);
	}

	/** Get HistoryPatient.
		@return HistoryPatient	  */
	public String getHistoryPatient () 
	{
		return (String)get_Value(COLUMNNAME_HistoryPatient);
	}

	public eone.base.model.I_HM_DiseaseCode getHM_DiseaseCode() throws RuntimeException
    {
		return (eone.base.model.I_HM_DiseaseCode)MTable.get(getCtx(), eone.base.model.I_HM_DiseaseCode.Table_Name)
			.getPO(getHM_DiseaseCode_ID(), get_TrxName());	}

	/** Set Disease Code.
		@param HM_DiseaseCode_ID Disease Code	  */
	public void setHM_DiseaseCode_ID (int HM_DiseaseCode_ID)
	{
		if (HM_DiseaseCode_ID < 1) 
			set_Value (COLUMNNAME_HM_DiseaseCode_ID, null);
		else 
			set_Value (COLUMNNAME_HM_DiseaseCode_ID, Integer.valueOf(HM_DiseaseCode_ID));
	}

	/** Get Disease Code.
		@return Disease Code	  */
	public int getHM_DiseaseCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_DiseaseCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_ICD10 getHM_ICD10() throws RuntimeException
    {
		return (eone.base.model.I_HM_ICD10)MTable.get(getCtx(), eone.base.model.I_HM_ICD10.Table_Name)
			.getPO(getHM_ICD10_ID(), get_TrxName());	}

	/** Set ICD10 List.
		@param HM_ICD10_ID ICD10 List	  */
	public void setHM_ICD10_ID (int HM_ICD10_ID)
	{
		if (HM_ICD10_ID < 1) 
			set_Value (COLUMNNAME_HM_ICD10_ID, null);
		else 
			set_Value (COLUMNNAME_HM_ICD10_ID, Integer.valueOf(HM_ICD10_ID));
	}

	/** Get ICD10 List.
		@return ICD10 List	  */
	public int getHM_ICD10_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_ICD10_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_Parts getHM_Parts() throws RuntimeException
    {
		return (eone.base.model.I_HM_Parts)MTable.get(getCtx(), eone.base.model.I_HM_Parts.Table_Name)
			.getPO(getHM_Parts_ID(), get_TrxName());	}

	/** Set Parts.
		@param HM_Parts_ID Parts	  */
	public void setHM_Parts_ID (int HM_Parts_ID)
	{
		if (HM_Parts_ID < 1) 
			set_Value (COLUMNNAME_HM_Parts_ID, null);
		else 
			set_Value (COLUMNNAME_HM_Parts_ID, Integer.valueOf(HM_Parts_ID));
	}

	/** Get Parts.
		@return Parts	  */
	public int getHM_Parts_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Parts_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_Patient getHM_Patient() throws RuntimeException
    {
		return (eone.base.model.I_HM_Patient)MTable.get(getCtx(), eone.base.model.I_HM_Patient.Table_Name)
			.getPO(getHM_Patient_ID(), get_TrxName());	}

	/** Set Patient.
		@param HM_Patient_ID Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID)
	{
		if (HM_Patient_ID < 1) 
			set_Value (COLUMNNAME_HM_Patient_ID, null);
		else 
			set_Value (COLUMNNAME_HM_Patient_ID, Integer.valueOf(HM_Patient_ID));
	}

	/** Get Patient.
		@return Patient	  */
	public int getHM_Patient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Patient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Patient Register.
		@param HM_PatientRegister_ID Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID)
	{
		if (HM_PatientRegister_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, Integer.valueOf(HM_PatientRegister_ID));
	}

	/** Get Patient Register.
		@return Patient Register	  */
	public int getHM_PatientRegister_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientRegister_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee_CLS() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_CLS_ID(), get_TrxName());	}

	/** Set HR_Employee_CLS_ID.
		@param HR_Employee_CLS_ID HR_Employee_CLS_ID	  */
	public void setHR_Employee_CLS_ID (int HR_Employee_CLS_ID)
	{
		if (HR_Employee_CLS_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_CLS_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_CLS_ID, Integer.valueOf(HR_Employee_CLS_ID));
	}

	/** Get HR_Employee_CLS_ID.
		@return HR_Employee_CLS_ID	  */
	public int getHR_Employee_CLS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_CLS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param HR_Employee_ID Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID)
	{
		if (HR_Employee_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_ID, Integer.valueOf(HR_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getHR_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee_KT() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_KT_ID(), get_TrxName());	}

	/** Set HR_Employee KT.
		@param HR_Employee_KT_ID HR_Employee KT	  */
	public void setHR_Employee_KT_ID (int HR_Employee_KT_ID)
	{
		if (HR_Employee_KT_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_KT_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_KT_ID, Integer.valueOf(HR_Employee_KT_ID));
	}

	/** Get HR_Employee KT.
		@return HR_Employee KT	  */
	public int getHR_Employee_KT_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_KT_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee_KTV() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_KTV_ID(), get_TrxName());	}

	/** Set HR_Employee KTV.
		@param HR_Employee_KTV_ID HR_Employee KTV	  */
	public void setHR_Employee_KTV_ID (int HR_Employee_KTV_ID)
	{
		if (HR_Employee_KTV_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_KTV_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_KTV_ID, Integer.valueOf(HR_Employee_KTV_ID));
	}

	/** Get HR_Employee KTV.
		@return HR_Employee KTV	  */
	public int getHR_Employee_KTV_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_KTV_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee_User() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_User_ID(), get_TrxName());	}

	/** Set Employee User.
		@param HR_Employee_User_ID Employee User	  */
	public void setHR_Employee_User_ID (int HR_Employee_User_ID)
	{
		if (HR_Employee_User_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_User_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_User_ID, Integer.valueOf(HR_Employee_User_ID));
	}

	/** Get Employee User.
		@return Employee User	  */
	public int getHR_Employee_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Destroy.
		@param IsDestroy Destroy	  */
	public void setIsDestroy (boolean IsDestroy)
	{
		set_Value (COLUMNNAME_IsDestroy, Boolean.valueOf(IsDestroy));
	}

	/** Get Destroy.
		@return Destroy	  */
	public boolean isDestroy () 
	{
		Object oo = get_Value(COLUMNNAME_IsDestroy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Finish.
		@param IsFinish Finish	  */
	public void setIsFinish (boolean IsFinish)
	{
		set_Value (COLUMNNAME_IsFinish, Boolean.valueOf(IsFinish));
	}

	/** Get Finish.
		@return Finish	  */
	public boolean isFinish () 
	{
		Object oo = get_Value(COLUMNNAME_IsFinish);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public eone.base.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (eone.base.model.I_M_Product)MTable.get(getCtx(), eone.base.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (eone.base.model.I_M_Warehouse)MTable.get(getCtx(), eone.base.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set No.
		@param No No	  */
	public void setNo (int No)
	{
		set_Value (COLUMNNAME_No, Integer.valueOf(No));
	}

	/** Get No.
		@return No	  */
	public int getNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_No);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		throw new IllegalArgumentException ("Phone is virtual column");	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Preathing.
		@param Preathing Preathing	  */
	public void setPreathing (int Preathing)
	{
		set_Value (COLUMNNAME_Preathing, Integer.valueOf(Preathing));
	}

	/** Get Preathing.
		@return Preathing	  */
	public int getPreathing () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Preathing);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prehistory.
		@param Prehistory 
		Optional short description of the record
	  */
	public void setPrehistory (String Prehistory)
	{
		set_Value (COLUMNNAME_Prehistory, Prehistory);
	}

	/** Get Prehistory.
		@return Optional short description of the record
	  */
	public String getPrehistory () 
	{
		return (String)get_Value(COLUMNNAME_Prehistory);
	}

	/** Set Prescription.
		@param Prescription Prescription	  */
	public void setPrescription (String Prescription)
	{
		set_Value (COLUMNNAME_Prescription, Prescription);
	}

	/** Get Prescription.
		@return Prescription	  */
	public String getPrescription () 
	{
		return (String)get_Value(COLUMNNAME_Prescription);
	}

	/** Set PrescriptionDate.
		@param PrescriptionDate PrescriptionDate	  */
	public void setPrescriptionDate (Timestamp PrescriptionDate)
	{
		set_Value (COLUMNNAME_PrescriptionDate, PrescriptionDate);
	}

	/** Get PrescriptionDate.
		@return PrescriptionDate	  */
	public Timestamp getPrescriptionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PrescriptionDate);
	}

	/** Set PrescriptionNo.
		@param PrescriptionNo PrescriptionNo	  */
	public void setPrescriptionNo (String PrescriptionNo)
	{
		set_Value (COLUMNNAME_PrescriptionNo, PrescriptionNo);
	}

	/** Get PrescriptionNo.
		@return PrescriptionNo	  */
	public String getPrescriptionNo () 
	{
		return (String)get_Value(COLUMNNAME_PrescriptionNo);
	}

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PrintService.
		@param PrintService PrintService	  */
	public void setPrintService (String PrintService)
	{
		set_Value (COLUMNNAME_PrintService, PrintService);
	}

	/** Get PrintService.
		@return PrintService	  */
	public String getPrintService () 
	{
		return (String)get_Value(COLUMNNAME_PrintService);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ReasonText.
		@param ReasonText ReasonText	  */
	public void setReasonText (String ReasonText)
	{
		set_Value (COLUMNNAME_ReasonText, ReasonText);
	}

	/** Get ReasonText.
		@return ReasonText	  */
	public String getReasonText () 
	{
		return (String)get_Value(COLUMNNAME_ReasonText);
	}

	/** Set ResultCLS.
		@param ResultCLS ResultCLS	  */
	public void setResultCLS (String ResultCLS)
	{
		set_Value (COLUMNNAME_ResultCLS, ResultCLS);
	}

	/** Get ResultCLS.
		@return ResultCLS	  */
	public String getResultCLS () 
	{
		return (String)get_Value(COLUMNNAME_ResultCLS);
	}

	/** Set SelectService.
		@param SelectService SelectService	  */
	public void setSelectService (String SelectService)
	{
		set_Value (COLUMNNAME_SelectService, SelectService);
	}

	/** Get SelectService.
		@return SelectService	  */
	public String getSelectService () 
	{
		return (String)get_Value(COLUMNNAME_SelectService);
	}

	/** Set ServiceNo.
		@param ServiceNo ServiceNo	  */
	public void setServiceNo (String ServiceNo)
	{
		set_Value (COLUMNNAME_ServiceNo, ServiceNo);
	}

	/** Get ServiceNo.
		@return ServiceNo	  */
	public String getServiceNo () 
	{
		return (String)get_Value(COLUMNNAME_ServiceNo);
	}

	/** Set Temperature.
		@param Temperature Temperature	  */
	public void setTemperature (int Temperature)
	{
		set_Value (COLUMNNAME_Temperature, Integer.valueOf(Temperature));
	}

	/** Get Temperature.
		@return Temperature	  */
	public int getTemperature () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Temperature);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (int Weight)
	{
		set_Value (COLUMNNAME_Weight, Integer.valueOf(Weight));
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public int getWeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Weight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}