/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for AD_Process
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_AD_Process 
{

    /** TableName=AD_Process */
    public static final String Table_Name = "AD_Process";

    /** AD_Table_ID=284 */
    public static final int Table_ID = 284;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/** Set Data Access Level.
	  * Access Level required
	  */
	public void setAccessLevel (String AccessLevel);

	/** Get Data Access Level.
	  * Access Level required
	  */
	public String getAccessLevel();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/** Set Special Form.
	  * Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID);

	/** Get Special Form.
	  * Special Form
	  */
	public int getAD_Form_ID();

	public eone.base.model.I_AD_Form getAD_Form() throws RuntimeException;

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

    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/** Set Print Format.
	  * Data Print Format
	  */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/** Get Print Format.
	  * Data Print Format
	  */
	public int getAD_PrintFormat_ID();

	public eone.base.model.I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException;

    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/** Set Process.
	  * Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID);

	/** Get Process.
	  * Process or Report
	  */
	public int getAD_Process_ID();

    /** Column name AD_Process_UU */
    public static final String COLUMNNAME_AD_Process_UU = "AD_Process_UU";

	/** Set AD_Process_UU	  */
	public void setAD_Process_UU (String AD_Process_UU);

	/** Get AD_Process_UU	  */
	public String getAD_Process_UU();

    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

	/** Set Classname.
	  * Java Classname
	  */
	public void setClassname (String Classname);

	/** Get Classname.
	  * Java Classname
	  */
	public String getClassname();

    /** Column name CopyFromProcess */
    public static final String COLUMNNAME_CopyFromProcess = "CopyFromProcess";

	/** Set Copy From Report and Process.
	  * Copy settings from one report and process to another.
	  */
	public void setCopyFromProcess (String CopyFromProcess);

	/** Get Copy From Report and Process.
	  * Copy settings from one report and process to another.
	  */
	public String getCopyFromProcess();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name IsBetaFunctionality */
    public static final String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/** Set Beta Functionality.
	  * This functionality is considered Beta
	  */
	public void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/** Get Beta Functionality.
	  * This functionality is considered Beta
	  */
	public boolean isBetaFunctionality();

    /** Column name IsReport */
    public static final String COLUMNNAME_IsReport = "IsReport";

	/** Set Report.
	  * Indicates a Report record
	  */
	public void setIsReport (boolean IsReport);

	/** Get Report.
	  * Indicates a Report record
	  */
	public boolean isReport();

    /** Column name IsServerProcess */
    public static final String COLUMNNAME_IsServerProcess = "IsServerProcess";

	/** Set Server Process.
	  * Run this Process on Server only
	  */
	public void setIsServerProcess (boolean IsServerProcess);

	/** Get Server Process.
	  * Run this Process on Server only
	  */
	public boolean isServerProcess();

    /** Column name JasperReport */
    public static final String COLUMNNAME_JasperReport = "JasperReport";

	/** Set Jasper Report	  */
	public void setJasperReport (String JasperReport);

	/** Get Jasper Report	  */
	public String getJasperReport();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name ProcedureName */
    public static final String COLUMNNAME_ProcedureName = "ProcedureName";

	/** Set Procedure.
	  * Name of the Database Procedure
	  */
	public void setProcedureName (String ProcedureName);

	/** Get Procedure.
	  * Name of the Database Procedure
	  */
	public String getProcedureName();

    /** Column name TemplateApply */
    public static final String COLUMNNAME_TemplateApply = "TemplateApply";

	/** Set Template Apply	  */
	public void setTemplateApply (String TemplateApply);

	/** Get Template Apply	  */
	public String getTemplateApply();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Code.
	  * Code
	  */
	public void setValue (String Value);

	/** Get Code.
	  * Code
	  */
	public String getValue();

    /** Column name ZO_Window_ID */
    public static final String COLUMNNAME_ZO_Window_ID = "ZO_Window_ID";

	/** Set Zoom Window.
	  * Zoom Window
	  */
	public void setZO_Window_ID (int ZO_Window_ID);

	/** Get Zoom Window.
	  * Zoom Window
	  */
	public int getZO_Window_ID();

	public eone.base.model.I_AD_Window getZO_Window() throws RuntimeException;
}
