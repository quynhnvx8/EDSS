/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HM_ICD10
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_ICD10 extends PO implements I_HM_ICD10, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210903L;

    /** Standard Constructor */
    public X_HM_ICD10 (Properties ctx, int HM_ICD10_ID, String trxName)
    {
      super (ctx, HM_ICD10_ID, trxName);
      /** if (HM_ICD10_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_ICD10 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_ICD10[")
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

	/** Set ICD10 List.
		@param HM_ICD10_ID ICD10 List	  */
	public void setHM_ICD10_ID (int HM_ICD10_ID)
	{
		if (HM_ICD10_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_ICD10_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_ICD10_ID, Integer.valueOf(HM_ICD10_ID));
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