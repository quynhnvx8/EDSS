/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HM_PatientSource
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_PatientSource extends PO implements I_HM_PatientSource, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210903L;

    /** Standard Constructor */
    public X_HM_PatientSource (Properties ctx, int HM_PatientSource_ID, String trxName)
    {
      super (ctx, HM_PatientSource_ID, trxName);
      /** if (HM_PatientSource_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_PatientSource (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_PatientSource[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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

	/** Set PatientSource.
		@param HM_PatientSource_ID PatientSource	  */
	public void setHM_PatientSource_ID (int HM_PatientSource_ID)
	{
		if (HM_PatientSource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientSource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientSource_ID, Integer.valueOf(HM_PatientSource_ID));
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