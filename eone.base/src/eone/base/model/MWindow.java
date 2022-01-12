
package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Window Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MWindow.java,v 1.2 2006/07/30 00:58:05 jjanke Exp $
 */
public class MWindow extends X_AD_Window
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200113429427897527L;

	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MWindow.class);

	/**	Cache						*/
	private static CCache<Integer,MWindow> s_cache = new CCache<Integer,MWindow>(Table_Name, 20);

	/**
	 * 	Get Window from Cache
	 *	@param ctx context
	 *	@param AD_Window_ID id
	 *	@return MWindow
	 */
	public static MWindow get (Properties ctx, int AD_Window_ID)
	{
		Integer key = Integer.valueOf(AD_Window_ID);
		MWindow retValue = s_cache.get (key);
		if (retValue != null && retValue.getCtx() == ctx) {
			return retValue;
		}
		retValue = new MWindow (ctx, AD_Window_ID, null);
		if (retValue.get_ID () != 0) {
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get

	/**
	 * get Window ID by UU
	 * @param ctx context
	 * @param uu AD_Window_UU
	 * @return MWindow object
	 */
	public static synchronized MWindow get(Properties ctx, String uu)
	{
		if (uu == null)
			return null;
		MWindow retValue = null;
		Iterator<MWindow> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			retValue = it.next();
			if (uu.equals(retValue.getAD_Window_UU()) && retValue.getCtx() == ctx)
			{
				return retValue;
			}
		}

		final String whereClause = MWindow.COLUMNNAME_AD_Window_UU + "=?";
		MWindow window = new Query(Env.getCtx(), MWindow.Table_Name, whereClause, null)
				.setParameters(uu)
				.setOnlyActiveRecords(true)
				.first();

		if (window != null)
			retValue = window;

		return retValue;
	}

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Window_ID
	 *	@param trxName transaction
	 */
	public MWindow (Properties ctx, int AD_Window_ID, String trxName)
	{
		super (ctx, AD_Window_ID, trxName);
		if (AD_Window_ID == 0)
		{
			setWindowType (WINDOWTYPE_Maintain);	// M
			setIsDefault (false);
			setIsSOTrx (true);	// Y
		}	}	//	M_Window

	/**
	 * 	Koad Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWindow (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	M_Window
	
	
	
	/**	The Lines						*/
	private MTab[]		m_tabs	= null;

	/**
	 * 	Get Fields
	 *	@param reload reload data
	 *	@return array of lines
	 *	@param trxName transaction
	 */
	public MTab[] getTabs (boolean reload, String trxName)
	{
		if (m_tabs != null && !reload)
			return m_tabs;
		final String whereClause = I_AD_Tab.COLUMNNAME_AD_Window_ID+"=?";
		List<MTab> list = new Query(getCtx(),I_AD_Tab.Table_Name,whereClause,trxName)
		.setParameters(getAD_Window_ID())
		.setOrderBy(I_AD_Tab.COLUMNNAME_SeqNo)
		.list();
		//
		m_tabs = new MTab[list.size ()];
		list.toArray (m_tabs);
		return m_tabs;
	}	//	getFields

	
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
		
		if (is_ValueChanged("IsActive") || is_ValueChanged("Name") 
			|| is_ValueChanged("Description") || is_ValueChanged("Help"))
		{
			MMenu[] menues = MMenu.get(getCtx(), "AD_Window_ID=" + getAD_Window_ID(), get_TrxName());
			for (int i = 0; i < menues.length; i++)
			{
				menues[i].setName(getName());
				menues[i].setDescription(getDescription());
				menues[i].setIsActive(isActive());
				menues[i].saveEx();
			}
			
		}
		return success;
	}	//	afterSave

	
	
	//vpj-cd begin e-evolution
	/**
	 * 	get Window ID
	 *	@param String windowName
	 *	@return int retValue
	 */
	public static int getWindow_ID(String windowName) {
		int retValue = 0;
		String SQL = "SELECT AD_Window_ID FROM AD_Window WHERE Name = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL, null);
			pstmt.setString(1, windowName);
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
	//end vpj-cd e-evolution
	
}	//	M_Window
