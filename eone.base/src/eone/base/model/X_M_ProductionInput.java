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

/** Generated Model for M_ProductionInput
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_M_ProductionInput extends PO implements I_M_ProductionInput, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220624L;

    /** Standard Constructor */
    public X_M_ProductionInput (Properties ctx, int M_ProductionInput_ID, String trxName)
    {
      super (ctx, M_ProductionInput_ID, trxName);
      /** if (M_ProductionInput_ID == 0)
        {
			setM_Product_ID (0);
			setM_ProductionInput_ID (0);
			setM_Warehouse_ID (0);
			setProcessed (false);
			setQtyOne (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_ProductionInput (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_ProductionInput[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Production Input.
		@param M_ProductionInput_ID Production Input	  */
	public void setM_ProductionInput_ID (int M_ProductionInput_ID)
	{
		if (M_ProductionInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductionInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductionInput_ID, Integer.valueOf(M_ProductionInput_ID));
	}

	/** Get Production Input.
		@return Production Input	  */
	public int getM_ProductionInput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductionInput_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_M_ProductionOutput getM_ProductionOutput() throws RuntimeException
    {
		return (eone.base.model.I_M_ProductionOutput)MTable.get(getCtx(), eone.base.model.I_M_ProductionOutput.Table_Name)
			.getPO(getM_ProductionOutput_ID(), get_TrxName());	}

	/** Set Production Output.
		@param M_ProductionOutput_ID Production Output	  */
	public void setM_ProductionOutput_ID (int M_ProductionOutput_ID)
	{
		if (M_ProductionOutput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductionOutput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductionOutput_ID, Integer.valueOf(M_ProductionOutput_ID));
	}

	/** Get Production Output.
		@return Production Output	  */
	public int getM_ProductionOutput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductionOutput_ID);
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

	/** Set Qty of One.
		@param QtyOne Qty of One	  */
	public void setQtyOne (BigDecimal QtyOne)
	{
		set_Value (COLUMNNAME_QtyOne, QtyOne);
	}

	/** Get Qty of One.
		@return Qty of One	  */
	public BigDecimal getQtyOne () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOne);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}