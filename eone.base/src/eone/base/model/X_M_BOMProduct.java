/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.Env;
import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_BOMProduct
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_M_BOMProduct extends PO implements I_M_BOMProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220623L;

    /** Standard Constructor */
    public X_M_BOMProduct (Properties ctx, int M_BOMProduct_ID, String trxName)
    {
      super (ctx, M_BOMProduct_ID, trxName);
      /** if (M_BOMProduct_ID == 0)
        {
			setBOMProductType (null);
// S
			setBOMQty (Env.ZERO);
// 1
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_BOMProduct WHERE M_BOM_ID=@M_BOM_ID@
			setM_BOM_ID (0);
			setM_BOMProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_BOMProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_BOMProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BOMProductType AD_Reference_ID=349 */
	public static final int BOMPRODUCTTYPE_AD_Reference_ID=349;
	/** Labor = L */
	public static final String BOMPRODUCTTYPE_Labor = "L";
	/** Production = P */
	public static final String BOMPRODUCTTYPE_Production = "P";
	/** Master Materials = M */
	public static final String BOMPRODUCTTYPE_MasterMaterials = "M";
	/** Secondary Materials = S */
	public static final String BOMPRODUCTTYPE_SecondaryMaterials = "S";
	/** Depreciation = D */
	public static final String BOMPRODUCTTYPE_Depreciation = "D";
	/** Maintenance = U */
	public static final String BOMPRODUCTTYPE_Maintenance = "U";
	/** Cost Other = C */
	public static final String BOMPRODUCTTYPE_CostOther = "C";
	/** Set Component Type.
		@param BOMProductType 
		BOM Product Type
	  */
	public void setBOMProductType (String BOMProductType)
	{

		set_Value (COLUMNNAME_BOMProductType, BOMProductType);
	}

	/** Get Component Type.
		@return BOM Product Type
	  */
	public String getBOMProductType () 
	{
		return (String)get_Value(COLUMNNAME_BOMProductType);
	}

	/** Set BOM Quantity.
		@param BOMQty 
		Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty)
	{
		set_Value (COLUMNNAME_BOMQty, BOMQty);
	}

	/** Get BOM Quantity.
		@return Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BOMQty);
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
    }

	public eone.base.model.I_M_BOM getM_BOM() throws RuntimeException
    {
		return (eone.base.model.I_M_BOM)MTable.get(getCtx(), eone.base.model.I_M_BOM.Table_Name)
			.getPO(getM_BOM_ID(), get_TrxName());	}

	/** Set BOM.
		@param M_BOM_ID 
		Bill of Material
	  */
	public void setM_BOM_ID (int M_BOM_ID)
	{
		if (M_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_BOM_ID, Integer.valueOf(M_BOM_ID));
	}

	/** Get BOM.
		@return Bill of Material
	  */
	public int getM_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BOM Component.
		@param M_BOMProduct_ID 
		Bill of Material Component (Product)
	  */
	public void setM_BOMProduct_ID (int M_BOMProduct_ID)
	{
		if (M_BOMProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_BOMProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_BOMProduct_ID, Integer.valueOf(M_BOMProduct_ID));
	}

	/** Get BOM Component.
		@return Bill of Material Component (Product)
	  */
	public int getM_BOMProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOMProduct_ID);
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}