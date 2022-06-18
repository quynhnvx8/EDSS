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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eone.util.Env;
import eone.util.Msg;

/**
 * 	Project Line Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProjectLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MItemLine extends X_HR_ItemLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2668549463273628848L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_ProjectLine_ID id
	 *	@param trxName transaction
	 */
	public MItemLine (Properties ctx, int HR_ItemLine_ID, String trxName)
	{
		super (ctx, HR_ItemLine_ID, trxName);
		
	}	//	MProjectLine

	/**
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs result set
	 *	@param trxName transaction
	 */
	public MItemLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProjectLine

	
	public String toString ()
	{
		return "";
	}	//	toString
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_IsDefault, true);
		dataColumn.put(COLUMNNAME_HR_Item_ID, getHR_Item_ID());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_HR_ItemLine_ID, getHR_ItemLine_ID(), get_TrxName());
		if (!check && isDefault()) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_IsDefault);
			return false;
		}
		
		return true;
	}	//	beforeSave
	
		
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		updateHeader();
		return success;
	}	//	afterSave
	
	
	/**
	 * 	After Delete
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		updateHeader();
		return success;
	}	//	afterDelete
	
	/**
	 * 	Update Header
	 */
	private void updateHeader()
	{
		
			
	} // updateHeader

} 
