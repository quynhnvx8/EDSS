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

/** Generated Model for HR_TimekeeperMap
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_TimekeeperMap extends PO implements I_HR_TimekeeperMap, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220626L;

    /** Standard Constructor */
    public X_HR_TimekeeperMap (Properties ctx, int HR_TimekeeperMap_ID, String trxName)
    {
      super (ctx, HR_TimekeeperMap_ID, trxName);
      /** if (HR_TimekeeperMap_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_TimekeeperMap (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuilder sb = new StringBuilder ("X_HR_TimekeeperMap[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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

	/** Set Timekeeper Map.
		@param HR_TimekeeperMap_ID Timekeeper Map	  */
	public void setHR_TimekeeperMap_ID (int HR_TimekeeperMap_ID)
	{
		if (HR_TimekeeperMap_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_TimekeeperMap_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_TimekeeperMap_ID, Integer.valueOf(HR_TimekeeperMap_ID));
	}

	/** Get Timekeeper Map.
		@return Timekeeper Map	  */
	public int getHR_TimekeeperMap_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_TimekeeperMap_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** P4 = P4 */
	public static final String NAME_P4 = "P4";
	/** KL = KL */
	public static final String NAME_KL = "KL";
	/** CO = CO */
	public static final String NAME_CO = "CO";
	/** L8 = L8 */
	public static final String NAME_L8 = "L8";
	/** OO = OO */
	public static final String NAME_OO = "OO";
	/** SP = SP */
	public static final String NAME_SP = "SP";
	/** TS = TS */
	public static final String NAME_TS = "TS";
	/** +8 = +8 */
	public static final String NAME_Plus8 = "+8";
	/** +4 = +4 */
	public static final String NAME_Plus4 = "+4";
	/** CT = CT */
	public static final String NAME_CT = "CT";
	/** HO = HO */
	public static final String NAME_HO = "HO";
	/** TN = TN */
	public static final String NAME_TN = "TN";
	/** NV = NV */
	public static final String NAME_NV = "NV";
	/** NL = NL */
	public static final String NAME_NL = "NL";
	/** NN = NN */
	public static final String NAME_NN = "NN";
	/** P8 = P8 */
	public static final String NAME_P8 = "P8";
	/** NB = NB */
	public static final String NAME_NB = "NB";
	/** NT = NT */
	public static final String NAME_NT = "NT";
	/** Đ = DD */
	public static final String NAME_Đ = "DD";
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

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Have a salary = HS */
	public static final String TYPETIMEKEEPER_HaveASalary = "HS";
	/** Without Salary = WS */
	public static final String TYPETIMEKEEPER_WithoutSalary = "WS";
	/** Insurance Salary = IS */
	public static final String TYPETIMEKEEPER_InsuranceSalary = "IS";
	/** Meternity Time = MT */
	public static final String TYPETIMEKEEPER_MeternityTime = "MT";
	/** Overtime On Holiday = OH */
	public static final String TYPETIMEKEEPER_OvertimeOnHoliday = "OH";
	/** Overtime On Weekdays = OW */
	public static final String TYPETIMEKEEPER_OvertimeOnWeekdays = "OW";
	/** Workday Product = WP */
	public static final String TYPETIMEKEEPER_WorkdayProduct = "WP";
	/** Overtime On Normal = ON */
	public static final String TYPETIMEKEEPER_OvertimeOnNormal = "ON";
	/** Overnight = OV */
	public static final String TYPETIMEKEEPER_Overnight = "OV";
	/** Set TypeTimeKeeper.
		@param TypeTimeKeeper TypeTimeKeeper	  */
	public void setTypeTimeKeeper (String TypeTimeKeeper)
	{

		set_Value (COLUMNNAME_TypeTimeKeeper, TypeTimeKeeper);
	}

	/** Get TypeTimeKeeper.
		@return TypeTimeKeeper	  */
	public String getTypeTimeKeeper () 
	{
		return (String)get_Value(COLUMNNAME_TypeTimeKeeper);
	}

	/** Set Value.
		@param ValueNumber 
		Numeric Value
	  */
	public void setValueNumber (BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	/** Get Value.
		@return Numeric Value
	  */
	public BigDecimal getValueNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumber);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}