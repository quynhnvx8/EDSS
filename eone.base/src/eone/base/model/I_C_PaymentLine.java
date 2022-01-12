/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_PaymentLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_PaymentLine 
{

    /** TableName=C_PaymentLine */
    public static final String Table_Name = "C_PaymentLine";

    /** AD_Table_ID=1200407 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment	  */
	public int getC_Payment_ID();

	public eone.base.model.I_C_Payment getC_Payment() throws RuntimeException;

    /** Column name C_PaymentLine_ID */
    public static final String COLUMNNAME_C_PaymentLine_ID = "C_PaymentLine_ID";

	/** Set Payment Line	  */
	public void setC_PaymentLine_ID (int C_PaymentLine_ID);

	/** Get Payment Line	  */
	public int getC_PaymentLine_ID();

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

    /** Column name HM_PatientRegisterLine_ID */
    public static final String COLUMNNAME_HM_PatientRegisterLine_ID = "HM_PatientRegisterLine_ID";

	/** Set PatientRegisterLine	  */
	public void setHM_PatientRegisterLine_ID (int HM_PatientRegisterLine_ID);

	/** Get PatientRegisterLine	  */
	public int getHM_PatientRegisterLine_ID();

	public eone.base.model.I_HM_PatientRegisterLine getHM_PatientRegisterLine() throws RuntimeException;

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public eone.base.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PerformStatus */
    public static final String COLUMNNAME_PerformStatus = "PerformStatus";

	/** Set Perform Status	  */
	public void setPerformStatus (String PerformStatus);

	/** Get Perform Status	  */
	public String getPerformStatus();

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
