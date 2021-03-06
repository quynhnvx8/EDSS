
package eone.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import eone.base.model.MBPartner;
import eone.base.model.MSysConfig;
import eone.base.model.MTable;
import eone.base.model.MUser;

public class WebUser
{
	public static WebUser get (HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return (WebUser)session.getAttribute(WebUser.NAME);
	}	//	get
	
	
	public static WebUser get (Properties ctx, String email)
	{
		return get (ctx, email, null, true);
	}	//	get

	public static WebUser get (Properties ctx, String email, String password, boolean useCache)
	{
		if (!useCache)
			s_cache = null;
		if (s_cache != null && email != null && email.equals(s_cache.getEmail()))
		{
			//	if password is null, don't check it
			if (password == null || password.equals(s_cache.getPassword()))
				return s_cache;
			s_cache.setPasswordOK(false, null);
			return s_cache;
		}
		s_cache = new WebUser (ctx, email, password);
		return s_cache;
	}	//	get

	/**
	 * 	Get user unconditional (from cache)
	 * 	@param ctx context
	 *	@param AD_User_ID BP Contact
	 * 	@return web user
	 */
	public static WebUser get (Properties ctx, int AD_User_ID)
	{
		if (s_cache != null && s_cache.getAD_User_ID() == AD_User_ID)
			return s_cache;
		s_cache = new WebUser (ctx, AD_User_ID, null);
		return s_cache;
	}	//	get

	/** Short term Cache for immediate re-query/post (hit rate 20%)	*/
	private volatile static WebUser	s_cache = null;

	/*************************************************************************/

	/**	Attribute Name - also in JSPs		*/
	public static final String		NAME = "webUser";
	/**	Logging						*/
	private CLogger					log = CLogger.getCLogger(getClass());

	/**
	 *	Load User with password
	 *	@param ctx context
	 *	@param email email
	 *	@param password password
	 */
	private WebUser (Properties ctx, String email, String password)
	{
		m_ctx = ctx;
		m_AD_Client_ID = Env.getAD_Client_ID(ctx);
		load (email, password);
	}	//	WebUser

	/**
	 *	Load User with password
	 *	@param ctx context
	 *	@param AD_User_ID BP Contact
	 * 	@param trxName transaction
	 */
	private WebUser (Properties ctx, int AD_User_ID, String trxName)
	{
		m_ctx = ctx;
		m_AD_Client_ID = Env.getAD_Client_ID(ctx);
		load (AD_User_ID);
	}	//	WebUser

	private Properties			m_ctx;
	//
	private MBPartner		 	m_bp;
	private MUser			 	m_bpc;
	//
	private boolean				m_passwordOK = false;
	private String				m_passwordMessage;
	private String				m_saveErrorMessage;
	//
	private int 				m_AD_Client_ID = 0;
	private boolean				m_loggedIn = false;


	/**
	 * 	Load Contact
	 * 	@param email email
	 *	@param password optional password
	 */
	private void load (String email, String password)
	{
		if (log.isLoggable(Level.INFO)) log.info(email + " - AD_Client_ID=" + m_AD_Client_ID);
		String sql = "SELECT * "
			+ "FROM AD_User "
			+ "WHERE AD_Client_ID=?"
			+ " AND TRIM(EMail)=?";
		if (email == null)
			email = "";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Client_ID);
			pstmt.setString(2, email.trim());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_bpc = new MUser (m_ctx, rs, null);
				if (log.isLoggable(Level.FINE)) log.fine("Found BPC=" + m_bpc);
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

		
		if (m_bpc != null && password != null && password.equals(m_bpc.getPassword()))
			m_passwordOK = true;
		if (m_passwordOK || m_bpc == null)
			m_passwordMessage = null;
		else
			setPasswordOK (false, password);

		
		if (log.isLoggable(Level.CONFIG)) log.config(m_bp + " - " + m_bpc);
	}	//	load

	/**
	 * 	Load Contact
	 * 	@param AD_User_ID BP Contact
	 */
	private void load (int AD_User_ID)
	{
		if (log.isLoggable(Level.INFO)) log.info("ID=" + AD_User_ID + ", AD_Client_ID=" + m_AD_Client_ID);
		String sql = "SELECT * "
			+ "FROM AD_User "
			+ "WHERE AD_Client_ID=?"
			+ " AND AD_User_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Client_ID);
			pstmt.setInt(2, AD_User_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_bpc = new MUser (m_ctx, rs, null);
				if (log.isLoggable(Level.FINE)) log.fine("= found BPC=" + m_bpc);
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

		//	Password not entered
		m_passwordOK = false;
		m_loggedIn = false;

		
		//	Make sure that all entities exist
		if (m_bpc == null)
		{
			m_bpc = new MUser (m_ctx, 0, null);
			m_bpc.setEMail("?");
			m_bpc.setPassword("?");
		}
		
		if (log.isLoggable(Level.INFO)) log.info("= " + m_bp + " - " + m_bpc);
		
	}	//	load

	/**
	 * 	Return Valid.
	 * 	@return return true if found
	 */
	public boolean isValid()
	{
		if (m_bpc == null)
			return false;
		boolean ok = m_bpc.getAD_User_ID() != 0;
		return ok;
	}	//	isValid

	/**
	 * 	Return Email Validation.
	 * 	@return return true if email is valid
	 */
	public boolean isEMailValid()
	{
		if (m_bpc == null || !WebUtil.exists(getEmail()))
		{
			if (log.isLoggable(Level.FINE)) log.fine(getEmail() + ", bpc=" + m_bpc);
			return false;
		}
		//
		boolean ok = m_bpc.getAD_User_ID() != 0
			&& m_bpc.isEMailValid();
		if (!ok)
			if (log.isLoggable(Level.FINE)) log.fine(getEmail()
				+ ", ID=" + m_bpc.getAD_User_ID()
				+ ", Online=" + m_bpc.isOnline()
				+ ", EMailValid=" + m_bpc.isEMailValid());
		return ok;
	}	//	isEMailValid

	/**
	 * 	Info
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder("WebUser[");
		sb.append(getEmail())
			.append(",LoggedIn=").append(m_loggedIn)
			.append(",").append(m_bpc)
			.append(",PasswordOK=").append(m_passwordOK)
			.append(",Valid=").append(isValid())
			.append(" - ").append(m_bp).append("Customer=").append(isCustomer())
			.append("]");
		return sb.toString();
	}	//	toString

	
	/**************************************************************************
	 * 	Save BPartner Objects
	 * 	@return true if saved
	 */
	public boolean save()
	{
		m_saveErrorMessage = null;
		if (log.isLoggable(Level.INFO)) log.info("BP.Value=" + m_bp.getValue() + ", Name=" + m_bp.getName());
		try
		{
			//	check if BPartner exists	***********************************
			if (m_bp.getC_BPartner_ID() == 0)
			{
				String sql = "SELECT * FROM C_BPartner WHERE AD_Client_ID=? AND Value=?";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt (1, m_AD_Client_ID);
					pstmt.setString (2, m_bp.getValue());
					rs = pstmt.executeQuery();
					if (rs.next())
					{
						//m_bp = new MBPartner (m_ctx, m_bpc.getC_BPartner_ID (), null);
						if (log.isLoggable(Level.FINE)) log.fine("BP loaded =" + m_bp);
					}
  			}
				catch (Exception e)
				{
					log.log(Level.SEVERE, "save-check", e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
			}

			//	save BPartner			***************************************
			if (m_bp.getName () == null || m_bp.getName().length() == 0)
				m_bp.setName (m_bpc.getName());
			if (m_bp.getValue() == null || m_bp.getValue().length() == 0)
				m_bp.setValue(m_bpc.getEMail());
			if (log.isLoggable(Level.FINE)) log.fine("BP=" + m_bp);
			if (!m_bp.save ())
			{
				m_saveErrorMessage = "Could not save Business Partner";
				return false;
			}

			//	save Location			***************************************

			
			if (log.isLoggable(Level.FINE)) log.fine("BPC=" + m_bpc);
			if (!m_bpc.save ())
			{
				m_saveErrorMessage = "Could not save Contact";
				return false;
			}
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "save", ex);
			m_saveErrorMessage = ex.toString();
			return false;
		}
		//
		return true;
	}	//	save

	/**
	 * 	Set Save Error Message
	 *	@param msg message
	 */
	public void setSaveErrorMessage(String msg)
	{
		m_saveErrorMessage = msg;
	}
	/**
	 * 	Get Save Error Message
	 *	@return message
	 */
	public String getSaveErrorMessage()
	{
		return m_saveErrorMessage;
	}

	/**************************************************************************
	 * 	Get EMail address.
	 * 	used as jsp parameter
	 * 	@return email address of contact
	 */
	public String getEmail ()
	{
		return m_bpc.getEMail();
	}

	public void setEmail (String email)
	{
		m_bpc.setEMail(email);
	}

	public String getName ()
	{
		return m_bpc.getName();
	}

	public void setName (String name)
	{
		m_bpc.setName(name);
	}

	public void setValue (String value)
	{
		m_bp.setValue (value);
	}

	
	/**
	 * 	Get Password
	 * 	@return password
	 */
	public String getPassword ()
	{
		String pwd = m_bpc.getPassword();
		if (pwd == null || pwd.length() == 0)	//	if no password use time
			pwd = String.valueOf(System.currentTimeMillis());
		return pwd;
	}	//	getPassword

	
	/**
	 * 	Set Password
	 * 	@param password new password
	 */
	public void setPassword (String password)
	{
		if (password == null || password.length() == 0)
			m_passwordMessage = "Enter Password";
		m_bpc.setPassword (password);
	}	//	setPassword

	/**
	 * 	Set Password OK
	 * 	@param ok password valid
	 * 	@param password password
	 */
	private void setPasswordOK (boolean ok, String password)
	{
		m_passwordOK = ok;
		if (ok)
			m_passwordMessage = null;
		else if (password == null || password.length() == 0)
			m_passwordMessage = "Enter Password";
		else
			m_passwordMessage = "Invalid Password";
	}	//	setPasswordOK

	/**
	 * 	Is Password OK
	 * 	@return true if OK
	 */
	public boolean isPasswordOK()
	{
		if (m_bpc == null || !WebUtil.exists(m_bpc.getPassword()))
			return false;
		return m_passwordOK;
	}	//	isPasswordOK

	/**
	 * 	Set Password Message
	 * 	@return error message or null
	 */
	public String getPasswordMessage ()
	{
		return m_passwordMessage;
	}	//	getPasswordMessage

	/**
	 *	Set Password Message
	 * 	@param passwordMessage message
	 */
	public void setPasswordMessage (String passwordMessage)
	{
		m_passwordMessage = passwordMessage;
	}	//	setPasswordMessage

	/**
	 * 	Log in with password
	 * 	@param password password
	 *	@return true if the user is logged in
	 */
	public boolean login (String password)
	{
		m_loggedIn = isValid () 			//	we have a contact
			 && WebUtil.exists (password); 	//	we have a password
			 //&& password.equals (getPassword ());
		boolean retValue = false;
		if(m_loggedIn)
		{
			boolean hash_password=MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);		    
	    	if(!hash_password)
		    {
		      String sql = "SELECT * FROM AD_User "
			   + "WHERE COALESCE(LDAPUser, Name)=? "  // #1
			   + " AND IsActive='Y' " // #4
		     ;
		     PreparedStatement pstmt = null;
		     ResultSet rs = null;
		     try
		     {
		    	 pstmt = DB.prepareStatement (sql, null);
		    	 pstmt.setString (1, m_bpc.getName());
			     rs = pstmt.executeQuery ();
			     if (rs.next ())
			     {
			    	do 
			    	{
			    		MUser user = new MUser(Env.getCtx(), rs, null);
			    		if (user.getPassword() != null && user.getPassword().equals(password)) {
			    			retValue = true;
			    			break;
			    		}
			    	} while (rs.next());			    	
			     }
			    else
				     log.fine("No record");
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
		   }
		   else{
			  String where = " COALESCE(LDAPUser,Name) = ? AND" +
					" EXISTS (SELECT * FROM AD_User_Roles ur" +
					"         INNER JOIN AD_Role r ON (ur.AD_Role_ID=r.AD_Role_ID)" +
					"         WHERE ur.AD_User_ID=AD_User.AD_User_ID AND ur.IsActive='Y' AND r.IsActive='Y') AND " +
					" EXISTS (SELECT * FROM AD_Client c" +
					"         WHERE c.AD_Client_ID=AD_User.AD_Client_ID" +
					"         AND c.IsActive='Y') AND " +
					" AD_User.IsActive='Y'";
			
			   MUser user = MTable.get(m_ctx, MUser.Table_ID).createQuery( where, null).setParameters(m_bpc.getName()).firstOnly();   // throws error if username collision occurs
			
			   String hash = null;
			   String salt = null;
			
		       if (user != null )
			   {
		 		  hash = user.getPassword();
		 		  salt = user.getSalt();
		       }
			
		    	 // always do calculation to confuse timing based attacks
			    if ( user == null )
				    user = MUser.get(null, 0);
			    if ( hash == null )
			        hash = "0000000000000000";
			    if ( salt == null )
				   salt = "0000000000000000";
			 
			   if ( user.authenticateHash(password) )
			   {
				   retValue=true;
			   }
		 }
		}
		m_loggedIn=m_loggedIn && retValue;
		setPasswordOK (m_loggedIn, password);
		if (log.isLoggable(Level.FINE)) log.fine("success=" + m_loggedIn);
		if (m_loggedIn)
			Env.setContext(m_ctx, "#AD_User_ID", getAD_User_ID());
		return m_loggedIn;
	}	//	isLoggedIn

	/**
	 * 	Log in with oassword
	 */
	public void logout ()
	{
		m_loggedIn = false;
	}	//	isLoggedIn

	/**
	 * 	Is User Logged in
	 *	@return is the user logged in
	 */
	public boolean isLoggedIn ()
	{
		return m_loggedIn;
	}	//	isLoggedIn

	

	
	public int getAD_Client_ID ()
	{
		return m_bpc.getAD_Client_ID();
	}

	public int getAD_User_ID ()
	{
		return m_bpc.getAD_User_ID();
	}

	public int getContactID ()
	{
		return getAD_User_ID();
	}

	/*************************************************************************/

	/**
	 * 	Get Company Name
	 *	@return company name
	 */
	public String getCompany()
	{
		return m_bp.getName();
	}

	public void setCompany(String company)
	{
		if (company==null) {
			m_bp.setName (m_bpc.getName ());
		} else {
			m_bp.setName(company);
		}
	}

	public int getC_BPartner_ID ()
	{
		return m_bp.getC_BPartner_ID();
	}
	
	public int getBpartnerID ()
	{
		return m_bp.getC_BPartner_ID();
	}

	
	public boolean isEmployee()
	{
		return m_bp.isEmployee();
	}


	public boolean isCustomer()
	{
		return m_bp.isCustomer();
	}

	public void setIsCustomer(boolean isCustomer)
	{
		m_bp.setIsCustomer(isCustomer);
	}

	public boolean isVendor()
	{
		return m_bp.isVendor();
	}

	
	
}	//	WebUser
