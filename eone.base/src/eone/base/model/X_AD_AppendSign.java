/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_AppendSign
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_AppendSign extends PO implements I_AD_AppendSign, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210910L;

    /** Standard Constructor */
    public X_AD_AppendSign (Properties ctx, int AD_AppendSign_ID, String trxName)
    {
      super (ctx, AD_AppendSign_ID, trxName);
      /** if (AD_AppendSign_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_AppendSign (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_AppendSign[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Append Sign.
		@param AD_AppendSign_ID Append Sign	  */
	public void setAD_AppendSign_ID (int AD_AppendSign_ID)
	{
		if (AD_AppendSign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AppendSign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AppendSign_ID, Integer.valueOf(AD_AppendSign_ID));
	}

	/** Get Append Sign.
		@return Append Sign	  */
	public int getAD_AppendSign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AppendSign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}