/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Window
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Window extends PO implements I_AD_Window, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210905L;

    /** Standard Constructor */
    public X_AD_Window (Properties ctx, int AD_Window_ID, String trxName)
    {
      super (ctx, AD_Window_ID, trxName);
      /** if (AD_Window_ID == 0)
        {
			setAD_Window_ID (0);
			setIsDefault (false);
			setIsSOTrx (true);
// Y
			setName (null);
			setWindowType (null);
// M
        } */
    }

    /** Load Constructor */
    public X_AD_Window (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Window[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Window_UU.
		@param AD_Window_UU AD_Window_UU	  */
	public void setAD_Window_UU (String AD_Window_UU)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Window_UU, AD_Window_UU);
	}

	/** Get AD_Window_UU.
		@return AD_Window_UU	  */
	public String getAD_Window_UU () 
	{
		return (String)get_Value(COLUMNNAME_AD_Window_UU);
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

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	/** Set Title Logic.
		@param TitleLogic 
		The result determines the title to be displayed for this window
	  */
	public void setTitleLogic (String TitleLogic)
	{
		set_Value (COLUMNNAME_TitleLogic, TitleLogic);
	}

	/** Get Title Logic.
		@return The result determines the title to be displayed for this window
	  */
	public String getTitleLogic () 
	{
		return (String)get_Value(COLUMNNAME_TitleLogic);
	}

	/** WindowType AD_Reference_ID=108 */
	public static final int WINDOWTYPE_AD_Reference_ID=108;
	/** Single Record = S */
	public static final String WINDOWTYPE_SingleRecord = "S";
	/** Maintain = M */
	public static final String WINDOWTYPE_Maintain = "M";
	/** Transaction = T */
	public static final String WINDOWTYPE_Transaction = "T";
	/** Query Only = Q */
	public static final String WINDOWTYPE_QueryOnly = "Q";
	/** Set WindowType.
		@param WindowType 
		Type or classification of a Window
	  */
	public void setWindowType (String WindowType)
	{

		set_Value (COLUMNNAME_WindowType, WindowType);
	}

	/** Get WindowType.
		@return Type or classification of a Window
	  */
	public String getWindowType () 
	{
		return (String)get_Value(COLUMNNAME_WindowType);
	}
}