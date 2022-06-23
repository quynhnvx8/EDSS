/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for C_TransferConfigLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_TransferConfigLine 
{

    /** TableName=C_TransferConfigLine */
    public static final String Table_Name = "C_TransferConfigLine";

    /** AD_Table_ID=1200294 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name Account_Cr_ID */
    public static final String COLUMNNAME_Account_Cr_ID = "Account_Cr_ID";

	/** Set Account Cr.
	  * Account Cr
	  */
	public void setAccount_Cr_ID (String Account_Cr_ID);

	/** Get Account Cr.
	  * Account Cr
	  */
	public String getAccount_Cr_ID();

    /** Column name Account_Dr_ID */
    public static final String COLUMNNAME_Account_Dr_ID = "Account_Dr_ID";

	/** Set Account Dr.
	  * Account Dr
	  */
	public void setAccount_Dr_ID (int Account_Dr_ID);

	/** Get Account Dr.
	  * Account Dr
	  */
	public int getAccount_Dr_ID();

	public eone.base.model.I_C_ElementValue getAccount_Dr() throws RuntimeException;

    /** Column name Account_RA_ID */
    public static final String COLUMNNAME_Account_RA_ID = "Account_RA_ID";

	/** Set Account Reciprocal All	  */
	public void setAccount_RA_ID (String Account_RA_ID);

	/** Get Account Reciprocal All	  */
	public String getAccount_RA_ID();

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

    /** Column name C_TransferConfig_ID */
    public static final String COLUMNNAME_C_TransferConfig_ID = "C_TransferConfig_ID";

	/** Set Transfer Config	  */
	public void setC_TransferConfig_ID (int C_TransferConfig_ID);

	/** Get Transfer Config	  */
	public int getC_TransferConfig_ID();

	public eone.base.model.I_C_TransferConfig getC_TransferConfig() throws RuntimeException;

    /** Column name C_TransferConfigLine_ID */
    public static final String COLUMNNAME_C_TransferConfigLine_ID = "C_TransferConfigLine_ID";

	/** Set Transfer Config Line	  */
	public void setC_TransferConfigLine_ID (int C_TransferConfigLine_ID);

	/** Get Transfer Config Line	  */
	public int getC_TransferConfigLine_ID();

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

    /** Column name MethodTransfer */
    public static final String COLUMNNAME_MethodTransfer = "MethodTransfer";

	/** Set MethodTransfer	  */
	public void setMethodTransfer (String MethodTransfer);

	/** Get MethodTransfer	  */
	public String getMethodTransfer();

    /** Column name OrderCalculate */
    public static final String COLUMNNAME_OrderCalculate = "OrderCalculate";

	/** Set OrderCalculate	  */
	public void setOrderCalculate (int OrderCalculate);

	/** Get OrderCalculate	  */
	public int getOrderCalculate();

    /** Column name TypeSelect */
    public static final String COLUMNNAME_TypeSelect = "TypeSelect";

	/** Set TypeSelect	  */
	public void setTypeSelect (String TypeSelect);

	/** Get TypeSelect	  */
	public String getTypeSelect();

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
