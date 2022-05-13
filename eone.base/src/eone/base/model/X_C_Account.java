/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Account
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_Account extends PO implements I_C_Account, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220513L;

    /** Standard Constructor */
    public X_C_Account (Properties ctx, int C_Account_ID, String trxName)
    {
      super (ctx, C_Account_ID, trxName);
      /** if (C_Account_ID == 0)
        {
			setAccount_ID (0);
			setC_Account_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Account (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_Account[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
    {
		return (eone.base.model.I_A_Asset_Group)MTable.get(getCtx(), eone.base.model.I_A_Asset_Group.Table_Name)
			.getPO(getA_Asset_Group_ID(), get_TrxName());	}

	/** Set Asset Group.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (eone.base.model.I_A_Asset)MTable.get(getCtx(), eone.base.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_ElementValue getAccount() throws RuntimeException
    {
		return (eone.base.model.I_C_ElementValue)MTable.get(getCtx(), eone.base.model.I_C_ElementValue.Table_Name)
			.getPO(getAccount_ID(), get_TrxName());	}

	/** Set Account.
		@param Account_ID 
		Account used
	  */
	public void setAccount_ID (int Account_ID)
	{
		if (Account_ID < 1) 
			set_Value (COLUMNNAME_Account_ID, null);
		else 
			set_Value (COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
	}

	/** Get Account.
		@return Account used
	  */
	public int getAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account.
		@param C_Account_ID Account	  */
	public void setC_Account_ID (int C_Account_ID)
	{
		if (C_Account_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Account_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Account_ID, Integer.valueOf(C_Account_ID));
	}

	/** Get Account.
		@return Account	  */
	public int getC_Account_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (eone.base.model.I_C_BPartner)MTable.get(getCtx(), eone.base.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Construction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Construction_ID, Integer.valueOf(C_Construction_ID));
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

	public eone.base.model.I_C_Contract getC_Contract() throws RuntimeException
    {
		return (eone.base.model.I_C_Contract)MTable.get(getCtx(), eone.base.model.I_C_Contract.Table_Name)
			.getPO(getC_Contract_ID(), get_TrxName());	}

	/** Set Contract.
		@param C_Contract_ID Contract	  */
	public void setC_Contract_ID (int C_Contract_ID)
	{
		if (C_Contract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Contract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Contract_ID, Integer.valueOf(C_Contract_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	public eone.base.model.I_C_Element getC_Element() throws RuntimeException
    {
		return (eone.base.model.I_C_Element)MTable.get(getCtx(), eone.base.model.I_C_Element.Table_Name)
			.getPO(getC_Element_ID(), get_TrxName());	}

	/** Set Element.
		@param C_Element_ID 
		Accounting Element
	  */
	public void setC_Element_ID (int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_Value (COLUMNNAME_C_Element_ID, null);
		else 
			set_Value (COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
	}

	/** Get Element.
		@return Accounting Element
	  */
	public int getC_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Element_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public eone.base.model.I_M_ProductGroup getM_ProductGroup() throws RuntimeException
    {
		return (eone.base.model.I_M_ProductGroup)MTable.get(getCtx(), eone.base.model.I_M_ProductGroup.Table_Name)
			.getPO(getM_ProductGroup_ID(), get_TrxName());	}

	/** Set ProductGroup.
		@param M_ProductGroup_ID ProductGroup	  */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, Integer.valueOf(M_ProductGroup_ID));
	}

	/** Get ProductGroup.
		@return ProductGroup	  */
	public int getM_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (eone.base.model.I_M_Warehouse)MTable.get(getCtx(), eone.base.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Debit Account = DEBT_ACCT */
	public static final String TYPEACCOUNT_DebitAccount = "DEBT_ACCT";
	/** Credit Account = CRED_ACCT */
	public static final String TYPEACCOUNT_CreditAccount = "CRED_ACCT";
	/** Credit Other Account = CREDO_ACCT */
	public static final String TYPEACCOUNT_CreditOtherAccount = "CREDO_ACCT";
	/** Debit Other Account = DEBTO_ACCT */
	public static final String TYPEACCOUNT_DebitOtherAccount = "DEBTO_ACCT";
	/** Asset Account = ASSET_ACCT */
	public static final String TYPEACCOUNT_AssetAccount = "ASSET_ACCT";
	/** Accumulate Account = ACCU_ACCT */
	public static final String TYPEACCOUNT_AccumulateAccount = "ACCU_ACCT";
	/** Tax Input Account = TAXIN_ACCT */
	public static final String TYPEACCOUNT_TaxInputAccount = "TAXIN_ACCT";
	/** Tax Output Account = TAXOU_ACCT */
	public static final String TYPEACCOUNT_TaxOutputAccount = "TAXOU_ACCT";
	/** Cost Account = COST_ACCT */
	public static final String TYPEACCOUNT_CostAccount = "COST_ACCT";
	/** Revenue Account = REVEN_ACCT */
	public static final String TYPEACCOUNT_RevenueAccount = "REVEN_ACCT";
	/** COGS Account = COGS_ACCT */
	public static final String TYPEACCOUNT_COGSAccount = "COGS_ACCT";
	/** Expense Accumulate Account = EXPEN_ACCT */
	public static final String TYPEACCOUNT_ExpenseAccumulateAccount = "EXPEN_ACCT";
	/** Set TypeAccount.
		@param TypeAccount TypeAccount	  */
	public void setTypeAccount (String TypeAccount)
	{

		set_Value (COLUMNNAME_TypeAccount, TypeAccount);
	}

	/** Get TypeAccount.
		@return TypeAccount	  */
	public String getTypeAccount () 
	{
		return (String)get_Value(COLUMNNAME_TypeAccount);
	}
}