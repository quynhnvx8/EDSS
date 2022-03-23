/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for AD_FlowLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_AD_FlowLine 
{

    /** TableName=AD_FlowLine */
    public static final String Table_Name = "AD_FlowLine";

    /** AD_Table_ID=1200409 */
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

    /** Column name AD_Department_ID */
    public static final String COLUMNNAME_AD_Department_ID = "AD_Department_ID";

	/** Set Department	  */
	public void setAD_Department_ID (int AD_Department_ID);

	/** Get Department	  */
	public int getAD_Department_ID();

	public eone.base.model.I_AD_Department getAD_Department() throws RuntimeException;

    /** Column name AD_Flow_ID */
    public static final String COLUMNNAME_AD_Flow_ID = "AD_Flow_ID";

	/** Set Flow process	  */
	public void setAD_Flow_ID (int AD_Flow_ID);

	/** Get Flow process	  */
	public int getAD_Flow_ID();

	public eone.base.model.I_AD_Flow getAD_Flow() throws RuntimeException;

    /** Column name AD_FlowLine_ID */
    public static final String COLUMNNAME_AD_FlowLine_ID = "AD_FlowLine_ID";

	/** Set Flow process Line	  */
	public void setAD_FlowLine_ID (int AD_FlowLine_ID);

	/** Get Flow process Line	  */
	public int getAD_FlowLine_ID();

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

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

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
}
