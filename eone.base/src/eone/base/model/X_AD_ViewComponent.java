/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.KeyNamePair;

/** Generated Model for AD_ViewComponent
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_ViewComponent extends PO implements I_AD_ViewComponent, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_ViewComponent (Properties ctx, int AD_ViewComponent_ID, String trxName)
    {
      super (ctx, AD_ViewComponent_ID, trxName);
      /** if (AD_ViewComponent_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_ViewComponent_ID (0);
			setFromClause (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_ViewComponent (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_ViewComponent[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Database View Component.
		@param AD_ViewComponent_ID Database View Component	  */
	public void setAD_ViewComponent_ID (int AD_ViewComponent_ID)
	{
		if (AD_ViewComponent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ViewComponent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ViewComponent_ID, Integer.valueOf(AD_ViewComponent_ID));
	}

	/** Get Database View Component.
		@return Database View Component	  */
	public int getAD_ViewComponent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ViewComponent_ID);
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

	/** Set Sql FROM.
		@param FromClause 
		SQL FROM clause
	  */
	public void setFromClause (String FromClause)
	{
		set_Value (COLUMNNAME_FromClause, FromClause);
	}

	/** Get Sql FROM.
		@return SQL FROM clause
	  */
	public String getFromClause () 
	{
		return (String)get_Value(COLUMNNAME_FromClause);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set Other SQL Clause.
		@param OtherClause 
		Other SQL Clause
	  */
	public void setOtherClause (String OtherClause)
	{
		set_Value (COLUMNNAME_OtherClause, OtherClause);
	}

	/** Get Other SQL Clause.
		@return Other SQL Clause
	  */
	public String getOtherClause () 
	{
		return (String)get_Value(COLUMNNAME_OtherClause);
	}

	public eone.base.model.I_AD_Table getReferenced_Table() throws RuntimeException
    {
		return (eone.base.model.I_AD_Table)MTable.get(getCtx(), eone.base.model.I_AD_Table.Table_Name)
			.getPO(getReferenced_Table_ID(), get_TrxName());	}

	/** Set Referenced Table.
		@param Referenced_Table_ID Referenced Table	  */
	public void setReferenced_Table_ID (int Referenced_Table_ID)
	{
		if (Referenced_Table_ID < 1) 
			set_Value (COLUMNNAME_Referenced_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_Table_ID, Integer.valueOf(Referenced_Table_ID));
	}

	/** Get Referenced Table.
		@return Referenced Table	  */
	public int getReferenced_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Referenced_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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