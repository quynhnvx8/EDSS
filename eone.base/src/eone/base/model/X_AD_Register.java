/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for AD_Register
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Register extends PO implements I_AD_Register, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220507L;

    /** Standard Constructor */
    public X_AD_Register (Properties ctx, int AD_Register_ID, String trxName)
    {
      super (ctx, AD_Register_ID, trxName);
      /** if (AD_Register_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_Register (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Register[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_ModelClient getAD_ModelClient() throws RuntimeException
    {
		return (eone.base.model.I_AD_ModelClient)MTable.get(getCtx(), eone.base.model.I_AD_ModelClient.Table_Name)
			.getPO(getAD_ModelClient_ID(), get_TrxName());	}

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

	public eone.base.model.I_AD_PackagePrice getAD_PackagePrice() throws RuntimeException
    {
		return (eone.base.model.I_AD_PackagePrice)MTable.get(getCtx(), eone.base.model.I_AD_PackagePrice.Table_Name)
			.getPO(getAD_PackagePrice_ID(), get_TrxName());	}

	/** Set Package Price.
		@param AD_PackagePrice_ID Package Price	  */
	public void setAD_PackagePrice_ID (int AD_PackagePrice_ID)
	{
		if (AD_PackagePrice_ID < 1) 
			set_Value (COLUMNNAME_AD_PackagePrice_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PackagePrice_ID, Integer.valueOf(AD_PackagePrice_ID));
	}

	/** Get Package Price.
		@return Package Price	  */
	public int getAD_PackagePrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PackagePrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Register.
		@param AD_Register_ID Register	  */
	public void setAD_Register_ID (int AD_Register_ID)
	{
		if (AD_Register_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Register_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Register_ID, Integer.valueOf(AD_Register_ID));
	}

	/** Get Register.
		@return Register	  */
	public int getAD_Register_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Register_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public eone.base.model.I_C_Element getC_Element() throws RuntimeException
    {
		return (eone.base.model.I_C_Element)MTable.get(getCtx(), eone.base.model.I_C_Element.Table_Name)
			.getPO(getC_Element_ID(), get_TrxName());	}

	/** Set Element.
		@param C_Element_ID 
		Accounting Element
	  */
	public void setC_Element_ID (int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
	}

	/** Get Element.
		@return Accounting Element
	  */
	public int getC_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Domain.
		@param Domain Domain	  */
	public void setDomain (String Domain)
	{
		set_Value (COLUMNNAME_Domain, Domain);
	}

	/** Get Domain.
		@return Domain	  */
	public String getDomain () 
	{
		return (String)get_Value(COLUMNNAME_Domain);
	}

	/** Set EMail.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** MMPolicy AD_Reference_ID=335 */
	public static final int MMPOLICY_AD_Reference_ID=335;
	/** LiFo = L */
	public static final String MMPOLICY_LiFo = "L";
	/** FiFo = F */
	public static final String MMPOLICY_FiFo = "F";
	/** Average = A */
	public static final String MMPOLICY_Average = "A";
	/** None = N */
	public static final String MMPOLICY_None = "N";
	/** Set Material Policy.
		@param MMPolicy 
		Material Movement Policy
	  */
	public void setMMPolicy (String MMPolicy)
	{

		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	/** Get Material Policy.
		@return Material Movement Policy
	  */
	public String getMMPolicy () 
	{
		return (String)get_Value(COLUMNNAME_MMPolicy);
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

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Trial = TRIAL */
	public static final String REGISTERTYPE_Trial = "TRIAL";
	/** Real Use = REAL */
	public static final String REGISTERTYPE_RealUse = "REAL";
	/** Set Register Type.
		@param RegisterType Register Type	  */
	public void setRegisterType (String RegisterType)
	{

		set_Value (COLUMNNAME_RegisterType, RegisterType);
	}

	/** Get Register Type.
		@return Register Type	  */
	public String getRegisterType () 
	{
		return (String)get_Value(COLUMNNAME_RegisterType);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Tax ID.
		@param TaxID 
		Tax Identification
	  */
	public void setTaxID (String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Tax ID.
		@return Tax Identification
	  */
	public String getTaxID () 
	{
		return (String)get_Value(COLUMNNAME_TaxID);
	}
}