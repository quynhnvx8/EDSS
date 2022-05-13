/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.Env;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for A_Asset_History
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_A_Asset_History extends PO implements I_A_Asset_History, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220511L;

    /** Standard Constructor */
    public X_A_Asset_History (Properties ctx, int A_Asset_History_ID, String trxName)
    {
      super (ctx, A_Asset_History_ID, trxName);
      /** if (A_Asset_History_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_A_Asset_History (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_A_Asset_History[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Asset History.
		@param A_Asset_History_ID Asset History	  */
	public void setA_Asset_History_ID (int A_Asset_History_ID)
	{
		if (A_Asset_History_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_History_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_History_ID, Integer.valueOf(A_Asset_History_ID));
	}

	/** Get Asset History.
		@return Asset History	  */
	public int getA_Asset_History_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_History_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (eone.base.model.I_A_Asset)MTable.get(getCtx(), eone.base.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AccumulateAmt.
		@param AccumulateAmt AccumulateAmt	  */
	public void setAccumulateAmt (BigDecimal AccumulateAmt)
	{
		set_Value (COLUMNNAME_AccumulateAmt, AccumulateAmt);
	}

	/** Get AccumulateAmt.
		@return AccumulateAmt	  */
	public BigDecimal getAccumulateAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccumulateAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ChangeDate.
		@param ChangeDate ChangeDate	  */
	public void setChangeDate (Timestamp ChangeDate)
	{
		set_Value (COLUMNNAME_ChangeDate, ChangeDate);
	}

	/** Get ChangeDate.
		@return ChangeDate	  */
	public Timestamp getChangeDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ChangeDate);
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

	/** Set IsFirst.
		@param IsFirst IsFirst	  */
	public void setIsFirst (boolean IsFirst)
	{
		set_Value (COLUMNNAME_IsFirst, Boolean.valueOf(IsFirst));
	}

	/** Get IsFirst.
		@return IsFirst	  */
	public boolean isFirst () 
	{
		Object oo = get_Value(COLUMNNAME_IsFirst);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UseLifes.
		@param UseLifes UseLifes	  */
	public void setUseLifes (BigDecimal UseLifes)
	{
		set_Value (COLUMNNAME_UseLifes, UseLifes);
	}

	/** Get UseLifes.
		@return UseLifes	  */
	public BigDecimal getUseLifes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UseLifes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}