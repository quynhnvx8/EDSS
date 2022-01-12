/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HM_Patient
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HM_Patient 
{

    /** TableName=HM_Patient */
    public static final String Table_Name = "HM_Patient";

    /** AD_Table_ID=1200396 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

	/** Set Address	  */
	public void setAddress (String Address);

	/** Get Address	  */
	public String getAddress();

    /** Column name Age */
    public static final String COLUMNNAME_Age = "Age";

	/** Set Age	  */
	public void setAge (String Age);

	/** Get Age	  */
	public String getAge();

    /** Column name Approved */
    public static final String COLUMNNAME_Approved = "Approved";

	/** Set Approved	  */
	public void setApproved (String Approved);

	/** Get Approved	  */
	public String getApproved();

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

    /** Column name birthFamily */
    public static final String COLUMNNAME_birthFamily = "birthFamily";

	/** Set birthFamily	  */
	public void setbirthFamily (Timestamp birthFamily);

	/** Get birthFamily	  */
	public Timestamp getbirthFamily();

    /** Column name BirthYear */
    public static final String COLUMNNAME_BirthYear = "BirthYear";

	/** Set BirthYear	  */
	public void setBirthYear (int BirthYear);

	/** Get BirthYear	  */
	public int getBirthYear();

    /** Column name ButtonZoom */
    public static final String COLUMNNAME_ButtonZoom = "ButtonZoom";

	/** Set ButtonZoom	  */
	public void setButtonZoom (String ButtonZoom);

	/** Get ButtonZoom	  */
	public String getButtonZoom();

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

    /** Column name CardID */
    public static final String COLUMNNAME_CardID = "CardID";

	/** Set CardID	  */
	public void setCardID (String CardID);

	/** Get CardID	  */
	public String getCardID();

    /** Column name Children */
    public static final String COLUMNNAME_Children = "Children";

	/** Set Children	  */
	public void setChildren (boolean Children);

	/** Get Children	  */
	public boolean isChildren();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name Email */
    public static final String COLUMNNAME_Email = "Email";

	/** Set Email.
	  * Electronic Mail Address
	  */
	public void setEmail (String Email);

	/** Get Email.
	  * Electronic Mail Address
	  */
	public String getEmail();

    /** Column name Family */
    public static final String COLUMNNAME_Family = "Family";

	/** Set Family	  */
	public void setFamily (String Family);

	/** Get Family	  */
	public String getFamily();

    /** Column name FamilyPlace */
    public static final String COLUMNNAME_FamilyPlace = "FamilyPlace";

	/** Set FamilyPlace	  */
	public void setFamilyPlace (String FamilyPlace);

	/** Get FamilyPlace	  */
	public String getFamilyPlace();

    /** Column name ForeignName */
    public static final String COLUMNNAME_ForeignName = "ForeignName";

	/** Set Foreign Name	  */
	public void setForeignName (String ForeignName);

	/** Get Foreign Name	  */
	public String getForeignName();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name HistoryPatient */
    public static final String COLUMNNAME_HistoryPatient = "HistoryPatient";

	/** Set HistoryPatient	  */
	public void setHistoryPatient (String HistoryPatient);

	/** Get HistoryPatient	  */
	public String getHistoryPatient();

    /** Column name HM_District_ID */
    public static final String COLUMNNAME_HM_District_ID = "HM_District_ID";

	/** Set District	  */
	public void setHM_District_ID (int HM_District_ID);

	/** Get District	  */
	public int getHM_District_ID();

	public eone.base.model.I_HM_District getHM_District() throws RuntimeException;

    /** Column name HM_Patient_ID */
    public static final String COLUMNNAME_HM_Patient_ID = "HM_Patient_ID";

	/** Set Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID);

	/** Get Patient	  */
	public int getHM_Patient_ID();

    /** Column name HM_PatientSource_ID */
    public static final String COLUMNNAME_HM_PatientSource_ID = "HM_PatientSource_ID";

	/** Set PatientSource	  */
	public void setHM_PatientSource_ID (int HM_PatientSource_ID);

	/** Get PatientSource	  */
	public int getHM_PatientSource_ID();

	public eone.base.model.I_HM_PatientSource getHM_PatientSource() throws RuntimeException;

    /** Column name HM_Province_ID */
    public static final String COLUMNNAME_HM_Province_ID = "HM_Province_ID";

	/** Set Province	  */
	public void setHM_Province_ID (int HM_Province_ID);

	/** Get Province	  */
	public int getHM_Province_ID();

	public eone.base.model.I_HM_Province getHM_Province() throws RuntimeException;

    /** Column name HM_Wards_ID */
    public static final String COLUMNNAME_HM_Wards_ID = "HM_Wards_ID";

	/** Set Wards	  */
	public void setHM_Wards_ID (int HM_Wards_ID);

	/** Get Wards	  */
	public int getHM_Wards_ID();

	public eone.base.model.I_HM_Wards getHM_Wards() throws RuntimeException;

    /** Column name HR_Employee_CS_ID */
    public static final String COLUMNNAME_HR_Employee_CS_ID = "HR_Employee_CS_ID";

	/** Set Employee	  */
	public void setHR_Employee_CS_ID (int HR_Employee_CS_ID);

	/** Get Employee	  */
	public int getHR_Employee_CS_ID();

	public eone.base.model.I_HR_Employee getHR_Employee_CS() throws RuntimeException;

    /** Column name HR_ItemLine_09_ID */
    public static final String COLUMNNAME_HR_ItemLine_09_ID = "HR_ItemLine_09_ID";

	/** Set Nation	  */
	public void setHR_ItemLine_09_ID (int HR_ItemLine_09_ID);

	/** Get Nation	  */
	public int getHR_ItemLine_09_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_09() throws RuntimeException;

    /** Column name HR_ItemLine_39_ID */
    public static final String COLUMNNAME_HR_ItemLine_39_ID = "HR_ItemLine_39_ID";

	/** Set Marital Status	  */
	public void setHR_ItemLine_39_ID (int HR_ItemLine_39_ID);

	/** Get Marital Status	  */
	public int getHR_ItemLine_39_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_39() throws RuntimeException;

    /** Column name HR_ItemLine_43_ID */
    public static final String COLUMNNAME_HR_ItemLine_43_ID = "HR_ItemLine_43_ID";

	/** Set Patient Jobs	  */
	public void setHR_ItemLine_43_ID (int HR_ItemLine_43_ID);

	/** Get Patient Jobs	  */
	public int getHR_ItemLine_43_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_43() throws RuntimeException;

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsPatient */
    public static final String COLUMNNAME_IsPatient = "IsPatient";

	/** Set Patient.
	  * Indicates if this Business Partner is a Patient
	  */
	public void setIsPatient (boolean IsPatient);

	/** Get Patient.
	  * Indicates if this Business Partner is a Patient
	  */
	public boolean isPatient();

    /** Column name JobName */
    public static final String COLUMNNAME_JobName = "JobName";

	/** Set JobName	  */
	public void setJobName (String JobName);

	/** Get JobName	  */
	public String getJobName();

    /** Column name monthAge */
    public static final String COLUMNNAME_monthAge = "monthAge";

	/** Set monthAge	  */
	public void setmonthAge (int monthAge);

	/** Get monthAge	  */
	public int getmonthAge();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name ObjectName */
    public static final String COLUMNNAME_ObjectName = "ObjectName";

	/** Set ObjectName	  */
	public void setObjectName (String ObjectName);

	/** Get ObjectName	  */
	public String getObjectName();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name phoneFamily */
    public static final String COLUMNNAME_phoneFamily = "phoneFamily";

	/** Set phoneFamily	  */
	public void setphoneFamily (String phoneFamily);

	/** Get phoneFamily	  */
	public String getphoneFamily();

    /** Column name Prehistory */
    public static final String COLUMNNAME_Prehistory = "Prehistory";

	/** Set Prehistory.
	  * Optional short description of the record
	  */
	public void setPrehistory (String Prehistory);

	/** Get Prehistory.
	  * Optional short description of the record
	  */
	public String getPrehistory();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/** Set Send EMail.
	  * Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail);

	/** Get Send EMail.
	  * Enable sending Document EMail
	  */
	public boolean isSendEMail();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Code.
	  * Code
	  */
	public void setValue (String Value);

	/** Get Code.
	  * Code
	  */
	public String getValue();
}
