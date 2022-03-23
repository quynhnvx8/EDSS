/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for C_Payment
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_Payment 
{

    /** TableName=C_Payment */
    public static final String Table_Name = "C_Payment";

    /** AD_Table_ID=1200406 */
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

    /** Column name Approved */
    public static final String COLUMNNAME_Approved = "Approved";

	/** Set Approved	  */
	public void setApproved (String Approved);

	/** Get Approved	  */
	public String getApproved();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment	  */
	public int getC_Payment_ID();

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

    /** Column name CashAmt */
    public static final String COLUMNNAME_CashAmt = "CashAmt";

	/** Set Cash Amt	  */
	public void setCashAmt (BigDecimal CashAmt);

	/** Get Cash Amt	  */
	public BigDecimal getCashAmt();

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DebitAmt */
    public static final String COLUMNNAME_DebitAmt = "DebitAmt";

	/** Set Debit Amt	  */
	public void setDebitAmt (BigDecimal DebitAmt);

	/** Get Debit Amt	  */
	public BigDecimal getDebitAmt();

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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name HM_Patient_ID */
    public static final String COLUMNNAME_HM_Patient_ID = "HM_Patient_ID";

	/** Set Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID);

	/** Get Patient	  */
	public int getHM_Patient_ID();

	public eone.base.model.I_HM_Patient getHM_Patient() throws RuntimeException;

    /** Column name HM_PatientRegister_ID */
    public static final String COLUMNNAME_HM_PatientRegister_ID = "HM_PatientRegister_ID";

	/** Set Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID);

	/** Get Patient Register	  */
	public int getHM_PatientRegister_ID();

	public eone.base.model.I_HM_PatientRegister getHM_PatientRegister() throws RuntimeException;

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

    /** Column name TotalPaid */
    public static final String COLUMNNAME_TotalPaid = "TotalPaid";

	/** Set Total Paid	  */
	public void setTotalPaid (BigDecimal TotalPaid);

	/** Get Total Paid	  */
	public BigDecimal getTotalPaid();

    /** Column name TransferAmt */
    public static final String COLUMNNAME_TransferAmt = "TransferAmt";

	/** Set Transfer Amt	  */
	public void setTransferAmt (BigDecimal TransferAmt);

	/** Get Transfer Amt	  */
	public BigDecimal getTransferAmt();

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
