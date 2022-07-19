/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for C_TypeCost
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_TypeCost 
{

    /** TableName=C_TypeCost */
    public static final String Table_Name = "C_TypeCost";

    /** AD_Table_ID=1000003 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name C_TypeCost_ID */
    public static final String COLUMNNAME_C_TypeCost_ID = "C_TypeCost_ID";

	/** Set TypeCost	  */
	public void setC_TypeCost_ID (int C_TypeCost_ID);

	/** Get TypeCost	  */
	public int getC_TypeCost_ID();

    /** Column name Code133 */
    public static final String COLUMNNAME_Code133 = "Code133";

	/** Set Code133	  */
	public void setCode133 (String Code133);

	/** Get Code133	  */
	public String getCode133();

    /** Column name Code200 */
    public static final String COLUMNNAME_Code200 = "Code200";

	/** Set Code200	  */
	public void setCode200 (String Code200);

	/** Get Code200	  */
	public String getCode200();

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


    /** Column name IsManufactured */
    public static final String COLUMNNAME_IsManufactured = "IsManufactured";

	/** Set Manufactured.
	  * This product is manufactured
	  */
	public void setIsManufactured (boolean IsManufactured);

	/** Get Manufactured.
	  * This product is manufactured
	  */
	public boolean isManufactured();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name MethodAllocation */
    public static final String COLUMNNAME_MethodAllocation = "MethodAllocation";

	/** Set Method Allocation	  */
	public void setMethodAllocation (String MethodAllocation);

	/** Get Method Allocation	  */
	public String getMethodAllocation();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name	  */
	public void setName (String Name);

	/** Get Name	  */
	public String getName();

    /** Column name TypeCost */
    public static final String COLUMNNAME_TypeCost = "TypeCost";

	/** Set TypeCost	  */
	public void setTypeCost (String TypeCost);

	/** Get TypeCost	  */
	public String getTypeCost();

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
