/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for M_InOut
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_M_InOut 
{

    /** TableName=M_InOut */
    public static final String Table_Name = "M_InOut";

    /** AD_Table_ID=319 */
    public static final int Table_ID = 319;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name Account_COGS_ID */
    public static final String COLUMNNAME_Account_COGS_ID = "Account_COGS_ID";

	/** Set Account COGS.
	  * Account COGS
	  */
	public void setAccount_COGS_ID (int Account_COGS_ID);

	/** Get Account COGS.
	  * Account COGS
	  */
	public int getAccount_COGS_ID();

	public eone.base.model.I_C_ElementValue getAccount_COGS() throws RuntimeException;

    /** Column name Account_REV_ID */
    public static final String COLUMNNAME_Account_REV_ID = "Account_REV_ID";

	/** Set Account Revenue.
	  * Account Revenue
	  */
	public void setAccount_REV_ID (int Account_REV_ID);

	/** Get Account Revenue.
	  * Account Revenue
	  */
	public int getAccount_REV_ID();

	public eone.base.model.I_C_ElementValue getAccount_REV() throws RuntimeException;

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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

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

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public eone.base.model.I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

    /** Column name Code_Original */
    public static final String COLUMNNAME_Code_Original = "Code_Original";

	/** Set Original	  */
	public void setCode_Original (String Code_Original);

	/** Get Original	  */
	public String getCode_Original();

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

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Date Ordered.
	  * Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered);

	/** Get Date Ordered.
	  * Date of Order
	  */
	public Timestamp getDateOrdered();

    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

	/** Set Date received.
	  * Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived);

	/** Get Date received.
	  * Date a product was received
	  */
	public Timestamp getDateReceived();

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

    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/** Set Discount Amount.
	  * Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt);

	/** Get Discount Amount.
	  * Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt();

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

    /** Column name IncludeDoc */
    public static final String COLUMNNAME_IncludeDoc = "IncludeDoc";

	/** Set IncludeDoc.
	  * Document sequence number of the document
	  */
	public void setIncludeDoc (String IncludeDoc);

	/** Get IncludeDoc.
	  * Document sequence number of the document
	  */
	public String getIncludeDoc();

    /** Column name IncludeTaxTab */
    public static final String COLUMNNAME_IncludeTaxTab = "IncludeTaxTab";

	/** Set IncludeTaxTab	  */
	public void setIncludeTaxTab (String IncludeTaxTab);

	/** Get IncludeTaxTab	  */
	public String getIncludeTaxTab();

    /** Column name InvoiceNo */
    public static final String COLUMNNAME_InvoiceNo = "InvoiceNo";

	/** Set InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo);

	/** Get InvoiceNo	  */
	public String getInvoiceNo();

    /** Column name InvoiceSymbol */
    public static final String COLUMNNAME_InvoiceSymbol = "InvoiceSymbol";

	/** Set InvoiceSymbol	  */
	public void setInvoiceSymbol (String InvoiceSymbol);

	/** Get InvoiceSymbol	  */
	public String getInvoiceSymbol();

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

    /** Column name Key_Original */
    public static final String COLUMNNAME_Key_Original = "Key_Original";

	/** Set Key Original	  */
	public void setKey_Original (int Key_Original);

	/** Get Key Original	  */
	public int getKey_Original();

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

    /** Column name ObjectName */
    public static final String COLUMNNAME_ObjectName = "ObjectName";

	/** Set ObjectName	  */
	public void setObjectName (String ObjectName);

	/** Get ObjectName	  */
	public String getObjectName();

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
