/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for HR_DayOffLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HR_DayOffLine 
{

    /** TableName=HR_DayOffLine */
    public static final String Table_Name = "HR_DayOffLine";

    /** AD_Table_ID=1200342 */
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

    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

	/** Set Address	  */
	public void setAddress (String Address);

	/** Get Address	  */
	public String getAddress();

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

    /** Column name DayOffType */
    public static final String COLUMNNAME_DayOffType = "DayOffType";

	/** Set DayOffType	  */
	public void setDayOffType (String DayOffType);

	/** Get DayOffType	  */
	public String getDayOffType();

    /** Column name EndTime */
    public static final String COLUMNNAME_EndTime = "EndTime";

	/** Set End Time.
	  * End of the time span
	  */
	public void setEndTime (Timestamp EndTime);

	/** Get End Time.
	  * End of the time span
	  */
	public Timestamp getEndTime();

    /** Column name HR_DayOff_ID */
    public static final String COLUMNNAME_HR_DayOff_ID = "HR_DayOff_ID";

	/** Set Day Off	  */
	public void setHR_DayOff_ID (int HR_DayOff_ID);

	/** Get Day Off	  */
	public int getHR_DayOff_ID();

	public eone.base.model.I_HR_DayOff getHR_DayOff() throws RuntimeException;

    /** Column name HR_DayOffLine_ID */
    public static final String COLUMNNAME_HR_DayOffLine_ID = "HR_DayOffLine_ID";

	/** Set Day Off	  */
	public void setHR_DayOffLine_ID (int HR_DayOffLine_ID);

	/** Get Day Off	  */
	public int getHR_DayOffLine_ID();

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

    /** Column name StartTime */
    public static final String COLUMNNAME_StartTime = "StartTime";

	/** Set Start Time.
	  * Time started
	  */
	public void setStartTime (Timestamp StartTime);

	/** Get Start Time.
	  * Time started
	  */
	public Timestamp getStartTime();

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
