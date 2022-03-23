/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import eone.util.KeyNamePair;

/** Generated Interface for C_Invoice
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_Invoice 
{

    /** TableName=C_Invoice */
    public static final String Table_Name = "C_Invoice";

    /** AD_Table_ID=318 */
    public static final int Table_ID = 318;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name Account_Cr_ID */
    public static final String COLUMNNAME_Account_Cr_ID = "Account_Cr_ID";

	/** Set Account Cr.
	  * Account Cr
	  */
	public void setAccount_Cr_ID (int Account_Cr_ID);

	/** Get Account Cr.
	  * Account Cr
	  */
	public int getAccount_Cr_ID();

	public eone.base.model.I_C_ElementValue getAccount_Cr() throws RuntimeException;

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

    /** Column name Account_Tax_ID */
    public static final String COLUMNNAME_Account_Tax_ID = "Account_Tax_ID";

	/** Set Account Tax	  */
	public void setAccount_Tax_ID (int Account_Tax_ID);

	/** Get Account Tax	  */
	public int getAccount_Tax_ID();

	public eone.base.model.I_C_ElementValue getAccount_Tax() throws RuntimeException;

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

    /** Column name AmountConvert */
    public static final String COLUMNNAME_AmountConvert = "AmountConvert";

	/** Set AmountConvert	  */
	public void setAmountConvert (BigDecimal AmountConvert);

	/** Get AmountConvert	  */
	public BigDecimal getAmountConvert();

    /** Column name Approved */
    public static final String COLUMNNAME_Approved = "Approved";

	/** Set Approved	  */
	public void setApproved (String Approved);

	/** Get Approved	  */
	public String getApproved();

    /** Column name C_BPartner_Cr_ID */
    public static final String COLUMNNAME_C_BPartner_Cr_ID = "C_BPartner_Cr_ID";

	/** Set Business Partner Cr.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_Cr_ID (int C_BPartner_Cr_ID);

	/** Get Business Partner Cr.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_Cr_ID();

	public eone.base.model.I_C_BPartner getC_BPartner_Cr() throws RuntimeException;

    /** Column name C_BPartner_Dr_ID */
    public static final String COLUMNNAME_C_BPartner_Dr_ID = "C_BPartner_Dr_ID";

	/** Set Business Partner Dr.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_Dr_ID (int C_BPartner_Dr_ID);

	/** Get Business Partner Dr.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_Dr_ID();

	public eone.base.model.I_C_BPartner getC_BPartner_Dr() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public eone.base.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public eone.base.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_DocTypeSub_ID */
    public static final String COLUMNNAME_C_DocTypeSub_ID = "C_DocTypeSub_ID";

	/** Set Sub Document.
	  * Document type for generating in dispute Shipments
	  */
	public void setC_DocTypeSub_ID (int C_DocTypeSub_ID);

	/** Get Sub Document.
	  * Document type for generating in dispute Shipments
	  */
	public int getC_DocTypeSub_ID();

	public eone.base.model.I_C_DocTypeSub getC_DocTypeSub() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public eone.base.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name C_TypeCost_ID */
    public static final String COLUMNNAME_C_TypeCost_ID = "C_TypeCost_ID";

	/** Set TypeCost	  */
	public void setC_TypeCost_ID (int C_TypeCost_ID);

	/** Get TypeCost	  */
	public int getC_TypeCost_ID();

	public eone.base.model.I_C_TypeCost getC_TypeCost() throws RuntimeException;

    /** Column name C_TypeRevenue_ID */
    public static final String COLUMNNAME_C_TypeRevenue_ID = "C_TypeRevenue_ID";

	/** Set Type Revenue	  */
	public void setC_TypeRevenue_ID (int C_TypeRevenue_ID);

	/** Get Type Revenue	  */
	public int getC_TypeRevenue_ID();

	public eone.base.model.I_C_TypeRevenue getC_TypeRevenue() throws RuntimeException;

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

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

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

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

    /** Column name InvoiceNo */
    public static final String COLUMNNAME_InvoiceNo = "InvoiceNo";

	/** Set InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo);

	/** Get InvoiceNo	  */
	public String getInvoiceNo();

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

    /** Column name Ref_Invoice_ID */
    public static final String COLUMNNAME_Ref_Invoice_ID = "Ref_Invoice_ID";

	/** Set Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID);

	/** Get Referenced Invoice	  */
	public int getRef_Invoice_ID();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

    /** Column name TaxBaseAmt */
    public static final String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";

	/** Set Tax base Amount.
	  * Base for calculating the tax amount
	  */
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt);

	/** Get Tax base Amount.
	  * Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt();

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
