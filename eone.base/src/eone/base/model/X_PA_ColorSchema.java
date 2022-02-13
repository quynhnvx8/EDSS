/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.KeyNamePair;

/** Generated Model for PA_ColorSchema
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_PA_ColorSchema extends PO implements I_PA_ColorSchema, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220113L;

    /** Standard Constructor */
    public X_PA_ColorSchema (Properties ctx, int PA_ColorSchema_ID, String trxName)
    {
      super (ctx, PA_ColorSchema_ID, trxName);
      /** if (PA_ColorSchema_ID == 0)
        {
			setMark1Percent (0);
			setMark2Percent (0);
			setName (null);
			setPA_ColorSchema_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PA_ColorSchema (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuilder sb = new StringBuilder ("X_PA_ColorSchema[")
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

	/** Set Mark 1 Percent.
		@param Mark1Percent 
		Percentage up to this color is used
	  */
	public void setMark1Percent (int Mark1Percent)
	{
		set_Value (COLUMNNAME_Mark1Percent, Integer.valueOf(Mark1Percent));
	}

	/** Get Mark 1 Percent.
		@return Percentage up to this color is used
	  */
	public int getMark1Percent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Mark1Percent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mark 2 Percent.
		@param Mark2Percent 
		Percentage up to this color is used
	  */
	public void setMark2Percent (int Mark2Percent)
	{
		set_Value (COLUMNNAME_Mark2Percent, Integer.valueOf(Mark2Percent));
	}

	/** Get Mark 2 Percent.
		@return Percentage up to this color is used
	  */
	public int getMark2Percent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Mark2Percent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mark 3 Percent.
		@param Mark3Percent 
		Percentage up to this color is used
	  */
	public void setMark3Percent (int Mark3Percent)
	{
		set_Value (COLUMNNAME_Mark3Percent, Integer.valueOf(Mark3Percent));
	}

	/** Get Mark 3 Percent.
		@return Percentage up to this color is used
	  */
	public int getMark3Percent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Mark3Percent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mark 4 Percent.
		@param Mark4Percent 
		Percentage up to this color is used
	  */
	public void setMark4Percent (int Mark4Percent)
	{
		set_Value (COLUMNNAME_Mark4Percent, Integer.valueOf(Mark4Percent));
	}

	/** Get Mark 4 Percent.
		@return Percentage up to this color is used
	  */
	public int getMark4Percent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Mark4Percent);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Color Schema.
		@param PA_ColorSchema_ID 
		Performance Color Schema
	  */
	public void setPA_ColorSchema_ID (int PA_ColorSchema_ID)
	{
		if (PA_ColorSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_ColorSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_ColorSchema_ID, Integer.valueOf(PA_ColorSchema_ID));
	}

	/** Get Color Schema.
		@return Performance Color Schema
	  */
	public int getPA_ColorSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ColorSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}