
package org.compiere.apps.form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

import eone.base.model.MRole;
import eone.base.model.MTree;
import eone.base.model.MTree_Node;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.KeyNamePair;

public class TreeMaintenance {

	/**	Window No				*/
	public int         	m_WindowNo = 0;
	/**	Active Tree				*/
	public MTree		 	m_tree;
	/**	Logger			*/
	public static final CLogger log = CLogger.getCLogger(TreeMaintenance.class);
	
	public KeyNamePair[] getTreeData()
	{
		return DB.getKeyNamePairs(MRole.addAccessSQL(
				"SELECT AD_Tree_ID, Name FROM AD_Tree WHERE IsActive='Y' AND TreeType = 'MM' And AD_Tree_ID != 10 ORDER BY 2", 
				"AD_Tree", MRole.SQL_NOTQUALIFIED, MRole.SQL_RW, Env.getCtx()), false);
	}
	
	public ArrayList<ListItem> getTreeItemData()
	{
		ArrayList<ListItem> data = new ArrayList<ListItem>();
		
		String fromClause = m_tree.getSourceTableName(false);	//	fully qualified
		String columnNameX = m_tree.getSourceTableName(true);
		String actionColor = m_tree.getActionColorName();
		String fieldName = null;
		String fieldDescription = null;
		String join = null;
		if (m_tree.getTreeType().equals(MTree.TREETYPE_Menu) // (see MTree.getNodeDetails)
			&& ! Env.isBaseLanguage(Env.getCtx(), "AD_Menu")) {
			fieldName = "trl.Name";
			fieldDescription ="trl.Description";
			join = " LEFT JOIN AD_Menu_Trl trl ON (t.AD_Menu_ID = trl.AD_Menu_ID AND trl.AD_Language='"
				+ Env.getAD_Language(Env.getCtx()) + "')";
		} else {
			fieldName ="t.Name";
			fieldDescription ="t.Description";
			join = "";
		}

		StringBuilder sqlb = new StringBuilder("SELECT t.")
			.append(columnNameX) 
			.append("_ID,").append(fieldName).append(",").append(fieldDescription).append(",t.IsSummary,")
			.append(actionColor)
			.append(" FROM ").append(fromClause).append(join)
		//	.append(" WHERE t.IsActive='Y'") // R/O
			.append(" ORDER BY 2");
		String sql = sqlb.toString();// MRole.addAccessSQL(sqlb.toString(), "t", MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO, Env.getCtx());
		log.config(sql);
		//	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				ListItem item = new ListItem(rs.getInt(1), rs.getString(2),
					rs.getString(3), "Y".equals(rs.getString(4)), rs.getString(5));
				data.add(item);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}
	
	
	public void updateTreeByRole(Map<Integer, String> data, int AD_Tree_ID) {
		String list_Y = "0";
		String list_N = "0";
		for(Integer node_ID : data.keySet()) {
			if ("Y".equals(data.get(node_ID))) {
				list_Y = list_Y + "," + node_ID;
			} else {
				list_N = list_N + "," + node_ID;
			}
		}
		
		String sql_Y = "UPDATE AD_TreeNode SET IsDisplayed = 'Y' WHERE Node_ID IN (" + list_Y + ") AND AD_Tree_ID = " + AD_Tree_ID;
		String sql_N = "UPDATE AD_TreeNode SET IsDisplayed = 'N' WHERE Node_ID IN (" + list_N + ") AND AD_Tree_ID = " + AD_Tree_ID;
		DB.executeUpdate(sql_Y);
		DB.executeUpdate(sql_N);
	}
	/**
	 * 	Action: Add Node to Tree
	 * 	@param item item
	 */
	public void addNode(ListItem item)
	{
		if (item != null)
		{
			MTree_Node node = new MTree_Node (m_tree, item.id);
			node.saveEx();
		}
	}	//	action_treeAdd
	
	/**
	 * 	Action: Delete Node from Tree
	 * 	@param item item
	 */
	public void deleteNode(ListItem item)
	{
		if (item != null)
		{
			MTree_Node node = MTree_Node.get (m_tree, item.id);
			if (node != null)
				node.delete(true);
		}
	}	//	action_treeDelete
	
	/**************************************************************************
	 * 	Tree Maintenance List Item
	 */
	public static class ListItem
	{
		/**
		 * 	ListItem
		 *	@param ID
		 *	@param Name
		 *	@param Description
		 *	@param summary
		 *	@param ImageIndicator
		 */
		public ListItem (int ID, String Name, String Description, 
			boolean summary, String ImageIndicator)
		{
			id = ID;
			name = Name;
			description = Description;
			isSummary = summary;
			imageIndicator = ImageIndicator;
		}	//	ListItem
		
		/**	ID			*/
		public int id;
		/** Name		*/
		public String name;
		/** Description	*/
		public String description;
		/** Summary		*/
		public boolean isSummary;
		/** Indicator	*/
		public String imageIndicator;  //  Menu - Action
		
		/**
		 * 	To String
		 *	@return	String Representation
		 */
		public String toString ()
		{
			String retValue = name;
			if (description != null && description.length() > 0)
				retValue += " (" + description + ")";
			return retValue;
		}	//	toString
		
	}	//	ListItem
}
