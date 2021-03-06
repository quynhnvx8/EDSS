
package eone.base.model;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.internet.InternetAddress;

import eone.db.CConnection;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.EMail;
import eone.util.Env;
import eone.util.Language;

/**
 *  Client Model
 *
 *  @author Quynhnv.x8 Mod 08.01.2022
 *  @version $Id: MClient.java,v 2
 *
 */
public class MClient extends X_AD_Client
{
	private static final long serialVersionUID = 8418331925351272377L;

	public static MClient get (Properties ctx, int AD_Client_ID)
	{
		Integer key = Integer.valueOf(AD_Client_ID);
		MClient client = (MClient)s_cache.get(key);
		if (client != null)
			return client;
		client = new MClient (ctx, AD_Client_ID, null);
		s_cache.put (key, client);
		return client;
	}	//	get

	/**
	 * 	Get all clients
	 *	@param ctx context
	 *	@return clients
	 */
	public static MClient[] getAll (Properties ctx)
	{
		return getAll(ctx, "");
	}	//	getAll

	/**
	 * 	Get all clients
	 *	@param ctx context
	 *	@param order by clause
	 *	@return clients
	 */
	public static MClient[] getAll (Properties ctx, String orderBy)
	{
		List<MClient> list = new Query(ctx,I_AD_Client.Table_Name,null,null, false)
		.setOrderBy(orderBy)
		.list();
		for(MClient client:list ){
			s_cache.put (Integer.valueOf(client.getAD_Client_ID()), client);
		}
		MClient[] retValue = new MClient[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getAll

	/**
	 * 	Get optionally cached client
	 *	@param ctx context
	 *	@return client
	 */
	public static MClient get (Properties ctx)
	{
		return get (ctx, Env.getAD_Client_ID(ctx));
	}	//	get

	/**	Static Logger				*/
	@SuppressWarnings("unused")
	private static CLogger	s_log	= CLogger.getCLogger (MClient.class);
	/**	Cache						*/
	private static CCache<Integer,MClient>	s_cache = new CCache<Integer,MClient>(Table_Name, 3, 120, true);


	/**************************************************************************
	 * 	Standard Constructor
	 * 	@param ctx context
	 * 	@param AD_Client_ID id
	 * 	@param createNew create new
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, int AD_Client_ID, boolean createNew, String trxName)
	{
		super (ctx, AD_Client_ID, trxName);
		m_createNew = createNew;
		if (AD_Client_ID == 0)
		{
			if (m_createNew)
			{
				setAD_Org_ID(0);
				setIsSmtpAuthorization (false);
				setIsServerEMail(false);
				setAD_Language(Language.getBaseAD_Language());
				setAutoArchive(AUTOARCHIVE_None);
				setMMPolicy (MMPOLICY_FiFo);	// F
			}
			else
				load(get_TrxName());
		}
	}	//	MClient

	/**
	 * 	Standard Constructor
	 * 	@param ctx context
	 * 	@param AD_Client_ID id
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, int AD_Client_ID, String trxName)
	{
		this (ctx, AD_Client_ID, false, trxName);
	}	//	MClient

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MClient

	/**
	 * 	Simplified Constructor
	 * 	@param ctx context
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, String trxName)
	{
		this (ctx, Env.getAD_Client_ID(ctx), trxName);
	}	//	MClient

	/**	Client Info					*/
	private MClientInfo 		m_info = null;
	/** Language					*/
	private Language			m_language = null;
	/** New Record					*/
	private boolean				m_createNew = false;
	/** Client Info Setup Tree for Account	*/
	private int					m_AD_Tree_Account_ID;

	/**
	 *	Get Client Info
	 *	@return Client Info
	 */
	public MClientInfo getInfo()
	{
		if (m_info == null)
			m_info = MClientInfo.get (getCtx(), getAD_Client_ID(), get_TrxName());
		return m_info;
	}	//	getMClientInfo

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder ("MClient[")
			.append(get_ID()).append("-").append(getValue())
			.append("]");
		return sb.toString();
	}	//	toString

	/**
	 *	Get Default Accounting Currency
	 *	@return currency or 0
	 */
	public int getC_Currency_ID()
	{
		if (m_info == null)
			getInfo();
		if (m_info != null)
			return m_info.getC_Currency_ID();
		return 0;
	}	//	getC_Currency_ID

	/**
	 * 	Get Language
	 *	@return client language
	 */
	public Language getLanguage()
	{
		if (m_language == null)
		{
			m_language = Language.getLanguage(getAD_Language());
			Env.verifyLanguage (getCtx(), m_language);
		}
		return m_language;
	}	//	getLanguage


	/**
	 * 	Set AD_Language
	 *	@param AD_Language new language
	 */
	public void setAD_Language (String AD_Language)
	{
		m_language = null;
		super.setAD_Language (AD_Language);
	}	//	setAD_Language

	/**
	 * 	Get AD_Language
	 *	@return Language
	 */
	public String getAD_Language ()
	{
		String s = super.getAD_Language ();
		if (s == null)
			return Language.getBaseAD_Language();
		return s;
	}	//	getAD_Language

	/**
	 * 	Get Locale
	 *	@return locale
	 */
	public Locale getLocale()
	{
		Language lang = getLanguage();
		if (lang != null)
			return lang.getLocale();
		return Locale.getDefault();
	}	//	getLocale


	/**************************************************************************
	 * 	Create Trees and Setup Client Info
	 * 	@param language language
	 * 	@return true if created
	 */
	public boolean setupClientInfo (String language)
	{
		//	Create Trees
		StringBuilder sql = null;
		if (Env.isBaseLanguage (language, "AD_Ref_List"))	//	Get TreeTypes & Name
			sql = new StringBuilder("SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID=120 AND IsActive='Y'");
		else
			sql = new StringBuilder("SELECT l.Value, t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t ")
				.append("WHERE l.AD_Reference_ID=120 AND l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.IsActive='Y'")
				.append(" AND t.AD_Language=").append(DB.TO_STRING(language));

		//  Tree IDs
		int AD_Tree_Org_ID=0, AD_Tree_BPartner_ID=0, AD_Tree_Project_ID=0,
			AD_Tree_SalesRegion_ID=0, AD_Tree_Product_ID=0,
			AD_Tree_Campaign_ID=0, AD_Tree_Activity_ID=0;

		boolean success = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = stmt.executeQuery();
			MTree tree = null;
			while (rs.next())
			{
				String value = rs.getString(1);
				StringBuilder name = new StringBuilder().append(getName()).append(" ").append(rs.getString(2));
				tree = new MTree (this, name.toString(), value);
				success = tree.save();
				if (!success)
				{
					log.log(Level.SEVERE, "Tree NOT created: " + name);
					break;
				}
			}
		}
		catch (SQLException e1)
		{
			log.log(Level.SEVERE, "Trees", e1);
			success = false;
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}

		if (!success)
			return false;

		//	Create ClientInfo
		MClientInfo clientInfo = new MClientInfo (this,
			AD_Tree_Org_ID, AD_Tree_BPartner_ID, AD_Tree_Project_ID,
			AD_Tree_SalesRegion_ID, AD_Tree_Product_ID,
			AD_Tree_Campaign_ID, AD_Tree_Activity_ID, get_TrxName());
		success = clientInfo.save();
		return success;
	}	//	createTrees

	/**
	 * 	Get AD_Tree_Account_ID created in setup client info
	 *	@return Account Tree ID
	 */
	public int getSetup_AD_Tree_Account_ID()
	{
		return m_AD_Tree_Account_ID;
	}	//	getSetup_AD_Tree_Account_ID

	/**
	 * 	Is Auto Archive on
	 *	@return true if auto archive
	 */
	public boolean isAutoArchive()
	{
		String aa = getAutoArchive();
		return aa != null && !aa.equals(AUTOARCHIVE_None);
	}	//	isAutoArchive

	
	public boolean save ()
	{
		if (get_ID() == 0 && !m_createNew)
			return saveUpdate();
		return super.save ();
	}	//	save


	/**************************************************************************
	 * 	Test EMail
	 *	@return OK or error
	 */
	public String testEMail()
	{
		if (getRequestEMail() == null || getRequestEMail().length() == 0){
			StringBuilder msgreturn = new StringBuilder("No Request EMail for ").append(getName());
			return msgreturn.toString();
		}	
		//
		String systemName = MSystem.get(getCtx()).getName();
		StringBuilder msgce = new StringBuilder(systemName).append(" EMail Test: ").append(toString());
		EMail email = createEMail (getRequestEMail(),
				systemName + " EMail Test",msgce.toString());
		if (email == null){
			StringBuilder msgreturn = new StringBuilder("Could not create EMail: ").append(getName());
			return msgreturn.toString();
		}	
		try
		{
			String msg = null;
			if (isServerEMail())
			{
				msg = CConnection.get().getServer().sendEMail(Env.getRemoteCallCtx(Env.getCtx()), email);
			}
			else
			{
				msg = email.send();
			}
			if (EMail.SENT_OK.equals (msg))
			{
				if (log.isLoggable(Level.INFO)) log.info("Sent Test EMail to " + getRequestEMail());
				return "OK";
			}
			else
			{
				log.warning("Could NOT send Test EMail from "
					+ getSMTPHost() + ": " + getRequestEMail()
					+ " (" + getRequestUser()
					+ ") to " + getRequestEMail() + ": " + msg);
				return msg;
			}
		}
		catch (Exception ex)
		{
			log.severe(getName() + " - " + ex.getLocalizedMessage());
			return ex.getLocalizedMessage();
		}
	}	//	testEMail

	/**
	 * 	Send EMail from Request User - with trace
	 *	@param AD_User_ID recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *	@return true if sent
	 */
	public boolean sendEMail (int AD_User_ID,
			String subject, String message, File attachment)
	{
		Collection<File> attachments = new ArrayList<File>();
		if (attachment != null)
			attachments.add(attachment);
		return true; //sendEMailAttachments(AD_User_ID, subject, message, attachments);
	}

	/**
	 * 	Send EMail from Request User - with trace
	 *	@param AD_User_ID recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional collection of attachments
	 *	@return true if sent
	 */
	public boolean sendEMailAttachments (int AD_User_ID,
		String subject, String message, Collection<File> attachments)
	{
		return sendEMailAttachments(AD_User_ID, subject, message, attachments, false);
	}

	/**
	 * 	Send EMail from Request User - with trace
	 *	@param AD_User_ID recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional collection of attachments
	 *  @param html
	 *	@return true if sent
	 */
	public boolean sendEMailAttachments (int AD_User_ID,
		String subject, String message, Collection<File> attachments, boolean html)
	{
		MUser to = MUser.get(getCtx(), AD_User_ID);
		String toEMail = to.getEMail();
		if (toEMail == null || toEMail.length() == 0)
		{
			log.warning("No EMail for recipient: " + to);
			return false;
		}
		EMail email = createEMail(null, to, subject, message, html);
		if (email == null)
			return false;
		email.addAttachments(attachments);
		try
		{
			return sendEmailNow(null, to, email);
		}
		catch (Exception ex)
		{
			log.severe(getName() + " - " + ex.getLocalizedMessage());
			return false;
		}
	}	//	sendEMail

	/**
	 * 	Send EMail from User
	 * 	@param from sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *	@return true if sent
	 */
	public boolean sendEMailAttachments (MUser from, MUser to,
		String subject, String message, List<File> attachments)
	{
		return sendEMailAttachments(from, to, subject, message, attachments, false);
	}

	/**
	 * 	Send EMail from User
	 * 	@param from sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *  @param isHtml
	 *	@return true if sent
	 */
	public boolean sendEMailAttachments (MUser from, MUser to,
		String subject, String message, List<File> attachments, boolean isHtml)
	{
		EMail email = createEMail(from, to, subject, message, isHtml);
		if (email == null)
			return false;

		if (attachments != null && !attachments.isEmpty())
		{
			for (File attachment : attachments)
				email.addAttachment(attachment);
		}
		InternetAddress emailFrom = email.getFrom();
		try
		{
			return sendEmailNow(from, to, email);
		}
		catch (Exception ex)
		{
			log.severe(getName() + " - from " + emailFrom
				+ " to " + to + ": " + ex.getLocalizedMessage());
			return false;
		}
	}	//	sendEMail
	
	/**
	 * 	Send EMail from Request User - no trace
	 *	@param to recipient email address
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *	@return true if sent
	 */
	public boolean sendEMail (String to,
		String subject, String message, File attachment)
	{
		return sendEMail(to, subject, message, attachment, false);
	}

	/**
	 * 	Send EMail from Request User - no trace
	 *	@param to recipient email address
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *  @param html
	 *	@return true if sent
	 */
	public boolean sendEMail (String to,
		String subject, String message, File attachment, boolean html)
	{
		return true;
		/*
		EMail email = createEMail(to, subject, message, html);
		if (email == null)
			return false;
		if (attachment != null)
			email.addAttachment(attachment);
		try
		{
			String msg = null;
			if (isServerEMail())
			{
				msg = CConnection.get().getServer().sendEMail(Env.getRemoteCallCtx(Env.getCtx()), email);
			}
			else
			{
				msg = email.send();
			}
			if (EMail.SENT_OK.equals (msg))
			{
				if (log.isLoggable(Level.INFO)) log.info("Sent EMail " + subject + " to " + to);
				return true;
			}
			else
			{
				log.warning("Could NOT Send Email: " + subject
					+ " to " + to + ": " + msg
					+ " (" + getName() + ")");
				return false;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			log.log(Level.SEVERE, getName() + " - " + ex.getLocalizedMessage(), ex);
			return false;
		}
		*/
	}	//	sendEMail

	/**
	 * 	Send EMail from User
	 * 	@param from sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *	@return true if sent
	 */
	public boolean sendEMail (MUser from, MUser to,
		String subject, String message, File attachment)
	{
		return sendEMail(from, to, subject, message, attachment, false);
	}

	/**
	 * 	Send EMail from User
	 * 	@param from sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 *  @param isHtml
	 *	@return true if sent
	 */
	public boolean sendEMail (MUser from, MUser to,
		String subject, String message, File attachment, boolean isHtml)
	{
		/*
		EMail email = createEMail(from, to, subject, message, isHtml);
		if (email == null)
			return false;

		if (attachment != null)
			email.addAttachment(attachment);
		InternetAddress emailFrom = email.getFrom();
		try
		{
			return sendEmailNow(from, to, email);
		}
		catch (Exception ex)
		{
			log.severe(getName() + " - from " + emailFrom
				+ " to " + to + ": " + ex.getLocalizedMessage());
			return false;
		}
		*/
		return true;
	}	//	sendEMail

	/**
	 * 	Send Email Now
	 *	@param from optional from user
	 *	@param to to user
	 *	@param email email
	 *	@return true if sent
	 */
	public boolean sendEmailNow(MUser from, MUser to, EMail email)
	{
		String msg = null;
		if (isServerEMail())
		{
			msg = CConnection.get().getServer().sendEMail(Env.getRemoteCallCtx(Env.getCtx()), email);
		}
		else
		{
			msg = email.send();
		}
		//
		X_AD_UserMail um = new X_AD_UserMail(getCtx(), 0, to.get_TrxName());
		um.setAD_User_ID(to.getAD_User_ID());
		um.setSubject(email.getSubject());
		um.setMailText(email.getMessageCRLF());
		um.setMailText(email.getMessageCRLF());
		um.setEMailFrom(email.getFrom().toString());
		um.setRecipientTo(MUserMail.getRecipientWithCommaSeparator(email.getTos()));
		um.setRecipientCc(MUserMail.getRecipientWithCommaSeparator(email.getCcs()));
		um.setRecipientBcc(MUserMail.getRecipientWithCommaSeparator(email.getBccs()));
		if (email.isSentOK())
			um.setMessageID(email.getMessageID());
		else
		{
			um.setMessageID(email.getSentMsg());
			um.setIsDelivered(X_AD_UserMail.ISDELIVERED_No);
		}
		um.saveEx();

		//
		if (email.isSentOK())
		{
			if (from != null) {
				if (log.isLoggable(Level.INFO)) log.info("Sent Email: " + email.getSubject()
					+ " from " + from.getEMail()
					+ " to " + to.getEMail());
			} else {
				if (log.isLoggable(Level.INFO)) log.info("Sent Email: " + email.getSubject()
						+ " to " + to.getEMail());
			}
			return true;
		}
		else
		{
			if (from != null)
				log.warning("Could NOT Send Email: " + email.getSubject()
					+ " from " + from.getEMail()
					+ " to " + to.getEMail() + ": " + msg
					+ " (" + getName() + ")");
			else
				log.warning("Could NOT Send Email: " + email.getSubject()
					+ " to " + to.getEMail() + ": " + msg
					+ " (" + getName() + ")");
			return false;
		}
	}	//	sendEmailNow

	/************
	 * 	Create EMail from Request User
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@return EMail
	 */
	public EMail createEMail (String to,
		String subject, String message)
	{
		return createEMail(to, subject, message, false);
	}

	/************
	 * 	Create EMail from Request User
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *  @param html
	 *	@return EMail
	 */
	public EMail createEMail (String to,
		String subject, String message, boolean html)
	{
		if (to == null || to.length() == 0)
		{
			log.warning("No To");
			return null;
		}
		//
		EMail email = new EMail (this,
				   getRequestEMail(), to,
				   subject, message, html);
		if (isSmtpAuthorization())
			email.createAuthenticator (getRequestUser(), getRequestUserPW());
		return email;
	}	//	createEMail

	/************
	 * 	Create EMail with a specific from address
	 *	@param from recipient
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *  @param html
	 *	@return EMail
	 */
	public EMail createEMailFrom (String from, String to,
		String subject, String message, boolean html)
	{
		if (from == null || from.length() == 0)
		{
			log.warning("No From");
			return null;
		}
		if (to == null || to.length() == 0)
		{
			log.warning("No To");
			return null;
		}
		//
		EMail email = new EMail (this,
				   from, to,
				   subject, message, html);
		if (isSmtpAuthorization())
			email.createAuthenticator (getRequestUser(), getRequestUserPW());
		return email;
	}	//	createEMailFrom

	/**
	 * 	Create EMail from User
	 * 	@param from optional sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@return EMail
	 */
	public EMail createEMail (MUser from, MUser to,
		String subject, String message)
	{
		return createEMail(from, to, subject, message, false);
	}

	/**
	 * 	Create EMail from User
	 * 	@param from optional sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *  @param html
	 *	@return EMail
	 */
	public EMail createEMail (MUser from, MUser to,
		String subject, String message, boolean html)
	{
		if (to == null)
		{
			log.warning("No To user");
			return null;
		}
		if (to.getEMail() == null || to.getEMail().length() == 0)
		{
			log.warning("No To address: " + to);
			return null;
		}
		return createEMail (from, to.getEMail(), subject, message, html);
	}	//	createEMail

	/**
	 * 	Create EMail from User
	 * 	@param from optional sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *	@return EMail
	 */
	public EMail createEMail (MUser from, String to,
		String subject, String message)
	{
		return createEMail(from, to, subject, message, false);
	}

	/**
	 * 	Create EMail from User
	 * 	@param from optional sender
	 *	@param to recipient
	 *	@param subject subject
	 *	@param message message
	 *  @param html
	 *	@return EMail
	 */
	public EMail createEMail (MUser from, String to,
		String subject, String message, boolean html)
	{
		if (to == null || to.length() == 0)
		{
			log.warning("No To address");
			return null;
		}
		//	No From - send from Request
		if (from == null)
			return createEMail (to, subject, message, html);
		//	No From details - Error
		if (from.getEMail() == null
			|| from.getEMailUser() == null
			|| (isSmtpAuthorization() && from.getEMailUserPW() == null) ) // is SMTP authorization and password is null - teo_sarca [ 1723309 ]
		{
			log.warning("From EMail incomplete: " + from + " (" + getName() + ")");
			return null;
		}
		//
		EMail email = new EMail (this,
				   from.getEMail(),
				   to,
				   subject,
				   message, html);
		if (isSmtpAuthorization())
			email.createAuthenticator (from.getEMailUser(), from.getEMailUserPW());
		if (from.getEMail() != null && ! from.getEMail().equalsIgnoreCase(from.getEMailUser())) {
			email.setReplyTo(from.getEMail());
		}
		return email;
	}	//	createEMail

	
	
	public boolean isDisplayField(int aDFieldID) {
		return true;
	}

	@Override
	public String getRequestUser() {
		// IDEMPIERE-722
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			return sysclient.getRequestUser();
		}
		return super.getRequestUser();
	}

	@Override
	public String getRequestUserPW() {
		// IDEMPIERE-722
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			return sysclient.getRequestUserPW();
		}
		return super.getRequestUserPW();
	}

	@Override
	public boolean isSmtpAuthorization() {
		// IDEMPIERE-722
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			return sysclient.isSmtpAuthorization();
		}
		return super.isSmtpAuthorization();
	}

	@Override
	public int getSMTPPort() {
		// IDEMPIERE-722
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			return sysclient.getSMTPPort();
		}
		return super.getSMTPPort();
	}

	@Override
	public boolean isSecureSMTP() {
		// IDEMPIERE-722
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			return sysclient.isSecureSMTP();
		}
		return super.isSecureSMTP();
	}

	/**
	 *	Get SMTP Host
	 *	@return SMTP or loaclhost
	 */
	@Override
	public String getSMTPHost() {
		String s = null;
		if (getAD_Client_ID() != 0 && isSendCredentialsSystem()) {
			MClient sysclient = MClient.get(getCtx(), 0);
			s = sysclient.getSMTPHost();
		} else {
			s = super.getSMTPHost();
		}
		if (s == null)
			s = "localhost";
		return s;
	}	//	getSMTPHost

	// IDEMPIERE-722
	private static final String MAIL_SEND_CREDENTIALS_USER = "U";
	private static final String MAIL_SEND_CREDENTIALS_CLIENT = "C";
	private static final String MAIL_SEND_CREDENTIALS_SYSTEM = "S";

	public static boolean isSendCredentialsClient() {
		String msc = MSysConfig.getValue(MSysConfig.MAIL_SEND_CREDENTIALS,
				MAIL_SEND_CREDENTIALS_USER, // default
				Env.getAD_Client_ID(Env.getCtx()));
		return (MAIL_SEND_CREDENTIALS_CLIENT.equalsIgnoreCase(msc));
	}

	public static boolean isSendCredentialsSystem() {
		String msc = MSysConfig.getValue(MSysConfig.MAIL_SEND_CREDENTIALS,
				MAIL_SEND_CREDENTIALS_USER, // default
				Env.getAD_Client_ID(Env.getCtx()));
		return (MAIL_SEND_CREDENTIALS_SYSTEM.equalsIgnoreCase(msc));
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		//Khi s???a th??ng tin tr??n CLient th?? update cho Org, Dep, Employee, BPartner
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return super.beforeDelete();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return super.afterDelete(success);
	}

	public static List<MClient> getAllClient(Properties ctx, String trxName) {
		List<MClient> arr = new Query(ctx, Table_Name, " IsActive = 'Y'", trxName)
				.list();
		return arr;
	}
}	//	MClient
