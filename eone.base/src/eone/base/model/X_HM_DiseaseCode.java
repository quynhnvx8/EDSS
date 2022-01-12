/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HM_DiseaseCode
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_DiseaseCode extends PO implements I_HM_DiseaseCode, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210916L;

    /** Standard Constructor */
    public X_HM_DiseaseCode (Properties ctx, int HM_DiseaseCode_ID, String trxName)
    {
      super (ctx, HM_DiseaseCode_ID, trxName);
      /** if (HM_DiseaseCode_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_DiseaseCode (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_DiseaseCode[")
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

	/** Set Disease Code.
		@param HM_DiseaseCode_ID Disease Code	  */
	public void setHM_DiseaseCode_ID (int HM_DiseaseCode_ID)
	{
		if (HM_DiseaseCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_DiseaseCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_DiseaseCode_ID, Integer.valueOf(HM_DiseaseCode_ID));
	}

	/** Get Disease Code.
		@return Disease Code	  */
	public int getHM_DiseaseCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_DiseaseCode_ID);
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