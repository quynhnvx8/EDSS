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

/** Generated Model for HR_Payroll
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_Payroll extends PO implements I_HR_Payroll, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220616L;

    /** Standard Constructor */
    public X_HR_Payroll (Properties ctx, int HR_Payroll_ID, String trxName)
    {
      super (ctx, HR_Payroll_ID, trxName);
      /** if (HR_Payroll_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_Payroll (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_Payroll[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DateApproved.
		@param DateApproved DateApproved	  */
	public void setDateApproved (Timestamp DateApproved)
	{
		set_Value (COLUMNNAME_DateApproved, DateApproved);
	}

	/** Get DateApproved.
		@return DateApproved	  */
	public Timestamp getDateApproved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproved);
	}

	/** Set DateNext.
		@param DateNext DateNext	  */
	public void setDateNext (Timestamp DateNext)
	{
		set_Value (COLUMNNAME_DateNext, DateNext);
	}

	/** Get DateNext.
		@return DateNext	  */
	public Timestamp getDateNext () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateNext);
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
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

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_05() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_05_ID(), get_TrxName());	}

	/** Set Jobs.
		@param HR_ItemLine_05_ID Jobs	  */
	public void setHR_ItemLine_05_ID (int HR_ItemLine_05_ID)
	{
		if (HR_ItemLine_05_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_05_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_05_ID, Integer.valueOf(HR_ItemLine_05_ID));
	}

	/** Get Jobs.
		@return Jobs	  */
	public int getHR_ItemLine_05_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_05_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_15() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_15_ID(), get_TrxName());	}

	/** Set Payroll Form.
		@param HR_ItemLine_15_ID Payroll Form	  */
	public void setHR_ItemLine_15_ID (int HR_ItemLine_15_ID)
	{
		if (HR_ItemLine_15_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_15_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_15_ID, Integer.valueOf(HR_ItemLine_15_ID));
	}

	/** Get Payroll Form.
		@return Payroll Form	  */
	public int getHR_ItemLine_15_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_15_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_ItemLine getHR_ItemLine_19() throws RuntimeException
    {
		return (eone.base.model.I_HR_ItemLine)MTable.get(getCtx(), eone.base.model.I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_19_ID(), get_TrxName());	}

	/** Set Payment Source .
		@param HR_ItemLine_19_ID Payment Source 	  */
	public void setHR_ItemLine_19_ID (int HR_ItemLine_19_ID)
	{
		if (HR_ItemLine_19_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_19_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_19_ID, Integer.valueOf(HR_ItemLine_19_ID));
	}

	/** Get Payment Source .
		@return Payment Source 	  */
	public int getHR_ItemLine_19_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_19_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll.
		@param HR_Payroll_ID Payroll	  */
	public void setHR_Payroll_ID (int HR_Payroll_ID)
	{
		if (HR_Payroll_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Payroll_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Payroll_ID, Integer.valueOf(HR_Payroll_ID));
	}

	/** Get Payroll.
		@return Payroll	  */
	public int getHR_Payroll_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Payroll_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_SalaryTable getHR_SalaryTable() throws RuntimeException
    {
		return (eone.base.model.I_HR_SalaryTable)MTable.get(getCtx(), eone.base.model.I_HR_SalaryTable.Table_Name)
			.getPO(getHR_SalaryTable_ID(), get_TrxName());	}

	/** Set Salary Table .
		@param HR_SalaryTable_ID Salary Table 	  */
	public void setHR_SalaryTable_ID (int HR_SalaryTable_ID)
	{
		if (HR_SalaryTable_ID < 1) 
			set_Value (COLUMNNAME_HR_SalaryTable_ID, null);
		else 
			set_Value (COLUMNNAME_HR_SalaryTable_ID, Integer.valueOf(HR_SalaryTable_ID));
	}

	/** Get Salary Table .
		@return Salary Table 	  */
	public int getHR_SalaryTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_SalaryTable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_SalaryTableLine getHR_SalaryTableLine() throws RuntimeException
    {
		return (eone.base.model.I_HR_SalaryTableLine)MTable.get(getCtx(), eone.base.model.I_HR_SalaryTableLine.Table_Name)
			.getPO(getHR_SalaryTableLine_ID(), get_TrxName());	}

	/** Set Table Salary Line.
		@param HR_SalaryTableLine_ID Table Salary Line	  */
	public void setHR_SalaryTableLine_ID (int HR_SalaryTableLine_ID)
	{
		if (HR_SalaryTableLine_ID < 1) 
			set_Value (COLUMNNAME_HR_SalaryTableLine_ID, null);
		else 
			set_Value (COLUMNNAME_HR_SalaryTableLine_ID, Integer.valueOf(HR_SalaryTableLine_ID));
	}

	/** Get Table Salary Line.
		@return Table Salary Line	  */
	public int getHR_SalaryTableLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_SalaryTableLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IncentiveRate.
		@param IncentiveRate IncentiveRate	  */
	public void setIncentiveRate (BigDecimal IncentiveRate)
	{
		set_Value (COLUMNNAME_IncentiveRate, IncentiveRate);
	}

	/** Get IncentiveRate.
		@return IncentiveRate	  */
	public BigDecimal getIncentiveRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IncentiveRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Level 1 = 01 */
	public static final String LEVELNO_Level1 = "01";
	/** Level 2 = 02 */
	public static final String LEVELNO_Level2 = "02";
	/** Level 3 = 03 */
	public static final String LEVELNO_Level3 = "03";
	/** Level 4 = 04 */
	public static final String LEVELNO_Level4 = "04";
	/** Level 5 = 05 */
	public static final String LEVELNO_Level5 = "05";
	/** Level 6 = 06 */
	public static final String LEVELNO_Level6 = "06";
	/** Level 7 = 07 */
	public static final String LEVELNO_Level7 = "07";
	/** Level 8 = 08 */
	public static final String LEVELNO_Level8 = "08";
	/** Set Level no.
		@param LevelNo Level no	  */
	public void setLevelNo (String LevelNo)
	{

		set_Value (COLUMNNAME_LevelNo, LevelNo);
	}

	/** Get Level no.
		@return Level no	  */
	public String getLevelNo () 
	{
		return (String)get_Value(COLUMNNAME_LevelNo);
	}

	/** Set LiabilityRate.
		@param LiabilityRate LiabilityRate	  */
	public void setLiabilityRate (BigDecimal LiabilityRate)
	{
		set_Value (COLUMNNAME_LiabilityRate, LiabilityRate);
	}

	/** Get LiabilityRate.
		@return LiabilityRate	  */
	public BigDecimal getLiabilityRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LiabilityRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PositionRate.
		@param PositionRate PositionRate	  */
	public void setPositionRate (BigDecimal PositionRate)
	{
		set_Value (COLUMNNAME_PositionRate, PositionRate);
	}

	/** Get PositionRate.
		@return PositionRate	  */
	public BigDecimal getPositionRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PositionRate);
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

	/** Set SalaryInsurance.
		@param SalaryInsurance SalaryInsurance	  */
	public void setSalaryInsurance (BigDecimal SalaryInsurance)
	{
		set_Value (COLUMNNAME_SalaryInsurance, SalaryInsurance);
	}

	/** Get SalaryInsurance.
		@return SalaryInsurance	  */
	public BigDecimal getSalaryInsurance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryInsurance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SalaryPercent.
		@param SalaryPercent SalaryPercent	  */
	public void setSalaryPercent (BigDecimal SalaryPercent)
	{
		set_Value (COLUMNNAME_SalaryPercent, SalaryPercent);
	}

	/** Get SalaryPercent.
		@return SalaryPercent	  */
	public BigDecimal getSalaryPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryPercent);
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

	/** Set SalaryRate.
		@param SalaryRate SalaryRate	  */
	public void setSalaryRate (BigDecimal SalaryRate)
	{
		set_Value (COLUMNNAME_SalaryRate, SalaryRate);
	}

	/** Get SalaryRate.
		@return SalaryRate	  */
	public BigDecimal getSalaryRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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