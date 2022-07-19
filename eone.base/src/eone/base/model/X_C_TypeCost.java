/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_TypeCost
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_TypeCost extends PO implements I_C_TypeCost, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220706L;

    /** Standard Constructor */
    public X_C_TypeCost (Properties ctx, int C_TypeCost_ID, String trxName)
    {
      super (ctx, C_TypeCost_ID, trxName);
      /** if (C_TypeCost_ID == 0)
        {
			setC_TypeCost_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_TypeCost (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_TypeCost[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

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

	/** Set Code133.
		@param Code133 Code133	  */
	public void setCode133 (String Code133)
	{
		set_Value (COLUMNNAME_Code133, Code133);
	}

	/** Get Code133.
		@return Code133	  */
	public String getCode133 () 
	{
		return (String)get_Value(COLUMNNAME_Code133);
	}

	/** Set Code200.
		@param Code200 Code200	  */
	public void setCode200 (String Code200)
	{
		set_Value (COLUMNNAME_Code200, Code200);
	}

	/** Get Code200.
		@return Code200	  */
	public String getCode200 () 
	{
		return (String)get_Value(COLUMNNAME_Code200);
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

	/** By Material = MATERIAL */
	public static final String METHODALLOCATION_ByMaterial = "MATERIAL";
	/** By Worker = WORKER */
	public static final String METHODALLOCATION_ByWorker = "WORKER";
	/** Fix Default = DEFAULT */
	public static final String METHODALLOCATION_FixDefault = "DEFAULT";
	/** Set Method Allocation.
		@param MethodAllocation Method Allocation	  */
	public void setMethodAllocation (String MethodAllocation)
	{

		set_Value (COLUMNNAME_MethodAllocation, MethodAllocation);
	}

	/** Get Method Allocation.
		@return Method Allocation	  */
	public String getMethodAllocation () 
	{
		return (String)get_Value(COLUMNNAME_MethodAllocation);
	}

	/** Set Name.
		@param Name Name	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** NONE = NONE */
	public static final String TYPECOST_NONE = "NONE";
	/** Private = PRIVATE */
	public static final String TYPECOST_Private = "PRIVATE";
	/** Public = PUBLIC */
	public static final String TYPECOST_Public = "PUBLIC";
	/** Set TypeCost.
		@param TypeCost TypeCost	  */
	public void setTypeCost (String TypeCost)
	{

		set_Value (COLUMNNAME_TypeCost, TypeCost);
	}

	/** Get TypeCost.
		@return TypeCost	  */
	public String getTypeCost () 
	{
		return (String)get_Value(COLUMNNAME_TypeCost);
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
}