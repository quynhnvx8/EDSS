/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for PA_MeasureCalc
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_PA_MeasureCalc 
{

    /** TableName=PA_MeasureCalc */
    public static final String Table_Name = "PA_MeasureCalc";

    /** AD_Table_ID=442 */
    public static final int Table_ID = 442;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name BPartnerColumn */
    public static final String COLUMNNAME_BPartnerColumn = "BPartnerColumn";

	/** Set B.Partner Column.
	  * Fully qualified Business Partner key column (C_BPartner_ID)
	  */
	public void setBPartnerColumn (String BPartnerColumn);

	/** Get B.Partner Column.
	  * Fully qualified Business Partner key column (C_BPartner_ID)
	  */
	public String getBPartnerColumn();

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

    /** Column name DateColumn */
    public static final String COLUMNNAME_DateColumn = "DateColumn";

	/** Set Date Column.
	  * Fully qualified date column
	  */
	public void setDateColumn (String DateColumn);

	/** Get Date Column.
	  * Fully qualified date column
	  */
	public String getDateColumn();

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

    /** Column name KeyColumn */
    public static final String COLUMNNAME_KeyColumn = "KeyColumn";

	/** Set Key Column.
	  * Key Column for Table
	  */
	public void setKeyColumn (String KeyColumn);

	/** Get Key Column.
	  * Key Column for Table
	  */
	public String getKeyColumn();

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

    /** Column name OrgColumn */
    public static final String COLUMNNAME_OrgColumn = "OrgColumn";

	/** Set Org Column.
	  * Fully qualified Organization column (AD_Org_ID)
	  */
	public void setOrgColumn (String OrgColumn);

	/** Get Org Column.
	  * Fully qualified Organization column (AD_Org_ID)
	  */
	public String getOrgColumn();

    /** Column name PA_MeasureCalc_ID */
    public static final String COLUMNNAME_PA_MeasureCalc_ID = "PA_MeasureCalc_ID";

	/** Set Measure Calculation.
	  * Calculation method for measuring performance
	  */
	public void setPA_MeasureCalc_ID (int PA_MeasureCalc_ID);

	/** Get Measure Calculation.
	  * Calculation method for measuring performance
	  */
	public int getPA_MeasureCalc_ID();

    /** Column name ProductColumn */
    public static final String COLUMNNAME_ProductColumn = "ProductColumn";

	/** Set Product Column.
	  * Fully qualified Product column (M_Product_ID)
	  */
	public void setProductColumn (String ProductColumn);

	/** Get Product Column.
	  * Fully qualified Product column (M_Product_ID)
	  */
	public String getProductColumn();

    /** Column name SelectClause */
    public static final String COLUMNNAME_SelectClause = "SelectClause";

	/** Set Sql SELECT.
	  * SQL SELECT clause
	  */
	public void setSelectClause (String SelectClause);

	/** Get Sql SELECT.
	  * SQL SELECT clause
	  */
	public String getSelectClause();

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
