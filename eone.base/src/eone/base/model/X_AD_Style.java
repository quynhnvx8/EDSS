/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Style
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Style extends PO implements I_AD_Style, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_Style (Properties ctx, int AD_Style_ID, String trxName)
    {
      super (ctx, AD_Style_ID, trxName);
      /** if (AD_Style_ID == 0)
        {
			setAD_Style_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Style (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Style[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Style.
		@param AD_Style_ID 
		CSS style for field and label
	  */
	public void setAD_Style_ID (int AD_Style_ID)
	{
		if (AD_Style_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Style_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Style_ID, Integer.valueOf(AD_Style_ID));
	}

	/** Get Style.
		@return CSS style for field and label
	  */
	public int getAD_Style_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Style_ID);
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
}