/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_FactoriesLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_M_FactoriesLine extends PO implements I_M_FactoriesLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220705L;

    /** Standard Constructor */
    public X_M_FactoriesLine (Properties ctx, int M_FactoriesLine_ID, String trxName)
    {
      super (ctx, M_FactoriesLine_ID, trxName);
      /** if (M_FactoriesLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_FactoriesLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_FactoriesLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_M_Factories getM_Factories() throws RuntimeException
    {
		return (eone.base.model.I_M_Factories)MTable.get(getCtx(), eone.base.model.I_M_Factories.Table_Name)
			.getPO(getM_Factories_ID(), get_TrxName());	}

	/** Set Factories.
		@param M_Factories_ID Factories	  */
	public void setM_Factories_ID (int M_Factories_ID)
	{
		if (M_Factories_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Factories_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Factories_ID, Integer.valueOf(M_Factories_ID));
	}

	/** Get Factories.
		@return Factories	  */
	public int getM_Factories_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Factories_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Factories Line.
		@param M_FactoriesLine_ID Factories Line	  */
	public void setM_FactoriesLine_ID (int M_FactoriesLine_ID)
	{
		if (M_FactoriesLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_FactoriesLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_FactoriesLine_ID, Integer.valueOf(M_FactoriesLine_ID));
	}

	/** Get Factories Line.
		@return Factories Line	  */
	public int getM_FactoriesLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FactoriesLine_ID);
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

	/** Set OrderNo.
		@param OrderNo OrderNo	  */
	public void setOrderNo (int OrderNo)
	{
		set_Value (COLUMNNAME_OrderNo, Integer.valueOf(OrderNo));
	}

	/** Get OrderNo.
		@return OrderNo	  */
	public int getOrderNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OrderNo);
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