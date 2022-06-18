/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HR_ItemLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_ItemLine extends PO implements I_HR_ItemLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220616L;

    /** Standard Constructor */
    public X_HR_ItemLine (Properties ctx, int HR_ItemLine_ID, String trxName)
    {
      super (ctx, HR_ItemLine_ID, trxName);
      /** if (HR_ItemLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_ItemLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_ItemLine[")
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

	public eone.base.model.I_HR_Item getHR_Item() throws RuntimeException
    {
		return (eone.base.model.I_HR_Item)MTable.get(getCtx(), eone.base.model.I_HR_Item.Table_Name)
			.getPO(getHR_Item_ID(), get_TrxName());	}

	/** Set Item.
		@param HR_Item_ID Item	  */
	public void setHR_Item_ID (int HR_Item_ID)
	{
		if (HR_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Item_ID, Integer.valueOf(HR_Item_ID));
	}

	/** Get Item.
		@return Item	  */
	public int getHR_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Item Line.
		@param HR_ItemLine_ID Item Line	  */
	public void setHR_ItemLine_ID (int HR_ItemLine_ID)
	{
		if (HR_ItemLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_ItemLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_ItemLine_ID, Integer.valueOf(HR_ItemLine_ID));
	}

	/** Get Item Line.
		@return Item Line	  */
	public int getHR_ItemLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name Name	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
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