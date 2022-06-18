/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for HR_DayOffLine
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_DayOffLine extends PO implements I_HR_DayOffLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220616L;

    /** Standard Constructor */
    public X_HR_DayOffLine (Properties ctx, int HR_DayOffLine_ID, String trxName)
    {
      super (ctx, HR_DayOffLine_ID, trxName);
      /** if (HR_DayOffLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_DayOffLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_DayOffLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address.
		@param Address Address	  */
	public void setAddress (String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

	/** Get Address.
		@return Address	  */
	public String getAddress () 
	{
		return (String)get_Value(COLUMNNAME_Address);
	}

	/** P4 = P4 */
	public static final String DAYOFFTYPE_P4 = "P4";
	/** KL = KL */
	public static final String DAYOFFTYPE_KL = "KL";
	/** CO = CO */
	public static final String DAYOFFTYPE_CO = "CO";
	/** L8 = L8 */
	public static final String DAYOFFTYPE_L8 = "L8";
	/** NO = NO */
	public static final String DAYOFFTYPE_NO = "NO";
	/** SP = SP */
	public static final String DAYOFFTYPE_SP = "SP";
	/** TS = TS */
	public static final String DAYOFFTYPE_TS = "TS";
	/** +8 = +8 */
	public static final String DAYOFFTYPE_Plus8 = "+8";
	/** +4 = +4 */
	public static final String DAYOFFTYPE_Plus4 = "+4";
	/** CT = CT */
	public static final String DAYOFFTYPE_CT = "CT";
	/** HO = HO */
	public static final String DAYOFFTYPE_HO = "HO";
	/** TN = TN */
	public static final String DAYOFFTYPE_TN = "TN";
	/** NV = NV */
	public static final String DAYOFFTYPE_NV = "NV";
	/** NL = NL */
	public static final String DAYOFFTYPE_NL = "NL";
	/** NN = NN */
	public static final String DAYOFFTYPE_NN = "NN";
	/** P8 = P8 */
	public static final String DAYOFFTYPE_P8 = "P8";
	/** NB = NB */
	public static final String DAYOFFTYPE_NB = "NB";
	/** NT = NT */
	public static final String DAYOFFTYPE_NT = "NT";
	/** Đ = DD */
	public static final String DAYOFFTYPE_Đ = "DD";
	/** Set DayOffType.
		@param DayOffType DayOffType	  */
	public void setDayOffType (String DayOffType)
	{

		set_Value (COLUMNNAME_DayOffType, DayOffType);
	}

	/** Get DayOffType.
		@return DayOffType	  */
	public String getDayOffType () 
	{
		return (String)get_Value(COLUMNNAME_DayOffType);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	public eone.base.model.I_HR_DayOff getHR_DayOff() throws RuntimeException
    {
		return (eone.base.model.I_HR_DayOff)MTable.get(getCtx(), eone.base.model.I_HR_DayOff.Table_Name)
			.getPO(getHR_DayOff_ID(), get_TrxName());	}

	/** Set Day Off.
		@param HR_DayOff_ID Day Off	  */
	public void setHR_DayOff_ID (int HR_DayOff_ID)
	{
		if (HR_DayOff_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_DayOff_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_DayOff_ID, Integer.valueOf(HR_DayOff_ID));
	}

	/** Get Day Off.
		@return Day Off	  */
	public int getHR_DayOff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_DayOff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Day Off.
		@param HR_DayOffLine_ID Day Off	  */
	public void setHR_DayOffLine_ID (int HR_DayOffLine_ID)
	{
		if (HR_DayOffLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_DayOffLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_DayOffLine_ID, Integer.valueOf(HR_DayOffLine_ID));
	}

	/** Get Day Off.
		@return Day Off	  */
	public int getHR_DayOffLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_DayOffLine_ID);
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

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}
}