/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_FlowLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_FlowLine extends PO implements I_AD_FlowLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211026L;

    /** Standard Constructor */
    public X_AD_FlowLine (Properties ctx, int AD_FlowLine_ID, String trxName)
    {
      super (ctx, AD_FlowLine_ID, trxName);
      /** if (AD_FlowLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_FlowLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_FlowLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_Department getAD_Department() throws RuntimeException
    {
		return (eone.base.model.I_AD_Department)MTable.get(getCtx(), eone.base.model.I_AD_Department.Table_Name)
			.getPO(getAD_Department_ID(), get_TrxName());	}

	/** Set Department.
		@param AD_Department_ID Department	  */
	public void setAD_Department_ID (int AD_Department_ID)
	{
		if (AD_Department_ID < 1) 
			set_Value (COLUMNNAME_AD_Department_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Department_ID, Integer.valueOf(AD_Department_ID));
	}

	/** Get Department.
		@return Department	  */
	public int getAD_Department_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Department_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Flow getAD_Flow() throws RuntimeException
    {
		return (eone.base.model.I_AD_Flow)MTable.get(getCtx(), eone.base.model.I_AD_Flow.Table_Name)
			.getPO(getAD_Flow_ID(), get_TrxName());	}

	/** Set Flow process.
		@param AD_Flow_ID Flow process	  */
	public void setAD_Flow_ID (int AD_Flow_ID)
	{
		if (AD_Flow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Flow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Flow_ID, Integer.valueOf(AD_Flow_ID));
	}

	/** Get Flow process.
		@return Flow process	  */
	public int getAD_Flow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Flow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Flow process Line.
		@param AD_FlowLine_ID Flow process Line	  */
	public void setAD_FlowLine_ID (int AD_FlowLine_ID)
	{
		if (AD_FlowLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_FlowLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_FlowLine_ID, Integer.valueOf(AD_FlowLine_ID));
	}

	/** Get Flow process Line.
		@return Flow process Line	  */
	public int getAD_FlowLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_FlowLine_ID);
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