
package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.util.CLogger;
import org.compiere.util.DB;


public class MMenu extends X_AD_Menu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671861281736697100L;

	
	public static MMenu[] get (Properties ctx, String whereClause)
	{
		return get(ctx, whereClause, null);
	}
	
	
	public static MMenu[] get (Properties ctx, final String whereClause, String trxName)
	{
		List<MMenu> list = new Query(ctx,I_AD_Menu.Table_Name,whereClause,trxName)
		.list();

		MMenu[] retValue = new MMenu[list.size()];
		list.toArray (retValue);
		return retValue;
	}	//	get
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MMenu.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Menu_ID id
	 *	@param trxName transaction
	 */
	public MMenu (Properties ctx, int AD_Menu_ID, String trxName)
	{
		super (ctx, AD_Menu_ID, trxName);
		if (AD_Menu_ID == 0)
		{
			setIsReadOnly (false);	// N
			setIsSOTrx (false);
			setIsSummary (false);
		//	setName (null);
		}
	}	//	MMenu

	/**
	 * 	Load Contrusctor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMenu (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MMenu

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Reset info
		if (isSummary() && getAction() != null)
			setAction(null);
		String action = getAction();
		if (action == null)
			action = "";
		//	Clean up references
		if (getAD_Window_ID() != 0 && !action.equals(ACTION_Window))
			setAD_Window_ID(0);
		if (getAD_Form_ID() != 0 && !action.equals(ACTION_Form))
			setAD_Form_ID(0);
		if (getAD_Task_ID() != 0 && !action.equals(ACTION_Task))
			setAD_Task_ID(0);
		if (getAD_Process_ID() != 0 
			&& !(action.equals(ACTION_Process) || action.equals(ACTION_Report)))
			setAD_Process_ID(0);
		if (getAD_InfoWindow_ID() != 0 && !action.equals(ACTION_Info))
			setAD_InfoWindow_ID(0);
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
		if (newRecord)
			insert_Tree(MTree.TREETYPE_Menu);
		return success;
	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(MTree.TREETYPE_Menu);
		return success;
	}	//	afterDelete
	
	/**
	 *  FR [ 1966326 ]
	 * 	get Menu ID
	 *	@param String Menu Name
	 *	@return int retValue
	 */
	public static int getMenu_ID(String menuName) {
		int retValue = 0;
		String SQL = "SELECT AD_Menu_ID FROM AD_Menu WHERE Name = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL, null);
			pstmt.setString(1, menuName);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getInt(1);
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, SQL, e);
			retValue = -1;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return retValue;
	}
	
}	//	MMenu
