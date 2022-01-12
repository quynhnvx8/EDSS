/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HM_District
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_District extends PO implements I_HM_District, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210903L;

    /** Standard Constructor */
    public X_HM_District (Properties ctx, int HM_District_ID, String trxName)
    {
      super (ctx, HM_District_ID, trxName);
      /** if (HM_District_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_District (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_District[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

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

	public I_HM_Province getHM_Province() throws RuntimeException
    {
		return (I_HM_Province)MTable.get(getCtx(), I_HM_Province.Table_Name)
			.getPO(getHM_Province_ID(), get_TrxName());	}

	/** Set Province.
		@param HM_Province_ID Province	  */
	public void setHM_Province_ID (int HM_Province_ID)
	{
		if (HM_Province_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_Province_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_Province_ID, Integer.valueOf(HM_Province_ID));
	}

	/** Get Province.
		@return Province	  */
	public int getHM_Province_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Province_ID);
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