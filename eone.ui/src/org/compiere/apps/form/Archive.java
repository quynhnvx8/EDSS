/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.sql.Timestamp;
import java.util.logging.Level;

import eone.base.model.MArchive;
import eone.base.model.MBPartner;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.TimeUtil;

public class Archive {
	
	/**	Window No			*/
	public int         m_WindowNo = 0;
	/**	The Archives		*/
	public MArchive[]	m_archives = new MArchive[0];
	/** Archive Index		*/
	public int			m_index = 0;
	/** Table direct		*/
	public int 		m_AD_Table_ID = 0;
	/** Record direct		*/
	public int 		m_Record_ID = 0;
	
	/**	Logger			*/
	public static final CLogger log = CLogger.getCLogger(Archive.class);

	public KeyNamePair[] getProcessData()
	{
		
		boolean trl = !Env.isBaseLanguage(Env.getCtx(), "AD_Process");
		String lang = Env.getAD_Language(Env.getCtx());
		
		String sql = "SELECT DISTINCT p.AD_Process_ID,"
				+ (trl ? "trl.Name" : "p.Name ")
			+ " FROM AD_Process p "
			+ (trl ? "LEFT JOIN AD_Process_Trl trl on (trl.AD_Process_ID=p.AD_Process_ID and trl.AD_Language=" + DB.TO_STRING(lang) + ")" : "") 
			+ " WHERE p.IsReport='Y' AND p.IsActive='Y' "
			+ "ORDER BY 2"; 
		return DB.getKeyNamePairs(sql, true);
	}
	
		
	public KeyNamePair[] getUserData()
	{
		//	Internal Users
		String sql = "SELECT AD_User_ID, Name "
			+ "FROM AD_User u WHERE EXISTS "
				+"(SELECT * FROM AD_User_Roles ur WHERE u.AD_User_ID=ur.AD_User_ID) "
			+ "ORDER BY 2";
		return DB.getKeyNamePairs(sql, true);
	}	//	dynInit
	
	/**
	 * 	Is it the same
	 *	@param s1 s1
	 *	@param s2 s1
	 *	@return true if the same
	 */
	public boolean isSame(String s1, String s2)
	{
		if (s1 == null)
			return s2 == null;
		else if (s2 == null)
			return false;
		else
			return s1.equals(s2);
	}	//	isSame
	
	/**************************************************************************
	 * 	Create Query
	 */
	public void cmd_query(boolean reports, KeyNamePair process, KeyNamePair table, Integer C_BPartner_ID, 
			String name, String description, String help, KeyNamePair createdBy, 
			Timestamp createdFrom, Timestamp createdTo)
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append(" AND IsReport=").append(reports ? "'Y'" : "'N'");
		
		//	Process
		if (reports)
		{
			if (process != null && process.getKey() > 0)
				sql.append(" AND AD_Process_ID=").append(process.getKey());
		}
		
		//	Table
		if (m_AD_Table_ID > 0)
		{
			sql.append(" AND ((AD_Table_ID=").append(m_AD_Table_ID);
			if (m_Record_ID > 0)
				sql.append(" AND Record_ID=").append(m_Record_ID);
			sql.append(")");
			if (m_AD_Table_ID == MBPartner.Table_ID && m_Record_ID > 0)
				sql.append(" OR C_BPartner_ID=").append(m_Record_ID);
			sql.append(")");
			//	Reset for query
			m_AD_Table_ID = 0;
			m_Record_ID = 0;
		}
		else
		{
			if (table != null && table.getKey() > 0)
				sql.append(" AND AD_Table_ID=").append(table.getKey());
		}
		
		//	Business Partner
		if (!reports)
		{
			if (C_BPartner_ID != null)
				sql.append(" AND C_BPartner_ID=").append(C_BPartner_ID);
			else
				sql.append(" AND C_BPartner_ID IS NOT NULL");
		}
		
		//	Name
		if (name != null && name.length() > 0)
		{
			if (name.indexOf('%') != -1 || name.indexOf('_') != -1)
				sql.append(" AND Name LIKE ").append(DB.TO_STRING(name));
			else
				sql.append(" AND Name=").append(DB.TO_STRING(name));
		}
		
		//	Description
		if (description != null && description.length() > 0)
		{
			if (description.indexOf('%') != -1 || description.indexOf('_') != -1)
				sql.append(" AND Description LIKE ").append(DB.TO_STRING(description));
			else
				sql.append(" AND Description=").append(DB.TO_STRING(description));
		}

		//	Help
		if (help != null && help.length() > 0)
		{
			if (help.indexOf('%') != -1 || help.indexOf('_') != -1)
				sql.append(" AND Help LIKE ").append(DB.TO_STRING(help));
			else
				sql.append(" AND Help=").append(DB.TO_STRING(help));
		}

		//	CreatedBy
		if (createdBy != null && createdBy.getKey() > 0)
			sql.append(" AND CreatedBy=").append(createdBy.getKey());
		
		//	Created
		if (createdFrom != null)
			sql.append(" AND Created>=").append(DB.TO_DATE(createdFrom, true));
		if (createdTo != null)
			sql.append(" AND Created<").append(DB.TO_DATE(TimeUtil.addDays(createdTo,1), true));
		
		if (log.isLoggable(Level.FINE)) log.fine(sql.toString());
		
		//metas: Bugfix zu included_Role
		//	Process Access
		sql.append(" AND (AD_Process_ID IS NULL)");
		//	Table Access
		sql.append(" AND (AD_Table_ID IS NULL OR (AD_Table_ID IS NOT NULL AND AD_Process_ID IS NOT NULL))");
		if (log.isLoggable(Level.FINEST)) log.finest(sql.toString());
		//metas: Bugfix zu included_Role ende
		//
		m_archives = MArchive.get(Env.getCtx(), sql.toString());
		if (log.isLoggable(Level.INFO)) log.info("Length=" + m_archives.length);
	}	//	cmd_query
}
