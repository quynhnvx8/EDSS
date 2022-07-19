/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for M_ProductGroup
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_M_ProductGroup 
{

    /** TableName=M_ProductGroup */
    public static final String Table_Name = "M_ProductGroup";

    /** AD_Table_ID=1200412 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

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

    /** Column name CategoryType */
    public static final String COLUMNNAME_CategoryType = "CategoryType";

	/** Set CategoryType	  */
	public void setCategoryType (String CategoryType);

	/** Get CategoryType	  */
	public String getCategoryType();

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

    /** Column name M_ProductGroup_ID */
    public static final String COLUMNNAME_M_ProductGroup_ID = "M_ProductGroup_ID";

	/** Set ProductGroup	  */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID);

	/** Get ProductGroup	  */
	public int getM_ProductGroup_ID();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name	  */
	public void setName (String Name);

	/** Get Name	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

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
}
