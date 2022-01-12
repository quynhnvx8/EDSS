/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Signer
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Signer extends PO implements I_AD_Signer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210919L;

    /** Standard Constructor */
    public X_AD_Signer (Properties ctx, int AD_Signer_ID, String trxName)
    {
      super (ctx, AD_Signer_ID, trxName);
      /** if (AD_Signer_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_Signer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Signer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_AppendSign getAD_AppendSign() throws RuntimeException
    {
		return (eone.base.model.I_AD_AppendSign)MTable.get(getCtx(), eone.base.model.I_AD_AppendSign.Table_Name)
			.getPO(getAD_AppendSign_ID(), get_TrxName());	}

	/** Set Append Sign.
		@param AD_AppendSign_ID Append Sign	  */
	public void setAD_AppendSign_ID (int AD_AppendSign_ID)
	{
		if (AD_AppendSign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AppendSign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AppendSign_ID, Integer.valueOf(AD_AppendSign_ID));
	}

	/** Get Append Sign.
		@return Append Sign	  */
	public int getAD_AppendSign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AppendSign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Signer.
		@param AD_Signer_ID Signer	  */
	public void setAD_Signer_ID (int AD_Signer_ID)
	{
		if (AD_Signer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Signer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Signer_ID, Integer.valueOf(AD_Signer_ID));
	}

	/** Get Signer.
		@return Signer	  */
	public int getAD_Signer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Signer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	/** Approved = APP */
	public static final String SIGNSTATUS_Approved = "APP";
	/** Cancel = CAN */
	public static final String SIGNSTATUS_Cancel = "CAN";
	/** NONE = NON */
	public static final String SIGNSTATUS_NONE = "NON";
	/** Set Sign Status.
		@param SignStatus Sign Status	  */
	public void setSignStatus (String SignStatus)
	{

		set_Value (COLUMNNAME_SignStatus, SignStatus);
	}

	/** Get Sign Status.
		@return Sign Status	  */
	public String getSignStatus () 
	{
		return (String)get_Value(COLUMNNAME_SignStatus);
	}
}