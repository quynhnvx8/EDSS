/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.KeyNamePair;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for AD_User
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_User extends PO implements I_AD_User, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220511L;

    /** Standard Constructor */
    public X_AD_User (Properties ctx, int AD_User_ID, String trxName)
    {
      super (ctx, AD_User_ID, trxName);
      /** if (AD_User_ID == 0)
        {
			setAD_User_ID (0);
			setFailedLoginCount (0);
// 0
			setIsExpired (false);
// N
			setIsLocked (false);
// 'N'
			setIsNoExpire (false);
// N
			setName (null);
			setNotificationType (null);
// X
        } */
    }

    /** Load Constructor */
    public X_AD_User (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuilder sb = new StringBuilder ("X_AD_User[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Image.
		@param AD_Image_ID 
		Image or Icon
	  */
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Image.
		@return Image or Icon
	  */
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Answer.
		@param Answer Answer	  */
	public void setAnswer (String Answer)
	{
		set_Value (COLUMNNAME_Answer, Answer);
	}

	/** Get Answer.
		@return Answer	  */
	public String getAnswer () 
	{
		return (String)get_Value(COLUMNNAME_Answer);
	}

	/** Set Date Account Locked.
		@param DateAccountLocked Date Account Locked	  */
	public void setDateAccountLocked (Timestamp DateAccountLocked)
	{
		set_Value (COLUMNNAME_DateAccountLocked, DateAccountLocked);
	}

	/** Get Date Account Locked.
		@return Date Account Locked	  */
	public Timestamp getDateAccountLocked () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAccountLocked);
	}

	/** Set Date Last Login.
		@param DateLastLogin Date Last Login	  */
	public void setDateLastLogin (Timestamp DateLastLogin)
	{
		set_Value (COLUMNNAME_DateLastLogin, DateLastLogin);
	}

	/** Get Date Last Login.
		@return Date Last Login	  */
	public Timestamp getDateLastLogin () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastLogin);
	}

	/** Set Date Password Changed.
		@param DatePasswordChanged Date Password Changed	  */
	public void setDatePasswordChanged (Timestamp DatePasswordChanged)
	{
		set_Value (COLUMNNAME_DatePasswordChanged, DatePasswordChanged);
	}

	/** Get Date Password Changed.
		@return Date Password Changed	  */
	public Timestamp getDatePasswordChanged () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePasswordChanged);
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

	/** Set EMail.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set EMail User ID.
		@param EMailUser 
		User Name (ID) in the Mail System
	  */
	public void setEMailUser (String EMailUser)
	{
		set_Value (COLUMNNAME_EMailUser, EMailUser);
	}

	/** Get EMail User ID.
		@return User Name (ID) in the Mail System
	  */
	public String getEMailUser () 
	{
		return (String)get_Value(COLUMNNAME_EMailUser);
	}

	/** Set EMail User Password.
		@param EMailUserPW 
		Password of your email user id
	  */
	public void setEMailUserPW (String EMailUserPW)
	{
		set_Value (COLUMNNAME_EMailUserPW, EMailUserPW);
	}

	/** Get EMail User Password.
		@return Password of your email user id
	  */
	public String getEMailUserPW () 
	{
		return (String)get_Value(COLUMNNAME_EMailUserPW);
	}

	/** Set Failed Login Count.
		@param FailedLoginCount Failed Login Count	  */
	public void setFailedLoginCount (int FailedLoginCount)
	{
		set_Value (COLUMNNAME_FailedLoginCount, Integer.valueOf(FailedLoginCount));
	}

	/** Get Failed Login Count.
		@return Failed Login Count	  */
	public int getFailedLoginCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FailedLoginCount);
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

	/** Set Change Price.
		@param IsChangePrice 
		Change Price
	  */
	public void setIsChangePrice (boolean IsChangePrice)
	{
		set_Value (COLUMNNAME_IsChangePrice, Boolean.valueOf(IsChangePrice));
	}

	/** Get Change Price.
		@return Change Price
	  */
	public boolean isChangePrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsChangePrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Expired.
		@param IsExpired Expired	  */
	public void setIsExpired (boolean IsExpired)
	{
		set_Value (COLUMNNAME_IsExpired, Boolean.valueOf(IsExpired));
	}

	/** Get Expired.
		@return Expired	  */
	public boolean isExpired () 
	{
		Object oo = get_Value(COLUMNNAME_IsExpired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Locked.
		@param IsLocked Locked	  */
	public void setIsLocked (boolean IsLocked)
	{
		set_Value (COLUMNNAME_IsLocked, Boolean.valueOf(IsLocked));
	}

	/** Get Locked.
		@return Locked	  */
	public boolean isLocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsLocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManyLogin.
		@param IsManyLogin ManyLogin	  */
	public void setIsManyLogin (boolean IsManyLogin)
	{
		set_Value (COLUMNNAME_IsManyLogin, Boolean.valueOf(IsManyLogin));
	}

	/** Get ManyLogin.
		@return ManyLogin	  */
	public boolean isManyLogin () 
	{
		Object oo = get_Value(COLUMNNAME_IsManyLogin);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set No Expire.
		@param IsNoExpire No Expire	  */
	public void setIsNoExpire (boolean IsNoExpire)
	{
		set_Value (COLUMNNAME_IsNoExpire, Boolean.valueOf(IsNoExpire));
	}

	/** Get No Expire.
		@return No Expire	  */
	public boolean isNoExpire () 
	{
		Object oo = get_Value(COLUMNNAME_IsNoExpire);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsUserAdmin.
		@param IsUserAdmin IsUserAdmin	  */
	public void setIsUserAdmin (boolean IsUserAdmin)
	{
		set_Value (COLUMNNAME_IsUserAdmin, Boolean.valueOf(IsUserAdmin));
	}

	/** Get IsUserAdmin.
		@return IsUserAdmin	  */
	public boolean isUserAdmin () 
	{
		Object oo = get_Value(COLUMNNAME_IsUserAdmin);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsUserSystem.
		@param IsUserSystem IsUserSystem	  */
	public void setIsUserSystem (boolean IsUserSystem)
	{
		set_Value (COLUMNNAME_IsUserSystem, Boolean.valueOf(IsUserSystem));
	}

	/** Get IsUserSystem.
		@return IsUserSystem	  */
	public boolean isUserSystem () 
	{
		Object oo = get_Value(COLUMNNAME_IsUserSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LDAP User Name.
		@param LDAPUser 
		User Name used for authorization via LDAP (directory) services
	  */
	public void setLDAPUser (String LDAPUser)
	{
		set_Value (COLUMNNAME_LDAPUser, LDAPUser);
	}

	/** Get LDAP User Name.
		@return User Name used for authorization via LDAP (directory) services
	  */
	public String getLDAPUser () 
	{
		return (String)get_Value(COLUMNNAME_LDAPUser);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** NotificationType AD_Reference_ID=344 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=344;
	/** EMail = E */
	public static final String NOTIFICATIONTYPE_EMail = "E";
	/** Notice = N */
	public static final String NOTIFICATIONTYPE_Notice = "N";
	/** None = X */
	public static final String NOTIFICATIONTYPE_None = "X";
	/** EMail+Notice = B */
	public static final String NOTIFICATIONTYPE_EMailPlusNotice = "B";
	/** Set Notification Type.
		@param NotificationType 
		Type of Notifications
	  */
	public void setNotificationType (String NotificationType)
	{

		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	/** Get Notification Type.
		@return Type of Notifications
	  */
	public String getNotificationType () 
	{
		return (String)get_Value(COLUMNNAME_NotificationType);
	}

	/** Set Password.
		@param Password 
		Password of any length (case sensitive)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set Salt.
		@param Salt 
		Random data added to improve password hash effectiveness
	  */
	public void setSalt (String Salt)
	{
		set_ValueNoCheck (COLUMNNAME_Salt, Salt);
	}

	/** Get Salt.
		@return Random data added to improve password hash effectiveness
	  */
	public String getSalt () 
	{
		return (String)get_Value(COLUMNNAME_Salt);
	}

	/** Set Security Question.
		@param SecurityQuestion Security Question	  */
	public void setSecurityQuestion (String SecurityQuestion)
	{
		set_Value (COLUMNNAME_SecurityQuestion, SecurityQuestion);
	}

	/** Get Security Question.
		@return Security Question	  */
	public String getSecurityQuestion () 
	{
		return (String)get_Value(COLUMNNAME_SecurityQuestion);
	}

	/** Set Session ID.
		@param SessionID Session ID	  */
	public void setSessionID (String SessionID)
	{
		set_Value (COLUMNNAME_SessionID, SessionID);
	}

	/** Get Session ID.
		@return Session ID	  */
	public String getSessionID () 
	{
		return (String)get_Value(COLUMNNAME_SessionID);
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