/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HM_Wards
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_Wards extends PO implements I_HM_Wards, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210903L;

    /** Standard Constructor */
    public X_HM_Wards (Properties ctx, int HM_Wards_ID, String trxName)
    {
      super (ctx, HM_Wards_ID, trxName);
      /** if (HM_Wards_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_Wards (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_Wards[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public I_HM_District getHM_District() throws RuntimeException
    {
		return (I_HM_District)MTable.get(getCtx(), I_HM_District.Table_Name)
			.getPO(getHM_District_ID(), get_TrxName());	}

	/** Set District.
		@param HM_District_ID District	  */
	public void setHM_District_ID (int HM_District_ID)
	{
		if (HM_District_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_District_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_District_ID, Integer.valueOf(HM_District_ID));
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

	/** Set Wards.
		@param HM_Wards_ID Wards	  */
	public void setHM_Wards_ID (int HM_Wards_ID)
	{
		if (HM_Wards_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_Wards_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_Wards_ID, Integer.valueOf(HM_Wards_ID));
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