/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import eone.util.Env;

/** Generated Model for C_PaymentLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_PaymentLine extends PO implements I_C_PaymentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211014L;

    /** Standard Constructor */
    public X_C_PaymentLine (Properties ctx, int C_PaymentLine_ID, String trxName)
    {
      super (ctx, C_PaymentLine_ID, trxName);
      /** if (C_PaymentLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_PaymentLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_PaymentLine[")
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

	public eone.base.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (eone.base.model.I_C_Payment)MTable.get(getCtx(), eone.base.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

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

	/** Set Payment Line.
		@param C_PaymentLine_ID Payment Line	  */
	public void setC_PaymentLine_ID (int C_PaymentLine_ID)
	{
		if (C_PaymentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentLine_ID, Integer.valueOf(C_PaymentLine_ID));
	}

	/** Get Payment Line.
		@return Payment Line	  */
	public int getC_PaymentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HM_PatientRegisterLine getHM_PatientRegisterLine() throws RuntimeException
    {
		return (eone.base.model.I_HM_PatientRegisterLine)MTable.get(getCtx(), eone.base.model.I_HM_PatientRegisterLine.Table_Name)
			.getPO(getHM_PatientRegisterLine_ID(), get_TrxName());	}

	/** Set PatientRegisterLine.
		@param HM_PatientRegisterLine_ID PatientRegisterLine	  */
	public void setHM_PatientRegisterLine_ID (int HM_PatientRegisterLine_ID)
	{
		if (HM_PatientRegisterLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegisterLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegisterLine_ID, Integer.valueOf(HM_PatientRegisterLine_ID));
	}

	/** Get PatientRegisterLine.
		@return PatientRegisterLine	  */
	public int getHM_PatientRegisterLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientRegisterLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (eone.base.model.I_M_Product)MTable.get(getCtx(), eone.base.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Null = NU */
	public static final String PERFORMSTATUS_Null = "NU";
	/** Yes = YE */
	public static final String PERFORMSTATUS_Yes = "YE";
	/** No = NO */
	public static final String PERFORMSTATUS_No = "NO";
	/** Set Perform Status.
		@param PerformStatus Perform Status	  */
	public void setPerformStatus (String PerformStatus)
	{

		set_Value (COLUMNNAME_PerformStatus, PerformStatus);
	}

	/** Get Perform Status.
		@return Perform Status	  */
	public String getPerformStatus () 
	{
		return (String)get_Value(COLUMNNAME_PerformStatus);
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
}