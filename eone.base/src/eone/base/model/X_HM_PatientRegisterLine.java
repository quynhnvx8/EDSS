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

/** Generated Model for HM_PatientRegisterLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HM_PatientRegisterLine extends PO implements I_HM_PatientRegisterLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211014L;

    /** Standard Constructor */
    public X_HM_PatientRegisterLine (Properties ctx, int HM_PatientRegisterLine_ID, String trxName)
    {
      super (ctx, HM_PatientRegisterLine_ID, trxName);
      /** if (HM_PatientRegisterLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HM_PatientRegisterLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HM_PatientRegisterLine[")
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

	/** Set btnFinish.
		@param btnFinish btnFinish	  */
	public void setbtnFinish (String btnFinish)
	{
		set_Value (COLUMNNAME_btnFinish, btnFinish);
	}

	/** Get btnFinish.
		@return btnFinish	  */
	public String getbtnFinish () 
	{
		return (String)get_Value(COLUMNNAME_btnFinish);
	}

	/** Set btnResult.
		@param btnResult btnResult	  */
	public void setbtnResult (String btnResult)
	{
		set_Value (COLUMNNAME_btnResult, btnResult);
	}

	/** Get btnResult.
		@return btnResult	  */
	public String getbtnResult () 
	{
		return (String)get_Value(COLUMNNAME_btnResult);
	}

	/** Set Detail.
		@param Detail Detail	  */
	public void setDetail (String Detail)
	{
		set_Value (COLUMNNAME_Detail, Detail);
	}

	/** Get Detail.
		@return Detail	  */
	public String getDetail () 
	{
		return (String)get_Value(COLUMNNAME_Detail);
	}

	public eone.base.model.I_HM_PatientRegister getHM_PatientRegister() throws RuntimeException
    {
		return (eone.base.model.I_HM_PatientRegister)MTable.get(getCtx(), eone.base.model.I_HM_PatientRegister.Table_Name)
			.getPO(getHM_PatientRegister_ID(), get_TrxName());	}

	/** Set Patient Register.
		@param HM_PatientRegister_ID Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID)
	{
		if (HM_PatientRegister_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegister_ID, Integer.valueOf(HM_PatientRegister_ID));
	}

	/** Get Patient Register.
		@return Patient Register	  */
	public int getHM_PatientRegister_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientRegister_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PatientRegisterLine.
		@param HM_PatientRegisterLine_ID PatientRegisterLine	  */
	public void setHM_PatientRegisterLine_ID (int HM_PatientRegisterLine_ID)
	{
		if (HM_PatientRegisterLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegisterLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HM_PatientRegisterLine_ID, Integer.valueOf(HM_PatientRegisterLine_ID));
	}

	/** Get PatientRegisterLine.
		@return PatientRegisterLine	  */
	public int getHM_PatientRegisterLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HM_PatientRegisterLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException
    {
		return (eone.base.model.I_HR_Employee)MTable.get(getCtx(), eone.base.model.I_HR_Employee.Table_Name)
			.getPO(getHR_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param HR_Employee_ID Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID)
	{
		if (HR_Employee_ID < 1) 
			set_Value (COLUMNNAME_HR_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Employee_ID, Integer.valueOf(HR_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getHR_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Schedule.
		@param IsSchedule Schedule	  */
	public void setIsSchedule (boolean IsSchedule)
	{
		set_Value (COLUMNNAME_IsSchedule, Boolean.valueOf(IsSchedule));
	}

	/** Get Schedule.
		@return Schedule	  */
	public boolean isSchedule () 
	{
		Object oo = get_Value(COLUMNNAME_IsSchedule);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	
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

	/** Null = NU */
	public static final String PERFORMSTATUS_Null = "NU";
	/** Yes = YE */
	public static final String PERFORMSTATUS_Yes = "YE";
	/** No = NO */
	public static final String PERFORMSTATUS_No = "NO";
	/** Set Perform Status.
		@param PerformStatus Perform Status	  */
	public void setPerformStatus (String PerformStatus)
	{

		set_Value (COLUMNNAME_PerformStatus, PerformStatus);
	}

	/** Get Perform Status.
		@return Perform Status	  */
	public String getPerformStatus () 
	{
		return (String)get_Value(COLUMNNAME_PerformStatus);
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

	/** Set Print Text.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Print Text.
		@return The label text to be printed on a document or correspondence.
	  */
	public String getPrintName () 
	{
		return (String)get_Value(COLUMNNAME_PrintName);
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

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ResultCLS.
		@param ResultCLS ResultCLS	  */
	public void setResultCLS (String ResultCLS)
	{
		set_Value (COLUMNNAME_ResultCLS, ResultCLS);
	}

	/** Get ResultCLS.
		@return ResultCLS	  */
	public String getResultCLS () 
	{
		return (String)get_Value(COLUMNNAME_ResultCLS);
	}
}