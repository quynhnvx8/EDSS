/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for HR_SalaryLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HR_SalaryLine 
{

    /** TableName=HR_SalaryLine */
    public static final String Table_Name = "HR_SalaryLine";

    /** AD_Table_ID=1200318 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public eone.base.model.I_C_Period getC_Period() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name HR_Employee_ID */
    public static final String COLUMNNAME_HR_Employee_ID = "HR_Employee_ID";

	/** Set Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID);

	/** Get Employee	  */
	public int getHR_Employee_ID();

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException;

    /** Column name HR_Salary_ID */
    public static final String COLUMNNAME_HR_Salary_ID = "HR_Salary_ID";

	/** Set Salary	  */
	public void setHR_Salary_ID (int HR_Salary_ID);

	/** Get Salary	  */
	public int getHR_Salary_ID();

	public eone.base.model.I_HR_Salary getHR_Salary() throws RuntimeException;

    /** Column name HR_SalaryLine_ID */
    public static final String COLUMNNAME_HR_SalaryLine_ID = "HR_SalaryLine_ID";

	/** Set SalaryLine	  */
	public void setHR_SalaryLine_ID (int HR_SalaryLine_ID);

	/** Get SalaryLine	  */
	public int getHR_SalaryLine_ID();

    /** Column name Insua_Health */
    public static final String COLUMNNAME_Insua_Health = "Insua_Health";

	/** Set Insua_Health	  */
	public void setInsua_Health (BigDecimal Insua_Health);

	/** Get Insua_Health	  */
	public BigDecimal getInsua_Health();

    /** Column name Insua_Social */
    public static final String COLUMNNAME_Insua_Social = "Insua_Social";

	/** Set Insua_Social	  */
	public void setInsua_Social (BigDecimal Insua_Social);

	/** Get Insua_Social	  */
	public BigDecimal getInsua_Social();

    /** Column name Insua_Unemployee */
    public static final String COLUMNNAME_Insua_Unemployee = "Insua_Unemployee";

	/** Set Insua_Unemployee	  */
	public void setInsua_Unemployee (BigDecimal Insua_Unemployee);

	/** Get Insua_Unemployee	  */
	public BigDecimal getInsua_Unemployee();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name OnionFeeAmt */
    public static final String COLUMNNAME_OnionFeeAmt = "OnionFeeAmt";

	/** Set OnionFee Amt	  */
	public void setOnionFeeAmt (BigDecimal OnionFeeAmt);

	/** Get OnionFee Amt	  */
	public BigDecimal getOnionFeeAmt();

    /** Column name Org_Insua_Health */
    public static final String COLUMNNAME_Org_Insua_Health = "Org_Insua_Health";

	/** Set Organization Insurance Health	  */
	public void setOrg_Insua_Health (BigDecimal Org_Insua_Health);

	/** Get Organization Insurance Health	  */
	public BigDecimal getOrg_Insua_Health();

    /** Column name Org_Insua_Social */
    public static final String COLUMNNAME_Org_Insua_Social = "Org_Insua_Social";

	/** Set Organization Insurance Social	  */
	public void setOrg_Insua_Social (BigDecimal Org_Insua_Social);

	/** Get Organization Insurance Social	  */
	public BigDecimal getOrg_Insua_Social();

    /** Column name Org_Insua_Unemployee */
    public static final String COLUMNNAME_Org_Insua_Unemployee = "Org_Insua_Unemployee";

	/** Set Organization Insurance Unmeployment	  */
	public void setOrg_Insua_Unemployee (BigDecimal Org_Insua_Unemployee);

	/** Get Organization Insurance Unmeployment	  */
	public BigDecimal getOrg_Insua_Unemployee();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name SalaryBase */
    public static final String COLUMNNAME_SalaryBase = "SalaryBase";

	/** Set SalaryBase	  */
	public void setSalaryBase (BigDecimal SalaryBase);

	/** Get SalaryBase	  */
	public BigDecimal getSalaryBase();

    /** Column name SalaryExtra */
    public static final String COLUMNNAME_SalaryExtra = "SalaryExtra";

	/** Set SalaryExtra	  */
	public void setSalaryExtra (BigDecimal SalaryExtra);

	/** Get SalaryExtra	  */
	public BigDecimal getSalaryExtra();

    /** Column name SalaryGross */
    public static final String COLUMNNAME_SalaryGross = "SalaryGross";

	/** Set SalaryGross	  */
	public void setSalaryGross (BigDecimal SalaryGross);

	/** Get SalaryGross	  */
	public BigDecimal getSalaryGross();

    /** Column name SalaryIncentive */
    public static final String COLUMNNAME_SalaryIncentive = "SalaryIncentive";

	/** Set SalaryIncentive	  */
	public void setSalaryIncentive (BigDecimal SalaryIncentive);

	/** Get SalaryIncentive	  */
	public BigDecimal getSalaryIncentive();

    /** Column name SalaryLiability */
    public static final String COLUMNNAME_SalaryLiability = "SalaryLiability";

	/** Set SalaryLiability	  */
	public void setSalaryLiability (BigDecimal SalaryLiability);

	/** Get SalaryLiability	  */
	public BigDecimal getSalaryLiability();

    /** Column name SalaryNet */
    public static final String COLUMNNAME_SalaryNet = "SalaryNet";

	/** Set SalaryNet	  */
	public void setSalaryNet (BigDecimal SalaryNet);

	/** Get SalaryNet	  */
	public BigDecimal getSalaryNet();

    /** Column name SalaryPart1 */
    public static final String COLUMNNAME_SalaryPart1 = "SalaryPart1";

	/** Set SalaryPart1	  */
	public void setSalaryPart1 (BigDecimal SalaryPart1);

	/** Get SalaryPart1	  */
	public BigDecimal getSalaryPart1();

    /** Column name SalaryPosition */
    public static final String COLUMNNAME_SalaryPosition = "SalaryPosition";

	/** Set SalaryPosition	  */
	public void setSalaryPosition (BigDecimal SalaryPosition);

	/** Get SalaryPosition	  */
	public BigDecimal getSalaryPosition();

    /** Column name SalaryProduction */
    public static final String COLUMNNAME_SalaryProduction = "SalaryProduction";

	/** Set SalaryProduction	  */
	public void setSalaryProduction (BigDecimal SalaryProduction);

	/** Get SalaryProduction	  */
	public BigDecimal getSalaryProduction();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

    /** Column name TotalDayInsurance */
    public static final String COLUMNNAME_TotalDayInsurance = "TotalDayInsurance";

	/** Set Total Day Insurance	  */
	public void setTotalDayInsurance (BigDecimal TotalDayInsurance);

	/** Get Total Day Insurance	  */
	public BigDecimal getTotalDayInsurance();

    /** Column name TotalDayMartenity */
    public static final String COLUMNNAME_TotalDayMartenity = "TotalDayMartenity";

	/** Set TotalDayMartenity	  */
	public void setTotalDayMartenity (BigDecimal TotalDayMartenity);

	/** Get TotalDayMartenity	  */
	public BigDecimal getTotalDayMartenity();

    /** Column name TotalDayOff */
    public static final String COLUMNNAME_TotalDayOff = "TotalDayOff";

	/** Set TotalDayOff	  */
	public void setTotalDayOff (BigDecimal TotalDayOff);

	/** Get TotalDayOff	  */
	public BigDecimal getTotalDayOff();

    /** Column name TotalDayOffNone */
    public static final String COLUMNNAME_TotalDayOffNone = "TotalDayOffNone";

	/** Set TotalDayOffNone	  */
	public void setTotalDayOffNone (BigDecimal TotalDayOffNone);

	/** Get TotalDayOffNone	  */
	public BigDecimal getTotalDayOffNone();

    /** Column name TotalDayOffUnNotice */
    public static final String COLUMNNAME_TotalDayOffUnNotice = "TotalDayOffUnNotice";

	/** Set TotalDayOffUnNotice	  */
	public void setTotalDayOffUnNotice (BigDecimal TotalDayOffUnNotice);

	/** Get TotalDayOffUnNotice	  */
	public BigDecimal getTotalDayOffUnNotice();

    /** Column name TotalWorkDay */
    public static final String COLUMNNAME_TotalWorkDay = "TotalWorkDay";

	/** Set TotalWorkDay	  */
	public void setTotalWorkDay (BigDecimal TotalWorkDay);

	/** Get TotalWorkDay	  */
	public BigDecimal getTotalWorkDay();

    /** Column name TotalWorkDayProduction */
    public static final String COLUMNNAME_TotalWorkDayProduction = "TotalWorkDayProduction";

	/** Set Total WorkDay Production	  */
	public void setTotalWorkDayProduction (BigDecimal TotalWorkDayProduction);

	/** Get Total WorkDay Production	  */
	public BigDecimal getTotalWorkDayProduction();

    /** Column name TotalWorkExtra */
    public static final String COLUMNNAME_TotalWorkExtra = "TotalWorkExtra";

	/** Set TotalWorkExtra	  */
	public void setTotalWorkExtra (BigDecimal TotalWorkExtra);

	/** Get TotalWorkExtra	  */
	public BigDecimal getTotalWorkExtra();

    /** Column name TotalWorkExtraEvening */
    public static final String COLUMNNAME_TotalWorkExtraEvening = "TotalWorkExtraEvening";

	/** Set Total WorkExtra Evening	  */
	public void setTotalWorkExtraEvening (BigDecimal TotalWorkExtraEvening);

	/** Get Total WorkExtra Evening	  */
	public BigDecimal getTotalWorkExtraEvening();

    /** Column name TotalWorkExtraHoliday */
    public static final String COLUMNNAME_TotalWorkExtraHoliday = "TotalWorkExtraHoliday";

	/** Set TotalWorkExtraHoliday	  */
	public void setTotalWorkExtraHoliday (BigDecimal TotalWorkExtraHoliday);

	/** Get TotalWorkExtraHoliday	  */
	public BigDecimal getTotalWorkExtraHoliday();

    /** Column name TotalWorkExtraWeeken */
    public static final String COLUMNNAME_TotalWorkExtraWeeken = "TotalWorkExtraWeeken";

	/** Set Total WorkExtra Weeken	  */
	public void setTotalWorkExtraWeeken (BigDecimal TotalWorkExtraWeeken);

	/** Get Total WorkExtra Weeken	  */
	public BigDecimal getTotalWorkExtraWeeken();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name WorkdaySTD */
    public static final String COLUMNNAME_WorkdaySTD = "WorkdaySTD";

	/** Set WorkdaySTD	  */
	public void setWorkdaySTD (int WorkdaySTD);

	/** Get WorkdaySTD	  */
	public int getWorkdaySTD();
}
