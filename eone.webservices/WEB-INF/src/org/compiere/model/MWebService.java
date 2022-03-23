

package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.model.X_WS_WebService;
import eone.base.model.X_WS_WebServiceMethod;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;

/**
 *	Web Services Model
 *	
 *  @author Carlos Ruiz
 */
public class MWebService extends X_WS_WebService
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3561409141850981248L;

	/**
	 * 	Get MWebService from Cache
	 *	@param ctx context
	 * 	@param WS_WebService_ID id
	 *	@return MWebService
	 */
	public static MWebService get (Properties ctx, int WS_WebService_ID)
	{
		Integer key = Integer.valueOf(WS_WebService_ID);
		MWebService retValue = (MWebService) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MWebService (ctx, WS_WebService_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get WebService from Cache
	 *	@param ctx context
	 *	@param webServiceValue
	 *	@return Table
	 */
	public static synchronized MWebService get (Properties ctx, String webServiceValue)
	{
		if (webServiceValue == null)
			return null;
		Iterator<MWebService> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			MWebService retValue = it.next();
			if (webServiceValue.equals(retValue.getValue())) 
				return retValue;
		}
		//
		MWebService retValue = null;
		String sql = "SELECT * FROM WS_WebService WHERE Value=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, webServiceValue);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MWebService (ctx, rs, null);
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (retValue != null)
		{
			Integer key = Integer.valueOf(retValue.getWS_WebService_ID());
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get

	/**	Methods				*/
	private X_WS_WebServiceMethod[]	m_methods = null;
	
	/**
	 * 	Get Methods
	 *	@param requery requery
	 *	@return array of methods
	 */
	public X_WS_WebServiceMethod[] getMethods (boolean requery)
	{
		if (m_methods != null && !requery)
			return m_methods;
		String sql = "SELECT * FROM WS_WebServiceMethod WHERE WS_WebService_ID=? AND IsActive='Y' ORDER BY Value";
		ArrayList<X_WS_WebServiceMethod> list = new ArrayList<X_WS_WebServiceMethod>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getWS_WebService_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new X_WS_WebServiceMethod (getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		m_methods = new X_WS_WebServiceMethod[list.size ()];
		list.toArray (m_methods);
		return m_methods;
	}	//	getMethods

	/**
	 * 	Get Method
	 *	@param methodValue
	 *	@return method if found
	 */
	public X_WS_WebServiceMethod getMethod (String methodValue)
	{
		if (methodValue == null || methodValue.length() == 0)
			return null;
		getMethods(false);
		//
		for (int i = 0; i < m_methods.length; i++)
		{
			if (methodValue.equals(m_methods[i].getValue()))
				return m_methods[i];
		}
		return null;
	}	//	getMethod
	
	/**	Cache						*/
	private static CCache<Integer,MWebService>	s_cache	= new CCache<Integer,MWebService>(Table_Name, 20);
	
	/**	Static Logger	*/
	private static final CLogger	s_log	= CLogger.getCLogger (MWebService.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param WS_WebService_ID
	 *	@param trxName transaction
	 */
	public MWebService (Properties ctx, int WS_WebService_ID, String trxName)
	{
		super (ctx, WS_WebService_ID, trxName);
        /** if (WS_WebService_ID == 0)
        {
			setName (null);
			setValue (null);
			setWS_WebService_ID (0);
        } */
	}	//	MWebService

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWebService (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWebService
	
}	//	MWebService
