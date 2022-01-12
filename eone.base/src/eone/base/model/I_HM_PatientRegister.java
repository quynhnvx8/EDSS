/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HM_PatientRegister
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HM_PatientRegister 
{

    /** TableName=HM_PatientRegister */
    public static final String Table_Name = "HM_PatientRegister";

    /** AD_Table_ID=1200389 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name AmountPrescription */
    public static final String COLUMNNAME_AmountPrescription = "AmountPrescription";

	/** Set AmountPrescription	  */
	public void setAmountPrescription (BigDecimal AmountPrescription);

	/** Get AmountPrescription	  */
	public BigDecimal getAmountPrescription();

    /** Column name Approved */
    public static final String COLUMNNAME_Approved = "Approved";

	/** Set Approved	  */
	public void setApproved (String Approved);

	/** Get Approved	  */
	public String getApproved();

    /** Column name BirthYear */
    public static final String COLUMNNAME_BirthYear = "BirthYear";

	/** Set BirthYear	  */
	public void setBirthYear (int BirthYear);

	/** Get BirthYear	  */
	public int getBirthYear();

    /** Column name BloodPressure */
    public static final String COLUMNNAME_BloodPressure = "BloodPressure";

	/** Set BloodPressure	  */
	public void setBloodPressure (int BloodPressure);

	/** Get BloodPressure	  */
	public int getBloodPressure();

    /** Column name BloodVessel */
    public static final String COLUMNNAME_BloodVessel = "BloodVessel";

	/** Set BloodVessel	  */
	public void setBloodVessel (int BloodVessel);

	/** Get BloodVessel	  */
	public int getBloodVessel();

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

    /** Column name CancelExam */
    public static final String COLUMNNAME_CancelExam = "CancelExam";

	/** Set CancelExam	  */
	public void setCancelExam (String CancelExam);

	/** Get CancelExam	  */
	public String getCancelExam();

    /** Column name CardID */
    public static final String COLUMNNAME_CardID = "CardID";

	/** Set CardID	  */
	public void setCardID (String CardID);

	/** Get CardID	  */
	public String getCardID();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/** Set Finish Date.
	  * Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish);

	/** Get Finish Date.
	  * Finish or (planned) completion date
	  */
	public Timestamp getDateFinish();

    /** Column name DateIssue */
    public static final String COLUMNNAME_DateIssue = "DateIssue";

	/** Set DateIssue	  */
	public void setDateIssue (Timestamp DateIssue);

	/** Get DateIssue	  */
	public Timestamp getDateIssue();

    /** Column name DateNext */
    public static final String COLUMNNAME_DateNext = "DateNext";

	/** Set DateNext	  */
	public void setDateNext (Timestamp DateNext);

	/** Get DateNext	  */
	public Timestamp getDateNext();

    /** Column name DateService */
    public static final String COLUMNNAME_DateService = "DateService";

	/** Set DateService	  */
	public void setDateService (Timestamp DateService);

	/** Get DateService	  */
	public Timestamp getDateService();

    /** Column name DayExam */
    public static final String COLUMNNAME_DayExam = "DayExam";

	/** Set DayExam	  */
	public void setDayExam (int DayExam);

	/** Get DayExam	  */
	public int getDayExam();

    /** Column name DayUse */
    public static final String COLUMNNAME_DayUse = "DayUse";

	/** Set DayUse	  */
	public void setDayUse (int DayUse);

	/** Get DayUse	  */
	public int getDayUse();

    /** Column name Debt */
    public static final String COLUMNNAME_Debt = "Debt";

	/** Set Debt	  */
	public void setDebt (BigDecimal Debt);

	/** Get Debt	  */
	public BigDecimal getDebt();

    /** Column name DefineService */
    public static final String COLUMNNAME_DefineService = "DefineService";

	/** Set DefineService	  */
	public void setDefineService (String DefineService);

	/** Get DefineService	  */
	public String getDefineService();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Diagnostic */
    public static final String COLUMNNAME_Diagnostic = "Diagnostic";

	/** Set Diagnostic	  */
	public void setDiagnostic (String Diagnostic);

	/** Get Diagnostic	  */
	public String getDiagnostic();

    /** Column name DiagnosticCLS */
    public static final String COLUMNNAME_DiagnosticCLS = "DiagnosticCLS";

	/** Set DiagnosticCLS	  */
	public void setDiagnosticCLS (String DiagnosticCLS);

	/** Get DiagnosticCLS	  */
	public String getDiagnosticCLS();

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EndExam */
    public static final String COLUMNNAME_EndExam = "EndExam";

	/** Set EndExam	  */
	public void setEndExam (String EndExam);

	/** Get EndExam	  */
	public String getEndExam();

    /** Column name Examination */
    public static final String COLUMNNAME_Examination = "Examination";

	/** Set Examination	  */
	public void setExamination (String Examination);

	/** Get Examination	  */
	public String getExamination();

    /** Column name ExportMedicine */
    public static final String COLUMNNAME_ExportMedicine = "ExportMedicine";

	/** Set Export Medicine	  */
	public void setExportMedicine (String ExportMedicine);

	/** Get Export Medicine	  */
	public String getExportMedicine();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name Height */
    public static final String COLUMNNAME_Height = "Height";

	/** Set Height	  */
	public void setHeight (BigDecimal Height);

	/** Get Height	  */
	public BigDecimal getHeight();

    /** Column name historyNo */
    public static final String COLUMNNAME_historyNo = "historyNo";

	/** Set historyNo	  */
	public void sethistoryNo (String historyNo);

	/** Get historyNo	  */
	public String gethistoryNo();

    /** Column name HistoryPatient */
    public static final String COLUMNNAME_HistoryPatient = "HistoryPatient";

	/** Set HistoryPatient	  */
	public void setHistoryPatient (String HistoryPatient);

	/** Get HistoryPatient	  */
	public String getHistoryPatient();

    /** Column name HM_DiseaseCode_ID */
    public static final String COLUMNNAME_HM_DiseaseCode_ID = "HM_DiseaseCode_ID";

	/** Set Disease Code	  */
	public void setHM_DiseaseCode_ID (int HM_DiseaseCode_ID);

	/** Get Disease Code	  */
	public int getHM_DiseaseCode_ID();

	public eone.base.model.I_HM_DiseaseCode getHM_DiseaseCode() throws RuntimeException;

    /** Column name HM_ICD10_ID */
    public static final String COLUMNNAME_HM_ICD10_ID = "HM_ICD10_ID";

	/** Set ICD10 List	  */
	public void setHM_ICD10_ID (int HM_ICD10_ID);

	/** Get ICD10 List	  */
	public int getHM_ICD10_ID();

	public eone.base.model.I_HM_ICD10 getHM_ICD10() throws RuntimeException;

    /** Column name HM_Parts_ID */
    public static final String COLUMNNAME_HM_Parts_ID = "HM_Parts_ID";

	/** Set Parts	  */
	public void setHM_Parts_ID (int HM_Parts_ID);

	/** Get Parts	  */
	public int getHM_Parts_ID();

	public eone.base.model.I_HM_Parts getHM_Parts() throws RuntimeException;

    /** Column name HM_Patient_ID */
    public static final String COLUMNNAME_HM_Patient_ID = "HM_Patient_ID";

	/** Set Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID);

	/** Get Patient	  */
	public int getHM_Patient_ID();

	public eone.base.model.I_HM_Patient getHM_Patient() throws RuntimeException;

    /** Column name HM_PatientRegister_ID */
    public static final String COLUMNNAME_HM_PatientRegister_ID = "HM_PatientRegister_ID";

	/** Set Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID);

	/** Get Patient Register	  */
	public int getHM_PatientRegister_ID();

    /** Column name HR_Employee_CLS_ID */
    public static final String COLUMNNAME_HR_Employee_CLS_ID = "HR_Employee_CLS_ID";

	/** Set HR_Employee_CLS_ID	  */
	public void setHR_Employee_CLS_ID (int HR_Employee_CLS_ID);

	/** Get HR_Employee_CLS_ID	  */
	public int getHR_Employee_CLS_ID();

	public eone.base.model.I_HR_Employee getHR_Employee_CLS() throws RuntimeException;

    /** Column name HR_Employee_ID */
    public static final String COLUMNNAME_HR_Employee_ID = "HR_Employee_ID";

	/** Set Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID);

	/** Get Employee	  */
	public int getHR_Employee_ID();

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException;

    /** Column name HR_Employee_KT_ID */
    public static final String COLUMNNAME_HR_Employee_KT_ID = "HR_Employee_KT_ID";

	/** Set HR_Employee KT	  */
	public void setHR_Employee_KT_ID (int HR_Employee_KT_ID);

	/** Get HR_Employee KT	  */
	public int getHR_Employee_KT_ID();

	public eone.base.model.I_HR_Employee getHR_Employee_KT() throws RuntimeException;

    /** Column name HR_Employee_KTV_ID */
    public static final String COLUMNNAME_HR_Employee_KTV_ID = "HR_Employee_KTV_ID";

	/** Set HR_Employee KTV	  */
	public void setHR_Employee_KTV_ID (int HR_Employee_KTV_ID);

	/** Get HR_Employee KTV	  */
	public int getHR_Employee_KTV_ID();

	public eone.base.model.I_HR_Employee getHR_Employee_KTV() throws RuntimeException;

    /** Column name HR_Employee_User_ID */
    public static final String COLUMNNAME_HR_Employee_User_ID = "HR_Employee_User_ID";

	/** Set Employee User	  */
	public void setHR_Employee_User_ID (int HR_Employee_User_ID);

	/** Get Employee User	  */
	public int getHR_Employee_User_ID();

	public eone.base.model.I_HR_Employee getHR_Employee_User() throws RuntimeException;

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

    /** Column name IsDestroy */
    public static final String COLUMNNAME_IsDestroy = "IsDestroy";

	/** Set Destroy	  */
	public void setIsDestroy (boolean IsDestroy);

	/** Get Destroy	  */
	public boolean isDestroy();

    /** Column name IsFinish */
    public static final String COLUMNNAME_IsFinish = "IsFinish";

	/** Set Finish	  */
	public void setIsFinish (boolean IsFinish);

	/** Get Finish	  */
	public boolean isFinish();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public eone.base.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public eone.base.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name No */
    public static final String COLUMNNAME_No = "No";

	/** Set No	  */
	public void setNo (int No);

	/** Get No	  */
	public int getNo();

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

    /** Column name Preathing */
    public static final String COLUMNNAME_Preathing = "Preathing";

	/** Set Preathing	  */
	public void setPreathing (int Preathing);

	/** Get Preathing	  */
	public int getPreathing();

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

    /** Column name Prescription */
    public static final String COLUMNNAME_Prescription = "Prescription";

	/** Set Prescription	  */
	public void setPrescription (String Prescription);

	/** Get Prescription	  */
	public String getPrescription();

    /** Column name PrescriptionDate */
    public static final String COLUMNNAME_PrescriptionDate = "PrescriptionDate";

	/** Set PrescriptionDate	  */
	public void setPrescriptionDate (Timestamp PrescriptionDate);

	/** Get PrescriptionDate	  */
	public Timestamp getPrescriptionDate();

    /** Column name PrescriptionNo */
    public static final String COLUMNNAME_PrescriptionNo = "PrescriptionNo";

	/** Set PrescriptionNo	  */
	public void setPrescriptionNo (String PrescriptionNo);

	/** Get PrescriptionNo	  */
	public String getPrescriptionNo();

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name PrintService */
    public static final String COLUMNNAME_PrintService = "PrintService";

	/** Set PrintService	  */
	public void setPrintService (String PrintService);

	/** Get PrintService	  */
	public String getPrintService();

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

    /** Column name ReasonText */
    public static final String COLUMNNAME_ReasonText = "ReasonText";

	/** Set ReasonText	  */
	public void setReasonText (String ReasonText);

	/** Get ReasonText	  */
	public String getReasonText();

    /** Column name ResultCLS */
    public static final String COLUMNNAME_ResultCLS = "ResultCLS";

	/** Set ResultCLS	  */
	public void setResultCLS (String ResultCLS);

	/** Get ResultCLS	  */
	public String getResultCLS();

    /** Column name SelectService */
    public static final String COLUMNNAME_SelectService = "SelectService";

	/** Set SelectService	  */
	public void setSelectService (String SelectService);

	/** Get SelectService	  */
	public String getSelectService();

    /** Column name ServiceNo */
    public static final String COLUMNNAME_ServiceNo = "ServiceNo";

	/** Set ServiceNo	  */
	public void setServiceNo (String ServiceNo);

	/** Get ServiceNo	  */
	public String getServiceNo();

    /** Column name Temperature */
    public static final String COLUMNNAME_Temperature = "Temperature";

	/** Set Temperature	  */
	public void setTemperature (int Temperature);

	/** Get Temperature	  */
	public int getTemperature();

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

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (int Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public int getWeight();
}
