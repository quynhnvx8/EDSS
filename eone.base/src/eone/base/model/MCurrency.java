/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import eone.util.CCache;

/**
 * 	Currency Model.
 *
 *  @author Jorg Janke
 */
public class MCurrency extends X_C_Currency
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2262097171335518186L;

	/**
	 * 	Currency Constructor
	 *	@param ctx context
	 *	@param C_Currency_ID id
	 *	@param trxName transaction
	 */
	public MCurrency (Properties ctx, int C_Currency_ID, String trxName)
	{
		super (ctx, C_Currency_ID, trxName);
		if (C_Currency_ID == 0)
		{
			setIsEMUMember (false);
			setIsEuro (false);
			setStdPrecision (2);
			setCostingPrecision (4);
		}
	}	//	MCurrency

	/**
	 * Resultset constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCurrency(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 	Currency Constructor
	 *	@param ctx context
	 *	@param ISO_Code ISO
	 *	@param Description Name
	 *	@param CurSymbol symbol
	 *	@param StdPrecision prec
	 *	@param CostingPrecision prec
	 *	@param trxName transaction
	 */
	public MCurrency (Properties ctx, String ISO_Code,
		String Description, String CurSymbol, int StdPrecision, int CostingPrecision, String trxName)
	{
		super(ctx, 0, trxName);
		setISO_Code(ISO_Code);
		setDescription(Description);
		setCurSymbol(CurSymbol);
		setStdPrecision (StdPrecision);
		setCostingPrecision (CostingPrecision);
		setIsEMUMember (false);
		setIsEuro (false);
	}	//	MCurrency


	/**	Store System Currencies			**/
	private static CCache<Integer,MCurrency> s_currencies = new CCache<Integer,MCurrency>(Table_Name, 50);
	/** Cache System Currencies by using ISO code as key **/
	private static CCache<String,MCurrency> s_currenciesISO = new CCache<String,MCurrency>(Table_Name, "C_CurrencyISO", 50);

	/**
	 * 	Get Currency using ISO code
	 *	@param ctx Context
	 *	@param ISOcode	Iso code
	 *	@return MCurrency
	 */
	public static MCurrency get (Properties ctx, String ISOcode)
	{
		//	Try Cache
		MCurrency retValue = (MCurrency)s_currenciesISO.get(ISOcode);
		if (retValue != null)
			return retValue;

		//	Try database
		Query query = new Query(ctx, I_C_Currency.Table_Name, "ISO_Code=?", null);
		query.setParameters(ISOcode);
		retValue = (MCurrency)query.firstOnly();
		
		//	Save 
		if (retValue!=null)
			s_currenciesISO.put(ISOcode, retValue);
		return retValue;
	}	
	

	/**
	 * 	Get Currency
	 *	@param ctx Context
	 *	@param C_Currency_ID currency
	 *	@return ISO Code
	 */
	public static MCurrency get (Properties ctx, int C_Currency_ID)
	{
		//	Try Cache
		Integer key = Integer.valueOf(C_Currency_ID);
		MCurrency retValue = (MCurrency)s_currencies.get(key);
		if (retValue != null)
			return retValue;

		//	Create it
		retValue = new MCurrency(ctx, C_Currency_ID, null);
		//	Save in System
		if (retValue.getAD_Client_ID() == 0)
			s_currencies.put(key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get Currency Iso Code.
	 *	@param ctx Context
	 *	@param C_Currency_ID currency
	 *	@return ISO Code
	 */
	public static String getISO_Code (Properties ctx, int C_Currency_ID)
	{
		StringBuilder contextKey = new StringBuilder("C_Currency_").append(C_Currency_ID);
		String retValue = ctx.getProperty(contextKey.toString());
		if (retValue != null)
			return retValue;

		//	Create it
		MCurrency c = get(ctx, C_Currency_ID);
		retValue = c.getISO_Code();
		ctx.setProperty(contextKey.toString(), retValue);
		return retValue;
	}	//	getISO

	/**
	 * 	Get Standard Precision.
	 *	@param ctx Context
	 *	@param C_Currency_ID currency
	 *	@return Standard Precision
	 */
	public static int getStdPrecision (Properties ctx, int C_Currency_ID)
	{
		MCurrency c = get(ctx, C_Currency_ID);
		return c.getStdPrecision();
	}	//	getStdPrecision

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuilder msgreturn = new StringBuilder("MCurrency[").append(getC_Currency_ID())
				.append("-").append(getISO_Code()).append("-").append(getCurSymbol())
				.append(",").append(getDescription())
				.append(",Precision=").append(getStdPrecision()).append("/").append(getCostingPrecision());
		return msgreturn.toString();
	}	//	toString

	/**
	 * 	Get Costing Precision.
	 *	@param ctx Context
	 *	@param C_Currency_ID currency
	 *	@return Costing Precision
	 */
	public static int getCostingPrecision(Properties ctx, int C_Currency_ID) {
		MCurrency c = get(ctx, C_Currency_ID);
		return c.getCostingPrecision();
	}


}	//	MCurrency
