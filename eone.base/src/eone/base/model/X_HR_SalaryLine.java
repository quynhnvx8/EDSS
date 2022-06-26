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

/** Generated Model for HR_SalaryLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_SalaryLine extends PO implements I_HR_SalaryLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220626L;

    /** Standard Constructor */
    public X_HR_SalaryLine (Properties ctx, int HR_SalaryLine_ID, String trxName)
    {
      super (ctx, HR_SalaryLine_ID, trxName);
      /** if (HR_SalaryLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_SalaryLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_SalaryLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public eone.base.model.I_HR_Salary getHR_Salary() throws RuntimeException
    {
		return (eone.base.model.I_HR_Salary)MTable.get(getCtx(), eone.base.model.I_HR_Salary.Table_Name)
			.getPO(getHR_Salary_ID(), get_TrxName());	}

	/** Set Salary.
		@param HR_Salary_ID Salary	  */
	public void setHR_Salary_ID (int HR_Salary_ID)
	{
		if (HR_Salary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Salary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Salary_ID, Integer.valueOf(HR_Salary_ID));
	}

	/** Get Salary.
		@return Salary	  */
	public int getHR_Salary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Salary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SalaryLine.
		@param HR_SalaryLine_ID SalaryLine	  */
	public void setHR_SalaryLine_ID (int HR_SalaryLine_ID)
	{
		if (HR_SalaryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_SalaryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_SalaryLine_ID, Integer.valueOf(HR_SalaryLine_ID));
	}

	/** Get SalaryLine.
		@return SalaryLine	  */
	public int getHR_SalaryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_SalaryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Insua_Health.
		@param Insua_Health Insua_Health	  */
	public void setInsua_Health (BigDecimal Insua_Health)
	{
		set_ValueNoCheck (COLUMNNAME_Insua_Health, Insua_Health);
	}

	/** Get Insua_Health.
		@return Insua_Health	  */
	public BigDecimal getInsua_Health () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Insua_Health);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Insua_Social.
		@param Insua_Social Insua_Social	  */
	public void setInsua_Social (BigDecimal Insua_Social)
	{
		set_ValueNoCheck (COLUMNNAME_Insua_Social, Insua_Social);
	}

	/** Get Insua_Social.
		@return Insua_Social	  */
	public BigDecimal getInsua_Social () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Insua_Social);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Insua_Unemployee.
		@param Insua_Unemployee Insua_Unemployee	  */
	public void setInsua_Unemployee (BigDecimal Insua_Unemployee)
	{
		set_ValueNoCheck (COLUMNNAME_Insua_Unemployee, Insua_Unemployee);
	}

	/** Get Insua_Unemployee.
		@return Insua_Unemployee	  */
	public BigDecimal getInsua_Unemployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Insua_Unemployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set OnionFee Amt.
		@param OnionFeeAmt OnionFee Amt	  */
	public void setOnionFeeAmt (BigDecimal OnionFeeAmt)
	{
		set_Value (COLUMNNAME_OnionFeeAmt, OnionFeeAmt);
	}

	/** Get OnionFee Amt.
		@return OnionFee Amt	  */
	public BigDecimal getOnionFeeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OnionFeeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Organization Insurance Health.
		@param Org_Insua_Health Organization Insurance Health	  */
	public void setOrg_Insua_Health (BigDecimal Org_Insua_Health)
	{
		set_Value (COLUMNNAME_Org_Insua_Health, Org_Insua_Health);
	}

	/** Get Organization Insurance Health.
		@return Organization Insurance Health	  */
	public BigDecimal getOrg_Insua_Health () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Org_Insua_Health);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Organization Insurance Social.
		@param Org_Insua_Social Organization Insurance Social	  */
	public void setOrg_Insua_Social (BigDecimal Org_Insua_Social)
	{
		set_Value (COLUMNNAME_Org_Insua_Social, Org_Insua_Social);
	}

	/** Get Organization Insurance Social.
		@return Organization Insurance Social	  */
	public BigDecimal getOrg_Insua_Social () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Org_Insua_Social);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Organization Insurance Unmeployment.
		@param Org_Insua_Unemployee Organization Insurance Unmeployment	  */
	public void setOrg_Insua_Unemployee (BigDecimal Org_Insua_Unemployee)
	{
		set_Value (COLUMNNAME_Org_Insua_Unemployee, Org_Insua_Unemployee);
	}

	/** Get Organization Insurance Unmeployment.
		@return Organization Insurance Unmeployment	  */
	public BigDecimal getOrg_Insua_Unemployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Org_Insua_Unemployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SalaryBase.
		@param SalaryBase SalaryBase	  */
	public void setSalaryBase (BigDecimal SalaryBase)
	{
		set_Value (COLUMNNAME_SalaryBase, SalaryBase);
	}

	/** Get SalaryBase.
		@return SalaryBase	  */
	public BigDecimal getSalaryBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryExtra.
		@param SalaryExtra SalaryExtra	  */
	public void setSalaryExtra (BigDecimal SalaryExtra)
	{
		set_Value (COLUMNNAME_SalaryExtra, SalaryExtra);
	}

	/** Get SalaryExtra.
		@return SalaryExtra	  */
	public BigDecimal getSalaryExtra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryExtra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryGross.
		@param SalaryGross SalaryGross	  */
	public void setSalaryGross (BigDecimal SalaryGross)
	{
		set_Value (COLUMNNAME_SalaryGross, SalaryGross);
	}

	/** Get SalaryGross.
		@return SalaryGross	  */
	public BigDecimal getSalaryGross () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryGross);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryIncentive.
		@param SalaryIncentive SalaryIncentive	  */
	public void setSalaryIncentive (BigDecimal SalaryIncentive)
	{
		set_Value (COLUMNNAME_SalaryIncentive, SalaryIncentive);
	}

	/** Get SalaryIncentive.
		@return SalaryIncentive	  */
	public BigDecimal getSalaryIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryIncentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryLiability.
		@param SalaryLiability SalaryLiability	  */
	public void setSalaryLiability (BigDecimal SalaryLiability)
	{
		set_Value (COLUMNNAME_SalaryLiability, SalaryLiability);
	}

	/** Get SalaryLiability.
		@return SalaryLiability	  */
	public BigDecimal getSalaryLiability () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryLiability);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryNet.
		@param SalaryNet SalaryNet	  */
	public void setSalaryNet (BigDecimal SalaryNet)
	{
		set_Value (COLUMNNAME_SalaryNet, SalaryNet);
	}

	/** Get SalaryNet.
		@return SalaryNet	  */
	public BigDecimal getSalaryNet () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryNet);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryPart1.
		@param SalaryPart1 SalaryPart1	  */
	public void setSalaryPart1 (BigDecimal SalaryPart1)
	{
		set_Value (COLUMNNAME_SalaryPart1, SalaryPart1);
	}

	/** Get SalaryPart1.
		@return SalaryPart1	  */
	public BigDecimal getSalaryPart1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryPart1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryPosition.
		@param SalaryPosition SalaryPosition	  */
	public void setSalaryPosition (BigDecimal SalaryPosition)
	{
		set_Value (COLUMNNAME_SalaryPosition, SalaryPosition);
	}

	/** Get SalaryPosition.
		@return SalaryPosition	  */
	public BigDecimal getSalaryPosition () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryPosition);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryProduction.
		@param SalaryProduction SalaryProduction	  */
	public void setSalaryProduction (BigDecimal SalaryProduction)
	{
		set_Value (COLUMNNAME_SalaryProduction, SalaryProduction);
	}

	/** Get SalaryProduction.
		@return SalaryProduction	  */
	public BigDecimal getSalaryProduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryProduction);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Day Insurance.
		@param TotalDayInsurance Total Day Insurance	  */
	public void setTotalDayInsurance (BigDecimal TotalDayInsurance)
	{
		set_Value (COLUMNNAME_TotalDayInsurance, TotalDayInsurance);
	}

	/** Get Total Day Insurance.
		@return Total Day Insurance	  */
	public BigDecimal getTotalDayInsurance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDayInsurance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalDayMartenity.
		@param TotalDayMartenity TotalDayMartenity	  */
	public void setTotalDayMartenity (BigDecimal TotalDayMartenity)
	{
		set_Value (COLUMNNAME_TotalDayMartenity, TotalDayMartenity);
	}

	/** Get TotalDayMartenity.
		@return TotalDayMartenity	  */
	public BigDecimal getTotalDayMartenity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDayMartenity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalDayOff.
		@param TotalDayOff TotalDayOff	  */
	public void setTotalDayOff (BigDecimal TotalDayOff)
	{
		set_Value (COLUMNNAME_TotalDayOff, TotalDayOff);
	}

	/** Get TotalDayOff.
		@return TotalDayOff	  */
	public BigDecimal getTotalDayOff () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDayOff);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalDayOffNone.
		@param TotalDayOffNone TotalDayOffNone	  */
	public void setTotalDayOffNone (BigDecimal TotalDayOffNone)
	{
		set_Value (COLUMNNAME_TotalDayOffNone, TotalDayOffNone);
	}

	/** Get TotalDayOffNone.
		@return TotalDayOffNone	  */
	public BigDecimal getTotalDayOffNone () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDayOffNone);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalDayOffUnNotice.
		@param TotalDayOffUnNotice TotalDayOffUnNotice	  */
	public void setTotalDayOffUnNotice (BigDecimal TotalDayOffUnNotice)
	{
		set_Value (COLUMNNAME_TotalDayOffUnNotice, TotalDayOffUnNotice);
	}

	/** Get TotalDayOffUnNotice.
		@return TotalDayOffUnNotice	  */
	public BigDecimal getTotalDayOffUnNotice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDayOffUnNotice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWorkDay.
		@param TotalWorkDay TotalWorkDay	  */
	public void setTotalWorkDay (BigDecimal TotalWorkDay)
	{
		set_Value (COLUMNNAME_TotalWorkDay, TotalWorkDay);
	}

	/** Get TotalWorkDay.
		@return TotalWorkDay	  */
	public BigDecimal getTotalWorkDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total WorkDay Production.
		@param TotalWorkDayProduction Total WorkDay Production	  */
	public void setTotalWorkDayProduction (BigDecimal TotalWorkDayProduction)
	{
		set_Value (COLUMNNAME_TotalWorkDayProduction, TotalWorkDayProduction);
	}

	/** Get Total WorkDay Production.
		@return Total WorkDay Production	  */
	public BigDecimal getTotalWorkDayProduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkDayProduction);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWorkExtra.
		@param TotalWorkExtra TotalWorkExtra	  */
	public void setTotalWorkExtra (BigDecimal TotalWorkExtra)
	{
		set_Value (COLUMNNAME_TotalWorkExtra, TotalWorkExtra);
	}

	/** Get TotalWorkExtra.
		@return TotalWorkExtra	  */
	public BigDecimal getTotalWorkExtra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkExtra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total WorkExtra Evening.
		@param TotalWorkExtraEvening Total WorkExtra Evening	  */
	public void setTotalWorkExtraEvening (BigDecimal TotalWorkExtraEvening)
	{
		set_Value (COLUMNNAME_TotalWorkExtraEvening, TotalWorkExtraEvening);
	}

	/** Get Total WorkExtra Evening.
		@return Total WorkExtra Evening	  */
	public BigDecimal getTotalWorkExtraEvening () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkExtraEvening);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWorkExtraHoliday.
		@param TotalWorkExtraHoliday TotalWorkExtraHoliday	  */
	public void setTotalWorkExtraHoliday (BigDecimal TotalWorkExtraHoliday)
	{
		set_Value (COLUMNNAME_TotalWorkExtraHoliday, TotalWorkExtraHoliday);
	}

	/** Get TotalWorkExtraHoliday.
		@return TotalWorkExtraHoliday	  */
	public BigDecimal getTotalWorkExtraHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkExtraHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total WorkExtra Weeken.
		@param TotalWorkExtraWeeken Total WorkExtra Weeken	  */
	public void setTotalWorkExtraWeeken (BigDecimal TotalWorkExtraWeeken)
	{
		set_Value (COLUMNNAME_TotalWorkExtraWeeken, TotalWorkExtraWeeken);
	}

	/** Get Total WorkExtra Weeken.
		@return Total WorkExtra Weeken	  */
	public BigDecimal getTotalWorkExtraWeeken () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkExtraWeeken);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set WorkdaySTD.
		@param WorkdaySTD WorkdaySTD	  */
	public void setWorkdaySTD (int WorkdaySTD)
	{
		set_Value (COLUMNNAME_WorkdaySTD, Integer.valueOf(WorkdaySTD));
	}

	/** Get WorkdaySTD.
		@return WorkdaySTD	  */
	public int getWorkdaySTD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WorkdaySTD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}