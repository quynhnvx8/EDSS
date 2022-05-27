/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.Env;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for A_Asset
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_A_Asset extends PO implements I_A_Asset, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220525L;

    /** Standard Constructor */
    public X_A_Asset (Properties ctx, int A_Asset_ID, String trxName)
    {
      super (ctx, A_Asset_ID, trxName);
      /** if (A_Asset_ID == 0)
        {
			setA_Asset_ID (0);
			setIsDepreciated (false);
			setIsDisposed (null);
// N
			setName (null);
			setProcessed (false);
// N
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_A_Asset (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_A_Asset[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
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
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
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

	/** Set AccumulateAmt.
		@param AccumulateAmt AccumulateAmt	  */
	public void setAccumulateAmt (BigDecimal AccumulateAmt)
	{
		set_Value (COLUMNNAME_AccumulateAmt, AccumulateAmt);
	}

	/** Get AccumulateAmt.
		@return AccumulateAmt	  */
	public BigDecimal getAccumulateAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccumulateAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Approved.
		@param Approved Approved	  */
	public void setApproved (String Approved)
	{
		set_Value (COLUMNNAME_Approved, Approved);
	}

	/** Get Approved.
		@return Approved	  */
	public String getApproved () 
	{
		return (String)get_Value(COLUMNNAME_Approved);
	}

	/** Set BaseAmtCurrent.
		@param BaseAmtCurrent BaseAmtCurrent	  */
	public void setBaseAmtCurrent (BigDecimal BaseAmtCurrent)
	{
		set_Value (COLUMNNAME_BaseAmtCurrent, BaseAmtCurrent);
	}

	/** Get BaseAmtCurrent.
		@return BaseAmtCurrent	  */
	public BigDecimal getBaseAmtCurrent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BaseAmtCurrent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set BaseAmtOriginal.
		@param BaseAmtOriginal BaseAmtOriginal	  */
	public void setBaseAmtOriginal (BigDecimal BaseAmtOriginal)
	{
		set_Value (COLUMNNAME_BaseAmtOriginal, BaseAmtOriginal);
	}

	/** Get BaseAmtOriginal.
		@return BaseAmtOriginal	  */
	public BigDecimal getBaseAmtOriginal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BaseAmtOriginal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Canceled.
		@param Canceled Canceled	  */
	public void setCanceled (String Canceled)
	{
		set_Value (COLUMNNAME_Canceled, Canceled);
	}

	/** Get Canceled.
		@return Canceled	  */
	public String getCanceled () 
	{
		return (String)get_Value(COLUMNNAME_Canceled);
	}

	/** Set Country.
		@param Country Country	  */
	public void setCountry (String Country)
	{
		set_Value (COLUMNNAME_Country, Country);
	}

	/** Get Country.
		@return Country	  */
	public String getCountry () 
	{
		return (String)get_Value(COLUMNNAME_Country);
	}

	/** Set Create Date.
		@param CreateDate Create Date	  */
	public void setCreateDate (Timestamp CreateDate)
	{
		set_ValueNoCheck (COLUMNNAME_CreateDate, CreateDate);
	}

	/** Get Create Date.
		@return Create Date	  */
	public Timestamp getCreateDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CreateDate);
	}

	/** Set Depreciation Split.
		@param DepreciationSplit Depreciation Split	  */
	public void setDepreciationSplit (boolean DepreciationSplit)
	{
		set_Value (COLUMNNAME_DepreciationSplit, Boolean.valueOf(DepreciationSplit));
	}

	/** Get Depreciation Split.
		@return Depreciation Split	  */
	public boolean isDepreciationSplit () 
	{
		Object oo = get_Value(COLUMNNAME_DepreciationSplit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Reject = RE */
	public static final String DOCSTATUS_Reject = "RE";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Guarantee Date.
		@param GuaranteeDate 
		Date when guarantee expires
	  */
	public void setGuaranteeDate (Timestamp GuaranteeDate)
	{
		set_Value (COLUMNNAME_GuaranteeDate, GuaranteeDate);
	}

	/** Get Guarantee Date.
		@return Date when guarantee expires
	  */
	public Timestamp getGuaranteeDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_GuaranteeDate);
	}

	/** Set Inventory No.
		@param InventoryNo Inventory No	  */
	public void setInventoryNo (String InventoryNo)
	{
		set_Value (COLUMNNAME_InventoryNo, InventoryNo);
	}

	/** Get Inventory No.
		@return Inventory No	  */
	public String getInventoryNo () 
	{
		return (String)get_Value(COLUMNNAME_InventoryNo);
	}

	/** Set Depreciate.
		@param IsDepreciated 
		The asset will be depreciated
	  */
	public void setIsDepreciated (boolean IsDepreciated)
	{
		set_Value (COLUMNNAME_IsDepreciated, Boolean.valueOf(IsDepreciated));
	}

	/** Get Depreciate.
		@return The asset will be depreciated
	  */
	public boolean isDepreciated () 
	{
		Object oo = get_Value(COLUMNNAME_IsDepreciated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** None = N */
	public static final String ISDISPOSED_None = "N";
	/** Yes = Y */
	public static final String ISDISPOSED_Yes = "Y";
	/** Processing = P */
	public static final String ISDISPOSED_Processing = "P";
	/** Mortgage = M */
	public static final String ISDISPOSED_Mortgage = "M";
	/** Set Disposed.
		@param IsDisposed 
		The asset is disposed
	  */
	public void setIsDisposed (String IsDisposed)
	{

		set_Value (COLUMNNAME_IsDisposed, IsDisposed);
	}

	/** Get Disposed.
		@return The asset is disposed
	  */
	public String getIsDisposed () 
	{
		return (String)get_Value(COLUMNNAME_IsDisposed);
	}

	/** Set IsRecordUsed.
		@param IsRecordUsed IsRecordUsed	  */
	public void setIsRecordUsed (boolean IsRecordUsed)
	{
		set_Value (COLUMNNAME_IsRecordUsed, Boolean.valueOf(IsRecordUsed));
	}

	/** Get IsRecordUsed.
		@return IsRecordUsed	  */
	public boolean isRecordUsed () 
	{
		Object oo = get_Value(COLUMNNAME_IsRecordUsed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Transferred.
		@param IsTransferred 
		Transferred to General Ledger (i.e. accounted)
	  */
	public void setIsTransferred (boolean IsTransferred)
	{
		set_ValueNoCheck (COLUMNNAME_IsTransferred, Boolean.valueOf(IsTransferred));
	}

	/** Get Transferred.
		@return Transferred to General Ledger (i.e. accounted)
	  */
	public boolean isTransferred () 
	{
		Object oo = get_Value(COLUMNNAME_IsTransferred);
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

	/** Set Pending Date.
		@param PendingDate 
		Pending date
	  */
	public void setPendingDate (Timestamp PendingDate)
	{
		set_Value (COLUMNNAME_PendingDate, PendingDate);
	}

	/** Get Pending Date.
		@return Pending date
	  */
	public Timestamp getPendingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PendingDate);
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

	/** Set RemainAmt.
		@param RemainAmt RemainAmt	  */
	public void setRemainAmt (BigDecimal RemainAmt)
	{
		set_Value (COLUMNNAME_RemainAmt, RemainAmt);
	}

	/** Get RemainAmt.
		@return RemainAmt	  */
	public BigDecimal getRemainAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Serial No.
		@param SerNo 
		Product Serial Number 
	  */
	public void setSerNo (String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serial No.
		@return Product Serial Number 
	  */
	public String getSerNo () 
	{
		return (String)get_Value(COLUMNNAME_SerNo);
	}

	/** None = NO */
	public static final String STATUSUSE_None = "NO";
	/** Using = US */
	public static final String STATUSUSE_Using = "US";
	/** UnUse = UN */
	public static final String STATUSUSE_UnUse = "UN";
	/** Delivery = DE */
	public static final String STATUSUSE_Delivery = "DE";
	/** Set StatusUse.
		@param StatusUse StatusUse	  */
	public void setStatusUse (String StatusUse)
	{

		set_Value (COLUMNNAME_StatusUse, StatusUse);
	}

	/** Get StatusUse.
		@return StatusUse	  */
	public String getStatusUse () 
	{
		return (String)get_Value(COLUMNNAME_StatusUse);
	}

	/** By Day = BD */
	public static final String TYPECALCULATE_ByDay = "BD";
	/** By Month = BM */
	public static final String TYPECALCULATE_ByMonth = "BM";
	/** Set TypeCalculate.
		@param TypeCalculate TypeCalculate	  */
	public void setTypeCalculate (String TypeCalculate)
	{

		set_Value (COLUMNNAME_TypeCalculate, TypeCalculate);
	}

	/** Get TypeCalculate.
		@return TypeCalculate	  */
	public String getTypeCalculate () 
	{
		return (String)get_Value(COLUMNNAME_TypeCalculate);
	}

	/** Set UseDate.
		@param UseDate UseDate	  */
	public void setUseDate (Timestamp UseDate)
	{
		set_Value (COLUMNNAME_UseDate, UseDate);
	}

	/** Get UseDate.
		@return UseDate	  */
	public Timestamp getUseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UseDate);
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