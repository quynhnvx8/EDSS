/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for AD_User
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_AD_User 
{

    /** TableName=AD_User */
    public static final String Table_Name = "AD_User";

    /** AD_Table_ID=114 */
    public static final int Table_ID = 114;

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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

    /** Column name Answer */
    public static final String COLUMNNAME_Answer = "Answer";

	/** Set Answer	  */
	public void setAnswer (String Answer);

	/** Get Answer	  */
	public String getAnswer();

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

    /** Column name DateAccountLocked */
    public static final String COLUMNNAME_DateAccountLocked = "DateAccountLocked";

	/** Set Date Account Locked	  */
	public void setDateAccountLocked (Timestamp DateAccountLocked);

	/** Get Date Account Locked	  */
	public Timestamp getDateAccountLocked();

    /** Column name DateLastLogin */
    public static final String COLUMNNAME_DateLastLogin = "DateLastLogin";

	/** Set Date Last Login	  */
	public void setDateLastLogin (Timestamp DateLastLogin);

	/** Get Date Last Login	  */
	public Timestamp getDateLastLogin();

    /** Column name DatePasswordChanged */
    public static final String COLUMNNAME_DatePasswordChanged = "DatePasswordChanged";

	/** Set Date Password Changed	  */
	public void setDatePasswordChanged (Timestamp DatePasswordChanged);

	/** Get Date Password Changed	  */
	public Timestamp getDatePasswordChanged();

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

    /** Column name EMailUser */
    public static final String COLUMNNAME_EMailUser = "EMailUser";

	/** Set EMail User ID.
	  * User Name (ID) in the Mail System
	  */
	public void setEMailUser (String EMailUser);

	/** Get EMail User ID.
	  * User Name (ID) in the Mail System
	  */
	public String getEMailUser();

    /** Column name EMailUserPW */
    public static final String COLUMNNAME_EMailUserPW = "EMailUserPW";

	/** Set EMail User Password.
	  * Password of your email user id
	  */
	public void setEMailUserPW (String EMailUserPW);

	/** Get EMail User Password.
	  * Password of your email user id
	  */
	public String getEMailUserPW();

    /** Column name FailedLoginCount */
    public static final String COLUMNNAME_FailedLoginCount = "FailedLoginCount";

	/** Set Failed Login Count	  */
	public void setFailedLoginCount (int FailedLoginCount);

	/** Get Failed Login Count	  */
	public int getFailedLoginCount();

    /** Column name HR_Employee_ID */
    public static final String COLUMNNAME_HR_Employee_ID = "HR_Employee_ID";

	/** Set Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID);

	/** Get Employee	  */
	public int getHR_Employee_ID();

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException;

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

    /** Column name IsChangePrice */
    public static final String COLUMNNAME_IsChangePrice = "IsChangePrice";

	/** Set Change Price.
	  * Change Price
	  */
	public void setIsChangePrice (boolean IsChangePrice);

	/** Get Change Price.
	  * Change Price
	  */
	public boolean isChangePrice();

    /** Column name IsExpired */
    public static final String COLUMNNAME_IsExpired = "IsExpired";

	/** Set Expired	  */
	public void setIsExpired (boolean IsExpired);

	/** Get Expired	  */
	public boolean isExpired();

    /** Column name IsLocked */
    public static final String COLUMNNAME_IsLocked = "IsLocked";

	/** Set Locked	  */
	public void setIsLocked (boolean IsLocked);

	/** Get Locked	  */
	public boolean isLocked();

    /** Column name IsManyLogin */
    public static final String COLUMNNAME_IsManyLogin = "IsManyLogin";

	/** Set ManyLogin	  */
	public void setIsManyLogin (boolean IsManyLogin);

	/** Get ManyLogin	  */
	public boolean isManyLogin();

    /** Column name IsNoExpire */
    public static final String COLUMNNAME_IsNoExpire = "IsNoExpire";

	/** Set No Expire	  */
	public void setIsNoExpire (boolean IsNoExpire);

	/** Get No Expire	  */
	public boolean isNoExpire();

    /** Column name IsUserAdmin */
    public static final String COLUMNNAME_IsUserAdmin = "IsUserAdmin";

	/** Set IsUserAdmin	  */
	public void setIsUserAdmin (boolean IsUserAdmin);

	/** Get IsUserAdmin	  */
	public boolean isUserAdmin();

    /** Column name IsUserSystem */
    public static final String COLUMNNAME_IsUserSystem = "IsUserSystem";

	/** Set IsUserSystem	  */
	public void setIsUserSystem (boolean IsUserSystem);

	/** Get IsUserSystem	  */
	public boolean isUserSystem();

    /** Column name LDAPUser */
    public static final String COLUMNNAME_LDAPUser = "LDAPUser";

	/** Set LDAP User Name.
	  * User Name used for authorization via LDAP (directory) services
	  */
	public void setLDAPUser (String LDAPUser);

	/** Get LDAP User Name.
	  * User Name used for authorization via LDAP (directory) services
	  */
	public String getLDAPUser();

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

    /** Column name NotificationType */
    public static final String COLUMNNAME_NotificationType = "NotificationType";

	/** Set Notification Type.
	  * Type of Notifications
	  */
	public void setNotificationType (String NotificationType);

	/** Get Notification Type.
	  * Type of Notifications
	  */
	public String getNotificationType();

    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/** Set Password.
	  * Password of any length (case sensitive)
	  */
	public void setPassword (String Password);

	/** Get Password.
	  * Password of any length (case sensitive)
	  */
	public String getPassword();

    /** Column name Salt */
    public static final String COLUMNNAME_Salt = "Salt";

	/** Set Salt.
	  * Random data added to improve password hash effectiveness
	  */
	public void setSalt (String Salt);

	/** Get Salt.
	  * Random data added to improve password hash effectiveness
	  */
	public String getSalt();

    /** Column name SecurityQuestion */
    public static final String COLUMNNAME_SecurityQuestion = "SecurityQuestion";

	/** Set Security Question	  */
	public void setSecurityQuestion (String SecurityQuestion);

	/** Get Security Question	  */
	public String getSecurityQuestion();

    /** Column name SessionID */
    public static final String COLUMNNAME_SessionID = "SessionID";

	/** Set Session ID	  */
	public void setSessionID (String SessionID);

	/** Get Session ID	  */
	public String getSessionID();

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
