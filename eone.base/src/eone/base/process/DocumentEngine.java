
package eone.base.process;

import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.osgi.service.event.Event;

import eone.base.acct.DocManager;
import eone.base.event.EventManager;
import eone.base.event.EventProperty;
import eone.base.event.IEventTopics;
import eone.base.model.PO;
import eone.base.model.SystemIDs;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
/*
 import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
 */

public class DocumentEngine implements DocAction
{

	
	public DocumentEngine (DocAction po, String docStatus, int AD_Window_ID, boolean posted)
	{
		/*
		HttpRequest request = null;
		HttpResponse<?> response;
		try {
			request = HttpRequest.newBuilder()
						.uri(new URI(""))
						.version(HttpClient.Version.HTTP_1_1)
						.GET()
						.build();
			response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		m_document = po;
		if (docStatus != null)
			m_status = docStatus;
		
		m_posted = posted; 
		
		this.AD_Window_ID = AD_Window_ID;
	}	

	private DocAction	m_document;
	private String		m_status = STATUS_Drafted;
	private String		m_message = null;
	private String		m_action = null;
	private static boolean m_posted; 
	private static CLogger log = CLogger.getCLogger(DocumentEngine.class);

	/**
	 * 	Get Doc Status
	 *	@return document status
	 */
	public String getDocStatus()
	{
		return m_status;
	}	//	getDocStatus

	@Override
	public void setDocStatus(String ignored)
	{
		
	}	//	setDocStatus

	
	public boolean isDrafted()
	{
		return STATUS_Drafted.equals(m_status);
	}	//	isDrafted

	
	
	/**
	 * 	Document is Completed
	 *	@return true if Completed
	 */
	public boolean isCompleted()
	{
		return STATUS_Completed.equals(m_status);
	}	//	isCompleted

	public boolean isInprogress()
	{
		return STATUS_Inprogress.equals(m_status);
	}
	
	public boolean processIt (String processAction, String docStatus)
	{
		
		m_message = null;
		m_action = null;

		if (isValidAction(processAction))	
			m_action = processAction;
		//
		else if (isValidAction(docStatus))	
			m_action = docStatus;
		//	Nothing to do
		
		else
		{
			throw new IllegalStateException("Status=" + getDocStatus()
				+ " - Invalid Actions: Process="  + processAction + ", Doc=" + docStatus);
		}
		if (m_document != null)
			m_document.get_Logger().info ("**** Action=" + m_action + " (Prc=" + processAction + "/Doc=" + docStatus + ") " + m_document);
		boolean success = processIt (m_action, AD_Window_ID);
		if (m_document != null)
			m_document.get_Logger().fine("**** Action=" + m_action + " - Success=" + success);
		return success;
	}	//	process

	
	public boolean processIt (String action, int AD_Window_ID)
	{
		m_message = null;
		m_action = action;
		
		if (STATUS_Completed.equals(m_action) || STATUS_Inprogress.equals(m_action))
		{
			String status = completeIt();
			boolean ok =   STATUS_Completed.equals(status) || STATUS_Inprogress.equals(status);
			if (m_document != null && ok)
			{
				m_document.saveEx();
				if (m_posted) {
					boolean retVal = postIt();
					if (! retVal) {
						return false;
					}
				}
			}
			return ok;
		}
		if (STATUS_ReActivate.equals(m_action))
			return reActivateIt();
		
		return false;
	}	//	processDocument

	
	
	public String completeIt()
	{
		if (!isValidAction(STATUS_Completed))
			return m_status;
		if (m_document != null)
		{
			m_status = m_document.completeIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	//	completeIt

	/**
	 * 	Post Document
	 * 	Does not change status
	 * 	@return true if success
	 */
	public boolean postIt()
	{
		if (m_document == null)
			return false;
		String error = DocManager.postDocument(m_document.get_Table_ID(), m_document.get_ID(), m_document.get_TrxName(), AD_Window_ID);
		m_document.setProcessMsg(error);
		return (error == null);
	}	//	postIt

	
	public boolean reActivateIt()
	{
		if (!isValidAction(STATUS_ReActivate))
			return false;
		if (m_document != null)
		{
			if (m_document.reActivateIt())
			{
				return true;
			}
			return false;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	reActivateIt


	/**
	 * 	Set Document Status to new Status
	 *	@param newStatus new status
	 */
	void setStatus (String newStatus)
	{
		m_status = newStatus;
	}	//	setStatus


	/**************************************************************************
	 * 	Get Action Options based on current Status
	 *	@return array of actions
	 */
	public String[] getActionOptions()
	{
		
		if (isDrafted())
			return new String[] {STATUS_Completed, STATUS_Inprogress};

		
		if (isCompleted())
			return new String[] {STATUS_ReActivate};
		
		if (isInprogress())
			return new String[] {STATUS_Completed, STATUS_Inprogress};

		return new String[] {};
	}	//	getActionOptions

	/**
	 * 	Is The Action Valid based on current state
	 *	@param action action
	 *	@return true if valid
	 */
	public boolean isValidAction (String action)
	{
		String[] options = getActionOptions();
		for (int i = 0; i < options.length; i++)
		{
			if (options[i].equals(action))
				return true;
		}
		return false;
	}	//	isValidAction

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg ()
	{
		return m_message;
	}	//	getProcessMsg

	/**
	 * 	Get Process Message
	 *	@param msg clear text error message
	 */
	public void setProcessMsg (String msg)
	{
		m_message = msg;
	}	//	setProcessMsg


	/**	Document Exception Message		*/
	private static String EXCEPTION_MSG = "Document Engine is no Document";

	/*************************************************************************
	 * 	Get Summary
	 *	@return throw exception
	 */
	public String getSummary()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	
	/**
	 * 	Save Document
	 *	@return throw exception
	 */
	public void saveEx() throws EONEException
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Context
	 *	@return context
	 */
	public Properties getCtx()
	{
		if (m_document != null)
			return m_document.getCtx();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	getCtx

	/**
	 * 	Get ID of record
	 *	@return ID
	 */
	public int get_ID()
	{
		if (m_document != null)
			return m_document.get_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_ID

	/**
	 * 	Get AD_Table_ID
	 *	@return AD_Table_ID
	 */
	public int get_Table_ID()
	{
		if (m_document != null)
			return m_document.get_Table_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_Table_ID

	/**
	 * 	Get Logger
	 *	@return logger
	 */
	public CLogger get_Logger()
	{
		if (m_document != null)
			return m_document.get_Logger();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_Logger

	/**
	 * 	Get Transaction
	 *	@return trx name
	 */
	public String get_TrxName()
	{
		return null;
	}	//	get_TrxName

	/**
	 * 	CreatePDF
	 *	@return null
	 */
	public File createPDF ()
	{
		return null;
	}

	/**
	 * Get list of valid document action into the options array parameter.
	 * Set default document action into the docAction array parameter.
	 */
	public static int getValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, boolean periodOpen, PO po)
	{
		if (options == null)
			throw new IllegalArgumentException("Option array parameter is null");
		if (docAction == null)
			throw new IllegalArgumentException("Doc action array parameter is null");

		int index = 0;


		if (docStatus.equals(DocumentEngine.STATUS_Drafted))
		{
			options[index++] = DocumentEngine.STATUS_Completed;
		}
		

		if (po instanceof DocOptions)
			index = ((DocOptions) po).customizeValidActions(docStatus, processing, orderType, isSOTrx,
					AD_Table_ID, docAction, options, index);

		AtomicInteger indexObj = new AtomicInteger(index);
		ArrayList<String> docActionsArray = new ArrayList<String>(Arrays.asList(docAction));
		ArrayList<String> optionsArray = new ArrayList<String>(Arrays.asList(options));
		DocActionEventData eventData = new DocActionEventData(docStatus, processing, orderType, isSOTrx, AD_Table_ID, docActionsArray, optionsArray, indexObj, po);
		Event event = EventManager.newEvent(IEventTopics.DOCACTION,
				new EventProperty(EventManager.EVENT_DATA, eventData),
				new EventProperty("tableName", po.get_TableName()));
		EventManager.getInstance().sendEvent(event);
		index = indexObj.get();
		for (int i = 0; i < optionsArray.size(); i++)
			options[i] = optionsArray.get(i);
		for (int i = 0; i < docActionsArray.size(); i++)
			docAction[i] = docActionsArray.get(i);

		return index;
	}

	/**
	 * Fill Vector with DocAction Ref_List(135) values
	 * @param v_value
	 * @param v_name
	 * @param v_description
	 */
	public static void readReferenceList(ArrayList<String> v_value, ArrayList<String> v_name,
			ArrayList<String> v_description)
	{
		if (v_value == null)
			throw new IllegalArgumentException("v_value parameter is null");
		if (v_name == null)
			throw new IllegalArgumentException("v_name parameter is null");
		if (v_description == null)
			throw new IllegalArgumentException("v_description parameter is null");

		String sql;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
			sql = "SELECT Value, Name, Description FROM AD_Ref_List "
				+ "WHERE AD_Reference_ID=? ORDER BY Name";
		else
			sql = "SELECT l.Value, t.Name, t.Description "
				+ "FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
				+ " AND l.AD_Reference_ID=? ORDER BY t.Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, DocAction.AD_REFERENCE_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				if (description == null)
					description = "";
				//
				v_value.add(value);
				v_name.add(name);
				v_description.add(description);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	public static void readReferenceListSign(ArrayList<String> v_value, ArrayList<String> v_name)
	{
		if (v_value == null)
			throw new IllegalArgumentException("v_value parameter is null");
		if (v_name == null)
			throw new IllegalArgumentException("v_name parameter is null");
		
		String sql;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
			sql = "SELECT Value, Name, Description FROM AD_Ref_List "
				+ "WHERE AD_Reference_ID=? ORDER BY Name";
		else
			sql = "SELECT l.Value, t.Name, t.Description "
				+ "FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
				+ " AND l.AD_Reference_ID=? ORDER BY t.Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, DocAction.AD_REFERENCE_SIGN_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				if (description == null)
					description = "";
				//
				v_value.add(value);
				v_name.add(name);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	
	public static void readStatusReferenceList(ArrayList<String> v_value, ArrayList<String> v_name,
			ArrayList<String> v_description)
	{
		if (v_value == null)
			throw new IllegalArgumentException("v_value parameter is null");
		if (v_name == null)
			throw new IllegalArgumentException("v_name parameter is null");
		if (v_description == null)
			throw new IllegalArgumentException("v_description parameter is null");

		String sql;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
			sql = "SELECT Value, Name, Description FROM AD_Ref_List "
				+ "WHERE AD_Reference_ID=? ORDER BY Name";
		else
			sql = "SELECT l.Value, t.Name, t.Description "
				+ "FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
				+ " AND l.AD_Reference_ID=? ORDER BY t.Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, SystemIDs.REFERENCE_DOCUMENTSTATUS);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				if (description == null)
					description = "";
				//
				v_value.add(value);
				v_name.add(name);
				v_description.add(description);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private int AD_Window_ID = 0;
	@Override
	public int getAD_Window_ID() {
		return AD_Window_ID;
		
	}
}	//	DocumentEnine
