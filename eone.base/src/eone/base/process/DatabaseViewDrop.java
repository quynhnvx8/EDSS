/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
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
package eone.base.process;

import eone.base.model.MTable;
import eone.exceptions.EONEException;
import eone.util.DB;
import eone.util.Msg;

public class DatabaseViewDrop extends SvrProcess {

	private int		p_AD_Table_ID = 0; 

	@Override
	protected void prepare() 
	{
		p_AD_Table_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception 
	{
		MTable table = new MTable(getCtx(), p_AD_Table_ID, get_TrxName());
		log.info(table.toString());
		if (!table.isView() || !table.isActive())
			throw new EONEException(Msg.getMsg(getCtx(), "NotActiveDatabaseView"));

		String sql = "DROP VIEW " + table.getTableName();
		int rvalue = DB.executeUpdateEx(sql, get_TrxName());

		return rvalue + " - " + sql;
	}
}	// DatabaseViewDrop