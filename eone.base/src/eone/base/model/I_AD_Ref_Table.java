/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for AD_Ref_Table
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_AD_Ref_Table 
{

    /** TableName=AD_Ref_Table */
    public static final String Table_Name = "AD_Ref_Table";

    /** AD_Table_ID=103 */
    public static final int Table_ID = 103;

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

    /** Column name AD_Display */
    public static final String COLUMNNAME_AD_Display = "AD_Display";

	/** Set Display column.
	  * Column that will display
	  */
	public void setAD_Display (int AD_Display);

	/** Get Display column.
	  * Column that will display
	  */
	public int getAD_Display();

	public eone.base.model.I_AD_Column getAD_Disp() throws RuntimeException;

    /** Column name AD_InfoWindow_ID */
    public static final String COLUMNNAME_AD_InfoWindow_ID = "AD_InfoWindow_ID";

	/** Set Info Window.
	  * Info and search/select Window
	  */
	public void setAD_InfoWindow_ID (int AD_InfoWindow_ID);

	/** Get Info Window.
	  * Info and search/select Window
	  */
	public int getAD_InfoWindow_ID();

	public eone.base.model.I_AD_InfoWindow getAD_InfoWindow() throws RuntimeException;

    /** Column name AD_Key */
    public static final String COLUMNNAME_AD_Key = "AD_Key";

	/** Set Key column.
	  * Unique identifier of a record
	  */
	public void setAD_Key (int AD_Key);

	/** Get Key column.
	  * Unique identifier of a record
	  */
	public int getAD_Key();

	public eone.base.model.I_AD_Column getAD_() throws RuntimeException;

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

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set Reference.
	  * System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get Reference.
	  * System Reference and Validation
	  */
	public int getAD_Reference_ID();

	public eone.base.model.I_AD_Reference getAD_Reference() throws RuntimeException;

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public eone.base.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/** Set Window.
	  * Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID);

	/** Get Window.
	  * Data entry or display window
	  */
	public int getAD_Window_ID();

	public eone.base.model.I_AD_Window getAD_Window() throws RuntimeException;

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

    /** Column name IsValueDisplayed */
    public static final String COLUMNNAME_IsValueDisplayed = "IsValueDisplayed";

	/** Set Display Value.
	  * Displays Value column with the Display column
	  */
	public void setIsValueDisplayed (boolean IsValueDisplayed);

	/** Get Display Value.
	  * Displays Value column with the Display column
	  */
	public boolean isValueDisplayed();

    /** Column name OrderByClause */
    public static final String COLUMNNAME_OrderByClause = "OrderByClause";

	/** Set Sql ORDER BY.
	  * Fully qualified ORDER BY clause
	  */
	public void setOrderByClause (String OrderByClause);

	/** Get Sql ORDER BY.
	  * Fully qualified ORDER BY clause
	  */
	public String getOrderByClause();

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

    /** Column name WhereClause */
    public static final String COLUMNNAME_WhereClause = "WhereClause";

	/** Set Sql WHERE.
	  * Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause);

	/** Get Sql WHERE.
	  * Fully qualified SQL WHERE clause
	  */
	public String getWhereClause();
}
