package org.compiere.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import eone.base.model.MBPartner;
import eone.base.model.MRole;
import eone.base.model.MSysConfig;
import eone.base.model.MSystem;
import eone.base.model.MUser;
import eone.base.model.MUserPreference;
import eone.base.model.Query;
import eone.base.model.X_C_BPartner;


public class Login
{
	private String loginErrMsg;
	private boolean isPasswordExpired;

	public String getLoginErrMsg() {
		return loginErrMsg;
	}
	
	public boolean isPasswordExpired() {
		return isPasswordExpired;
	}

	
	public static boolean isJavaOK (boolean isClient)
	{
		String jVersion = System.getProperty("java.version");
		if (jVersion.startsWith("1.8.0") || jVersion.startsWith("9.") || jVersion.startsWith("10.") || jVersion.startsWith("11."))
			return true;

		StringBuilder msg = new StringBuilder();
		msg.append(System.getProperty("java.vm.name")).append(" - ").append(jVersion);
		msg.append("  <>  1.8.0 | 9 | 10 | 11");
		//
		if (isClient)
			JOptionPane.showMessageDialog(null, msg.toString(),
				org.compiere.EOne.getName() + " - Java Version Check",
				JOptionPane.ERROR_MESSAGE);
		else
			log.severe(msg.toString());
		return false;
	}   //  isJavaOK

	

	public Login (Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException("Context missing");
		m_ctx = ctx;
	}	//	Login
	
	/**	Logger				*/
	private static CLogger log = CLogger.getCLogger(Login.class);
	/** Context				*/
	private Properties 		m_ctx = null;
	

	public String validateLogin (KeyNamePair org)
	{
		int AD_Client_ID = Env.getAD_Client_ID(m_ctx);
		int AD_Role_ID = Env.getAD_Role_ID(m_ctx);

		if (! MRole.get(m_ctx, AD_Role_ID).isAccessAdvanced()) {
			if (MSysConfig.getBooleanValue(MSysConfig.SYSTEM_IN_MAINTENANCE_MODE, false, 0))
				return Msg.getMsg(m_ctx, "SystemInMaintenance");
			if (AD_Client_ID != 0 && MSysConfig.getBooleanValue(MSysConfig.SYSTEM_IN_MAINTENANCE_MODE, false, AD_Client_ID))
				return Msg.getMsg(m_ctx, "SystemInMaintenance");
		}

		return null;
	}	//	validateLogin
	

	public String loadPreferences (KeyNamePair org)
	{
		if (log.isLoggable(Level.INFO)) log.info("Org: " + org.toStringX());

		if (m_ctx == null || org == null)
			throw new IllegalArgumentException("Required parameter missing");
		if (Env.getContext(m_ctx,Env.AD_CLIENT_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_Client_ID");
		if (Env.getContext(m_ctx,Env.AD_USER_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_User_ID");
		if (Env.getContext(m_ctx,Env.AD_ROLE_ID).length() == 0)
			throw new UnsupportedOperationException("Missing Context #AD_Role_ID");

		//  Org Info - assumes that it is valid
		Env.setContext(m_ctx, Env.AD_ORG_ID, org.getKey());
		Env.setContext(m_ctx, Env.AD_ORG_NAME, org.getName());
		Ini.setProperty(Ini.P_ORG, org.getName());

				
		//	Other
		loadUserPreferences();
		
		Env.setContext(m_ctx, "#ShowTrl", Ini.getProperty(Ini.P_SHOW_TRL));
		

		String retValue = "";

		//	Other Settings
		Env.setContext(m_ctx, "#YYYY", "Y");
		Env.setContext(m_ctx, "#StdPrecision", 2);

		//	AccountSchema Info (first)
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{

			
			sql = "SELECT Attribute, Value, AD_Window_ID, AD_Process_ID, AD_InfoWindow_ID, PreferenceFor "
				+ "FROM AD_Preference "
				+ "WHERE AD_Client_ID IN (0, @#AD_Client_ID@)"
				+ " AND AD_Org_ID IN (0, @#AD_Org_ID@)"
				+ " AND (AD_User_ID IS NULL OR AD_User_ID=0 OR AD_User_ID=@#AD_User_ID@)"
				+ " AND IsActive='Y' "
				+ "ORDER BY Attribute, AD_Client_ID, AD_User_ID DESC, AD_Org_ID";
				//	the last one overwrites - System - Client - User - Org - Window
			sql = Env.parseContext(m_ctx, 0, sql, false);
			if (sql.length() == 0)
				log.log(Level.SEVERE, "loadPreferences - Missing Environment");
			else
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					int AD_Window_ID = rs.getInt(3);
					boolean isAllWindow = rs.wasNull(); 
					int AD_Process_ID = rs.getInt(4);
					int AD_InfoWindow_ID = rs.getInt(5);
					String PreferenceFor = rs.getString(6);
					String at = "";
					
					// preference for window
					if ("W".equals(PreferenceFor)){
					  if (isAllWindow)
						at = "P|" + rs.getString(1);
					  else
						at = "P" + AD_Window_ID + "|" + rs.getString(1);
					}else if ("P".equals(PreferenceFor)){ // preference for processs
						// when apply for all window or all process format is "P0|0|m_Attribute; 
						at = "P" + AD_Window_ID + "|" + AD_InfoWindow_ID + "|" + AD_Process_ID + "|" + rs.getString(1);
					}else if ("I".equals(PreferenceFor)){ // preference for infoWindow
						at = "P" + AD_Window_ID + "|" + AD_InfoWindow_ID + "|" + rs.getString(1);
					}
					
					String va = rs.getString(2);
					Env.setContext(m_ctx, at, va);
				}
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}

			//	Default Values
			if (log.isLoggable(Level.INFO)) log.info("Default Values ...");
			sql = "SELECT t.TableName, c.ColumnName "
				+ "FROM AD_Column c "
				+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE c.IsKey='Y' AND t.IsActive='Y' AND t.IsView='N'"
				+ " AND EXISTS (SELECT * FROM AD_Column cc "
				+ " WHERE ColumnName = 'IsDefault' AND t.AD_Table_ID=cc.AD_Table_ID AND cc.IsActive='Y')";
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
				loadDefault (rs.getString(1), rs.getString(2));
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "loadPreferences", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	loadPreferences
	
	/**
	 * Load preferences based on user
	 */
	public void loadUserPreferences(){
		MUserPreference userPreference = MUserPreference.getUserPreference(Env.getAD_User_ID(m_ctx), Env.getAD_Client_ID(m_ctx));
		userPreference.fillPreferences();
	}// loadUserPreferences

	/**
	 *	Load Default Value for Table into Context.
	 *  @param TableName table name
	 *  @param ColumnName column name
	 */
	private void loadDefault (String TableName, String ColumnName)
	{
		if (TableName.startsWith("AD_Window")
			|| TableName.startsWith("AD_PrintFormat")
			|| TableName.startsWith("M_Locator") )
			return;
		String value = null;
		//
		String sql = "SELECT " + ColumnName + " FROM " + TableName	//	most specific first
			+ " WHERE IsDefault='Y' AND IsActive='Y' ORDER BY AD_Client_ID DESC, AD_Org_ID DESC";
		sql = MRole.addAccessSQL(sql, TableName, MRole.SQL_NOTQUALIFIED, MRole.SQL_RO, m_ctx);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next())
				value = rs.getString(1);
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, TableName + " (" + sql + ")", e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	Set Context Value
		if (value != null && value.length() != 0)
		{
			if (TableName.equals("C_DocType"))
				Env.setContext(m_ctx, "#C_DocTypeTarget_ID", value);
			else
				Env.setContext(m_ctx, "#" + ColumnName, value);
		}
	}	//	loadDefault
	
	
	public KeyNamePair[] getClients(String app_user, String app_pwd, String roleTypes) {
		if (log.isLoggable(Level.INFO)) log.info("User=" + app_user);

		if (Util.isEmpty(app_user))
		{
			log.warning("No Apps User");
			return null;
		}

		//	Authentication
		boolean authenticated = false;
		MSystem system = MSystem.get(m_ctx);
		if (system == null)
			throw new IllegalStateException("No System Info");

		if (app_pwd == null || app_pwd.length() == 0)
		{
			log.warning("No Apps Password");
			return null;
		}
		
		loginErrMsg = null;
		isPasswordExpired = false;

		
		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
		KeyNamePair[] retValue = null;
		ArrayList<KeyNamePair> clientList = new ArrayList<KeyNamePair>();
		ArrayList<Integer> clientsValidated = new ArrayList<Integer>();

		StringBuilder where = new StringBuilder("Password IS NOT NULL AND ");
		if (email_login)
			where.append("EMail=?");
		else
			where.append("Value=?");
		where.append(" AND")
				.append(" EXISTS (SELECT * FROM AD_User_Roles ur")
				.append("         INNER JOIN AD_Role r ON (ur.AD_Role_ID=r.AD_Role_ID)")
				.append("         WHERE ur.AD_User_ID=AD_User.AD_User_ID AND ur.IsActive='Y' AND r.IsActive='Y'");
		
		where.append(") AND ")
				.append(" EXISTS (SELECT * FROM AD_Client c")
				.append("         WHERE c.AD_Client_ID=AD_User.AD_Client_ID")
				.append("         AND c.IsActive='Y') AND ")
				.append(" AD_User.IsActive='Y'");
		
		List<MUser> users = new Query(m_ctx, MUser.Table_Name, where.toString(), null)
			.setParameters(app_user)
			.setOrderBy(MUser.COLUMNNAME_AD_User_ID)
			.list();
		
		if (users.size() == 0) {
			log.saveError("UserPwdError", app_user, false);
			return null;
		}

		int MAX_ACCOUNT_LOCK_MINUTES = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_MAX_ACCOUNT_LOCK_MINUTES, 0);
		int MAX_INACTIVE_PERIOD_DAY = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_MAX_INACTIVE_PERIOD_DAY, 0);
		int MAX_PASSWORD_AGE = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_MAX_PASSWORD_AGE_DAY, 0);
		long now = new Date().getTime();
		for (MUser user : users) {
			if (MAX_ACCOUNT_LOCK_MINUTES > 0 && user.isLocked() && user.getDateAccountLocked() != null)
			{
				long minutes = (now - user.getDateAccountLocked().getTime()) / (1000 * 60);
				if (minutes > MAX_ACCOUNT_LOCK_MINUTES)
				{
					boolean inactive = false;
					if (MAX_INACTIVE_PERIOD_DAY > 0 && user.getDateLastLogin() != null && !user.isNoExpire())
					{
						long days = (now - user.getDateLastLogin().getTime()) / (1000 * 60 * 60 * 24);
						if (days > MAX_INACTIVE_PERIOD_DAY)
							inactive = true;
					}
					
					if (!inactive)
					{
						user.setIsLocked(false);
						user.setDateAccountLocked(null);
						user.setFailedLoginCount(0);
						Env.setContext(Env.getCtx(), "#AD_Client_ID", user.getAD_Client_ID());
						if (!user.save())
							log.severe("Failed to unlock user account");
					}
				}					
			}
			
			if (MAX_INACTIVE_PERIOD_DAY > 0 && !user.isLocked() && user.getDateLastLogin() != null && !user.isNoExpire())
			{
				long days = (now - user.getDateLastLogin().getTime()) / (1000 * 60 * 60 * 24);
				if (days > MAX_INACTIVE_PERIOD_DAY)
				{
					user.setIsLocked(true);
					user.setDateAccountLocked(new Timestamp(now));
					Env.setContext(Env.getCtx(), "#AD_Client_ID", user.getAD_Client_ID());
					if (!user.save())
						log.severe("Failed to lock user account");
				}
			}
		}
		
		boolean validButLocked = false;
		for (MUser user : users) {
			if (clientsValidated.contains(user.getAD_Client_ID())) {
				log.severe("Two users with password with the same name/email combination on same tenant: " + app_user);
				return null;
			}
			clientsValidated.add(user.getAD_Client_ID());
			boolean valid = false;
			// authenticated by ldap
			if (authenticated) {
				valid = true;
			} else {
				if (!system.isLDAP() || Util.isEmpty(user.getLDAPUser())) {
					if (hash_password) {
						//valid = user.authenticateHash(app_pwd);
						valid = user.authenticateHash(app_pwd, app_user);
					} else {
						// password not hashed
						valid = user.getPassword() != null && user.getPassword().equals(app_pwd);
					}			
				}
			}
			
			if (valid ) {
				if (user.isLocked())
				{
					validButLocked = true;
					continue;
				}
				
				if (user.isExpired())
					isPasswordExpired = true;
				else if (MAX_PASSWORD_AGE > 0)
				{
					if (user.getDatePasswordChanged() == null)
						user.setDatePasswordChanged(new Timestamp(now));
					
					long days = (now - user.getDatePasswordChanged().getTime()) / (1000 * 60 * 60 * 24);
					if (days > MAX_PASSWORD_AGE)
					{
						user.setIsExpired(true);
						isPasswordExpired = true;
					}
				}
												
				StringBuilder sql= new StringBuilder("SELECT  DISTINCT cli.AD_Client_ID, cli.Name, u.AD_User_ID, u.Name");
			      sql.append(" FROM AD_User_Roles ur")
                   .append(" INNER JOIN AD_User u on (ur.AD_User_ID=u.AD_User_ID)")
                   .append(" INNER JOIN AD_Client cli on (ur.AD_Client_ID=cli.AD_Client_ID)")
                   .append(" WHERE ur.IsActive='Y'")
                   .append(" AND u.IsActive='Y'")
                   .append(" AND cli.IsActive='Y'")
                   .append(" AND ur.AD_User_ID=? ORDER BY cli.Name");
			      PreparedStatement pstmt=null;
			      ResultSet rs=null;
			      try{
			    	  pstmt=DB.prepareStatement(sql.toString(),null);
			    	  pstmt.setInt(1, user.getAD_User_ID());
			    	  rs=pstmt.executeQuery();
		
			    	  while (rs.next() && rs!=null){
			    		  int AD_Client_ID=rs.getInt(1);
			    		  String Name=rs.getString(2);
			    		  KeyNamePair p = new KeyNamePair(AD_Client_ID,Name);
				          clientList.add(p);
			    	  }
			        }catch (SQLException ex)
					{
						log.log(Level.SEVERE, sql.toString(), ex);
						retValue = null;
					}
					finally
					{
						DB.close(rs, pstmt);
						rs = null; pstmt = null;
					}			  
			}
		}
		if (clientList.size() > 0)
			authenticated=true;

		if (authenticated) {
			if (Ini.isClient())
			{
				if (MSystem.isSwingRememberUserAllowed())
					Ini.setProperty(Ini.P_UID, app_user);
				else
					Ini.setProperty(Ini.P_UID, "");
				if (Ini.isPropertyBool(Ini.P_STORE_PWD) && MSystem.isSwingRememberPasswordAllowed())
					Ini.setProperty(Ini.P_PWD, app_pwd);

			}
			retValue = new KeyNamePair[clientList.size()];
			clientList.toArray(retValue);
			if (log.isLoggable(Level.FINE)) log.fine("User=" + app_user + " - roles #" + retValue.length);
			
			for (MUser user : users) 
			{
				user.setFailedLoginCount(0);
				user.setDateLastLogin(new Timestamp(now));
				Env.setContext(Env.getCtx(), "#AD_Client_ID", user.getAD_Client_ID());
				if (!user.save())
					log.severe("Failed to update user record with date last login (" + user.getName() + " / clientID = " + user.getAD_Client_ID() + ")");
			}
		}
		else if (validButLocked)
		{
			// User account ({0}) is locked, please contact the system administrator
			loginErrMsg = Msg.getMsg(m_ctx, "UserAccountLocked", new Object[] {app_user});
		}
		else 
		{
			boolean foundLockedAccount = false;
			for (MUser user : users) 
			{
				if (user.isLocked())
				{
					foundLockedAccount = true;
					continue;
				}
				
				int count = user.getFailedLoginCount() + 1;
				
				boolean reachMaxAttempt = false;
				int MAX_LOGIN_ATTEMPT = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_MAX_LOGIN_ATTEMPT, 0);
				if (MAX_LOGIN_ATTEMPT > 0 && count >= MAX_LOGIN_ATTEMPT)
				{
					// Reached the maximum number of login attempts, user account ({0}) is locked
					loginErrMsg = Msg.getMsg(m_ctx, "ReachedMaxLoginAttempts", new Object[] {app_user});
					reachMaxAttempt = true;
				}
				else if (MAX_LOGIN_ATTEMPT > 0)
				{
					if (count == MAX_LOGIN_ATTEMPT -1){ 
						// Invalid User ID or Password (Login Attempts: {0} / {1})
						loginErrMsg = Msg.getMsg(m_ctx, "FailedLoginAttempt", new Object[] {count, MAX_LOGIN_ATTEMPT});
						reachMaxAttempt = false;
					}else{
						loginErrMsg = Msg.getMsg(m_ctx,"FailedLogin", true);
					}
				
				}
				else
				{
					reachMaxAttempt = false;
				}
				
				user.setFailedLoginCount(count);
				user.setIsLocked(reachMaxAttempt);
				user.setDateAccountLocked(user.isLocked() ? new Timestamp(now) : null);
				if (!user.save())
					log.severe("Failed to update user record with increase failed login count");
			}
			
			if (loginErrMsg == null && foundLockedAccount)
			{
				// User account ({0}) is locked, please contact the system administrator
				loginErrMsg = Msg.getMsg(m_ctx, "UserAccountLocked", new Object[] {app_user});				
			}
		}
		return retValue;
	}

	
   
    public void loadContextElementValue(int AD_Client_ID, ItemMandatory itemMan) {
		String sql = 
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'AST' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailAsset = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'CTN' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailConstruction = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'CTP' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailConstructionPharse = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'CTT' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailContract = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'CTS' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailContractSchedule = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'WAE' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailWarehouse = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'TCT' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailTypeCost = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'TRE' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailTypeRevenue = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'PRJ' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailProject = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'PRP' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailProjectPharse = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'BAC' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsBankAccount = 'Y' "+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'PRD' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailProduct = 'Y'"+
				" union all "+
				" select COALESCE(string_agg(c_elementvalue_id::text,','),'') as Ids, 'BPN' as Name "+
				" from c_elementvalue where AD_Client_ID = ? And IsDetailBPartner = 'Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			if (DB.isOracle()) {
				sql = sql.replaceAll("COALESCE(string_agg(c_elementvalue_id::text,','),'')", 
						"COALESCE(listagg(c_elementvalue_id,',') WITHIN GROUP (ORDER BY c_elementvalue_id),'') ");
			}
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, AD_Client_ID);
			pstmt.setInt(3, AD_Client_ID);
			pstmt.setInt(4, AD_Client_ID);
			pstmt.setInt(5, AD_Client_ID);
			pstmt.setInt(6, AD_Client_ID);
			pstmt.setInt(7, AD_Client_ID);
			pstmt.setInt(8, AD_Client_ID);
			pstmt.setInt(9, AD_Client_ID);
			pstmt.setInt(10, AD_Client_ID);
			pstmt.setInt(11, AD_Client_ID);
			pstmt.setInt(12, AD_Client_ID);
			pstmt.setInt(13, AD_Client_ID);
			
			rs = pstmt.executeQuery();
				
			while (rs.next()) 
			{
				if ("AST".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setAsset(rs.getString("Ids"));
				if ("CTN".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setConstruction(rs.getString("Ids"));
				if ("CTP".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setConstructionLine(rs.getString("Ids"));
				if ("CTT".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setContract(rs.getString("Ids"));
				if ("CTS".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setContractLine(rs.getString("Ids"));
				if ("WAE".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setWarehouse(rs.getString("Ids"));
				if ("TCT".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setTypeCost(rs.getString("Ids"));
				if ("TRE".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setTypeRevenue(rs.getString("Ids"));
				if ("PRJ".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setProject(rs.getString("Ids"));
				if ("PRP".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setProjectLine(rs.getString("Ids"));
				if ("BAC".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setBankAccount(rs.getString("Ids"));
				if ("PRD".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setProduct(rs.getString("Ids"));
				if ("BPN".equalsIgnoreCase(rs.getString("Name")))
					itemMan.setBpartner(rs.getString("Ids"));
			}		
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
   
	
	public void getClientEnveronment(int AD_Client_ID, ItemDisplayLogic itemDisplay) 
	{
		//trường showlistitem dùng để ẩn hiện trên giao diện và có code hạch toán
		String sql = " select isgroup, ismulticurrency, showlistitem, MMPolicy, C_Currency_ID from ad_client Where Ad_Client_ID = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			
			pstmt.setInt(1, AD_Client_ID);
			
			rs = pstmt.executeQuery();
				
			while (rs.next()) 
			{
				
				itemDisplay.setIsGroup(rs.getString(1)); 			//La tap doan hay cong ty => Hien thi tieu de bao cao
				itemDisplay.setIsMoreCurrency(rs.getString(2));		//Su dung da tien te hay don tien te. Da tien tien thi ko readonly.
				itemDisplay.setCurrenctyDefault(rs.getString(5));	//Gia tri mac dinh tien te theo cong ty
				itemDisplay.setMaterialPolicy(rs.getString(4));			//Cach tinh gia vat tu
				
				String lists = rs.getString("showlistitem");
				if (lists != null && !lists.isEmpty()) {
					lists = lists.replaceAll(" ", "").replaceAll(";", ",");
					String [] item = lists.split(",");
					for (int i = 0; i < item.length; i++) {
						String [] data = item[i].split("=");
						if ("C_Contract".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsContract(data[1]);
						}
						if ("C_ContractLine".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsContractLine(data[1]);
						}
						if ("C_Project".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsProject(data[1]);
						}
						if ("C_ProjectLine".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsProjectLine(data[1]);
						}
						if ("C_Construction".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsConstruction(data[1]);
						}
						if ("C_ConstructionLine".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsConstructionLine(data[1]);
						}
						
						if ("M_Product".equalsIgnoreCase(data[0])) {
							itemDisplay.setIsProduct(data[1]);
						}
						
					}
				}
				
				
			}		
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
			
	}
	
	public void loadDocType() {
		String sql = " select COALESCE(string_agg(C_DocType_ID::text,','),'') as Ids, 'SUB' as Name from C_DocType where IsShowSub = 'Y' Union All "+
				"select COALESCE(string_agg(C_DocType_ID::text,','),'') as Ids, 'TAB' as Name from C_DocType where IsShowTab = 'Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			if (DB.isOracle()) {
				sql = sql.replaceAll("COALESCE(string_agg(C_DocType_ID::text,','),'')", 
						"COALESCE(listagg(C_DocType_ID,',') WITHIN GROUP (ORDER BY C_DocType_ID),'') ");
			}
			pstmt = DB.prepareStatement(sql, null);
			
			rs = pstmt.executeQuery();
				
			while (rs.next()) 
			{
				if ("SUB".equalsIgnoreCase(rs.getString("Name")))
					Env.setContext(m_ctx, "#ShowDocTypeSub", rs.getString("Ids"));
				if ("TAB".equalsIgnoreCase(rs.getString("Name")))
					Env.setContext(m_ctx, "#ShowTabDetail", rs.getString("Ids"));
				
			}		
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	public MBPartner getBPartner(int HR_Employee_ID) {
		MBPartner retValue = new Query(Env.getCtx(), X_C_BPartner.Table_Name, "HR_Employee_ID = ?", null)
				.setParameters(HR_Employee_ID)
				.first();
		return retValue;
	}
	
	public void getInfoLogin(String userName) {
		String sql = 
				" WITH tmp AS "
				+" (	 "
				+" 	SELECT u.AD_User_ID,  "
				+" 		STRING_AGG(DISTINCT r.AD_Tree_Menu_ID::text,',') listTree,  "
				+" 		STRING_AGG(DISTINCT RoleType,',') RoleType,  "
				+" 		STRING_AGG(DISTINCT RoleLevel,',') RoleLevel, "
				+" 		STRING_AGG(DISTINCT r.AD_Role_ID::text,',') ListRole, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsShowAcct,',')) > 0 THEN 'Y' ELSE 'N' END  IsShowAcct, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.isShowPrice,',')) > 0 THEN 'Y' ELSE 'N' END isShowPrice, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.isConfigAcct,',')) > 0 THEN 'Y' ELSE 'N' END isConfigAcct, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.isDelItem,',')) > 0 THEN 'Y' ELSE 'N' END isDelItem, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsMasterRole,',')) > 0 THEN 'Y' ELSE 'N' END IsMasterRole, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsAccessAllOrgs,',')) > 0 THEN 'Y' ELSE 'N' END IsAccessAllOrgs,  "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsAccessAdvanced,',')) > 0 THEN 'Y' ELSE 'N' END IsAccessAdvanced,  "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsDragDropMenu,',')) > 0 THEN 'Y' ELSE 'N' END IsDragDropMenu, "
				+" 		CASE WHEN POSITION('Y' IN STRING_AGG(r.IsCanExport,',')) > 0 THEN 'Y' ELSE 'N' END IsCanExport, "
				+" 		MAX(MaxQueryRecords) MaxQueryRecords "
				+" 	FROM AD_Role r  "
				+" 		INNER JOIN AD_User_Roles ur ON r.AD_Role_ID = ur.AD_Role_ID "
				+" 		INNER JOIN AD_User u ON ur.AD_User_ID = u.AD_User_ID "
				+" 	WHERE u.Value = ? "
				+" 	GROUP BY u.AD_User_ID "
				+" ) "
				+" SELECT u.AD_User_ID, u.Value, u.Name UserName, o.AD_Org_ID, o.Name OrgNames, "
				+"		e.HR_Employee_ID, e.Name EmployeeName, d.AD_Department_ID, d.Name DeptName, c.AD_Client_ID, c.Name ClientName, "
				+" 		CASE WHEN r.IsAccessAllOrgs = 'Y'  "
				+" 		THEN (SELECT STRING_AGG(AD_Org_ID::text,',') FROM AD_Org WHERE IsActive = 'Y') "
				+" 		ELSE (SELECT STRING_AGG(AD_Org_ID::text,',') FROM AD_User_OrgAccess uo Where uO.AD_User_ID = u.AD_User_ID) END listOrg, "
				+" 		r.ListTree, r.RoleType, r.RoleLevel, r.ListRole, r.IsShowAcct, r.isShowPrice, r.isConfigAcct, r.isDelItem, "
				+" 		r.IsMasterRole, r.IsAccessAllOrgs, r.IsAccessAdvanced, r.IsDragDropMenu, r.IsCanExport, r.MaxQueryRecords, "
				+"		COALESCE(u.isUserAdmin, 'N') IsUserAdmin, COALESCE(u.isUserSystem, 'N') IsUserSystem"
				+" FROM AD_User u INNER JOIN tmp r ON u.AD_User_ID = r.AD_User_ID "
				+"		LEFT JOIN HR_Employee e ON u.HR_Employee_ID = e.HR_Employee_ID "
				+"		LEFT JOIN AD_Department d ON d.AD_Department_ID = e.AD_Department_ID "
				+"		INNER JOIN AD_Client c ON c.AD_Client_ID = e.AD_Client_ID "
				+"		INNER JOIN AD_Org o ON o.AD_Org_ID = d.AD_Org_ID "
				+" WHERE u.Value = ?";
			PreparedStatement ps = DB.prepareCall(sql);
			ResultSet rs = null;
			try {
				ps.setString(1, userName);
				ps.setString(2, userName);
				rs = ps.executeQuery();
				while (rs.next()) {
					Env.setContext(m_ctx, "#IsShowPrice", rs.getString("IsShowPrice"));
					Env.setContext(m_ctx, "#IsConfigAcct", rs.getString("IsConfigAcct"));
					Env.setContext(m_ctx, "#ShowAcct", rs.getString("IsShowAcct"));
					Env.setContext(m_ctx, "#AD_Role_IDs", rs.getString("ListRole"));
					Env.setContext(m_ctx, "#AD_Tree_IDs", rs.getString("ListTree"));
					//Env.setContext(m_ctx, "#AD_Role_Name", rs.getString(""));
					
					Env.setContext(m_ctx, "#RoleType", rs.getString("RoleType"));
					Env.setContext(m_ctx, "#RoleLevel", rs.getString("RoleLevel"));
					Env.setContext(m_ctx, "#IsDelItem", rs.getString("IsDelItem"));
					
					Env.setContext(m_ctx, "#AD_User_ID", rs.getString("AD_User_ID"));
					Env.setContext(m_ctx, "#AD_User_Name", rs.getString("UserName"));
					Env.setContext(m_ctx, "#AD_User_Value", rs.getString("Value"));
					
					Env.setContext(m_ctx, "#AD_Client_ID", rs.getString("AD_Client_ID"));
					Env.setContext(m_ctx, "#AD_Client_Name", rs.getString("ClientName"));
					
					Env.setContext(m_ctx, "#HR_Employee_ID", rs.getString("HR_Employee_ID"));	
					Env.setContext(m_ctx, "#EmployeeName", rs.getString("EmployeeName"));
					Env.setContext(m_ctx, "#AD_Department_ID", rs.getString("AD_Department_ID"));
					Env.setContext(m_ctx, "#AD_Department_Name", rs.getString("DeptName"));
					Env.setContext(m_ctx, "#AD_Org_ID", rs.getString("AD_Org_ID"));
					Env.setContext(m_ctx, "#AD_Org_Name", rs.getString("OrgNames"));
					Env.setContext(m_ctx, "#AD_OrgAccess_ID", rs.getString("listOrg"));
					
					Env.setContext(m_ctx, "#ShowAdvanced", rs.getString("IsAccessAdvanced"));
					Env.setContext(m_ctx, "#IsDragDropMenu", rs.getString("IsDragDropMenu"));
					Env.setContext(m_ctx, "#IsCanExport", rs.getString("IsCanExport"));
					Env.setContext(m_ctx, "#MaxQueryRecords", rs.getString("MaxQueryRecords"));
					Env.setContext(m_ctx, "#IsUserAdmin", rs.getString("isUserAdmin"));
					Env.setContext(m_ctx, "#IsUserSystem", rs.getString("isUserSystem"));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();				
			} finally {
				DB.close(rs, ps);
				rs = null;
				ps = null;
			}
	}
	
	
	public void loadAllContext(int AD_Client_ID, String userName) {
		//Date
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String date = simpleDateFormat.format(ts);
		Env.setContext(m_ctx, "#Date", date + " 00:00:00");
		Env.setContext(m_ctx, "#Day", new SimpleDateFormat("dd").format(ts));
		Env.setContext(m_ctx, "#Month", new SimpleDateFormat("MM").format(ts));
		Env.setContext(m_ctx, "#Year", new SimpleDateFormat("yyyy").format(ts));
		Env.setContext(m_ctx, "#FDate", TimeUtil.getDayFirstMonth(ts));
		Env.setContext(m_ctx, "#TDate", TimeUtil.getDayLastMonth(ts));
		Env.setContext(m_ctx, "#FYDate", TimeUtil.getDayFirstYear(ts));
		Env.setContext(m_ctx, "#LYDate", TimeUtil.getDayLastYear(ts));
		
		//Config from database
		ItemDisplayLogic 	itemDis = new ItemDisplayLogic();
		ItemMandatory 		itemMan = new ItemMandatory();
		
		getClientEnveronment(AD_Client_ID, itemDis);
		
		setContextExtend(itemDis, itemMan);
		
		loadContextElementValue(0, itemMan);//AD_Client_ID
		
		loadDocType();
		
		//Load info login
		getInfoLogin(userName);
		
	}
	
	
	public void setContextExtend(ItemDisplayLogic itemDis, ItemMandatory itemMan) {
		//Displaylogic		
		Env.setContext(m_ctx, "#IsContract", 			itemDis.getIsContract());
		Env.setContext(m_ctx, "#IsContractLine",		itemDis.getIsContractLine());
		Env.setContext(m_ctx, "#IsProject", 			itemDis.getIsProject());
		Env.setContext(m_ctx, "#IsProjectLine", 		itemDis.getIsProjectLine());
		Env.setContext(m_ctx, "#IsConstruction", 		itemDis.getIsConstruction());
		Env.setContext(m_ctx, "#IsConstructionLine", 	itemDis.getIsConstructionLine());
		Env.setContext(m_ctx, "#IsProduct", 			itemDis.getIsProduct());
		Env.setContext(m_ctx, "#IsGroup", 				itemDis.getIsGroup());
		Env.setContext(m_ctx, "#IsMoreCurrency", 		itemDis.getIsMoreCurrency());
		Env.setContext(m_ctx, "#C_CurrencyDefault_ID", 	itemDis.getCurrenctyDefault());
		Env.setContext(m_ctx, "#MaterialPolicy", 		itemDis.getMaterialPolicy());
		
		Env.DisProduct 				= itemDis.getIsProduct()!= null 			&& itemDis.getIsProduct().equals(Env.YES) ? true : false;
		Env.DisContract 			= itemDis.getIsContract() != null 			&& itemDis.getIsContract().equals(Env.YES) ? true : false;
		Env.DisContractLine 		= itemDis.getIsContractLine() != null 		&& itemDis.getIsContractLine().equals(Env.YES) ? true : false;
		Env.DisProject 				= itemDis.getIsProject() != null 			&& itemDis.getIsProject().equals(Env.YES) ? true : false;
		Env.DisProjectLine 			= itemDis.getIsProjectLine() != null 		&& itemDis.getIsProjectLine().equals(Env.YES) ? true : false;
		Env.DisConstruction 		= itemDis.getIsConstruction() != null 		&& itemDis.getIsConstruction().equals(Env.YES) ? true : false;
		Env.DisConstructionLine 	= itemDis.getIsConstructionLine() != null 	&& itemDis.getIsConstructionLine().equals(Env.YES) ? true : false;
		
		//MandatoryLogic
		
		Env.setContext(m_ctx, "#BankAccount", 		itemMan.getBankAccount());
		Env.setContext(m_ctx, "#Asset", 			itemMan.getAsset());
		Env.setContext(m_ctx, "#BPartner", 			itemMan.getBpartner());
		Env.setContext(m_ctx, "#Product", 			itemMan.getProduct());
		Env.setContext(m_ctx, "#Project", 			itemMan.getProject());
		Env.setContext(m_ctx, "#ProjectLine", 		itemMan.getProjectLine());
		Env.setContext(m_ctx, "#Contract", 			itemMan.getContract());
		Env.setContext(m_ctx, "#ContractLine", 		itemMan.getContractLine());
		Env.setContext(m_ctx, "#Construction", 		itemMan.getConstruction());
		Env.setContext(m_ctx, "#ConstructionLine",  itemMan.getConstructionLine());
		Env.setContext(m_ctx, "#Warehouse", 		itemMan.getWarehouse());
		Env.setContext(m_ctx, "#TypeCost", 			itemMan.getTypeCost());
		Env.setContext(m_ctx, "#TypeRevenue", 		itemMan.getTypeRevenue());
		
	}
}	//	Login
