/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.KeyNamePair;

/** Generated Model for C_ElementValue
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_ElementValue extends PO implements I_C_ElementValue, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220109L;

    /** Standard Constructor */
    public X_C_ElementValue (Properties ctx, int C_ElementValue_ID, String trxName)
    {
      super (ctx, C_ElementValue_ID, trxName);
      /** if (C_ElementValue_ID == 0)
        {
			setAccountType (null);
// E
			setC_ElementValue_ID (0);
			setIsDetailBPartner (false);
// N
			setIsDetailProduct (false);
// N
			setIsSummary (false);
			setName (null);
			setPostActual (true);
// Y
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_ElementValue (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_ElementValue[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** AccountType AD_Reference_ID=117 */
	public static final int ACCOUNTTYPE_AD_Reference_ID=117;
	/** Asset = A */
	public static final String ACCOUNTTYPE_Asset = "A";
	/** Liability = L */
	public static final String ACCOUNTTYPE_Liability = "L";
	/** Revenue = R */
	public static final String ACCOUNTTYPE_Revenue = "R";
	/** Expense = E */
	public static final String ACCOUNTTYPE_Expense = "E";
	/** Owner's Equity = O */
	public static final String ACCOUNTTYPE_OwnerSEquity = "O";
	/** Memo = M */
	public static final String ACCOUNTTYPE_Memo = "M";
	/** Set Account Type.
		@param AccountType 
		Indicates the type of account
	  */
	public void setAccountType (String AccountType)
	{

		set_Value (COLUMNNAME_AccountType, AccountType);
	}

	/** Get Account Type.
		@return Indicates the type of account
	  */
	public String getAccountType () 
	{
		return (String)get_Value(COLUMNNAME_AccountType);
	}

	public eone.base.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (eone.base.model.I_C_Currency)MTable.get(getCtx(), eone.base.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
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

	/** Set Bank Account.
		@param IsBankAccount 
		Indicates if this is the Bank Account
	  */
	public void setIsBankAccount (boolean IsBankAccount)
	{
		set_Value (COLUMNNAME_IsBankAccount, Boolean.valueOf(IsBankAccount));
	}

	/** Get Bank Account.
		@return Indicates if this is the Bank Account
	  */
	public boolean isBankAccount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBankAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailAsset.
		@param IsDetailAsset IsDetailAsset	  */
	public void setIsDetailAsset (boolean IsDetailAsset)
	{
		set_Value (COLUMNNAME_IsDetailAsset, Boolean.valueOf(IsDetailAsset));
	}

	/** Get IsDetailAsset.
		@return IsDetailAsset	  */
	public boolean isDetailAsset () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailAsset);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manage Business Partners.
		@param IsDetailBPartner Manage Business Partners	  */
	public void setIsDetailBPartner (boolean IsDetailBPartner)
	{
		set_Value (COLUMNNAME_IsDetailBPartner, Boolean.valueOf(IsDetailBPartner));
	}

	/** Get Manage Business Partners.
		@return Manage Business Partners	  */
	public boolean isDetailBPartner () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailBPartner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailConstruction.
		@param IsDetailConstruction IsDetailConstruction	  */
	public void setIsDetailConstruction (boolean IsDetailConstruction)
	{
		set_Value (COLUMNNAME_IsDetailConstruction, Boolean.valueOf(IsDetailConstruction));
	}

	/** Get IsDetailConstruction.
		@return IsDetailConstruction	  */
	public boolean isDetailConstruction () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailConstruction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailConstructionPharse.
		@param IsDetailConstructionPharse IsDetailConstructionPharse	  */
	public void setIsDetailConstructionPharse (boolean IsDetailConstructionPharse)
	{
		set_Value (COLUMNNAME_IsDetailConstructionPharse, Boolean.valueOf(IsDetailConstructionPharse));
	}

	/** Get IsDetailConstructionPharse.
		@return IsDetailConstructionPharse	  */
	public boolean isDetailConstructionPharse () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailConstructionPharse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailContract.
		@param IsDetailContract IsDetailContract	  */
	public void setIsDetailContract (boolean IsDetailContract)
	{
		set_Value (COLUMNNAME_IsDetailContract, Boolean.valueOf(IsDetailContract));
	}

	/** Get IsDetailContract.
		@return IsDetailContract	  */
	public boolean isDetailContract () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailContract);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailContractSchedule.
		@param IsDetailContractSchedule IsDetailContractSchedule	  */
	public void setIsDetailContractSchedule (boolean IsDetailContractSchedule)
	{
		set_Value (COLUMNNAME_IsDetailContractSchedule, Boolean.valueOf(IsDetailContractSchedule));
	}

	/** Get IsDetailContractSchedule.
		@return IsDetailContractSchedule	  */
	public boolean isDetailContractSchedule () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailContractSchedule);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manage Products.
		@param IsDetailProduct Manage Products	  */
	public void setIsDetailProduct (boolean IsDetailProduct)
	{
		set_Value (COLUMNNAME_IsDetailProduct, Boolean.valueOf(IsDetailProduct));
	}

	/** Get Manage Products.
		@return Manage Products	  */
	public boolean isDetailProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailProject.
		@param IsDetailProject IsDetailProject	  */
	public void setIsDetailProject (boolean IsDetailProject)
	{
		set_Value (COLUMNNAME_IsDetailProject, Boolean.valueOf(IsDetailProject));
	}

	/** Get IsDetailProject.
		@return IsDetailProject	  */
	public boolean isDetailProject () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailProject);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailProjectPharse.
		@param IsDetailProjectPharse IsDetailProjectPharse	  */
	public void setIsDetailProjectPharse (boolean IsDetailProjectPharse)
	{
		set_Value (COLUMNNAME_IsDetailProjectPharse, Boolean.valueOf(IsDetailProjectPharse));
	}

	/** Get IsDetailProjectPharse.
		@return IsDetailProjectPharse	  */
	public boolean isDetailProjectPharse () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailProjectPharse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailTypeCost.
		@param IsDetailTypeCost IsDetailTypeCost	  */
	public void setIsDetailTypeCost (boolean IsDetailTypeCost)
	{
		set_Value (COLUMNNAME_IsDetailTypeCost, Boolean.valueOf(IsDetailTypeCost));
	}

	/** Get IsDetailTypeCost.
		@return IsDetailTypeCost	  */
	public boolean isDetailTypeCost () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailTypeCost);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailTypeRevenue.
		@param IsDetailTypeRevenue IsDetailTypeRevenue	  */
	public void setIsDetailTypeRevenue (boolean IsDetailTypeRevenue)
	{
		set_Value (COLUMNNAME_IsDetailTypeRevenue, Boolean.valueOf(IsDetailTypeRevenue));
	}

	/** Get IsDetailTypeRevenue.
		@return IsDetailTypeRevenue	  */
	public boolean isDetailTypeRevenue () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailTypeRevenue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDetailWarehouse.
		@param IsDetailWarehouse IsDetailWarehouse	  */
	public void setIsDetailWarehouse (boolean IsDetailWarehouse)
	{
		set_Value (COLUMNNAME_IsDetailWarehouse, Boolean.valueOf(IsDetailWarehouse));
	}

	/** Get IsDetailWarehouse.
		@return IsDetailWarehouse	  */
	public boolean isDetailWarehouse () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailWarehouse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Distribute.
		@param IsDistribute Distribute	  */
	public void setIsDistribute (boolean IsDistribute)
	{
		set_Value (COLUMNNAME_IsDistribute, Boolean.valueOf(IsDistribute));
	}

	/** Get Distribute.
		@return Distribute	  */
	public boolean isDistribute () 
	{
		Object oo = get_Value(COLUMNNAME_IsDistribute);
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

	public eone.base.model.I_C_ElementValue getParent() throws RuntimeException
    {
		return (eone.base.model.I_C_ElementValue)MTable.get(getCtx(), eone.base.model.I_C_ElementValue.Table_Name)
			.getPO(getParent_ID(), get_TrxName());	}

	/** Set Parent.
		@param Parent_ID 
		Parent of Entity
	  */
	public void setParent_ID (int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_Value (COLUMNNAME_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_ID, Integer.valueOf(Parent_ID));
	}

	/** Get Parent.
		@return Parent of Entity
	  */
	public int getParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Post Actual.
		@param PostActual 
		Actual Values can be posted
	  */
	public void setPostActual (boolean PostActual)
	{
		set_Value (COLUMNNAME_PostActual, Boolean.valueOf(PostActual));
	}

	/** Get Post Actual.
		@return Actual Values can be posted
	  */
	public boolean isPostActual () 
	{
		Object oo = get_Value(COLUMNNAME_PostActual);
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
}