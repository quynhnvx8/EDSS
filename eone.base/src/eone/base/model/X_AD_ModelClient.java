/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ModelClient
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_ModelClient extends PO implements I_AD_ModelClient, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220507L;

    /** Standard Constructor */
    public X_AD_ModelClient (Properties ctx, int AD_ModelClient_ID, String trxName)
    {
      super (ctx, AD_ModelClient_ID, trxName);
      /** if (AD_ModelClient_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_ModelClient (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuilder sb = new StringBuilder ("X_AD_ModelClient[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Model Client.
		@param AD_ModelClient_ID Model Client	  */
	public void setAD_ModelClient_ID (int AD_ModelClient_ID)
	{
		if (AD_ModelClient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ModelClient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ModelClient_ID, Integer.valueOf(AD_ModelClient_ID));
	}

	/** Get Model Client.
		@return Model Client	  */
	public int getAD_ModelClient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ModelClient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
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