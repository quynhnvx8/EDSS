/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.KeyNamePair;

/** Generated Model for AD_SysConfig
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_SysConfig extends PO implements I_AD_SysConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_SysConfig (Properties ctx, int AD_SysConfig_ID, String trxName)
    {
      super (ctx, AD_SysConfig_ID, trxName);
      /** if (AD_SysConfig_ID == 0)
        {
			setAD_SysConfig_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_SysConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_SysConfig[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set System Configurator.
		@param AD_SysConfig_ID System Configurator	  */
	public void setAD_SysConfig_ID (int AD_SysConfig_ID)
	{
		if (AD_SysConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SysConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SysConfig_ID, Integer.valueOf(AD_SysConfig_ID));
	}

	/** Get System Configurator.
		@return System Configurator	  */
	public int getAD_SysConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SysConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ConfigurationLevel AD_Reference_ID=53222 */
	public static final int CONFIGURATIONLEVEL_AD_Reference_ID=53222;
	/** System = S */
	public static final String CONFIGURATIONLEVEL_System = "S";
	/** Client = C */
	public static final String CONFIGURATIONLEVEL_Client = "C";
	/** Organization = O */
	public static final String CONFIGURATIONLEVEL_Organization = "O";
	/** Set Configuration Level.
		@param ConfigurationLevel 
		Configuration Level for this parameter
	  */
	public void setConfigurationLevel (String ConfigurationLevel)
	{

		set_Value (COLUMNNAME_ConfigurationLevel, ConfigurationLevel);
	}

	/** Get Configuration Level.
		@return Configuration Level for this parameter
	  */
	public String getConfigurationLevel () 
	{
		return (String)get_Value(COLUMNNAME_ConfigurationLevel);
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