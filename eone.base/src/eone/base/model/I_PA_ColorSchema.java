/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for PA_ColorSchema
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_PA_ColorSchema 
{

    /** TableName=PA_ColorSchema */
    public static final String Table_Name = "PA_ColorSchema";

    /** AD_Table_ID=831 */
    public static final int Table_ID = 831;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name Mark1Percent */
    public static final String COLUMNNAME_Mark1Percent = "Mark1Percent";

	/** Set Mark 1 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark1Percent (int Mark1Percent);

	/** Get Mark 1 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark1Percent();

    /** Column name Mark2Percent */
    public static final String COLUMNNAME_Mark2Percent = "Mark2Percent";

	/** Set Mark 2 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark2Percent (int Mark2Percent);

	/** Get Mark 2 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark2Percent();

    /** Column name Mark3Percent */
    public static final String COLUMNNAME_Mark3Percent = "Mark3Percent";

	/** Set Mark 3 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark3Percent (int Mark3Percent);

	/** Get Mark 3 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark3Percent();

    /** Column name Mark4Percent */
    public static final String COLUMNNAME_Mark4Percent = "Mark4Percent";

	/** Set Mark 4 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark4Percent (int Mark4Percent);

	/** Get Mark 4 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark4Percent();

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

    /** Column name PA_ColorSchema_ID */
    public static final String COLUMNNAME_PA_ColorSchema_ID = "PA_ColorSchema_ID";

	/** Set Color Schema.
	  * Performance Color Schema
	  */
	public void setPA_ColorSchema_ID (int PA_ColorSchema_ID);

	/** Get Color Schema.
	  * Performance Color Schema
	  */
	public int getPA_ColorSchema_ID();

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
