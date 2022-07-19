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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;

/**
 * 	BOM Model
 *  @author Jorg Janke
 *  @version $Id: MBOM.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBOM extends X_M_BOM implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8885316310068284701L;


	/**
	 * 	Get BOM from Cache
	 *	@param ctx context
	 *	@param M_BOM_ID id
	 *	@return MBOM
	 */
	public static MBOM get (Properties ctx, int M_BOM_ID)
	{
		Integer key = Integer.valueOf(M_BOM_ID);
		MBOM retValue = (MBOM) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MBOM (ctx, M_BOM_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get BOMs Of Product
	 *	@param ctx context
	 *	@param M_Product_ID product
	 *	@param trxName trx
	 *	@param whereClause optional WHERE clause w/o AND
	 *	@return array of BOMs
	 */
	public static MBOM[] getOfProduct (Properties ctx, int M_Product_ID, 
		String trxName, String whereClause)
	{
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		StringBuilder where = new StringBuilder("M_Product_ID=?");
		if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);
		List <MBOM> list = new Query(ctx, I_M_BOM.Table_Name, where.toString(), trxName)
		.setParameters(M_Product_ID)
		.list();
		
		MBOM[] retValue = new MBOM[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfProduct

	/**	Cache						*/
	private static CCache<Integer,MBOM>	s_cache	
		= new CCache<Integer,MBOM>(Table_Name, 20);
	/**	Logger						*/
	@SuppressWarnings("unused")
	private static CLogger	s_log	= CLogger.getCLogger (MBOM.class);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_BOM_ID id
	 *	@param trxName trx
	 */
	public MBOM (Properties ctx, int M_BOM_ID, String trxName)
	{
		super (ctx, M_BOM_ID, trxName);
		
	}	//	MBOM

	/**
	 * 	Load Constructor
	 *	@param ctx ctx
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MBOM (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MBOM

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		String sql = "SELECT COUNT(1) FROM M_BOM WHERE M_Product_ID = ?  AND M_BOM_ID !=?";//AND M_Warehouse_ID = ?
		List<Object> params = new ArrayList<Object>();
		params.add(getM_Product_ID());
		//params.add(getM_Warehouse_ID());
		params.add(getM_BOM_ID());
		int count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count >= 1) {
			log.saveError("Error", "Sản phẩm định mức này đã thiết lập rồi");
			return false;
		}
		return true;
	}	//	beforeSave
	
	
	//Implements DocAction
	protected String		m_processMsg = null;
	
	protected MBOMProduct[]	m_lines = null;
	
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE M_BOMProduct SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE M_BOM_ID=").append(getM_BOM_ID());
		int noLine = DB.executeUpdate(sql.toString(), get_TrxName());
		m_lines = null;
		if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
	}
	
	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}

	@Override
	public String completeIt() {
		
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
			
		return true;
	}


	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}
	
	@Override
	public String getSummary() {
		
		return "";
	}


	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}	//	MBOM
