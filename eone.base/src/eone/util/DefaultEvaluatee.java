
package eone.util;

import java.util.Properties;

import eone.base.model.GridTab;
import eone.base.model.MColumn;
import eone.base.model.MTable;

public class DefaultEvaluatee implements Evaluatee {

	private GridTab m_GridTab;
	private int m_WindowNo;
	private int m_TabNo;

	public DefaultEvaluatee(GridTab gridTab, int windowNo, int tabNo) {
		this.m_GridTab = gridTab;
		this.m_WindowNo = windowNo;
		this.m_TabNo = tabNo;
	}

	@Override
	public String get_ValueAsString(String variableName) {
		return get_ValueAsString(Env.getCtx(), variableName);
	}

	public String get_ValueAsString (Properties ctx, String variableName)
	{
		//ref column
		String foreignColumn = "";
		int f = variableName.indexOf('.');
		if (f > 0) {
			foreignColumn = variableName.substring(f+1, variableName.length());
			variableName = variableName.substring(0, f);
		}
		
		String value = null;
		if( m_TabNo == 0)
	    	value = Env.getContext (ctx, m_WindowNo, variableName, true);
	    else
	    {
	    	boolean tabOnly = false;
	    	if (variableName.startsWith("~")) 
	    	{
	    		variableName = variableName.substring(1);
	    		tabOnly = true;
	    	}
	    	value = Env.getContext (ctx, m_WindowNo, m_TabNo, variableName, tabOnly, true);
	    }
		if (!Util.isEmpty(value) && !Util.isEmpty(foreignColumn) && variableName.endsWith("_ID")) {
			int id = 0;
			try {
				id = Integer.parseInt(value);
			} catch (Exception e){}
			if (id > 0) {
				String refValue = "";
				MColumn column = null;
				if (m_GridTab != null) {
					column = MColumn.get(ctx, m_GridTab.getTableName(), variableName);					
					if (column == null) {
						//try parent
						GridTab parent = m_GridTab.getParentTab();
						while (column == null && parent != null) {
							column = MColumn.get(ctx, parent.getTableName(), variableName);
							parent = parent.getParentTab();
						}
					}
				}
				if (column != null) {
					String foreignTable = column.getReferenceTableName();
					refValue = DB.getSQLValueString(null,
							"SELECT " + foreignColumn + " FROM " + foreignTable + " WHERE " 
									+ foreignTable + "_ID = ?", id);
					return refValue;
				} else {
					// no MColumn found, try tableName from columnName
					String foreignTable = variableName.substring(0, variableName.length()-3);
					MTable table = MTable.get(ctx, foreignTable);
					if (table != null) {
						refValue = DB.getSQLValueString(null,
								"SELECT " + foreignColumn + " FROM " + foreignTable + " WHERE " 
										+ foreignTable + "_ID = ?", id);
						return refValue;
					}
				}
			}
		}
		return value;
	}
}
