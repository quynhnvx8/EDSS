/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PeriodPayment
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_PeriodPayment extends PO implements I_C_PeriodPayment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220608L;

    /** Standard Constructor */
    public X_C_PeriodPayment (Properties ctx, int C_PeriodPayment_ID, String trxName)
    {
      super (ctx, C_PeriodPayment_ID, trxName);
      /** if (C_PeriodPayment_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_PeriodPayment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_PeriodPayment[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Period Payment.
		@param C_PeriodPayment_ID Period Payment	  */
	public void setC_PeriodPayment_ID (int C_PeriodPayment_ID)
	{
		if (C_PeriodPayment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PeriodPayment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PeriodPayment_ID, Integer.valueOf(C_PeriodPayment_ID));
	}

	/** Get Period Payment.
		@return Period Payment	  */
	public int getC_PeriodPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Three Month = 003 */
	public static final String TYPEPERIOD_ThreeMonth = "003";
	/** Six Month = 006 */
	public static final String TYPEPERIOD_SixMonth = "006";
	/** Nine Month = 009 */
	public static final String TYPEPERIOD_NineMonth = "009";
	/** Twelve Month = 012 */
	public static final String TYPEPERIOD_TwelveMonth = "012";
	/** Five Years = 060 */
	public static final String TYPEPERIOD_FiveYears = "060";
	/** Over Five Years = 061 */
	public static final String TYPEPERIOD_OverFiveYears = "061";
	/** NONE = 000 */
	public static final String TYPEPERIOD_NONE = "000";
	/** Less Than Twelve = 013 */
	public static final String TYPEPERIOD_LessThanTwelve = "013";
	/** Set TypePeriod.
		@param TypePeriod TypePeriod	  */
	public void setTypePeriod (String TypePeriod)
	{

		set_Value (COLUMNNAME_TypePeriod, TypePeriod);
	}

	/** Get TypePeriod.
		@return TypePeriod	  */
	public String getTypePeriod () 
	{
		return (String)get_Value(COLUMNNAME_TypePeriod);
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