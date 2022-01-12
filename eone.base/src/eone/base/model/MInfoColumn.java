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
import java.util.logging.Level;

import org.compiere.db.Database;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluator;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import eone.base.model.AccessSqlParser.TableInfo;

/**
 * 	Info Window Column Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MInfoColumn.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInfoColumn extends X_AD_InfoColumn implements IInfoColumn
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6313260451237775302L;

	/**
	 * 	Stanfard Constructor
	 *	@param ctx context
	 *	@param AD_InfoColumn_ID id
	 *	@param trxName transaction
	 */
	public MInfoColumn (Properties ctx, int AD_InfoColumn_ID, String trxName)
	{
		super (ctx, AD_InfoColumn_ID, trxName);
	}	//	MInfoColumn

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MInfoColumn (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MInfoColumn

	public MInfoColumn(MInfoWindow targetInfoWindow) {
		this(targetInfoWindow.getCtx(), 0, targetInfoWindow.get_TrxName());
		m_parent = targetInfoWindow;
		this.setAD_InfoWindow_ID (targetInfoWindow.getAD_InfoWindow_ID());
	}

	/** Parent						*/
	private MInfoWindow	m_parent = null;

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MInfoWindow getParent()
	{
		if (m_parent == null)
			m_parent = new MInfoWindow(getCtx(), getAD_InfoWindow_ID(), get_TrxName());
		return m_parent;
	}	//	getParent

	/**
	 * check column read access
	 * @param tableInfos
	 * @return false if current role don't have read access to the column, false otherwise
	 */
	public boolean isColumnAccess(TableInfo[] tableInfos)
	{
		return true;
	}

	/**
	 * @param ctx
	 * @param windowNo
	 * @return boolean
	 */
	public boolean isDisplayed(final Properties ctx, final int windowNo) {
		if (!isDisplayed())
			return false;
		
		if (getDisplayLogic() == null || getDisplayLogic().trim().length() == 0)
			return true;
		
		Evaluatee evaluatee = new Evaluatee() {
			public String get_ValueAsString(String variableName) {
				return Env.getContext (ctx, windowNo, variableName, true);
			}
		};
		
		boolean retValue = Evaluator.evaluateLogic(evaluatee, getDisplayLogic());
		if (log.isLoggable(Level.FINEST)) log.finest(getName() 
					+ " (" + getDisplayLogic() + ") => " + retValue);
		return retValue;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		String error = Database.isValidIdentifier(getColumnName());
		if (!Util.isEmpty(error)) {
			log.saveError("Error", Msg.getMsg(getCtx(), error) + " [ColumnName]");
			return false;
		}
		// Sync Terminology
		if ((newRecord || is_ValueChanged ("AD_Element_ID")) && getAD_Element_ID() != 0)
		{
			M_Element element = new M_Element (getCtx(), getAD_Element_ID (), get_TrxName());
			setName (element.getName());
		}

		if (isQueryCriteria() && getSeqNoSelection() <= 0) {
			int next = DB.getSQLValueEx(get_TrxName(),
					"SELECT ROUND((COALESCE(MAX(SeqNoSelection),0)+10)/10,0)*10 FROM AD_InfoColumn WHERE AD_InfoWindow_ID=? AND IsQueryCriteria='Y' AND IsActive='Y'",
					getAD_InfoWindow_ID());
			setSeqNoSelection(next);
		}

		return true;
	}
	
	/**
	 * when change field relate to sql, call valid from infoWindow
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success)
			return success;
	
		// evaluate need valid
		boolean isNeedValid = getParent().isValidateEachColumn() && (newRecord || is_ValueChanged (MInfoColumn.COLUMNNAME_SelectClause));
		
		// call valid of parent
		if (isNeedValid){
			//getParent().validate();
			getParent().saveEx(get_TrxName());
		}
				
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * when delete record, call valid from parent to set state
	 * when delete all, valid state is false
	 * when delete a wrong column can make valid state to true
	 */
	@Override
	protected boolean afterDelete(boolean success) {
		//getParent().validate();
		getParent().saveEx(get_TrxName());
		return super.afterDelete(success);
	}

	@Override
	public int getInfoColumnID() {		
		return get_ID();
	}

	@Override
	public MInfoColumn getAD_InfoColumn() {
		return this;
	}
}	//	MInfoColumn
