package eone.base.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Evaluatee;

public class GridTabVO implements Evaluatee, Serializable
{
	private static final long serialVersionUID = 9091214632840854495L;

	public static GridTabVO create (GridWindowVO wVO, int TabNo, ResultSet rs, 
		boolean isRO, boolean onlyCurrentRows)
	{
		CLogger.get().config("#" + TabNo);

		GridTabVO vo = new GridTabVO (wVO.ctx, wVO.WindowNo);
		vo.AD_Window_ID = wVO.AD_Window_ID;
		vo.TabNo = TabNo;
		//
		if (!loadTabDetails(vo, rs))
			return null;

		if (isRO)
		{
			CLogger.get().fine("Tab is ReadOnly");
			vo.IsReadOnly = true;
		}
		vo.onlyCurrentRows = onlyCurrentRows;

		//  Create Fields
		if (vo.IsSortTab)
		{
			vo.Fields = new ArrayList<GridFieldVO>();	//	dummy
		}
		
		return vo;
	}	//	create

	/**
	 * 	Load Tab Details from rs into vo
	 * 	@param vo Tab value object
	 *	@param rs ResultSet from AD_Tab_v/t
	 * 	@return true if read ok
	 */
	private static boolean loadTabDetails (GridTabVO vo, ResultSet rs)
	{
		boolean showTrl = "Y".equals(Env.getContext(vo.ctx, "#ShowTrl"));
		try
		{
			vo.AD_Tab_ID = rs.getInt("AD_Tab_ID");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Tab_ID, String.valueOf(vo.AD_Tab_ID));
			vo.AD_Tab_UU = rs.getString("AD_Tab_UU");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Tab_UU, vo.AD_Tab_UU);
			
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_Name, vo.Name);

			vo.Name = rs.getString("Name");
			
			//	Translation Tab	**
			if (rs.getString("IsTranslationTab").equals("Y"))
			{
				//	Document Translation
				vo.TableName = rs.getString("TableName");
				if (!Env.isBaseTranslation(vo.TableName))
					showTrl = false;
				if (!showTrl)
				{
					CLogger.get().config("TrlTab Not displayed - AD_Tab_ID=" 
						+ vo.AD_Tab_ID + "=" + vo.Name + ", Table=" + vo.TableName
						+ ", BaseTrl=" + Env.isBaseTranslation(vo.TableName));
					return false;
				}
			}
			
			//	DisplayLogic
			vo.DisplayLogic = rs.getString("DisplayLogic");
			
			if (rs.getString("IsSignFlow").equals("Y"))
				vo.IsSignFlow = true;
			
			//	Access Level
			vo.AccessLevel = rs.getString("AccessLevel");
			
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AccessLevel, vo.AccessLevel);

			//	Table Access
			vo.AD_Table_ID = rs.getInt("AD_Table_ID");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Table_ID, String.valueOf(vo.AD_Table_ID));
			
			vo.AD_Table_UU = rs.getString("AD_Table_UU");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Table_UU, vo.AD_Table_UU);
			
			if (rs.getString("IsReadOnly").equals("Y"))
				vo.IsReadOnly = true;
			vo.ReadOnlyLogic = rs.getString("ReadOnlyLogic");
			
			vo.ReadOnlyLogicRecord = rs.getString("ReadOnlyLogicRecord");
			
			vo.DeleteLogic = rs.getString("DeleteLogic");
			
			if (rs.getString("IsInsertRecord").equals("N"))
				vo.IsInsertRecord = false;
			
			//
			vo.Description = rs.getString("Description");
			if (vo.Description == null)
				vo.Description = "";
			
			vo.Help = rs.getString("Help");
			if (vo.Help == null)
				vo.Help = "";
			
			if (rs.getString("IsSingleRow").equals("Y"))
				vo.IsSingleRow = true;
			
			if (rs.getString("HasTree").equals("Y"))
				vo.HasTree = true;

			vo.TreeDisplayedOn = rs.getString("TreeDisplayedOn");

			vo.AD_Table_ID = rs.getInt("AD_Table_ID");
			vo.TableName = rs.getString("TableName");
			if (rs.getString("IsView").equals("Y"))
				vo.IsView = true;
			vo.AD_Column_ID = rs.getInt("AD_Column_ID");   //  Primary Link Column
			vo.Parent_Column_ID = rs.getInt("Parent_Column_ID");   // Parent tab link column

			if (rs.getString("IsSecurityEnabled").equals("Y"))
				vo.IsSecurityEnabled = true;
			if (rs.getString("IsDeleteable").equals("Y"))
				vo.IsDeleteable = true;
			if (rs.getString("IsHighVolume").equals("Y"))
				vo.IsHighVolume = true;

			vo.CommitWarning = rs.getString("CommitWarning");
			if (vo.CommitWarning == null)
				vo.CommitWarning = "";
			vo.WhereClause = rs.getString("WhereClause");
			if (vo.WhereClause == null)
				vo.WhereClause = "";
			if (vo.WhereClause.indexOf("=null")>0)				
				vo.WhereClause = vo.WhereClause.replaceAll("=null", " IS NULL ");
			if (vo.WhereClause.trim().length() > 0) {
				vo.WhereClause = "("+vo.WhereClause+")";
			}

			vo.OrderByClause = rs.getString("OrderByClause");
			if (vo.OrderByClause == null)
				vo.OrderByClause = "";

			vo.AD_Process_ID = rs.getInt("AD_Process_ID");
			if (rs.wasNull())
				vo.AD_Process_ID = 0;
			vo.AD_Image_ID = rs.getInt("AD_Image_ID");
			if (rs.wasNull())
				vo.AD_Image_ID = 0;
			vo.Included_Tab_ID = rs.getInt("Included_Tab_ID");
			if (rs.wasNull())
				vo.Included_Tab_ID = 0;
			//
			vo.TabLevel = rs.getInt("TabLevel");
			if (rs.wasNull())
				vo.TabLevel = 0;
			//
			vo.IsSortTab = rs.getString("IsSortTab").equals("Y");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_IsSortTab, vo.IsSortTab ? "Y" : "N");
			if (vo.IsSortTab)
			{
				vo.AD_ColumnSortOrder_ID = rs.getInt("AD_ColumnSortOrder_ID");
				vo.AD_ColumnSortYesNo_ID = rs.getInt("AD_ColumnSortYesNo_ID");
			}
			//
			//	Replication Type - set R/O if Reference
			try
			{
				int index = rs.findColumn ("ReplicationType");
				vo.ReplicationType = rs.getString (index);
				if ("R".equals(vo.ReplicationType))
					vo.IsReadOnly = true;
			}
			catch (Exception e)
			{
			}
		}
		catch (SQLException ex)
		{
			CLogger.get().log(Level.SEVERE, "", ex);
			return false;
		}
		
		return true;
	}	//	loadTabDetails


	/**************************************************************************
	 *  Create Tab Fields
	 *  @param mTabVO tab value object
	 *  @return true if fields were created
	 */
	private static boolean createFields (GridTabVO mTabVO)
	{
		//local only or remote fail for vpn profile
		mTabVO.Fields = new ArrayList<GridFieldVO>();

		String sql = GridFieldVO.getSQL(mTabVO.ctx);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, mTabVO.AD_Tab_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				GridFieldVO voF = GridFieldVO.create (mTabVO.ctx, 
					mTabVO.WindowNo, mTabVO.TabNo, 
					mTabVO.AD_Window_ID, mTabVO.AD_Tab_ID, 
					mTabVO.IsReadOnly, rs);
				if (voF != null)
					mTabVO.Fields.add(voF);
			}
		}
		catch (Exception e)
		{
			CLogger.get().log(Level.SEVERE, "", e);
			return false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
		
		Collections.sort(mTabVO.Fields, new GridFieldVO.SeqNoComparator());
		mTabVO.initFields = true;
		
		return mTabVO.Fields.size() != 0;
	}   //  createFields
	
	/**
	 *  Return the SQL statement used for the MTabVO.create
	 *  @param ctx context
	 *  @return SQL SELECT String
	 */
	protected static String getSQL (Properties ctx)
	{
		String ASPFilter = "";
		
		StringBuilder sql;
		if (!Env.isBaseLanguage(ctx, "AD_Window")) {
			sql = new StringBuilder( "SELECT * FROM AD_Tab_vt WHERE AD_Window_ID=?");
			sql.append(" AND AD_Language='" + Env.getAD_Language(ctx) + "'")
				.append(ASPFilter);
			sql.append(" ORDER BY SeqNo");
		}else{
			sql =  new StringBuilder("SELECT * FROM AD_Tab_v WHERE AD_Window_ID=?");
			sql.append(ASPFilter) ;
			sql.append(" ORDER BY SeqNo");
		}
		return sql.toString();
	}   //  getSQL
	
	
	/**************************************************************************
	 *  Private constructor - must use Factory
	 *  @param Ctx context
	 *  @param windowNo window
	 */
	private GridTabVO (Properties Ctx, int windowNo)
	{
		ctx = Ctx;
		WindowNo = windowNo;
	}   //  MTabVO

	/** Context - replicated    */
	public  Properties      ctx;
	/** Window No - replicated  */
	public  int				WindowNo;
	/** AD Window - replicated  */
	public  int             AD_Window_ID;

	/** Tab No (not AD_Tab_ID) 0.. */
	public  int				TabNo;

	/**	Tab	ID			*/
	public	int		    AD_Tab_ID;
	/** Tab UUID		*/
	public  String		AD_Tab_UU;
	/** Name			*/
	public	String	    Name = "";
	/** Description		*/
	public	String	    Description = "";
	/** Help			*/
	public	String	    Help = "";
	/** Single Row		*/
	public	boolean	    IsSingleRow = false;
	/** Read Only		*/
	public  boolean     IsReadOnly = false;
	/** Insert Record	*/
	public 	boolean		IsInsertRecord = true;
	/** Tree			*/
	public  boolean	    HasTree = false;
	/** Tree displayed on	*/
	public  String	    TreeDisplayedOn = "";
	
	/** Table			*/
	public  int		    AD_Table_ID;
	/** Table UUID		*/
	public  String		AD_Table_UU;
	/** Primary Link Column   */
	public  int		    AD_Column_ID = 0;
	/** Parent Tab Link Column */
	public	int			Parent_Column_ID = 0;
	/** Table Name		*/
	public  String	    TableName;
	/** Table is View	*/
	public  boolean     IsView = false;
	/** Table Access Level	*/
	public  String	    AccessLevel;
	/** Security		*/
	public  boolean	    IsSecurityEnabled = false;
	/** Table Deleteable	*/
	public  boolean	    IsDeleteable = false;
	/** Table High Volume	*/
	public  boolean     IsHighVolume = false;
	/** Process			*/
	public	int		    AD_Process_ID = 0;
	/** Process UUID		*/
	public  String		AD_Process_UU;
	/** Commit Warning	*/
	public  String	    CommitWarning;
	/** Where			*/
	public  String	    WhereClause;
	/** Order by		*/
	public  String      OrderByClause;
	/** Tab Read Only	*/
	public  String      ReadOnlyLogic;
	public  String      ReadOnlyLogicRecord;
	
	public  String      DeleteLogic;
	/** Tab Display		*/
	public  String      DisplayLogic;
	
	public  boolean      IsSignFlow;
	/** Level			*/
	public  int         TabLevel = 0;
	/** Image			*/
	public int          AD_Image_ID = 0;
	/** Included Tab	*/
	public int          Included_Tab_ID = 0;
	/** Replication Type	*/
	public String		ReplicationType = "L";

	/** Sort Tab			*/
	public boolean		IsSortTab = false;
	/** Column Sort			*/
	public int			AD_ColumnSortOrder_ID = 0;
	/** Column Displayed	*/
	public int			AD_ColumnSortYesNo_ID = 0;

	/** Only Current Rows - derived	*/
	public  boolean     onlyCurrentRows = true;
	/**	Only Current Days - derived	*/
	public int			onlyCurrentDays = 0;

	/** Fields contain MFieldVO entities    */
	private ArrayList<GridFieldVO>	Fields = null;

	private boolean initFields = false;
	
	public ArrayList<GridFieldVO> getFields()
	{
		if (!initFields) createFields(this);
		return Fields;
	}
	
	/**
	 *  Set Context including contained elements
	 *  @param newCtx new context
	 */
	public void setCtx (Properties newCtx)
	{
		ctx = newCtx;
		if (Fields != null)
		{
			for (int i = 0; i < Fields.size() ; i++)
			{
				GridFieldVO field = (GridFieldVO)Fields.get(i);
				field.setCtx(newCtx);
			}
		}
	}   //  setCtx
	
	/**
	 * 	Get Variable Value (Evaluatee)
	 *	@param variableName name
	 *	@return value
	 */
	public String get_ValueAsString (String variableName)
	{
		return Env.getContext (ctx, WindowNo, variableName, false);	// not just window
	}	//	get_ValueAsString

	/**
	 * 	Clone
	 * 	@param Ctx context
	 * 	@param windowNo no
	 *	@return MTabVO or null
	 */
	protected GridTabVO clone(Properties Ctx, int windowNo)
	{
		GridTabVO clone = new GridTabVO(Ctx, windowNo);
		clone.AD_Window_ID = AD_Window_ID;
		clone.TabNo = TabNo;
		Env.setContext(Ctx, windowNo, clone.TabNo, GridTab.CTX_AD_Tab_ID, String.valueOf(clone.AD_Tab_ID));
		//
		clone.AD_Tab_ID = AD_Tab_ID;
		clone.AD_Tab_UU = AD_Tab_UU;
		clone.Name = Name;
		Env.setContext(Ctx, windowNo, clone.TabNo, GridTab.CTX_Name, clone.Name);
		clone.Description = Description;
		clone.Help = Help;
		clone.IsSingleRow = IsSingleRow;
		clone.IsReadOnly = IsReadOnly;
		clone.IsInsertRecord = IsInsertRecord;
		clone.HasTree = HasTree;
		clone.TreeDisplayedOn = TreeDisplayedOn;
		clone.AD_Table_ID = AD_Table_ID;
		clone.AD_Column_ID = AD_Column_ID;
		clone.Parent_Column_ID = Parent_Column_ID;
		clone.TableName = TableName;
		clone.IsView = IsView;
		clone.AccessLevel = AccessLevel;
		clone.IsSecurityEnabled = IsSecurityEnabled;
		clone.IsDeleteable = IsDeleteable;
		clone.IsHighVolume = IsHighVolume;
		clone.AD_Process_ID = AD_Process_ID;
		clone.CommitWarning = CommitWarning;
		clone.WhereClause = WhereClause;
		clone.OrderByClause = OrderByClause;
		clone.ReadOnlyLogic = ReadOnlyLogic;
		clone.ReadOnlyLogicRecord = ReadOnlyLogicRecord;
		
		clone.DeleteLogic = DeleteLogic;
		clone.DisplayLogic = DisplayLogic;
		clone.IsSignFlow = IsSignFlow;
		clone.TabLevel = TabLevel;
		clone.AD_Image_ID = AD_Image_ID;
		clone.Included_Tab_ID = Included_Tab_ID;
		clone.ReplicationType = ReplicationType;
		Env.setContext(Ctx, windowNo, clone.TabNo, GridTab.CTX_AccessLevel, clone.AccessLevel);
		Env.setContext(Ctx, windowNo, clone.TabNo, GridTab.CTX_AD_Table_ID, String.valueOf(clone.AD_Table_ID));

		//
		clone.IsSortTab = IsSortTab;
		clone.AD_ColumnSortOrder_ID = AD_ColumnSortOrder_ID;
		clone.AD_ColumnSortYesNo_ID = AD_ColumnSortYesNo_ID;
		//  Derived
		clone.onlyCurrentRows = true;
		clone.onlyCurrentDays = 0;

		clone.Fields = new ArrayList<GridFieldVO>();
		for (int i = 0; i < Fields.size(); i++)
		{
			GridFieldVO field = Fields.get(i);
			GridFieldVO cloneField = field.clone(Ctx, windowNo, TabNo, 
				AD_Window_ID, AD_Tab_ID, IsReadOnly);
			if (cloneField == null)
				return null;
			clone.Fields.add(cloneField);
		}
		
		return clone;
	}	//	clone

	/**
	 * @return the initFields
	 */
	public boolean isInitFields() {
		return initFields;
	}

}   //  MTabVO