/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_Invoice
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_Invoice extends PO implements I_C_Invoice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211227L;

    /** Standard Constructor */
    public X_C_Invoice (Properties ctx, int C_Invoice_ID, String trxName)
    {
      super (ctx, C_Invoice_ID, trxName);
      /** if (C_Invoice_ID == 0)
        {
			setC_Invoice_ID (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Invoice (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_Invoice[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_C_ElementValue getAccount_Cr() throws RuntimeException
    {
		return (eone.base.model.I_C_ElementValue)MTable.get(getCtx(), eone.base.model.I_C_ElementValue.Table_Name)
			.getPO(getAccount_Cr_ID(), get_TrxName());	}

	/** Set Account Cr.
		@param Account_Cr_ID 
		Account Cr
	  */
	public void setAccount_Cr_ID (int Account_Cr_ID)
	{
		if (Account_Cr_ID < 1) 
			set_Value (COLUMNNAME_Account_Cr_ID, null);
		else 
			set_Value (COLUMNNAME_Account_Cr_ID, Integer.valueOf(Account_Cr_ID));
	}

	/** Get Account Cr.
		@return Account Cr
	  */
	public int getAccount_Cr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Cr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ElementValue getAccount_Dr() throws RuntimeException
    {
		return (eone.base.model.I_C_ElementValue)MTable.get(getCtx(), eone.base.model.I_C_ElementValue.Table_Name)
			.getPO(getAccount_Dr_ID(), get_TrxName());	}

	/** Set Account Dr.
		@param Account_Dr_ID 
		Account Dr
	  */
	public void setAccount_Dr_ID (int Account_Dr_ID)
	{
		if (Account_Dr_ID < 1) 
			set_Value (COLUMNNAME_Account_Dr_ID, null);
		else 
			set_Value (COLUMNNAME_Account_Dr_ID, Integer.valueOf(Account_Dr_ID));
	}

	/** Get Account Dr.
		@return Account Dr
	  */
	public int getAccount_Dr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Dr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ElementValue getAccount_Tax() throws RuntimeException
    {
		return (eone.base.model.I_C_ElementValue)MTable.get(getCtx(), eone.base.model.I_C_ElementValue.Table_Name)
			.getPO(getAccount_Tax_ID(), get_TrxName());	}

	/** Set Account Tax.
		@param Account_Tax_ID Account Tax	  */
	public void setAccount_Tax_ID (int Account_Tax_ID)
	{
		if (Account_Tax_ID < 1) 
			set_Value (COLUMNNAME_Account_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_Account_Tax_ID, Integer.valueOf(Account_Tax_ID));
	}

	/** Get Account Tax.
		@return Account Tax	  */
	public int getAccount_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmountConvert.
		@param AmountConvert AmountConvert	  */
	public void setAmountConvert (BigDecimal AmountConvert)
	{
		set_Value (COLUMNNAME_AmountConvert, AmountConvert);
	}

	/** Get AmountConvert.
		@return AmountConvert	  */
	public BigDecimal getAmountConvert () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountConvert);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Approved.
		@param Approved Approved	  */
	public void setApproved (String Approved)
	{
		set_Value (COLUMNNAME_Approved, Approved);
	}

	/** Get Approved.
		@return Approved	  */
	public String getApproved () 
	{
		return (String)get_Value(COLUMNNAME_Approved);
	}

	public eone.base.model.I_C_BPartner getC_BPartner_Cr() throws RuntimeException
    {
		return (eone.base.model.I_C_BPartner)MTable.get(getCtx(), eone.base.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_Cr_ID(), get_TrxName());	}

	/** Set Business Partner Cr.
		@param C_BPartner_Cr_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_Cr_ID (int C_BPartner_Cr_ID)
	{
		if (C_BPartner_Cr_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Cr_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Cr_ID, Integer.valueOf(C_BPartner_Cr_ID));
	}

	/** Get Business Partner Cr.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_Cr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Cr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_BPartner getC_BPartner_Dr() throws RuntimeException
    {
		return (eone.base.model.I_C_BPartner)MTable.get(getCtx(), eone.base.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_Dr_ID(), get_TrxName());	}

	/** Set Business Partner Dr.
		@param C_BPartner_Dr_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_Dr_ID (int C_BPartner_Dr_ID)
	{
		if (C_BPartner_Dr_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Dr_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Dr_ID, Integer.valueOf(C_BPartner_Dr_ID));
	}

	/** Get Business Partner Dr.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_Dr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Dr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (eone.base.model.I_C_Currency)MTable.get(getCtx(), eone.base.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (eone.base.model.I_C_DocType)MTable.get(getCtx(), eone.base.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_DocTypeSub getC_DocTypeSub() throws RuntimeException
    {
		return (eone.base.model.I_C_DocTypeSub)MTable.get(getCtx(), eone.base.model.I_C_DocTypeSub.Table_Name)
			.getPO(getC_DocTypeSub_ID(), get_TrxName());	}

	/** Set Sub Document.
		@param C_DocTypeSub_ID 
		Document type for generating in dispute Shipments
	  */
	public void setC_DocTypeSub_ID (int C_DocTypeSub_ID)
	{
		if (C_DocTypeSub_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeSub_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeSub_ID, Integer.valueOf(C_DocTypeSub_ID));
	}

	/** Get Sub Document.
		@return Document type for generating in dispute Shipments
	  */
	public int getC_DocTypeSub_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeSub_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (eone.base.model.I_C_Tax)MTable.get(getCtx(), eone.base.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_TypeCost getC_TypeCost() throws RuntimeException
    {
		return (eone.base.model.I_C_TypeCost)MTable.get(getCtx(), eone.base.model.I_C_TypeCost.Table_Name)
			.getPO(getC_TypeCost_ID(), get_TrxName());	}

	/** Set TypeCost.
		@param C_TypeCost_ID TypeCost	  */
	public void setC_TypeCost_ID (int C_TypeCost_ID)
	{
		if (C_TypeCost_ID < 1) 
			set_Value (COLUMNNAME_C_TypeCost_ID, null);
		else 
			set_Value (COLUMNNAME_C_TypeCost_ID, Integer.valueOf(C_TypeCost_ID));
	}

	/** Get TypeCost.
		@return TypeCost	  */
	public int getC_TypeCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TypeCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_TypeRevenue getC_TypeRevenue() throws RuntimeException
    {
		return (eone.base.model.I_C_TypeRevenue)MTable.get(getCtx(), eone.base.model.I_C_TypeRevenue.Table_Name)
			.getPO(getC_TypeRevenue_ID(), get_TrxName());	}

	/** Set Type Revenue.
		@param C_TypeRevenue_ID Type Revenue	  */
	public void setC_TypeRevenue_ID (int C_TypeRevenue_ID)
	{
		if (C_TypeRevenue_ID < 1) 
			set_Value (COLUMNNAME_C_TypeRevenue_ID, null);
		else 
			set_Value (COLUMNNAME_C_TypeRevenue_ID, Integer.valueOf(C_TypeRevenue_ID));
	}

	/** Get Type Revenue.
		@return Type Revenue	  */
	public int getC_TypeRevenue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TypeRevenue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canceled.
		@param Canceled Canceled	  */
	public void setCanceled (String Canceled)
	{
		set_Value (COLUMNNAME_Canceled, Canceled);
	}

	/** Get Canceled.
		@return Canceled	  */
	public String getCanceled () 
	{
		return (String)get_Value(COLUMNNAME_Canceled);
	}

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Reject = RE */
	public static final String DOCSTATUS_Reject = "RE";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set InvoiceNo.
		@param InvoiceNo InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo)
	{
		set_Value (COLUMNNAME_InvoiceNo, InvoiceNo);
	}

	/** Get InvoiceNo.
		@return InvoiceNo	  */
	public String getInvoiceNo () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceNo);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Referenced Invoice.
		@param Ref_Invoice_ID Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
	}

	/** Get Referenced Invoice.
		@return Referenced Invoice	  */
	public int getRef_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax base Amount.
		@param TaxBaseAmt 
		Base for calculating the tax amount
	  */
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt)
	{
		set_Value (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Tax base Amount.
		@return Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}