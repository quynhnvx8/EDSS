
package eone.base.model;

import static eone.base.model.SystemIDs.USER_SYS;
import static eone.base.model.SystemIDs.USER_SYSTEM;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;

public final class MRole extends X_AD_Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4649095180532036099L;

	/**
	 * 	Get Default (Client) Role
	 *	@return role
	 */
	public static MRole [] getDefault ()
	{
		return getDefault (Env.getCtx(), false);
	}	//	getDefault

	
	public static MRole [] getDefault (Properties ctx, boolean reload)
	{
		String AD_Role_IDs = Env.getContext(ctx, "#AD_Role_IDs");
		List<MRole> role = new Query(ctx, X_AD_Role.Table_Name, "AD_Role_ID IN ("+ AD_Role_IDs +")", null)
				.list();
		return (MRole[]) role.toArray();
	}	//	getDefault
	
	private static void setDefaultRole(MRole defaultRole) {
		Env.getCtx().remove(ROLE_KEY);
		Env.getCtx().put(ROLE_KEY, defaultRole);
	}

	private static MRole getDefaultRole() {
		return (MRole) Env.getCtx().get(ROLE_KEY);
	}

	/**
	 * 	Get Role for User
	 * 	@param ctx context
	 * 	@param AD_Role_ID role
	 * 	@param AD_User_ID user
	 * 	@param reload if true forces load
	 *	@return role
	 */
	public synchronized static MRole get (Properties ctx, int AD_Role_ID, int AD_User_ID, boolean reload)
	{
		if (s_log.isLoggable(Level.INFO)) s_log.info("AD_Role_ID=" + AD_Role_ID + ", AD_User_ID=" + AD_User_ID + ", reload=" + reload);
		String key = AD_Role_ID + "_" + AD_User_ID;
		MRole role = (MRole)s_roles.get (key);
		if (role == null || reload)
		{
			role = new MRole (ctx, AD_Role_ID, null);
			s_roles.put (key, role);
			if (AD_Role_ID == 0)
			{
				String trxName = null;
				role.load(trxName);			//	special Handling
			}
			role.setAD_User_ID(AD_User_ID);
			if (s_log.isLoggable(Level.INFO)) s_log.info(role.toString());
		}
		return role;
	}	//	get

	
	/**
	 * 	Get Role (cached).
	 * 	Did not set user - so no access loaded
	 * 	@param ctx context
	 * 	@param AD_Role_ID role
	 *	@return role
	 */
	public static MRole get (Properties ctx, int AD_Role_ID)
	{
		return get(ctx, AD_Role_ID, Env.getAD_User_ID(ctx), false); 
		
	}	//	get
	
	/**
	 * 	Get Roles Of Client
	 *	@param ctx context
	 *	@return roles of client
	 */
	public static MRole[] getOfClient (Properties ctx)
	{
		String sql = "SELECT * FROM AD_Role WHERE AD_Client_ID=?";
		ArrayList<MRole> list = new ArrayList<MRole> ();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, Env.getAD_Client_ID(ctx));
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRole(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MRole[] retValue = new MRole[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfClient
	
	/**
	 * 	Get Roles With where clause
	 *	@param ctx context
	 *	@param whereClause where clause
	 *	@return roles of client
	 */
	public static MRole[] getOf (Properties ctx, String whereClause)
	{
		String sql = "SELECT * FROM AD_Role";
		if (whereClause != null && whereClause.length() > 0)
			sql += " WHERE " + whereClause;
		ArrayList<MRole> list = new ArrayList<MRole> ();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRole(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MRole[] retValue = new MRole[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOf
		
	/** Role/User Cache			*/
	private static CCache<String,MRole> s_roles = new CCache<String,MRole>(Table_Name, 5);
	/** Log						*/ 
	private static CLogger			s_log = CLogger.getCLogger(MRole.class);
	
	/**	Access SQL Read Write		*/
	public static final boolean		SQL_RW = true;
	/**	Access SQL Read Only		*/
	public static final boolean		SQL_RO = false;
	/**	Access SQL Fully Qualified	*/
	public static final boolean		SQL_FULLYQUALIFIED = true;
	/**	Access SQL Not Fully Qualified	*/
	public static final boolean		SQL_NOTQUALIFIED = false;

	/**	The AD_User_ID of the System				*/
	public static final int			SYSTEM_USER_ID = USER_SYSTEM;
	/**	The AD_User_ID of the System Administrator	*/
	public static final int			SYS_USER_ID = USER_SYS;
	
	private static final String ROLE_KEY = "eone.base.model.DefaultRole";
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Role_ID id
	 *	@param trxName transaction
	 */
	public MRole (Properties ctx, int AD_Role_ID, String trxName)
	{
		super (ctx, AD_Role_ID, trxName);
		//	ID=0 == System Administrator
		if (AD_Role_ID == 0)
		{
		//	setName (null);
			setIsCanExport (true);
			setIsShowAcct (false);
			setIsAccessAllOrgs(false);
			setIsUseUserOrgAccess(false);
			setMaxQueryRecords(0);
		}
	}	//	MRole

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRole(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRole

	
	public boolean isQueryRequire (int noRecords)
	{
		if (noRecords < 2)
			return false;
		int max = getMaxQueryRecords();
		return (max > 0 && noRecords > max);
	}	//	isQueryRequire

	/**
	 * 	Over max Query
	 *	@param noRecords records
	 *	@return true if over max query
	 */
	public boolean isQueryMax (int noRecords)
	{
		int max = getMaxQueryRecords();
		return max > 0 && noRecords > max;
	}	//	isQueryMax

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if it can be saved
	 */
	protected boolean beforeSave(boolean newRecord)
	{
		//Chỉ cho phép 1 Role khai báo Admin để điều khiển các role trong các công ty
		if (newRecord || is_ValueChanged(X_AD_Role.COLUMNNAME_IsAdminClient)) {
			Map<String, Object> dataColumn = new HashMap<String, Object>();
			dataColumn.put(COLUMNNAME_AD_Role_ID, getAD_Role_ID());
			dataColumn.put(COLUMNNAME_IsAdminClient, isAdminClient());
			boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_Role_ID, getAD_Role_ID());
			
			if (!check) {
				log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_IsAdminClient);
				return false;
			}
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
		if (newRecord && success)
		{
			//	Add Role to SuperUser
			MUserRoles su = new MUserRoles(getCtx(), SYSTEM_USER_ID, getAD_Role_ID(), get_TrxName());
			su.saveEx();
			//	Add Role to User
			if (getCreatedBy() != SYSTEM_USER_ID && MSysConfig.getBooleanValue(MSysConfig.AUTO_ASSIGN_ROLE_TO_CREATOR_USER, false, getAD_Client_ID()))
			{
				MUserRoles ur = new MUserRoles(getCtx(), getCreatedBy(), getAD_Role_ID(), get_TrxName());
				ur.saveEx();
			}
			updateAccessRecords();
		}
		//
		else if (is_ValueChanged("UserLevel"))
			updateAccessRecords();
		
		//	Default Role changed
		if (getDefaultRole() != null 
			&& getDefaultRole().get_ID() == get_ID())
			setDefaultRole(this);
		return success;
	}	//	afterSave
	
	/**
	 * 	Executed after Delete operation.
	 * 	@param success true if record deleted
	 *	@return true if delete is a success
	 */
	protected boolean afterDelete (boolean success)
	{
		
		return success;
	} 	//	afterDelete

	/**
	 * 	Create Access Records
	 *	@return info
	 */
	public String updateAccessRecords ()
	{
		return "";// updateAccessRecords(true);
	}
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MRole[");
		sb.append(getAD_Role_ID()).append(",").append(getName())
			.append(",").append("")
			.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Extended String Representation
	 *	@param ctx Properties
	 *	@return extended info
	 */
	public String toStringX (Properties ctx)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Msg.translate(ctx, "AD_Role_ID")).append("=").append(getName())
			.append(" - ").append(Msg.translate(ctx, "IsCanExport")).append("=").append(Msg.translate(ctx,String.valueOf(isCanExport())))
			.append(Env.NL).append(Env.NL);
		sb.append(Env.NL);
		
		return sb.toString();
	}	//	toStringX



	/*************************************************************************
	 * 	Access Management
	 ************************************************************************/

	/** User 								*/
	private int						m_AD_User_ID = -1;	

	public void setAD_User_ID(int AD_User_ID)
	{
		m_AD_User_ID = AD_User_ID;
	}	//	setAD_User_ID

	/**
	 * 	Get Logged in user
	 *	@return AD_User_ID user requesting info
	 */
	public int getAD_User_ID()
	{
		return m_AD_User_ID;
	}	//	getAD_User_ID

	
	
		
	public boolean isCanExport (int AD_Table_ID)
	{
		return Env.getContext(getCtx(), "#IsCanExport") == "Y" ? true : false;
	}	//	isCanExport

	public static String addAccessSQL (String SQL, String TableNameIn, boolean fullyQualified, boolean rw, Properties ctx)
	{
		
		StringBuilder retSQL = new StringBuilder();

		//	Cut off last ORDER BY clause
		String orderBy = "";
		int posOrder = SQL.lastIndexOf(" ORDER BY ");
		if (posOrder != -1)
		{
			orderBy = SQL.substring(posOrder);
			retSQL.append(SQL.substring(0, posOrder));
		}
		else
			retSQL.append(SQL);

		//	Parse SQL
		AccessSqlParser asp = new AccessSqlParser(retSQL.toString());
		AccessSqlParser.TableInfo[] ti = asp.getTableInfo(asp.getMainSqlIndex()); 
		//  Do we have to add WHERE or AND
		if (asp.getMainSql().indexOf(" WHERE ") == -1)
			retSQL.append(" WHERE 1=1");
		
		
		//	Use First Table
		String tableName = "";
		String tableRoot = "";
		if (ti.length > 0)
		{
			tableName = ti[0].getSynonym();
			if (tableName.length() == 0)
				tableName = ti[0].getTableName();
			tableRoot = ti[0].getTableName();
		}
		if (TableNameIn != null && !tableName.equals(TableNameIn))
		{
			tableName = TableNameIn;
		}
		
		//Add Quyen truy cap don vi theo login. khi khai bao them don vi truy cap thi phai login lai.
		
		MTable table = MTable.get(ctx, tableRoot);
		
		//Client
		if (!Env.isUserSystem(ctx)) {
			
			//String roleType = Env.getContext(ctx, "#RoleType");
			if (ACCESSLEVEL_Client == table.get_AccessLevel()) {
				String listOrg = Env.getContext(ctx, "#AD_OrgAccess_ID");
				if(listOrg != null && !listOrg.isEmpty())
				{
					retSQL.append(" AND ");
					if (fullyQualified)
						retSQL.append(tableName).append(".");
					retSQL.append("AD_Org_ID IN(0, " + listOrg + ")");
				}
				//Cong ty
				retSQL.append(" AND ");
				
				if ("AD_Client".equalsIgnoreCase(tableName) || "AD_Org".equalsIgnoreCase(tableName)) {
					if (fullyQualified)
						retSQL.append(tableName).append(".");
					retSQL.append("AD_Client_ID IN (0," + Env.getAD_Client_ID(ctx)).append(")");
				} else {
					if (fullyQualified)
						retSQL.append(tableName).append(".");
					retSQL.append("AD_Client_ID = " + Env.getAD_Client_ID(ctx));
				}
				
				
			}
			
			if (ACCESSLEVEL_Special == table.get_AccessLevel()) {
				String listOrg = Env.getContext(ctx, "#AD_OrgAccess_ID");
				if(listOrg != null && !listOrg.isEmpty())
				{
					retSQL.append(" AND ");
					if (fullyQualified)
						retSQL.append(tableName).append(".");
					retSQL.append("AD_Org_ID IN(0, " + listOrg + ")");
				}
				
				retSQL.append(" AND ");
				if (!fullyQualified) {
					retSQL.append("(AD_Client_ID = " + Env.getAD_Client_ID(ctx))
					.append(" OR AD_Client_ID = 0 AND IsAdminClient = 'Y')");
				} else {
					retSQL.append("(")
					.append(tableName).append(".").append("AD_Client_ID = " + Env.getAD_Client_ID(ctx))
					.append(" OR ")
					.append(tableName).append(".").append("AD_Client_ID = 0 ")
					.append(" AND ")
					.append(tableName).append(".").append("IsAdminClient = 'Y')");
				}
			}
		}
		
		
		retSQL.append(orderBy);
		return retSQL.toString();
	}	//	addAccessSQL

	
	
	@Override
	protected boolean beforeDelete() {
		if (isAdminClient()) {
			//Vai trò này dùng cho Account Admin các công ty. Không xóa vai trò này.
			log.saveError("Error!", "Bạn không được xóa vai trò này !");
			return false;
		}
		return super.beforeDelete();
	}


	public boolean canUpdate (int AD_Client_ID, int AD_Org_ID, 
		int AD_Table_ID, int Record_ID, boolean createError)
	{
		return true;
		
	}	//	canUpdate

	
	class OrgAccess implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4880665261978385315L;


		/**
		 * 	Org Access constructor
		 *	@param ad_Client_ID client
		 *	@param ad_Org_ID org
		 *	@param readonly r/o
		 */
		public OrgAccess (int ad_Client_ID, int ad_Org_ID, boolean readonly)
		{
			this.AD_Client_ID = ad_Client_ID;
			this.AD_Org_ID = ad_Org_ID;
			this.readOnly = readonly;
		}
		/** Client				*/
		public int AD_Client_ID = 0;
		/** Organization		*/
		public int AD_Org_ID = 0;
		/** Read Only			*/
		public boolean readOnly = true;
		
		
		/**
		 * 	Equals
		 *	@param obj object to compare
		 *	@return true if equals
		 */
		public boolean equals (Object obj)
		{
			if (obj != null && obj instanceof OrgAccess)
			{
				OrgAccess comp = (OrgAccess)obj;
				return comp.AD_Client_ID == AD_Client_ID 
					&& comp.AD_Org_ID == AD_Org_ID;
			}
			return false;
		}	//	equals
		
		
		/**
		 * 	Hash Code
		 *	@return hash Code
		 */
		public int hashCode ()
		{
			return (AD_Client_ID*7) + AD_Org_ID;
		}	//	hashCode
		
		/**
		 * 	Extended String Representation
		 *	@return extended info
		 */
		public String toString ()
		{
			String clientName = "System";
			if (AD_Client_ID != 0)
				clientName = MClient.get(getCtx(), AD_Client_ID).getName();
			String orgName = "*";
			if (AD_Org_ID != 0)
				orgName = MOrg.get(getCtx(), AD_Org_ID).getName();
			StringBuilder sb = new StringBuilder();
			sb.append(Msg.translate(getCtx(), "AD_Client_ID")).append("=")
				.append(clientName).append(" - ")
				.append(Msg.translate(getCtx(), "AD_Org_ID")).append("=")
				.append(orgName);
			if (readOnly)
				sb.append(" r/o");
			return sb.toString();
		}	//	toString

	}	//	OrgAccess
	
	


	//Quynhnv: 25/09/2020: Phan quyen cho phep keo tha cay menu
	public static boolean isDragDrop()
	{
		return "Y".equalsIgnoreCase(Env.getContext(Env.getCtx(), "#IsDragDropMenu"));		
	}

}	//	MRole
