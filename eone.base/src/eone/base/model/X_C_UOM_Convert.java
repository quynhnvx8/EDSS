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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_UOM_Convert
 *  @author iDempiere (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_C_UOM_Convert extends PO implements I_C_UOM_Convert, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200827L;

    /** Standard Constructor */
    public X_C_UOM_Convert (Properties ctx, int C_UOM_Convert_ID, String trxName)
    {
      super (ctx, C_UOM_Convert_ID, trxName);
      /** if (C_UOM_Convert_ID == 0)
        {
			setC_UOM_Convert_ID (0);
			setC_UOM_ID (0);
			setC_UOM_To_ID (0);
			setDivideRate (Env.ZERO);
			setMultiplyRate (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_UOM_Convert (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuilder sb = new StringBuilder ("X_C_UOM_Convert[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set UOM Convert.
		@param C_UOM_Convert_ID 
		UOM Convert
	  */
	public void setC_UOM_Convert_ID (int C_UOM_Convert_ID)
	{
		if (C_UOM_Convert_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_Convert_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_Convert_ID, Integer.valueOf(C_UOM_Convert_ID));
	}

	/** Get UOM Convert.
		@return UOM Convert
	  */
	public int getC_UOM_Convert_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Convert_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_UOM_Convert_ID()));
    }

	public eone.base.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (eone.base.model.I_C_UOM)MTable.get(getCtx(), eone.base.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_C_UOM getC_UOM_To() throws RuntimeException
    {
		return (eone.base.model.I_C_UOM)MTable.get(getCtx(), eone.base.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_To_ID(), get_TrxName());	}

	/** Set UoM To.
		@param C_UOM_To_ID 
		Target or destination Unit of Measure
	  */
	public void setC_UOM_To_ID (int C_UOM_To_ID)
	{
		if (C_UOM_To_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_To_ID, Integer.valueOf(C_UOM_To_ID));
	}

	/** Get UoM To.
		@return Target or destination Unit of Measure
	  */
	public int getC_UOM_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}