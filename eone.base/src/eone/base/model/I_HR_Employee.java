/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for HR_Employee
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HR_Employee 
{

    /** TableName=HR_Employee */
    public static final String Table_Name = "HR_Employee";

    /** AD_Table_ID=53086 */
    public static final int Table_ID = 53086;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Department_ID */
    public static final String COLUMNNAME_AD_Department_ID = "AD_Department_ID";

	/** Set Department	  */
	public void setAD_Department_ID (int AD_Department_ID);

	/** Get Department	  */
	public int getAD_Department_ID();

	public eone.base.model.I_AD_Department getAD_Department() throws RuntimeException;

    /** Column name AD_Image_ID */
    public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

	/** Set Image.
	  * Image or Icon
	  */
	public void setAD_Image_ID (int AD_Image_ID);

	/** Get Image.
	  * Image or Icon
	  */
	public int getAD_Image_ID();

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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

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

    /** Column name BirthPlace */
    public static final String COLUMNNAME_BirthPlace = "BirthPlace";

	/** Set BirthPlace	  */
	public void setBirthPlace (String BirthPlace);

	/** Get BirthPlace	  */
	public String getBirthPlace();

    /** Column name BloodType */
    public static final String COLUMNNAME_BloodType = "BloodType";

	/** Set BloodType	  */
	public void setBloodType (String BloodType);

	/** Get BloodType	  */
	public String getBloodType();

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

    /** Column name CardNumber */
    public static final String COLUMNNAME_CardNumber = "CardNumber";

	/** Set CardNumber	  */
	public void setCardNumber (String CardNumber);

	/** Get CardNumber	  */
	public String getCardNumber();

    /** Column name Code */
    public static final String COLUMNNAME_Code = "Code";

	/** Set Code.
	  * Validation Code
	  */
	public void setCode (String Code);

	/** Get Code.
	  * Validation Code
	  */
	public String getCode();

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

    /** Column name DateIssue */
    public static final String COLUMNNAME_DateIssue = "DateIssue";

	/** Set DateIssue	  */
	public void setDateIssue (Timestamp DateIssue);

	/** Get DateIssue	  */
	public Timestamp getDateIssue();

    /** Column name DatePayroll */
    public static final String COLUMNNAME_DatePayroll = "DatePayroll";

	/** Set DatePayroll	  */
	public void setDatePayroll (Timestamp DatePayroll);

	/** Get DatePayroll	  */
	public Timestamp getDatePayroll();

    /** Column name DateTest */
    public static final String COLUMNNAME_DateTest = "DateTest";

	/** Set DateTest	  */
	public void setDateTest (Timestamp DateTest);

	/** Get DateTest	  */
	public Timestamp getDateTest();

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

    /** Column name Education */
    public static final String COLUMNNAME_Education = "Education";

	/** Set Education.
	  * Education
	  */
	public void setEducation (String Education);

	/** Get Education.
	  * Education
	  */
	public String getEducation();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name Healthy */
    public static final String COLUMNNAME_Healthy = "Healthy";

	/** Set Healthy	  */
	public void setHealthy (String Healthy);

	/** Get Healthy	  */
	public String getHealthy();

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

    /** Column name HomeTown */
    public static final String COLUMNNAME_HomeTown = "HomeTown";

	/** Set HomeTown	  */
	public void setHomeTown (String HomeTown);

	/** Get HomeTown	  */
	public String getHomeTown();

    /** Column name HR_Employee_ID */
    public static final String COLUMNNAME_HR_Employee_ID = "HR_Employee_ID";

	/** Set Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID);

	/** Get Employee	  */
	public int getHR_Employee_ID();

    /** Column name HR_ItemLine_05_ID */
    public static final String COLUMNNAME_HR_ItemLine_05_ID = "HR_ItemLine_05_ID";

	/** Set Jobs	  */
	public void setHR_ItemLine_05_ID (int HR_ItemLine_05_ID);

	/** Get Jobs	  */
	public int getHR_ItemLine_05_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_05() throws RuntimeException;

    /** Column name HR_ItemLine_06_ID */
    public static final String COLUMNNAME_HR_ItemLine_06_ID = "HR_ItemLine_06_ID";

	/** Set Position	  */
	public void setHR_ItemLine_06_ID (int HR_ItemLine_06_ID);

	/** Get Position	  */
	public int getHR_ItemLine_06_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_06() throws RuntimeException;

    /** Column name HR_ItemLine_09_ID */
    public static final String COLUMNNAME_HR_ItemLine_09_ID = "HR_ItemLine_09_ID";

	/** Set Nation	  */
	public void setHR_ItemLine_09_ID (int HR_ItemLine_09_ID);

	/** Get Nation	  */
	public int getHR_ItemLine_09_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_09() throws RuntimeException;

    /** Column name HR_ItemLine_12_ID */
    public static final String COLUMNNAME_HR_ItemLine_12_ID = "HR_ItemLine_12_ID";

	/** Set Training Form	  */
	public void setHR_ItemLine_12_ID (int HR_ItemLine_12_ID);

	/** Get Training Form	  */
	public int getHR_ItemLine_12_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_12() throws RuntimeException;

    /** Column name HR_ItemLine_18_ID */
    public static final String COLUMNNAME_HR_ItemLine_18_ID = "HR_ItemLine_18_ID";

	/** Set Training Level	  */
	public void setHR_ItemLine_18_ID (int HR_ItemLine_18_ID);

	/** Get Training Level	  */
	public int getHR_ItemLine_18_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_18() throws RuntimeException;

    /** Column name HR_ItemLine_28_ID */
    public static final String COLUMNNAME_HR_ItemLine_28_ID = "HR_ItemLine_28_ID";

	/** Set Item Line 28	  */
	public void setHR_ItemLine_28_ID (int HR_ItemLine_28_ID);

	/** Get Item Line 28	  */
	public int getHR_ItemLine_28_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_28() throws RuntimeException;

    /** Column name HR_ItemLine_29_ID */
    public static final String COLUMNNAME_HR_ItemLine_29_ID = "HR_ItemLine_29_ID";

	/** Set National	  */
	public void setHR_ItemLine_29_ID (int HR_ItemLine_29_ID);

	/** Get National	  */
	public int getHR_ItemLine_29_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_29() throws RuntimeException;

    /** Column name HR_ItemLine_30_ID */
    public static final String COLUMNNAME_HR_ItemLine_30_ID = "HR_ItemLine_30_ID";

	/** Set Item Line 30	  */
	public void setHR_ItemLine_30_ID (int HR_ItemLine_30_ID);

	/** Get Item Line 30	  */
	public int getHR_ItemLine_30_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_30() throws RuntimeException;

    /** Column name HR_ItemLine_31_ID */
    public static final String COLUMNNAME_HR_ItemLine_31_ID = "HR_ItemLine_31_ID";

	/** Set Item Line 31	  */
	public void setHR_ItemLine_31_ID (int HR_ItemLine_31_ID);

	/** Get Item Line 31	  */
	public int getHR_ItemLine_31_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_31() throws RuntimeException;

    /** Column name HR_ItemLine_32_ID */
    public static final String COLUMNNAME_HR_ItemLine_32_ID = "HR_ItemLine_32_ID";

	/** Set Politic Level	  */
	public void setHR_ItemLine_32_ID (int HR_ItemLine_32_ID);

	/** Get Politic Level	  */
	public int getHR_ItemLine_32_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_32() throws RuntimeException;

    /** Column name HR_ItemLine_39_ID */
    public static final String COLUMNNAME_HR_ItemLine_39_ID = "HR_ItemLine_39_ID";

	/** Set Marital Status	  */
	public void setHR_ItemLine_39_ID (int HR_ItemLine_39_ID);

	/** Get Marital Status	  */
	public int getHR_ItemLine_39_ID();

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_39() throws RuntimeException;

    /** Column name ImageURL */
    public static final String COLUMNNAME_ImageURL = "ImageURL";

	/** Set Image URL.
	  * URL of  image
	  */
	public void setImageURL (String ImageURL);

	/** Get Image URL.
	  * URL of  image
	  */
	public String getImageURL();

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

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name OfficialPartyDate */
    public static final String COLUMNNAME_OfficialPartyDate = "OfficialPartyDate";

	/** Set Official Party Date	  */
	public void setOfficialPartyDate (Timestamp OfficialPartyDate);

	/** Get Official Party Date	  */
	public Timestamp getOfficialPartyDate();

    /** Column name PartyBadgeNo */
    public static final String COLUMNNAME_PartyBadgeNo = "PartyBadgeNo";

	/** Set Party Badge No	  */
	public void setPartyBadgeNo (String PartyBadgeNo);

	/** Get Party Badge No	  */
	public String getPartyBadgeNo();

    /** Column name PartyCardPlace */
    public static final String COLUMNNAME_PartyCardPlace = "PartyCardPlace";

	/** Set Party Card Place	  */
	public void setPartyCardPlace (String PartyCardPlace);

	/** Get Party Card Place	  */
	public String getPartyCardPlace();

    /** Column name PartyDate */
    public static final String COLUMNNAME_PartyDate = "PartyDate";

	/** Set Party Date	  */
	public void setPartyDate (Timestamp PartyDate);

	/** Get Party Date	  */
	public Timestamp getPartyDate();

    /** Column name partyNo */
    public static final String COLUMNNAME_partyNo = "partyNo";

	/** Set partyNo.
	  * Alphanumeric identifier of the entity
	  */
	public void setpartyNo (String partyNo);

	/** Get partyNo.
	  * Alphanumeric identifier of the entity
	  */
	public String getpartyNo();

    /** Column name PartyPlace */
    public static final String COLUMNNAME_PartyPlace = "PartyPlace";

	/** Set Party Place	  */
	public void setPartyPlace (String PartyPlace);

	/** Get Party Place	  */
	public String getPartyPlace();

    /** Column name PartyRecPlace */
    public static final String COLUMNNAME_PartyRecPlace = "PartyRecPlace";

	/** Set Party Rec Place	  */
	public void setPartyRecPlace (String PartyRecPlace);

	/** Get Party Rec Place	  */
	public String getPartyRecPlace();

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

    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

	/** Set 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2);

	/** Get 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public String getPhone2();

    /** Column name PlaceIssue */
    public static final String COLUMNNAME_PlaceIssue = "PlaceIssue";

	/** Set PlaceIssue	  */
	public void setPlaceIssue (String PlaceIssue);

	/** Get PlaceIssue	  */
	public String getPlaceIssue();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name Surname */
    public static final String COLUMNNAME_Surname = "Surname";

	/** Set Surname.
	  * Alphanumeric identifier of the entity
	  */
	public void setSurname (String Surname);

	/** Get Surname.
	  * Alphanumeric identifier of the entity
	  */
	public String getSurname();

    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

	/** Set Tax ID.
	  * Tax Identification
	  */
	public void setTaxID (String TaxID);

	/** Get Tax ID.
	  * Tax Identification
	  */
	public String getTaxID();

    /** Column name UnionDate */
    public static final String COLUMNNAME_UnionDate = "UnionDate";

	/** Set Union Date	  */
	public void setUnionDate (Timestamp UnionDate);

	/** Get Union Date	  */
	public Timestamp getUnionDate();

    /** Column name UnionPlace */
    public static final String COLUMNNAME_UnionPlace = "UnionPlace";

	/** Set Union Place	  */
	public void setUnionPlace (String UnionPlace);

	/** Get Union Place	  */
	public String getUnionPlace();

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

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set User Name	  */
	public void setUserName (String UserName);

	/** Get User Name	  */
	public String getUserName();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
