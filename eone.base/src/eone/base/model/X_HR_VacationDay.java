/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.Env;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HR_VacationDay
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_VacationDay extends PO implements I_HR_VacationDay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220616L;

    /** Standard Constructor */
    public X_HR_VacationDay (Properties ctx, int HR_VacationDay_ID, String trxName)
    {
      super (ctx, HR_VacationDay_ID, trxName);
      /** if (HR_VacationDay_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_VacationDay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_VacationDay[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accumulate Qty.
		@param AccumulateQty Accumulate Qty	  */
	public void setAccumulateQty (BigDecimal AccumulateQty)
	{
		set_Value (COLUMNNAME_AccumulateQty, AccumulateQty);
	}

	/** Get Accumulate Qty.
		@return Accumulate Qty	  */
	public BigDecimal getAccumulateQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccumulateQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public eone.base.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (eone.base.model.I_C_Period)MTable.get(getCtx(), eone.base.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (eone.base.model.I_C_Year)MTable.get(getCtx(), eone.base.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param HR_Employee_ID Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID)
	{
		if (HR_Employee_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_ID, Integer.valueOf(HR_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getHR_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vacation Day.
		@param HR_VacationDay_ID Vacation Day	  */
	public void setHR_VacationDay_ID (int HR_VacationDay_ID)
	{
		if (HR_VacationDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_VacationDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_VacationDay_ID, Integer.valueOf(HR_VacationDay_ID));
	}

	/** Get Vacation Day.
		@return Vacation Day	  */
	public int getHR_VacationDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_VacationDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set RemainQty.
		@param RemainQty RemainQty	  */
	public void setRemainQty (BigDecimal RemainQty)
	{
		set_ValueNoCheck (COLUMNNAME_RemainQty, RemainQty);
	}

	/** Get RemainQty.
		@return RemainQty	  */
	public BigDecimal getRemainQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Vacant Day.
		@param VacantDay Vacant Day	  */
	public void setVacantDay (BigDecimal VacantDay)
	{
		set_Value (COLUMNNAME_VacantDay, VacantDay);
	}

	/** Get Vacant Day.
		@return Vacant Day	  */
	public BigDecimal getVacantDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VacantDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}