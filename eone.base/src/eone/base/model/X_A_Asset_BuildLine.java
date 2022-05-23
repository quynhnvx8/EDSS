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

/** Generated Model for A_Asset_BuildLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_A_Asset_BuildLine extends PO implements I_A_Asset_BuildLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220523L;

    /** Standard Constructor */
    public X_A_Asset_BuildLine (Properties ctx, int A_Asset_BuildLine_ID, String trxName)
    {
      super (ctx, A_Asset_BuildLine_ID, trxName);
      /** if (A_Asset_BuildLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_A_Asset_BuildLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_A_Asset_BuildLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_A_Asset_Build getA_Asset_Build() throws RuntimeException
    {
		return (eone.base.model.I_A_Asset_Build)MTable.get(getCtx(), eone.base.model.I_A_Asset_Build.Table_Name)
			.getPO(getA_Asset_Build_ID(), get_TrxName());	}

	/** Set Asset Build.
		@param A_Asset_Build_ID Asset Build	  */
	public void setA_Asset_Build_ID (int A_Asset_Build_ID)
	{
		if (A_Asset_Build_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Build_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Build_ID, Integer.valueOf(A_Asset_Build_ID));
	}

	/** Get Asset Build.
		@return Asset Build	  */
	public int getA_Asset_Build_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Build_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line.
		@param A_Asset_BuildLine_ID Line	  */
	public void setA_Asset_BuildLine_ID (int A_Asset_BuildLine_ID)
	{
		if (A_Asset_BuildLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_BuildLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_BuildLine_ID, Integer.valueOf(A_Asset_BuildLine_ID));
	}

	/** Get Line.
		@return Line	  */
	public int getA_Asset_BuildLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_BuildLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_A_Asset getA_Asset_Cr() throws RuntimeException
    {
		return (eone.base.model.I_A_Asset)MTable.get(getCtx(), eone.base.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_Cr_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_Cr_ID Asset	  */
	public void setA_Asset_Cr_ID (int A_Asset_Cr_ID)
	{
		if (A_Asset_Cr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Cr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Cr_ID, Integer.valueOf(A_Asset_Cr_ID));
	}

	/** Get Asset.
		@return Asset	  */
	public int getA_Asset_Cr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Cr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_C_DocTypeSub_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeSub_ID, Integer.valueOf(C_DocTypeSub_ID));
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

	public eone.base.model.I_M_Warehouse getM_Warehouse_Cr() throws RuntimeException
    {
		return (eone.base.model.I_M_Warehouse)MTable.get(getCtx(), eone.base.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_Cr_ID(), get_TrxName());	}

	/** Set Warehouse Cr.
		@param M_Warehouse_Cr_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_Cr_ID (int M_Warehouse_Cr_ID)
	{
		if (M_Warehouse_Cr_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Cr_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Cr_ID, Integer.valueOf(M_Warehouse_Cr_ID));
	}

	/** Get Warehouse Cr.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_Cr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_Cr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UseLifed.
		@param UseLifed UseLifed	  */
	public void setUseLifed (int UseLifed)
	{
		set_Value (COLUMNNAME_UseLifed, Integer.valueOf(UseLifed));
	}

	/** Get UseLifed.
		@return UseLifed	  */
	public int getUseLifed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseLifed);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UseLifes.
		@param UseLifes UseLifes	  */
	public void setUseLifes (BigDecimal UseLifes)
	{
		set_Value (COLUMNNAME_UseLifes, UseLifes);
	}

	/** Get UseLifes.
		@return UseLifes	  */
	public BigDecimal getUseLifes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UseLifes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}