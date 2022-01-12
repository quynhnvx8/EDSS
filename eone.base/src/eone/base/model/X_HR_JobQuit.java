/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for HR_JobQuit
 *  @author iDempiere (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_HR_JobQuit extends PO implements I_HR_JobQuit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200804L;

    /** Standard Constructor */
    public X_HR_JobQuit (Properties ctx, int HR_JobQuit_ID, String trxName)
    {
      super (ctx, HR_JobQuit_ID, trxName);
      /** if (HR_JobQuit_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HR_JobQuit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_HR_JobQuit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Content.
		@param ContentText Content	  */
	public void setContentText (String ContentText)
	{
		set_Value (COLUMNNAME_ContentText, ContentText);
	}

	/** Get Content.
		@return Content	  */
	public String getContentText () 
	{
		return (String)get_Value(COLUMNNAME_ContentText);
	}

	/** Set DateApproved.
		@param DateApproved DateApproved	  */
	public void setDateApproved (Timestamp DateApproved)
	{
		set_Value (COLUMNNAME_DateApproved, DateApproved);
	}

	/** Get DateApproved.
		@return DateApproved	  */
	public Timestamp getDateApproved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproved);
	}

	/** Set DateEffective.
		@param DateEffective DateEffective	  */
	public void setDateEffective (Timestamp DateEffective)
	{
		set_Value (COLUMNNAME_DateEffective, DateEffective);
	}

	/** Get DateEffective.
		@return DateEffective	  */
	public Timestamp getDateEffective () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEffective);
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

	public I_HR_Employee getHR_Employee() throws RuntimeException
    {
		return (I_HR_Employee)MTable.get(getCtx(), I_HR_Employee.Table_Name)
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

	public I_HR_ItemLine getHR_ItemLine_03() throws RuntimeException
    {
		return (I_HR_ItemLine)MTable.get(getCtx(), I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_03_ID(), get_TrxName());	}

	/** Set Decision Level.
		@param HR_ItemLine_03_ID Decision Level	  */
	public void setHR_ItemLine_03_ID (int HR_ItemLine_03_ID)
	{
		if (HR_ItemLine_03_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_03_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_03_ID, Integer.valueOf(HR_ItemLine_03_ID));
	}

	/** Get Decision Level.
		@return Decision Level	  */
	public int getHR_ItemLine_03_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_03_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_ItemLine getHR_ItemLine_04() throws RuntimeException
    {
		return (I_HR_ItemLine)MTable.get(getCtx(), I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_04_ID(), get_TrxName());	}

	/** Set Allowance.
		@param HR_ItemLine_04_ID Allowance	  */
	public void setHR_ItemLine_04_ID (int HR_ItemLine_04_ID)
	{
		if (HR_ItemLine_04_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_04_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_04_ID, Integer.valueOf(HR_ItemLine_04_ID));
	}

	/** Get Allowance.
		@return Allowance	  */
	public int getHR_ItemLine_04_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_04_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_ItemLine getHR_ItemLine_16() throws RuntimeException
    {
		return (I_HR_ItemLine)MTable.get(getCtx(), I_HR_ItemLine.Table_Name)
			.getPO(getHR_ItemLine_16_ID(), get_TrxName());	}

	/** Set Job Quit Form.
		@param HR_ItemLine_16_ID Job Quit Form	  */
	public void setHR_ItemLine_16_ID (int HR_ItemLine_16_ID)
	{
		if (HR_ItemLine_16_ID < 1) 
			set_Value (COLUMNNAME_HR_ItemLine_16_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ItemLine_16_ID, Integer.valueOf(HR_ItemLine_16_ID));
	}

	/** Get Job Quit Form.
		@return Job Quit Form	  */
	public int getHR_ItemLine_16_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ItemLine_16_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Job Quit.
		@param HR_JobQuit_ID Job Quit	  */
	public void setHR_JobQuit_ID (int HR_JobQuit_ID)
	{
		if (HR_JobQuit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_JobQuit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_JobQuit_ID, Integer.valueOf(HR_JobQuit_ID));
	}

	/** Get Job Quit.
		@return Job Quit	  */
	public int getHR_JobQuit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_JobQuit_ID);
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