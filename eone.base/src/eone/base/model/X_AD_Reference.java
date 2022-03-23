/******************************************************************************
 * Product: EOoe ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.KeyNamePair;

/** Generated Model for AD_Reference
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Reference extends PO implements I_AD_Reference, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, int AD_Reference_ID, String trxName)
    {
      super (ctx, AD_Reference_ID, trxName);
      /** if (AD_Reference_ID == 0)
        {
			setAD_Reference_ID (0);
			setName (null);
			setValidationType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Reference (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Reference[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_Element getAD_Element() throws RuntimeException
    {
		return (eone.base.model.I_AD_Element)MTable.get(getCtx(), eone.base.model.I_AD_Element.Table_Name)
			.getPO(getAD_Element_ID(), get_TrxName());	}

	/** Set System Element.
		@param AD_Element_ID 
		System Element enables the central maintenance of column description and help.
	  */
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
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

	/** Set Order By Value.
		@param IsOrderByValue 
		Order list using the value column instead of the name column
	  */
	public void setIsOrderByValue (boolean IsOrderByValue)
	{
		set_Value (COLUMNNAME_IsOrderByValue, Boolean.valueOf(IsOrderByValue));
	}

	/** Get Order By Value.
		@return Order list using the value column instead of the name column
	  */
	public boolean isOrderByValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrderByValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** ValidationType AD_Reference_ID=2 */
	public static final int VALIDATIONTYPE_AD_Reference_ID=2;
	/** List Validation = L */
	public static final String VALIDATIONTYPE_ListValidation = "L";
	/** DataType = D */
	public static final String VALIDATIONTYPE_DataType = "D";
	/** Table Validation = T */
	public static final String VALIDATIONTYPE_TableValidation = "T";
	/** Set Validation type.
		@param ValidationType 
		Different method of validating data
	  */
	public void setValidationType (String ValidationType)
	{

		set_Value (COLUMNNAME_ValidationType, ValidationType);
	}

	/** Get Validation type.
		@return Different method of validating data
	  */
	public String getValidationType () 
	{
		return (String)get_Value(COLUMNNAME_ValidationType);
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setVFormat (String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getVFormat () 
	{
		return (String)get_Value(COLUMNNAME_VFormat);
	}
}