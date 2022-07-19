/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ProductGroup
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_M_ProductGroup extends PO implements I_M_ProductGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220701L;

    /** Standard Constructor */
    public X_M_ProductGroup (Properties ctx, int M_ProductGroup_ID, String trxName)
    {
      super (ctx, M_ProductGroup_ID, trxName);
      /** if (M_ProductGroup_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_ProductGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_ProductGroup[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** NONE = 00_NON */
	public static final String CATEGORYTYPE_NONE = "00_NON";
	/** MaterialMain = 01_MAM */
	public static final String CATEGORYTYPE_MaterialMain = "01_MAM";
	/** Asset = 03_ASS */
	public static final String CATEGORYTYPE_Asset = "03_ASS";
	/** Tools = 04_TOO */
	public static final String CATEGORYTYPE_Tools = "04_TOO";
	/** Service = 05_SER */
	public static final String CATEGORYTYPE_Service = "05_SER";
	/** Goods = 06_GOO */
	public static final String CATEGORYTYPE_Goods = "06_GOO";
	/** Stocked = 07_STO */
	public static final String CATEGORYTYPE_Stocked = "07_STO";
	/** Set CategoryType.
		@param CategoryType CategoryType	  */
	public void setCategoryType (String CategoryType)
	{

		set_Value (COLUMNNAME_CategoryType, CategoryType);
	}

	/** Get CategoryType.
		@return CategoryType	  */
	public String getCategoryType () 
	{
		return (String)get_Value(COLUMNNAME_CategoryType);
	}

	/** Set ProductGroup.
		@param M_ProductGroup_ID ProductGroup	  */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, Integer.valueOf(M_ProductGroup_ID));
	}

	/** Get ProductGroup.
		@return ProductGroup	  */
	public int getM_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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