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

import eone.util.Env;

/** Generated Model for C_Payment
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_Payment extends PO implements I_C_Payment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211014L;

    /** Standard Constructor */
    public X_C_Payment (Properties ctx, int C_Payment_ID, String trxName)
    {
      super (ctx, C_Payment_ID, trxName);
      /** if (C_Payment_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_Payment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_Payment[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Payment.
		@param C_Payment_ID Payment	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
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

	/** Set Cash Amt.
		@param CashAmt Cash Amt	  */
	public void setCashAmt (BigDecimal CashAmt)
	{
		set_Value (COLUMNNAME_CashAmt, CashAmt);
	}

	/** Get Cash Amt.
		@return Cash Amt	  */
	public BigDecimal getCashAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CashAmt);
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

	/** Set Debit Amt.
		@param DebitAmt Debit Amt	  */
	public void setDebitAmt (BigDecimal DebitAmt)
	{
		set_Value (COLUMNNAME_DebitAmt, DebitAmt);
	}

	/** Get Debit Amt.
		@return Debit Amt	  */
	public BigDecimal getDebitAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DebitAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public eone.base.model.I_HM_Patient getHM_Patient() throws RuntimeException
    {
		return (eone.base.model.I_HM_Patient)MTable.get(getCtx(), eone.base.model.I_HM_Patient.Table_Name)
			.getPO(getHM_Patient_ID(), get_TrxName());	}

	/** Set Patient.
		@param HM_Patient_ID Patient	  */
	public void setHM_Patient_ID (int HM_Patient_ID)
	{
		if (HM_Patient_ID < 1) 
			set_Value (COLUMNNAME_HM_Patient_ID, null);
		else 
			set_Value (COLUMNNAME_HM_Patient_ID, Integer.valueOf(HM_Patient_ID));
	}

	/** Get Patient.
		@return Patient	  */
	public int getHM_Patient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_Patient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_PatientRegister getHM_PatientRegister() throws RuntimeException
    {
		return (eone.base.model.I_HM_PatientRegister)MTable.get(getCtx(), eone.base.model.I_HM_PatientRegister.Table_Name)
			.getPO(getHM_PatientRegister_ID(), get_TrxName());	}

	/** Set Patient Register.
		@param HM_PatientRegister_ID Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID)
	{
		if (HM_PatientRegister_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, Integer.valueOf(HM_PatientRegister_ID));
	}

	/** Get Patient Register.
		@return Patient Register	  */
	public int getHM_PatientRegister_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientRegister_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Total Paid.
		@param TotalPaid Total Paid	  */
	public void setTotalPaid (BigDecimal TotalPaid)
	{
		set_Value (COLUMNNAME_TotalPaid, TotalPaid);
	}

	/** Get Total Paid.
		@return Total Paid	  */
	public BigDecimal getTotalPaid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalPaid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transfer Amt.
		@param TransferAmt Transfer Amt	  */
	public void setTransferAmt (BigDecimal TransferAmt)
	{
		set_Value (COLUMNNAME_TransferAmt, TransferAmt);
	}

	/** Get Transfer Amt.
		@return Transfer Amt	  */
	public BigDecimal getTransferAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TransferAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}