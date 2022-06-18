/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.Env;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_GeneralLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_GeneralLine extends PO implements I_C_GeneralLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220618L;

    /** Standard Constructor */
    public X_C_GeneralLine (Properties ctx, int C_GeneralLine_ID, String trxName)
    {
      super (ctx, C_GeneralLine_ID, trxName);
      /** if (C_GeneralLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_GeneraLline_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_GeneralLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_GeneralLine[")
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

	public eone.base.model.I_C_Construction getC_Construction() throws RuntimeException
    {
		return (eone.base.model.I_C_Construction)MTable.get(getCtx(), eone.base.model.I_C_Construction.Table_Name)
			.getPO(getC_Construction_ID(), get_TrxName());	}

	/** Set Construction.
		@param C_Construction_ID Construction	  */
	public void setC_Construction_ID (int C_Construction_ID)
	{
		if (C_Construction_ID < 1) 
			set_Value (COLUMNNAME_C_Construction_ID, null);
		else 
			set_Value (COLUMNNAME_C_Construction_ID, Integer.valueOf(C_Construction_ID));
	}

	/** Get Construction.
		@return Construction	  */
	public int getC_Construction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Construction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ConstructionLine getC_ConstructionLine() throws RuntimeException
    {
		return (eone.base.model.I_C_ConstructionLine)MTable.get(getCtx(), eone.base.model.I_C_ConstructionLine.Table_Name)
			.getPO(getC_ConstructionLine_ID(), get_TrxName());	}

	/** Set Construction Line.
		@param C_ConstructionLine_ID Construction Line	  */
	public void setC_ConstructionLine_ID (int C_ConstructionLine_ID)
	{
		if (C_ConstructionLine_ID < 1) 
			set_Value (COLUMNNAME_C_ConstructionLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConstructionLine_ID, Integer.valueOf(C_ConstructionLine_ID));
	}

	/** Get Construction Line.
		@return Construction Line	  */
	public int getC_ConstructionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConstructionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_Contract getC_Contract() throws RuntimeException
    {
		return (eone.base.model.I_C_Contract)MTable.get(getCtx(), eone.base.model.I_C_Contract.Table_Name)
			.getPO(getC_Contract_ID(), get_TrxName());	}

	/** Set Contract.
		@param C_Contract_ID Contract	  */
	public void setC_Contract_ID (int C_Contract_ID)
	{
		if (C_Contract_ID < 1) 
			set_Value (COLUMNNAME_C_Contract_ID, null);
		else 
			set_Value (COLUMNNAME_C_Contract_ID, Integer.valueOf(C_Contract_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getC_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ContractAnnex getC_ContractAnnex() throws RuntimeException
    {
		return (eone.base.model.I_C_ContractAnnex)MTable.get(getCtx(), eone.base.model.I_C_ContractAnnex.Table_Name)
			.getPO(getC_ContractAnnex_ID(), get_TrxName());	}

	/** Set C_ContractAnnex.
		@param C_ContractAnnex_ID C_ContractAnnex	  */
	public void setC_ContractAnnex_ID (int C_ContractAnnex_ID)
	{
		if (C_ContractAnnex_ID < 1) 
			set_Value (COLUMNNAME_C_ContractAnnex_ID, null);
		else 
			set_Value (COLUMNNAME_C_ContractAnnex_ID, Integer.valueOf(C_ContractAnnex_ID));
	}

	/** Get C_ContractAnnex.
		@return C_ContractAnnex	  */
	public int getC_ContractAnnex_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ContractAnnex_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ContractLine getC_ContractLine() throws RuntimeException
    {
		return (eone.base.model.I_C_ContractLine)MTable.get(getCtx(), eone.base.model.I_C_ContractLine.Table_Name)
			.getPO(getC_ContractLine_ID(), get_TrxName());	}

	/** Set Contract Line.
		@param C_ContractLine_ID Contract Line	  */
	public void setC_ContractLine_ID (int C_ContractLine_ID)
	{
		if (C_ContractLine_ID < 1) 
			set_Value (COLUMNNAME_C_ContractLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ContractLine_ID, Integer.valueOf(C_ContractLine_ID));
	}

	/** Get Contract Line.
		@return Contract Line	  */
	public int getC_ContractLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ContractLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_General getC_General() throws RuntimeException
    {
		return (eone.base.model.I_C_General)MTable.get(getCtx(), eone.base.model.I_C_General.Table_Name)
			.getPO(getC_General_ID(), get_TrxName());	}

	/** Set Gereral.
		@param C_General_ID Gereral	  */
	public void setC_General_ID (int C_General_ID)
	{
		if (C_General_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_General_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_General_ID, Integer.valueOf(C_General_ID));
	}

	/** Get Gereral.
		@return Gereral	  */
	public int getC_General_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_General_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GeneraLline.
		@param C_GeneraLline_ID GeneraLline	  */
	public void setC_GeneraLline_ID (int C_GeneraLline_ID)
	{
		if (C_GeneraLline_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_GeneraLline_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_GeneraLline_ID, Integer.valueOf(C_GeneraLline_ID));
	}

	/** Get GeneraLline.
		@return GeneraLline	  */
	public int getC_GeneraLline_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_GeneraLline_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (eone.base.model.I_C_Invoice)MTable.get(getCtx(), eone.base.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	public eone.base.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (eone.base.model.I_C_Project)MTable.get(getCtx(), eone.base.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ProjectLine getC_ProjectLine() throws RuntimeException
    {
		return (eone.base.model.I_C_ProjectLine)MTable.get(getCtx(), eone.base.model.I_C_ProjectLine.Table_Name)
			.getPO(getC_ProjectLine_ID(), get_TrxName());	}

	/** Set Project Line.
		@param C_ProjectLine_ID 
		Task or step in a project
	  */
	public void setC_ProjectLine_ID (int C_ProjectLine_ID)
	{
		if (C_ProjectLine_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectLine_ID, Integer.valueOf(C_ProjectLine_ID));
	}

	/** Get Project Line.
		@return Task or step in a project
	  */
	public int getC_ProjectLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectLine_ID);
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

	public eone.base.model.I_PA_ReportLine getPA_ReportLine() throws RuntimeException
    {
		return (eone.base.model.I_PA_ReportLine)MTable.get(getCtx(), eone.base.model.I_PA_ReportLine.Table_Name)
			.getPO(getPA_ReportLine_ID(), get_TrxName());	}

	/** Set Report Line.
		@param PA_ReportLine_ID Report Line	  */
	public void setPA_ReportLine_ID (int PA_ReportLine_ID)
	{
		if (PA_ReportLine_ID < 1) 
			set_Value (COLUMNNAME_PA_ReportLine_ID, null);
		else 
			set_Value (COLUMNNAME_PA_ReportLine_ID, Integer.valueOf(PA_ReportLine_ID));
	}

	/** Get Report Line.
		@return Report Line	  */
	public int getPA_ReportLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportLine_ID);
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
}