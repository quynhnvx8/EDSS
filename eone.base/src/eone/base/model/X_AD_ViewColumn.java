/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ViewColumn
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_ViewColumn extends PO implements I_AD_ViewColumn, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_ViewColumn (Properties ctx, int AD_ViewColumn_ID, String trxName)
    {
      super (ctx, AD_ViewColumn_ID, trxName);
      /** if (AD_ViewColumn_ID == 0)
        {
			setAD_ViewColumn_ID (0);
			setAD_ViewComponent_ID (0);
			setColumnName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_ViewColumn (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_ViewColumn[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Database View Column.
		@param AD_ViewColumn_ID Database View Column	  */
	public void setAD_ViewColumn_ID (int AD_ViewColumn_ID)
	{
		if (AD_ViewColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ViewColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ViewColumn_ID, Integer.valueOf(AD_ViewColumn_ID));
	}

	/** Get Database View Column.
		@return Database View Column	  */
	public int getAD_ViewColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ViewColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_ViewComponent getAD_ViewComponent() throws RuntimeException
    {
		return (eone.base.model.I_AD_ViewComponent)MTable.get(getCtx(), eone.base.model.I_AD_ViewComponent.Table_Name)
			.getPO(getAD_ViewComponent_ID(), get_TrxName());	}

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

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Column SQL.
		@param ColumnSQL 
		Virtual Column (r/o)
	  */
	public void setColumnSQL (String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	/** Get Column SQL.
		@return Virtual Column (r/o)
	  */
	public String getColumnSQL () 
	{
		return (String)get_Value(COLUMNNAME_ColumnSQL);
	}

	/** DBDataType AD_Reference_ID=200070 */
	public static final int DBDATATYPE_AD_Reference_ID=200070;
	/** Binary LOB = B */
	public static final String DBDATATYPE_BinaryLOB = "B";
	/** Character Fixed = C */
	public static final String DBDATATYPE_CharacterFixed = "C";
	/** Decimal = D */
	public static final String DBDATATYPE_Decimal = "D";
	/** Integer = I */
	public static final String DBDATATYPE_Integer = "I";
	/** Character LOB = L */
	public static final String DBDATATYPE_CharacterLOB = "L";
	/** Number = N */
	public static final String DBDATATYPE_Number = "N";
	/** Timestamp = T */
	public static final String DBDATATYPE_Timestamp = "T";
	/** Character Variable = V */
	public static final String DBDATATYPE_CharacterVariable = "V";
	/** Set Database Data Type.
		@param DBDataType Database Data Type	  */
	public void setDBDataType (String DBDataType)
	{

		set_Value (COLUMNNAME_DBDataType, DBDataType);
	}

	/** Get Database Data Type.
		@return Database Data Type	  */
	public String getDBDataType () 
	{
		return (String)get_Value(COLUMNNAME_DBDataType);
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
}