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

/** Generated Model for HR_WorkCompletedLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_WorkCompletedLine extends PO implements I_HR_WorkCompletedLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220629L;

    /** Standard Constructor */
    public X_HR_WorkCompletedLine (Properties ctx, int HR_WorkCompletedLine_ID, String trxName)
    {
      super (ctx, HR_WorkCompletedLine_ID, trxName);
      /** if (HR_WorkCompletedLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_WorkCompletedLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_WorkCompletedLine[")
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

	public eone.base.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (eone.base.model.I_C_UOM)MTable.get(getCtx(), eone.base.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	public eone.base.model.I_HR_WorkCompleted getHR_WorkCompleted() throws RuntimeException
    {
		return (eone.base.model.I_HR_WorkCompleted)MTable.get(getCtx(), eone.base.model.I_HR_WorkCompleted.Table_Name)
			.getPO(getHR_WorkCompleted_ID(), get_TrxName());	}

	/** Set Work Completed.
		@param HR_WorkCompleted_ID Work Completed	  */
	public void setHR_WorkCompleted_ID (int HR_WorkCompleted_ID)
	{
		if (HR_WorkCompleted_ID < 1) 
			set_Value (COLUMNNAME_HR_WorkCompleted_ID, null);
		else 
			set_Value (COLUMNNAME_HR_WorkCompleted_ID, Integer.valueOf(HR_WorkCompleted_ID));
	}

	/** Get Work Completed.
		@return Work Completed	  */
	public int getHR_WorkCompleted_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_WorkCompleted_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work Completed Line.
		@param HR_WorkCompletedLine_ID Work Completed Line	  */
	public void setHR_WorkCompletedLine_ID (int HR_WorkCompletedLine_ID)
	{
		if (HR_WorkCompletedLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_WorkCompletedLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_WorkCompletedLine_ID, Integer.valueOf(HR_WorkCompletedLine_ID));
	}

	/** Get Work Completed Line.
		@return Work Completed Line	  */
	public int getHR_WorkCompletedLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_WorkCompletedLine_ID);
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

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}