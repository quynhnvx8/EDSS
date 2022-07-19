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

/** Generated Model for C_TypeCostLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_TypeCostLine extends PO implements I_C_TypeCostLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220706L;

    /** Standard Constructor */
    public X_C_TypeCostLine (Properties ctx, int C_TypeCostLine_ID, String trxName)
    {
      super (ctx, C_TypeCostLine_ID, trxName);
      /** if (C_TypeCostLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_TypeCostLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuilder sb = new StringBuilder ("X_C_TypeCostLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_TypeCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TypeCost_ID, Integer.valueOf(C_TypeCost_ID));
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

	/** Set TypeCost Line.
		@param C_TypeCostLine_ID TypeCost Line	  */
	public void setC_TypeCostLine_ID (int C_TypeCostLine_ID)
	{
		if (C_TypeCostLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TypeCostLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TypeCostLine_ID, Integer.valueOf(C_TypeCostLine_ID));
	}

	/** Get TypeCost Line.
		@return TypeCost Line	  */
	public int getC_TypeCostLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TypeCostLine_ID);
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

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}