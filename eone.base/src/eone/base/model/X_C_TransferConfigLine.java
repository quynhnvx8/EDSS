/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_TransferConfigLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_TransferConfigLine extends PO implements I_C_TransferConfigLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220622L;

    /** Standard Constructor */
    public X_C_TransferConfigLine (Properties ctx, int C_TransferConfigLine_ID, String trxName)
    {
      super (ctx, C_TransferConfigLine_ID, trxName);
      /** if (C_TransferConfigLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_TransferConfigLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuilder sb = new StringBuilder ("X_C_TransferConfigLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account Cr.
		@param Account_Cr_ID 
		Account Cr
	  */
	public void setAccount_Cr_ID (String Account_Cr_ID)
	{

		set_Value (COLUMNNAME_Account_Cr_ID, Account_Cr_ID);
	}

	/** Get Account Cr.
		@return Account Cr
	  */
	public String getAccount_Cr_ID () 
	{
		return (String)get_Value(COLUMNNAME_Account_Cr_ID);
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

	/** Set Account Reciprocal All.
		@param Account_RA_ID Account Reciprocal All	  */
	public void setAccount_RA_ID (String Account_RA_ID)
	{

		set_Value (COLUMNNAME_Account_RA_ID, Account_RA_ID);
	}

	/** Get Account Reciprocal All.
		@return Account Reciprocal All	  */
	public String getAccount_RA_ID () 
	{
		return (String)get_Value(COLUMNNAME_Account_RA_ID);
	}

	public eone.base.model.I_C_TransferConfig getC_TransferConfig() throws RuntimeException
    {
		return (eone.base.model.I_C_TransferConfig)MTable.get(getCtx(), eone.base.model.I_C_TransferConfig.Table_Name)
			.getPO(getC_TransferConfig_ID(), get_TrxName());	}

	/** Set Transfer Config.
		@param C_TransferConfig_ID Transfer Config	  */
	public void setC_TransferConfig_ID (int C_TransferConfig_ID)
	{
		if (C_TransferConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TransferConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TransferConfig_ID, Integer.valueOf(C_TransferConfig_ID));
	}

	/** Get Transfer Config.
		@return Transfer Config	  */
	public int getC_TransferConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TransferConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transfer Config Line.
		@param C_TransferConfigLine_ID Transfer Config Line	  */
	public void setC_TransferConfigLine_ID (int C_TransferConfigLine_ID)
	{
		if (C_TransferConfigLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TransferConfigLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TransferConfigLine_ID, Integer.valueOf(C_TransferConfigLine_ID));
	}

	/** Get Transfer Config Line.
		@return Transfer Config Line	  */
	public int getC_TransferConfigLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TransferConfigLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Incurred Credit = PSCO */
	public static final String METHODTRANSFER_IncurredCredit = "PSCO";
	/** Incurred Debit = PSNO */
	public static final String METHODTRANSFER_IncurredDebit = "PSNO";
	/** Balance Debit = DUNO */
	public static final String METHODTRANSFER_BalanceDebit = "DUNO";
	/** Balance Credit = DUCO */
	public static final String METHODTRANSFER_BalanceCredit = "DUCO";
	/** ABSValue = ABSV */
	public static final String METHODTRANSFER_ABSValue = "ABSV";
	/** Set MethodTransfer.
		@param MethodTransfer MethodTransfer	  */
	public void setMethodTransfer (String MethodTransfer)
	{

		set_Value (COLUMNNAME_MethodTransfer, MethodTransfer);
	}

	/** Get MethodTransfer.
		@return MethodTransfer	  */
	public String getMethodTransfer () 
	{
		return (String)get_Value(COLUMNNAME_MethodTransfer);
	}

	/** Set OrderCalculate.
		@param OrderCalculate OrderCalculate	  */
	public void setOrderCalculate (int OrderCalculate)
	{
		set_Value (COLUMNNAME_OrderCalculate, Integer.valueOf(OrderCalculate));
	}

	/** Get OrderCalculate.
		@return OrderCalculate	  */
	public int getOrderCalculate () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OrderCalculate);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AND = AND */
	public static final String TYPESELECT_AND = "AND";
	/** NOT = NOT */
	public static final String TYPESELECT_NOT = "NOT";
	/** NONE = NONE */
	public static final String TYPESELECT_NONE = "NONE";
	/** Set TypeSelect.
		@param TypeSelect TypeSelect	  */
	public void setTypeSelect (String TypeSelect)
	{

		set_Value (COLUMNNAME_TypeSelect, TypeSelect);
	}

	/** Get TypeSelect.
		@return TypeSelect	  */
	public String getTypeSelect () 
	{
		return (String)get_Value(COLUMNNAME_TypeSelect);
	}
}