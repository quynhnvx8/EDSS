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

/** Generated Model for HR_Config
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_Config extends PO implements I_HR_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220616L;

    /** Standard Constructor */
    public X_HR_Config (Properties ctx, int HR_Config_ID, String trxName)
    {
      super (ctx, HR_Config_ID, trxName);
      /** if (HR_Config_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_Config[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set HR Config.
		@param HR_Config_ID HR Config	  */
	public void setHR_Config_ID (int HR_Config_ID)
	{
		if (HR_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Config_ID, Integer.valueOf(HR_Config_ID));
	}

	/** Get HR Config.
		@return HR Config	  */
	public int getHR_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** BaseSalaryMin = BaseSalaryMin */
	public static final String NAME_BaseSalaryMin = "BaseSalaryMin";
	/** BaseSalaryPay = BaseSalaryPay */
	public static final String NAME_BaseSalaryPay = "BaseSalaryPay";
	/** MaleRetireAge = MaleRetireAge */
	public static final String NAME_MaleRetireAge = "MaleRetireAge";
	/** FemaleRetireAge = FemaleRetireAge */
	public static final String NAME_FemaleRetireAge = "FemaleRetireAge";
	/** WorkingTimeStandard = WorkingTimeStandard */
	public static final String NAME_WorkingTimeStandard = "WorkingTimeStandard";
	/** Individual Health Insurance = IN_HealthInsurance */
	public static final String NAME_IndividualHealthInsurance = "IN_HealthInsurance";
	/** UnemploymentInsurance = UnemploymentInsurance */
	public static final String NAME_UnemploymentInsurance = "UnemploymentInsurance";
	/** Individual Social Insurance = IN_SocialInsurance */
	public static final String NAME_IndividualSocialInsurance = "IN_SocialInsurance";
	/** PersonalDeduction = PersonalDeduction */
	public static final String NAME_PersonalDeduction = "PersonalDeduction";
	/** DependentDeduction = DependentDeduction */
	public static final String NAME_DependentDeduction = "DependentDeduction";
	/** Organization Social Insurance = OR_SocialInsurance */
	public static final String NAME_OrganizationSocialInsurance = "OR_SocialInsurance";
	/** Organization Health Insurance = OR_HealthInsurance */
	public static final String NAME_OrganizationHealthInsurance = "OR_HealthInsurance";
	/** Union Fee = UinonFee */
	public static final String NAME_UnionFee = "UinonFee";
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