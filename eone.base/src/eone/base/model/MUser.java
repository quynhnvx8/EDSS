
package eone.base.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import eone.exceptions.DBException;
import eone.util.AesBase64Wrapper;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.EMail;
import eone.util.Env;
import eone.util.Msg;
import eone.util.SecureEngine;
import eone.util.Util;


public class MUser extends X_AD_User
{
	private static final long serialVersionUID = 1366564982801896588L;

	
	public static MUser get (Properties ctx, int AD_User_ID)
	{
		Integer key = Integer.valueOf(AD_User_ID);
		MUser retValue = (MUser)s_cache.get(key);
		if (retValue == null)
		{
			retValue = new MUser (ctx, AD_User_ID, null);
			if (AD_User_ID == 0)
			{
				String trxName = null;
				retValue.load(trxName);	//	load System Record
			}
			s_cache.put(key, retValue);
		}
		return retValue;
	}	//	get
	
	

	/**
	 * 	Get Current User (cached)
	 *	@param ctx context
	 *	@return user
	 */
	public static MUser get (Properties ctx)
	{
		return get(ctx, Env.getAD_User_ID(ctx));
	}	//	get

	/**
	 * 	Get User
	 *	@param ctx context
	 *	@param name name
	 *	@param password password
	 *	@return user or null
	 */
	public static MUser get (Properties ctx, String name, String password)
	{
		if (name == null || name.length() == 0 || password == null || password.length() == 0)
		{
			s_log.warning ("Invalid Name/Password = " + name);
			return null;
		}
		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
//		ArrayList<KeyNamePair> clientList = new ArrayList<KeyNamePair>();
		ArrayList<Integer> clientsValidated = new ArrayList<Integer>();
		MUser retValue = null;
		
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
		
		List<MUser> users = new Query(ctx, MUser.Table_Name, where.toString(), null)
			.setParameters(name)
			.setOrderBy("AD_Client_ID, AD_User_ID") // prefer first user on System
			.list();
		
		if (users.size() == 0) {
			s_log.saveError("UserPwdError", name, false);
			return null;
		}
		
		for (MUser user : users) {
			if (clientsValidated.contains(user.getAD_Client_ID())) {
				s_log.severe("Two users with password with the same name/email combination on same tenant: " + name);
				return null;
			}
			
			clientsValidated.add(user.getAD_Client_ID());
			boolean valid = false;
			MSystem system = MSystem.get(Env.getCtx());
			if (system == null)
				throw new IllegalStateException("No System Info");
			
			
			if (system.isLDAP() && ! Util.isEmpty(user.getLDAPUser())) {
				valid = system.isLDAP(name, password);
			} else if (hash_password) {
				valid = user.authenticateHash(password, name);
			} else {
				// password not hashed
				valid = user.getPassword().equals(password);
			}
			
			if (valid){
				retValue=user;
				break;
			}
		}	
	
		 return retValue;
	}	//	get
	
	/**
	 *  Get Name of AD_User
	 *  @param  AD_User_ID   System User
	 *  @return Name of user or ?
	 */
	public static String getNameOfUser (int AD_User_ID)
	{
		MUser user = get(Env.getCtx(), AD_User_ID);
		if (user.getAD_User_ID() != AD_User_ID)
			return "?";
		return user.getName();
	}	//	getNameOfUser

	
	
	/**	Cache					*/
	static private CCache<Integer,MUser> s_cache = new CCache<Integer,MUser>(Table_Name, 30, 60);
	/**	Static Logger			*/
	private static CLogger	s_log	= CLogger.getCLogger (MUser.class);
	
	
	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param AD_User_ID id
	 * 	@param trxName transaction
	 */
	public MUser (Properties ctx, int AD_User_ID, String trxName)
	{
		super (ctx, AD_User_ID, trxName);	//	0 is also System
		if (AD_User_ID == 0)
		{
			setNotificationType(NOTIFICATIONTYPE_None);
		}		
	}	//	MUser

	
	public MUser (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MUser

	/**	Roles of User with Org	*/
	private MRole[] 			m_roles = null;
	/**	Roles of User with Org	*/
	private int		 			m_rolesAD_Org_ID = -1;
	/** Is Administrator		*/
	private Boolean				m_isAdministrator = null;

	/** Password Hashed **/
	private boolean being_hashed = false;
	
		
	/**
	 * 	Get Value - 7 bit lower case alpha numerics max length 8
	 *	@return value
	 */
	public String getValue()
	{
		String s = super.getValue();
		if (s != null)
			return s;
		setValue(null);
		return super.getValue();
	}	//	getValue

	/**
	 * 	Set Value - 7 bit lower case alpha numerics max length 8
	 *	@param Value
	 */
	public void setValue(String Value)
	{
		if (Value == null || Value.trim().length () == 0)
			Value = getLDAPUser();
		if (Value == null || Value.length () == 0)
			Value = getName();
		if (Value == null || Value.length () == 0)
			Value = "noname";
		//
		String result = cleanValue(Value);
		if (result.length() > 8)
		{
			String first = getName(Value, true);
			String last = getName(Value, false);
			if (last.length() > 0)
			{
				String temp = last;
				if (first.length() > 0)
					temp = first.substring (0, 1) + last;
				result = cleanValue(temp);
			}
			else
				result = cleanValue(first);
		}
		if (result.length() > 8)
			result = result.substring (0, 8);
		super.setValue(result);
	}	//	setValue
	
	/**
	 * 	Clean Value
	 *	@param value value
	 *	@return lower case cleaned value
	 */
	private String cleanValue (String value)
	{
		char[] chars = value.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++)
		{
			char ch = chars[i];
			ch = Character.toLowerCase (ch);
			if ((ch >= '0' && ch <= '9')		//	digits
				|| (ch >= 'a' && ch <= 'z'))	//	characters
				sb.append(ch);
		}
		return sb.toString ();
	}	//	cleanValue
	
	/**
	 * Convert Password to SHA-512 hash with salt * 1000 iterations https://www.owasp.org/index.php/Hashing_Java
	 * @param password -- plain text password
	 */
	@Override
	public void setPassword(String password) {		
		if ( password == null )
		{
			super.setPassword(password);
			return;
		}
		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		
		if(!hash_password){
			super.setPassword(password);
			return;
		}
		
		if ( being_hashed  )
			return;
		
		being_hashed = true;   // prevents double call from beforeSave
		
		String text = password + getValue();
		String hash = AesBase64Wrapper.encryptAndEncode(text);
		super.setPassword(hash);
		
		/*FIXME Comment code idempiere. Quynhnv.X8. Mode 08/03/2021
		// Uses a secure Random not a simple Random
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];
			random.nextBytes(bSalt);
			// Digest computation
			String hash;
			hash = SecureEngine.getSHA512Hash(1000,password,bSalt);

	        String sSalt = Secure.convertToHexString(bSalt);
			super.setPassword(hash);
			setSalt(sSalt);
		} catch (NoSuchAlgorithmException e) {
			super.setPassword(password);
		} catch (UnsupportedEncodingException e) {
			super.setPassword(password);
		}
		*/
	}
	
	/**
	 * check if hashed password matches
	 */
	public boolean authenticateHash (String password)  {
		return SecureEngine.isMatchHash (getPassword(), getSalt(), password);
	}	
	
	//Check theo ma hoa pass moi
	public boolean authenticateHash (String password, String account)  {
		String text = AesBase64Wrapper.encryptAndEncode(password + account);
		//System.out.println(text);
		return getPassword().equals(text);
	}
	
	/**
	 * 	Get First Name
	 *	@return first name
	 */
	public String getFirstName()
	{
		return getName (getName(), true);
	}	//	getFirstName
	
	/**
	 * 	Get Last Name
	 *	@return first name
	 */
	public String getLastName()
	{
		return getName (getName(), false);
	}	//	getLastName

	/**
	 * 	Get First/Last Name
	 *	@param name name
	 *	@param getFirst if true first name is returned
	 *	@return first/last name
	 */
	private String getName (String name, boolean getFirst)
	{
		if (name == null || name.length () == 0)
			return "";
		String first = null;
		String last = null;
		boolean lastFirst = name.indexOf(',') != -1;
		StringTokenizer st = null;
		if (lastFirst)
			st = new StringTokenizer(name, ",");
		else
			st = new StringTokenizer(name, " ");
		while (st.hasMoreTokens())
		{
			String s = st.nextToken().trim();
			if (lastFirst)
			{
				if (last == null)
					last = s;
				else if (first == null)
					first = s;
			}
			else
			{
				if (first == null)
					first = s;
				else
					last = s;
			}
		}
		if (getFirst)
		{
			if (first == null)
				return "";
			return first.trim();
		}
		if (last == null)
			return "";
		return last.trim();
	}	//	getName
	
	
	/**
	 * 	Add to Description
	 *	@param description description to be added
	 */
	public void addDescription (String description)
	{
		if (description == null || description.length() == 0)
			return;
		String descr = getDescription();
		if (descr == null || descr.length() == 0)
			setDescription (description);
		else
			setDescription (descr + " - " + description);
	}	//	addDescription
	
	
	/**
	 * 	String Representation
	 *	@return Info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUser[")
			.append(get_ID())
			.append(",Name=").append(getName())
			.append(",EMailUserID=").append(getEMailUser())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Is it an Online Access User
	 *	@return true if it has an email and password
	 */
	public boolean isOnline ()
	{
		if (getEMail() == null || getPassword() == null)
			return false;
		return true;
	}	//	isOnline

	/**
	 * 	Set EMail - reset validation
	 *	@param EMail email
	 */
	public void setEMail(String EMail)
	{
		super.setEMail (EMail);
	}	//	setEMail
	
	/**
	 * 	Convert EMail
	 *	@return Valid Internet Address
	 */
	public InternetAddress getInternetAddress ()
	{
		String email = getEMail();
		if (email == null || email.length() == 0)
			return null;
		try
		{
			InternetAddress ia = new InternetAddress (email, true);
			if (ia != null)
				ia.validate();	//	throws AddressException
			return ia;
		}
		catch (AddressException ex)
		{
			log.warning(email + " - " + ex.getLocalizedMessage());
		}
		return null;
	}	//	getInternetAddress

	/**
	 * 	Validate Email (does not work).
	 * 	Check DNS MX record
	 * 	@param ia email
	 *	@return error message or ""
	 */
	private String validateEmail (InternetAddress ia)
	{
		if (ia == null)
			return "NoEmail";
                else return ia.getAddress();
		
	}	//	validateEmail
	
	/**
	 * 	Is the email valid
	 * 	@return return true if email is valid (artificial check)
	 */
	public boolean isEMailValid()
	{
		return validateEmail(getInternetAddress()) != null;
	}	//	isEMailValid
	
	/**
	 * 	Could we send an email
	 * 	@return true if EMail Uwer/PW exists
	 */
	public boolean isCanSendEMail()
	{
		String s = getEMailUser();
		if (s == null || s.length() == 0)
			return false;
		// If SMTP authorization is not required, then don't check password - teo_sarca [ 1723309 ]
		if (!MClient.get(getCtx()).isSmtpAuthorization())
			return true;
		s = getEMailUserPW();
		return s != null && s.length() > 0;
	}	//	isCanSendEMail


	/**
	 * 	Get Notification via EMail
	 *	@return true if email
	 */
	public boolean isNotificationEMail()
	{
		String s = getNotificationType();
		return s == null || NOTIFICATIONTYPE_EMail.equals(s)
				|| NOTIFICATIONTYPE_EMailPlusNotice.equals(s);
	}	//	isNotificationEMail
	
	/**
	 * 	Get Notification via Note
	 *	@return true if note
	 */
	public boolean isNotificationNote()
	{
		String s = getNotificationType();
		return s != null && (NOTIFICATIONTYPE_Notice.equals(s)
							|| NOTIFICATIONTYPE_EMailPlusNotice.equals(s));
	}	//	isNotificationNote
	
	
	/**************************************************************************
	 * 	Get User Roles for Org
	 * 	@param AD_Org_ID org
	 *	@return array of roles
	 */
	public MRole[] getRoles (int AD_Org_ID)
	{
		if (m_roles != null && m_rolesAD_Org_ID == AD_Org_ID)
			return m_roles;
		
		ArrayList<MRole> list = new ArrayList<MRole>();
		
		String sql = "SELECT * FROM AD_Role r " 
			+ "WHERE r.IsActive='Y'" 
			+ " AND( EXISTS (SELECT * FROM AD_User_Roles ur" 
			+ " WHERE r.AD_Role_ID=ur.AD_Role_ID AND ur.IsActive='Y' AND ur.AD_User_ID=?) "
			+ " OR EXISTS (SELECT * FROM AD_User_OrgAccess uo"
			+ " WHERE uo.AD_User_ID=? AND uo.IsActive='Y' AND uo.AD_Org_ID=?))" 
			+ "ORDER BY AD_Role_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getAD_User_ID());
			pstmt.setInt (2, getAD_User_ID());
			pstmt.setInt (3, AD_Org_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRole(getCtx(), rs, get_TrxName()));
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
		//
		m_rolesAD_Org_ID = AD_Org_ID;
		m_roles = new MRole[list.size()];
		list.toArray (m_roles);
		return m_roles;
	}	//	getRoles
	
	/**
	 * 	Is User an Administrator?
	 *	@return true if Admin
	 */
	public boolean isAdministrator()
	{
		if (m_isAdministrator == null)
		{
			m_isAdministrator = Boolean.FALSE;
			MRole[] roles = getRoles(0);
			for (int i = 0; i < roles.length; i++)
			{
				if (roles[i].getAD_Role_ID() == 0)
				{
					m_isAdministrator = Boolean.TRUE;
					break;
				}
			}
		}
		return m_isAdministrator.booleanValue();
	}	//	isAdministrator

	/**
	 * 	User has access to URL form?
	 *	@return true if user has access
	 */
	public boolean hasURLFormAccess(String url)
	{
		if (Util.isEmpty(url, true)) {
			return false;
		}
		
		return true;
	}	//	hasURLFormAccess

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord || (is_ValueChanged(X_AD_User.COLUMNNAME_IsUserSystem) && isUserSystem())) {
			Map<String, Object> dataColumn = new HashMap<String, Object>();
			dataColumn.put(COLUMNNAME_AD_User_ID, getAD_User_ID());
			dataColumn.put(COLUMNNAME_IsUserSystem, isUserSystem());
			
			boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_User_ID, getAD_User_ID(), get_TrxName());
			
			if (!check) {
				log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_IsUserSystem);
				return false;
			}
		}
		
		if (!Util.isEmpty(getEMail()) && (newRecord || is_ValueChanged("EMail"))) {
			if (! EMail.validate(getEMail())) {
				log.saveError("SaveError", Msg.getMsg(getCtx(), "InvalidEMailFormat") + Msg.getElement(getCtx(), COLUMNNAME_EMail) + " - [" + getEMail() + "]");
				return false;
			}
		}
		
		String error = checkCreateAcctRegister();
		if (!"".equals(error)) {
			log.saveError("Error", error);
			return false;
		}
		
		if (newRecord) {
			setPassword("1");
		}
		

		if (getPassword() != null && getPassword().length() > 0) {
			boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
			if (email_login) {
				// email is mandatory for users with password
				if (getEMail() == null || getEMail().length() == 0) {
					log.saveError("SaveError", Msg.getMsg(getCtx(), "FillMandatory") + Msg.getElement(getCtx(), COLUMNNAME_EMail) + " - " + toString());
					return false;
				}
				// email with password must be unique on the same tenant
				int cnt = DB.getSQLValue(get_TrxName(),
						"SELECT COUNT(*) FROM AD_User WHERE Password IS NOT NULL AND EMail=? AND AD_Client_ID=? AND AD_User_ID!=?",
						getEMail(), getAD_Client_ID(), getAD_User_ID());
				if (cnt > 0) {
					log.saveError("SaveError", Msg.getMsg(getCtx(), DBException.SAVE_ERROR_NOT_UNIQUE_MSG, true) + Msg.getElement(getCtx(), COLUMNNAME_EMail));
					return false;
				}
			} else {
				
				String nameToValidate = getLDAPUser();
				if (Util.isEmpty(nameToValidate))
					nameToValidate = getName();
				int cnt = DB.getSQLValue(get_TrxName(),
						"SELECT COUNT(*) FROM AD_User WHERE Password IS NOT NULL AND COALESCE(LDAPUser,Name)=? AND AD_Client_ID=? AND AD_User_ID!=?",
						nameToValidate, getAD_Client_ID(), getAD_User_ID());
				if (cnt > 0) {
					log.saveError("SaveError", Msg.getMsg(getCtx(), DBException.SAVE_ERROR_NOT_UNIQUE_MSG, true) + Msg.getElement(getCtx(), COLUMNNAME_Name) + " / " + Msg.getElement(getCtx(), COLUMNNAME_LDAPUser));
					return false;
				}
			}
		}

		boolean hasPassword = ! Util.isEmpty(getPassword());
		if (hasPassword && (newRecord || is_ValueChanged("Password"))) {
			if (! (get_ValueOld("Salt") == null && get_Value("Salt") != null)) { // not being hashed
				MPasswordRule pwdrule = MPasswordRule.getRules(getCtx(), get_TrxName());
				if (pwdrule != null){
					List<MPasswordHistory> passwordHistorys = MPasswordHistory.getPasswordHistoryForCheck(pwdrule.getDays_Reuse_Password(), this.getAD_User_ID());
					pwdrule.validate((getLDAPUser() != null ? getLDAPUser() : getName()), getPassword(), passwordHistorys);
				}
				setDatePasswordChanged(new Timestamp(new Date().getTime()));
			}
		}

		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		if (   hasPassword
			&& is_ValueChanged("Password")
			&& (!newRecord || (hash_password && getSalt() == null))) {
			if (hash_password)
				setPassword(getPassword());
		}
		
		
		return true;
	}	//	beforeSave


	private String checkCreateAcctRegister() {
		String domain = Env.getContext(getCtx(), "#Domain");
		if (!"".equals(domain) && !domain.isEmpty()) {
			String sql = "SELECT count(1) FROM AD_User WHERE AD_Client_ID = ? AND IsActive = 'Y'";
			BigDecimal count = DB.getSQLValueBD(get_TrxName(), sql, Env.getAD_Client_ID(getCtx()));
			MRegister reg = MRegister.getAllDomain(getCtx(), get_TrxName(), domain);
			if (reg != null && X_AD_Register.REGISTERTYPE_RealUse.equals(reg.getRegisterType())) {
				MPackagePrice price = MPackagePrice.get(getCtx(), reg.getAD_PackagePrice_ID(), get_TrxName());
				if (price.getMaxValue().subtract(Env.ONE).compareTo(count) <= 0) {
					return "Với gói hiện tại bạn chỉ đăng ký được tối đa là " + price.getMaxValue() + " người dùng!";
				}
			}
		}
		return "";
	}

	/**
	 * 	Get User that has roles (already authenticated)
	 *	@param ctx context
	 *	@param name name
	 *	@return user or null
	 */
	public static MUser get(Properties ctx, String name) {
		if (name == null || name.length() == 0)
		{
			s_log.warning ("Invalid Name = " + name);
			return null;
		}
		MUser retValue = null;
		int AD_Client_ID = Env.getAD_Client_ID(ctx);

		StringBuilder sql = new StringBuilder("SELECT DISTINCT u.AD_User_ID ")
			.append("FROM AD_User u")
			.append(" INNER JOIN AD_User_Roles ur ON (u.AD_User_ID=ur.AD_User_ID AND ur.IsActive='Y')")
			.append(" INNER JOIN AD_Role r ON (ur.AD_Role_ID=r.AD_Role_ID AND r.IsActive='Y') ");
		sql.append("WHERE u.Password IS NOT NULL AND ur.AD_Client_ID=? AND ");		//	#1/2
		boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
		if (email_login)
			sql.append("u.EMail=?");
		else
			sql.append("COALESCE(u.LDAPUser,u.Value)=?");
		sql.append(" AND u.IsActive='Y'").append(" AND EXISTS (SELECT * FROM AD_Client c WHERE u.AD_Client_ID=c.AD_Client_ID AND c.IsActive='Y')");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), null);
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString (2, name);
			rs = pstmt.executeQuery ();
			if (rs.next())
			{
				retValue = MUser.get(ctx, rs.getInt(1));
				if (rs.next())
					s_log.warning ("More then one user with Name/Password = " + name);
			}
			else
				s_log.fine("No record");
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}
	
	
	/**
	 * save new pass to history
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (getPassword() != null && getPassword().length() > 0 && (newRecord || is_ValueChanged("Password"))) {
			MPasswordRule pwdrule = MPasswordRule.getRules(getCtx(), get_TrxName());
			if (pwdrule != null && pwdrule.getDays_Reuse_Password() > 0) {
				boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
				if (! hash_password) {
					log.severe("Saving password history: it is strongly encouraged to save password history just when using hashed passwords - WARNING! table AD_Password_History is possibly keeping plain passwords");
				}
				MPasswordHistory passwordHistory = new MPasswordHistory(this.getCtx(), 0, this.get_TrxName());
				passwordHistory.setSalt(this.getSalt());
				passwordHistory.setPassword(this.getPassword());
				if (!this.is_new() && this.getAD_User_ID() == 0){
					passwordHistory.set_Value(MPasswordHistory.COLUMNNAME_AD_User_ID, 0);
				}else{
					passwordHistory.setAD_User_ID(this.getAD_User_ID());
				}
				passwordHistory.setDatePasswordChanged(this.getUpdated());
				passwordHistory.saveEx();
			}
		}
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean postDelete() {
		if (getAD_Image_ID() > 0) {
			MImage img = new MImage(getCtx(), getAD_Image_ID(), get_TrxName());
			if (!img.delete(true)) {
				log.warning("Associated image could not be deleted for user - AD_Image_ID=" + getAD_Image_ID());
				return false;
			}
		}
		return true;
	}

}	//	MUser
