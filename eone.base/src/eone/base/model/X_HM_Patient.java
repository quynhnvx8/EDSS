/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for HM_Patient
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_Patient extends PO implements I_HM_Patient, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210929L;

    /** Standard Constructor */
    public X_HM_Patient (Properties ctx, int HM_Patient_ID, String trxName)
    {
      super (ctx, HM_Patient_ID, trxName);
      /** if (HM_Patient_ID == 0)
        {
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_HM_Patient (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_Patient[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Address.
		@param Address Address	  */
	public void setAddress (String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

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

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set birthFamily.
		@param birthFamily birthFamily	  */
	public void setbirthFamily (Timestamp birthFamily)
	{
		set_Value (COLUMNNAME_birthFamily, birthFamily);
	}

	/** Get birthFamily.
		@return birthFamily	  */
	public Timestamp getbirthFamily () 
	{
		return (Timestamp)get_Value(COLUMNNAME_birthFamily);
	}

	/** Set BirthYear.
		@param BirthYear BirthYear	  */
	public void setBirthYear (int BirthYear)
	{
		set_Value (COLUMNNAME_BirthYear, Integer.valueOf(BirthYear));
	}

	/** Get BirthYear.
		@return BirthYear	  */
	public int getBirthYear () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BirthYear);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ButtonZoom.
		@param ButtonZoom ButtonZoom	  */
	public void setButtonZoom (String ButtonZoom)
	{
		set_Value (COLUMNNAME_ButtonZoom, ButtonZoom);
	}

	/** Get ButtonZoom.
		@return ButtonZoom	  */
	public String getButtonZoom () 
	{
		return (String)get_Value(COLUMNNAME_ButtonZoom);
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

	/** Set CardID.
		@param CardID CardID	  */
	public void setCardID (String CardID)
	{
		set_Value (COLUMNNAME_CardID, CardID);
	}

	/** Get CardID.
		@return CardID	  */
	public String getCardID () 
	{
		return (String)get_Value(COLUMNNAME_CardID);
	}

	/** Set Children.
		@param Children Children	  */
	public void setChildren (boolean Children)
	{
		set_Value (COLUMNNAME_Children, Boolean.valueOf(Children));
	}

	/** Get Children.
		@return Children	  */
	public boolean isChildren () 
	{
		Object oo = get_Value(COLUMNNAME_Children);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Email.
		@param Email 
		Electronic Mail Address
	  */
	public void setEmail (String Email)
	{
		set_Value (COLUMNNAME_Email, Email);
	}

	/** Get Email.
		@return Electronic Mail Address
	  */
	public String getEmail () 
	{
		return (String)get_Value(COLUMNNAME_Email);
	}

	/** Set Family.
		@param Family Family	  */
	public void setFamily (String Family)
	{
		set_Value (COLUMNNAME_Family, Family);
	}

	/** Get Family.
		@return Family	  */
	public String getFamily () 
	{
		return (String)get_Value(COLUMNNAME_Family);
	}

	/** Set FamilyPlace.
		@param FamilyPlace FamilyPlace	  */
	public void setFamilyPlace (String FamilyPlace)
	{
		set_Value (COLUMNNAME_FamilyPlace, FamilyPlace);
	}

	/** Get FamilyPlace.
		@return FamilyPlace	  */
	public String getFamilyPlace () 
	{
		return (String)get_Value(COLUMNNAME_FamilyPlace);
	}

	/** Set Foreign Name.
		@param ForeignName Foreign Name	  */
	public void setForeignName (String ForeignName)
	{
		set_Value (COLUMNNAME_ForeignName, ForeignName);
	}

	/** Get Foreign Name.
		@return Foreign Name	  */
	public String getForeignName () 
	{
		return (String)get_Value(COLUMNNAME_ForeignName);
	}

	/** Female = F */
	public static final String GENDER_Female = "F";
	/** Male = M */
	public static final String GENDER_Male = "M";
	/** Set Gender.
		@param Gender Gender	  */
	public void setGender (String Gender)
	{

		set_Value (COLUMNNAME_Gender, Gender);
	}

	/** Get Gender.
		@return Gender	  */
	public String getGender () 
	{
		return (String)get_Value(COLUMNNAME_Gender);
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

	public eone.base.model.I_HM_District getHM_District() throws RuntimeException
    {
		return (eone.base.model.I_HM_District)MTable.get(getCtx(), eone.base.model.I_HM_District.Table_Name)
			.getPO(getHM_District_ID(), get_TrxName());	}

	/** Set District.
		@param HM_District_ID District	  */
	public void setHM_District_ID (int HM_District_ID)
	{
		if (HM_District_ID < 1) 
			set_Value (COLUMNNAME_HM_District_ID, null);
		else 
			set_Value (COLUMNNAME_HM_District_ID, Integer.valueOf(HM_District_ID));
	}

	/** Get District.
		@return District	  */
	public int getHM_District_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_District_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Patient.
		@param HM_Patient_ID Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID)
	{
		if (HM_Patient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_Patient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_Patient_ID, Integer.valueOf(HM_Patient_ID));
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

	public eone.base.model.I_HM_PatientSource getHM_PatientSource() throws RuntimeException
    {
		return (eone.base.model.I_HM_PatientSource)MTable.get(getCtx(), eone.base.model.I_HM_PatientSource.Table_Name)
			.getPO(getHM_PatientSource_ID(), get_TrxName());	}

	/** Set PatientSource.
		@param HM_PatientSource_ID PatientSource	  */
	public void setHM_PatientSource_ID (int HM_PatientSource_ID)
	{
		if (HM_PatientSource_ID < 1) 
			set_Value (COLUMNNAME_HM_PatientSource_ID, null);
		else 
			set_Value (COLUMNNAME_HM_PatientSource_ID, Integer.valueOf(HM_PatientSource_ID));
	}

	/** Get PatientSource.
		@return PatientSource	  */
	public int getHM_PatientSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_Province getHM_Province() throws RuntimeException
    {
		return (eone.base.model.I_HM_Province)MTable.get(getCtx(), eone.base.model.I_HM_Province.Table_Name)
			.getPO(getHM_Province_ID(), get_TrxName());	}

	/** Set Province.
		@param HM_Province_ID Province	  */
	public void setHM_Province_ID (int HM_Province_ID)
	{
		if (HM_Province_ID < 1) 
			set_Value (COLUMNNAME_HM_Province_ID, null);
		else 
			set_Value (COLUMNNAME_HM_Province_ID, Integer.valueOf(HM_Province_ID));
	}

	/** Get Province.
		@return Province	  */
	public int getHM_Province_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Province_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_Wards getHM_Wards() throws RuntimeException
    {
		return (eone.base.model.I_HM_Wards)MTable.get(getCtx(), eone.base.model.I_HM_Wards.Table_Name)
			.getPO(getHM_Wards_ID(), get_TrxName());	}

	/** Set Wards.
		@param HM_Wards_ID Wards	  */
	public void setHM_Wards_ID (int HM_Wards_ID)
	{
		if (HM_Wards_ID < 1) 
			set_Value (COLUMNNAME_HM_Wards_ID, null);
		else 
			set_Value (COLUMNNAME_HM_Wards_ID, Integer.valueOf(HM_Wards_ID));
	}

	/** Get Wards.
		@return Wards	  */
	public int getHM_Wards_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Wards_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee_CS() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_CS_ID(), get_TrxName());	}

	/** Set Employee.
		@param HR_Employee_CS_ID Employee	  */
	public void setHR_Employee_CS_ID (int HR_Employee_CS_ID)
	{
		if (HR_Employee_CS_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_CS_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_CS_ID, Integer.valueOf(HR_Employee_CS_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getHR_Employee_CS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_CS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_09() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_09_ID(), get_TrxName());	}

	/** Set Nation.
		@param HR_ItemLine_09_ID Nation	  */
	public void setHR_ItemLine_09_ID (int HR_ItemLine_09_ID)
	{
		if (HR_ItemLine_09_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_09_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_09_ID, Integer.valueOf(HR_ItemLine_09_ID));
	}

	/** Get Nation.
		@return Nation	  */
	public int getHR_ItemLine_09_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_09_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_39() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_39_ID(), get_TrxName());	}

	/** Set Marital Status.
		@param HR_ItemLine_39_ID Marital Status	  */
	public void setHR_ItemLine_39_ID (int HR_ItemLine_39_ID)
	{
		if (HR_ItemLine_39_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_39_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_39_ID, Integer.valueOf(HR_ItemLine_39_ID));
	}

	/** Get Marital Status.
		@return Marital Status	  */
	public int getHR_ItemLine_39_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_39_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_43() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_43_ID(), get_TrxName());	}

	/** Set Patient Jobs.
		@param HR_ItemLine_43_ID Patient Jobs	  */
	public void setHR_ItemLine_43_ID (int HR_ItemLine_43_ID)
	{
		if (HR_ItemLine_43_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_43_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_43_ID, Integer.valueOf(HR_ItemLine_43_ID));
	}

	/** Get Patient Jobs.
		@return Patient Jobs	  */
	public int getHR_ItemLine_43_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_43_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Patient.
		@param IsPatient 
		Indicates if this Business Partner is a Patient
	  */
	public void setIsPatient (boolean IsPatient)
	{
		set_Value (COLUMNNAME_IsPatient, Boolean.valueOf(IsPatient));
	}

	/** Get Patient.
		@return Indicates if this Business Partner is a Patient
	  */
	public boolean isPatient () 
	{
		Object oo = get_Value(COLUMNNAME_IsPatient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JobName.
		@param JobName JobName	  */
	public void setJobName (String JobName)
	{
		set_Value (COLUMNNAME_JobName, JobName);
	}

	/** Get JobName.
		@return JobName	  */
	public String getJobName () 
	{
		return (String)get_Value(COLUMNNAME_JobName);
	}

	/** Set monthAge.
		@param monthAge monthAge	  */
	public void setmonthAge (int monthAge)
	{
		set_Value (COLUMNNAME_monthAge, Integer.valueOf(monthAge));
	}

	/** Get monthAge.
		@return monthAge	  */
	public int getmonthAge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_monthAge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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

	/** Service = 01 */
	public static final String OBJECTNAME_Service = "01";
	/** Insurance = 02 */
	public static final String OBJECTNAME_Insurance = "02";
	/** Set ObjectName.
		@param ObjectName ObjectName	  */
	public void setObjectName (String ObjectName)
	{

		set_Value (COLUMNNAME_ObjectName, ObjectName);
	}

	/** Get ObjectName.
		@return ObjectName	  */
	public String getObjectName () 
	{
		return (String)get_Value(COLUMNNAME_ObjectName);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set phoneFamily.
		@param phoneFamily phoneFamily	  */
	public void setphoneFamily (String phoneFamily)
	{
		set_Value (COLUMNNAME_phoneFamily, phoneFamily);
	}

	/** Get phoneFamily.
		@return phoneFamily	  */
	public String getphoneFamily () 
	{
		return (String)get_Value(COLUMNNAME_phoneFamily);
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

	/** Set Send EMail.
		@param SendEMail 
		Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get Send EMail.
		@return Enable sending Document EMail
	  */
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Code.
		@param Value 
		Code
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Code.
		@return Code
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}