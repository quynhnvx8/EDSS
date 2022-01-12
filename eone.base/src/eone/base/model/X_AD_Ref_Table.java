/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Ref_Table
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Ref_Table extends PO implements I_AD_Ref_Table, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_Ref_Table (Properties ctx, int AD_Ref_Table_ID, String trxName)
    {
      super (ctx, AD_Ref_Table_ID, trxName);
      /** if (AD_Ref_Table_ID == 0)
        {
			setAD_Display (0);
			setAD_Key (0);
			setAD_Reference_ID (0);
			setAD_Table_ID (0);
			setIsValueDisplayed (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Ref_Table (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Ref_Table[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_Column getAD_Disp() throws RuntimeException
    {
		return (eone.base.model.I_AD_Column)MTable.get(getCtx(), eone.base.model.I_AD_Column.Table_Name)
			.getPO(getAD_Display(), get_TrxName());	}

	/** Set Display column.
		@param AD_Display 
		Column that will display
	  */
	public void setAD_Display (int AD_Display)
	{
		set_Value (COLUMNNAME_AD_Display, Integer.valueOf(AD_Display));
	}

	/** Get Display column.
		@return Column that will display
	  */
	public int getAD_Display () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Display);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_InfoWindow getAD_InfoWindow() throws RuntimeException
    {
		return (eone.base.model.I_AD_InfoWindow)MTable.get(getCtx(), eone.base.model.I_AD_InfoWindow.Table_Name)
			.getPO(getAD_InfoWindow_ID(), get_TrxName());	}

	/** Set Info Window.
		@param AD_InfoWindow_ID 
		Info and search/select Window
	  */
	public void setAD_InfoWindow_ID (int AD_InfoWindow_ID)
	{
		if (AD_InfoWindow_ID < 1) 
			set_Value (COLUMNNAME_AD_InfoWindow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InfoWindow_ID, Integer.valueOf(AD_InfoWindow_ID));
	}

	/** Get Info Window.
		@return Info and search/select Window
	  */
	public int getAD_InfoWindow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InfoWindow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Column getAD_() throws RuntimeException
    {
		return (eone.base.model.I_AD_Column)MTable.get(getCtx(), eone.base.model.I_AD_Column.Table_Name)
			.getPO(getAD_Key(), get_TrxName());	}

	/** Set Key column.
		@param AD_Key 
		Unique identifier of a record
	  */
	public void setAD_Key (int AD_Key)
	{
		set_Value (COLUMNNAME_AD_Key, Integer.valueOf(AD_Key));
	}

	/** Get Key column.
		@return Unique identifier of a record
	  */
	public int getAD_Key () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Key);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (eone.base.model.I_AD_Reference)MTable.get(getCtx(), eone.base.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Reference_ID()));
    }

	public eone.base.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (eone.base.model.I_AD_Table)MTable.get(getCtx(), eone.base.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Window getAD_Window() throws RuntimeException
    {
		return (eone.base.model.I_AD_Window)MTable.get(getCtx(), eone.base.model.I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Display Value.
		@param IsValueDisplayed 
		Displays Value column with the Display column
	  */
	public void setIsValueDisplayed (boolean IsValueDisplayed)
	{
		set_Value (COLUMNNAME_IsValueDisplayed, Boolean.valueOf(IsValueDisplayed));
	}

	/** Get Display Value.
		@return Displays Value column with the Display column
	  */
	public boolean isValueDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsValueDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sql ORDER BY.
		@param OrderByClause 
		Fully qualified ORDER BY clause
	  */
	public void setOrderByClause (String OrderByClause)
	{
		set_Value (COLUMNNAME_OrderByClause, OrderByClause);
	}

	/** Get Sql ORDER BY.
		@return Fully qualified ORDER BY clause
	  */
	public String getOrderByClause () 
	{
		return (String)get_Value(COLUMNNAME_OrderByClause);
	}

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	public String getWhereClause () 
	{
		return (String)get_Value(COLUMNNAME_WhereClause);
	}
}