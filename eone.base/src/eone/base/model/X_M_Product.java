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
import eone.util.KeyNamePair;

/** Generated Model for M_Product
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_M_Product extends PO implements I_M_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220111L;

    /** Standard Constructor */
    public X_M_Product (Properties ctx, int M_Product_ID, String trxName)
    {
      super (ctx, M_Product_ID, trxName);
      /** if (M_Product_ID == 0)
        {
			setC_UOM_ID (0);
			setIsBOM (false);
			setIsManufactured (false);
			setIsPurchased (false);
			setIsSelfService (false);
			setIsSold (false);
			setIsStocked (false);
			setIsSummary (false);
			setIsVerified (false);
			setLowLevel (0);
// 0
			setM_Product_Category_ID (0);
			setM_Product_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_Product[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Barcode.
		@param Barcode 
		Barcode
	  */
	public void setBarcode (String Barcode)
	{
		set_Value (COLUMNNAME_Barcode, Barcode);
	}

	/** Get Barcode.
		@return Barcode
	  */
	public String getBarcode () 
	{
		return (String)get_Value(COLUMNNAME_Barcode);
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

	/** Set Bill of Materials.
		@param IsBOM 
		Bill of Materials
	  */
	public void setIsBOM (boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, Boolean.valueOf(IsBOM));
	}

	/** Get Bill of Materials.
		@return Bill of Materials
	  */
	public boolean isBOM () 
	{
		Object oo = get_Value(COLUMNNAME_IsBOM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manufactured.
		@param IsManufactured 
		This product is manufactured
	  */
	public void setIsManufactured (boolean IsManufactured)
	{
		set_Value (COLUMNNAME_IsManufactured, Boolean.valueOf(IsManufactured));
	}

	/** Get Manufactured.
		@return This product is manufactured
	  */
	public boolean isManufactured () 
	{
		Object oo = get_Value(COLUMNNAME_IsManufactured);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Medical.
		@param IsMedical Medical	  */
	public void setIsMedical (boolean IsMedical)
	{
		set_Value (COLUMNNAME_IsMedical, Boolean.valueOf(IsMedical));
	}

	/** Get Medical.
		@return Medical	  */
	public boolean isMedical () 
	{
		Object oo = get_Value(COLUMNNAME_IsMedical);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Purchased.
		@param IsPurchased 
		Organization purchases this product
	  */
	public void setIsPurchased (boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, Boolean.valueOf(IsPurchased));
	}

	/** Get Purchased.
		@return Organization purchases this product
	  */
	public boolean isPurchased () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_ValueNoCheck (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Service.
		@param IsService Service	  */
	public void setIsService (boolean IsService)
	{
		set_Value (COLUMNNAME_IsService, Boolean.valueOf(IsService));
	}

	/** Get Service.
		@return Service	  */
	public boolean isService () 
	{
		Object oo = get_Value(COLUMNNAME_IsService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sold.
		@param IsSold 
		Organization sells this product
	  */
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Stocked.
		@param IsStocked 
		Organization stocks this product
	  */
	public void setIsStocked (boolean IsStocked)
	{
		set_ValueNoCheck (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	/** Get Stocked.
		@return Organization stocks this product
	  */
	public boolean isStocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsStocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verified.
		@param IsVerified 
		The BOM configuration has been verified
	  */
	public void setIsVerified (boolean IsVerified)
	{
		set_Value (COLUMNNAME_IsVerified, Boolean.valueOf(IsVerified));
	}

	/** Get Verified.
		@return The BOM configuration has been verified
	  */
	public boolean isVerified () 
	{
		Object oo = get_Value(COLUMNNAME_IsVerified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Low Level.
		@param LowLevel 
		The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	  */
	public void setLowLevel (int LowLevel)
	{
		set_Value (COLUMNNAME_LowLevel, Integer.valueOf(LowLevel));
	}

	/** Get Low Level.
		@return The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	  */
	public int getLowLevel () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LowLevel);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (eone.base.model.I_M_Product_Category)MTable.get(getCtx(), eone.base.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	
	/** Set ProductGroup.
		@param M_ProductGroup_ID ProductGroup	  */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductGroup_ID, Integer.valueOf(M_ProductGroup_ID));
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

	/** Set Minimum Length.
		@param MinLength Minimum Length	  */
	public void setMinLength (int MinLength)
	{
		set_Value (COLUMNNAME_MinLength, Integer.valueOf(MinLength));
	}

	/** Get Minimum Length.
		@return Minimum Length	  */
	public int getMinLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MinLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Minimum Quantity.
		@param MinQty 
		Minimum quantity for the business partner
	  */
	public void setMinQty (BigDecimal MinQty)
	{
		set_Value (COLUMNNAME_MinQty, MinQty);
	}

	/** Get Minimum Quantity.
		@return Minimum quantity for the business partner
	  */
	public BigDecimal getMinQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Package Std.
		@param PackageStd Package Std	  */
	public void setPackageStd (int PackageStd)
	{
		set_Value (COLUMNNAME_PackageStd, Integer.valueOf(PackageStd));
	}

	/** Get Package Std.
		@return Package Std	  */
	public int getPackageStd () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PackageStd);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PO Price.
		@param PricePO 
		Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Price based on a purchase order
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceSO.
		@param PriceSO 
		PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO)
	{
		set_Value (COLUMNNAME_PriceSO, PriceSO);
	}

	/** Get PriceSO.
		@return PriceSO
	  */
	public BigDecimal getPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** ProductType AD_Reference_ID=270 */
	public static final int PRODUCTTYPE_AD_Reference_ID=270;
	/** Main Material = MAT_MAI */
	public static final String PRODUCTTYPE_MainMaterial = "MAT_MAI";
	/** Service = SER_SER */
	public static final String PRODUCTTYPE_Service = "SER_SER";
	/** Tools = TOO_TOO */
	public static final String PRODUCTTYPE_Tools = "TOO_TOO";
	/** Goods = GOO_GOO */
	public static final String PRODUCTTYPE_Goods = "GOO_GOO";
	/** Asset = ASS_ASS */
	public static final String PRODUCTTYPE_Asset = "ASS_ASS";
	/** Production = GOO_PRO */
	public static final String PRODUCTTYPE_Production = "GOO_PRO";
	/** Ultrasound = MED_ULT */
	public static final String PRODUCTTYPE_Ultrasound = "MED_ULT";
	/** Secondary Material = MAT_SEC */
	public static final String PRODUCTTYPE_SecondaryMaterial = "MAT_SEC";
	/** Medicial Examination = MED_EXA */
	public static final String PRODUCTTYPE_MedicialExamination = "MED_EXA";
	/** Not finish = MAT_NOT */
	public static final String PRODUCTTYPE_NotFinish = "MAT_NOT";
	/** X-ray = MED_XRA */
	public static final String PRODUCTTYPE_X_Ray = "MED_XRA";
	/** Laborary = MED_LAB */
	public static final String PRODUCTTYPE_Laborary = "MED_LAB";
	/** Electrocardiogram = MED_ELE */
	public static final String PRODUCTTYPE_Electrocardiogram = "MED_ELE";
	/** Endoscopic  = MED_END */
	public static final String PRODUCTTYPE_Endoscopic = "MED_END";
	/** None = NON_NON */
	public static final String PRODUCTTYPE_None = "NON_NON";
	/** Tax88 = Tax_Tax */
	public static final String PRODUCTTYPE_Tax88 = "Tax_Tax";
	/** Set Product Type.
		@param ProductType 
		Type of product
	  */
	public void setProductType (String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Product Type.
		@return Type of product
	  */
	public String getProductType () 
	{
		return (String)get_Value(COLUMNNAME_ProductType);
	}

	/** Set RemainQty.
		@param RemainQty RemainQty	  */
	public void setRemainQty (BigDecimal RemainQty)
	{
		set_ValueNoCheck (COLUMNNAME_RemainQty, RemainQty);
	}

	/** Get RemainQty.
		@return RemainQty	  */
	public BigDecimal getRemainQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Code.
		@param Value 
		Code
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Code.
		@return Code
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getValue());
    }

	/** Set Version No.
		@param VersionNo 
		Version Number
	  */
	public void setVersionNo (String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}