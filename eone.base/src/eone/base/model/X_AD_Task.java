/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.KeyNamePair;

/** Generated Model for AD_Task
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Task extends PO implements I_AD_Task, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_Task (Properties ctx, int AD_Task_ID, String trxName)
    {
      super (ctx, AD_Task_ID, trxName);
      /** if (AD_Task_ID == 0)
        {
			setAccessLevel (null);
			setAD_Task_ID (0);
			setIsServerProcess (false);
// N
			setName (null);
			setOS_Command (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Task (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuilder sb = new StringBuilder ("X_AD_Task[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** AccessLevel AD_Reference_ID=5 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** Client+Organization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** System only = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** System+Client = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** Client only = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	/** Set Data Access Level.
		@param AccessLevel 
		Access Level required
	  */
	public void setAccessLevel (String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	/** Get Data Access Level.
		@return Access Level required
	  */
	public String getAccessLevel () 
	{
		return (String)get_Value(COLUMNNAME_AccessLevel);
	}

	/** Set OS Task.
		@param AD_Task_ID 
		Operation System Task
	  */
	public void setAD_Task_ID (int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Task_ID, Integer.valueOf(AD_Task_ID));
	}

	/** Get OS Task.
		@return Operation System Task
	  */
	public int getAD_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Server Process.
		@param IsServerProcess 
		Run this Process on Server only
	  */
	public void setIsServerProcess (boolean IsServerProcess)
	{
		set_Value (COLUMNNAME_IsServerProcess, Boolean.valueOf(IsServerProcess));
	}

	/** Get Server Process.
		@return Run this Process on Server only
	  */
	public boolean isServerProcess () 
	{
		Object oo = get_Value(COLUMNNAME_IsServerProcess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set OS Command.
		@param OS_Command 
		Operating System Command
	  */
	public void setOS_Command (String OS_Command)
	{
		set_Value (COLUMNNAME_OS_Command, OS_Command);
	}

	/** Get OS Command.
		@return Operating System Command
	  */
	public String getOS_Command () 
	{
		return (String)get_Value(COLUMNNAME_OS_Command);
	}
}