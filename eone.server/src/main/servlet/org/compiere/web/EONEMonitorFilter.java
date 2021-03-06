
package org.compiere.web;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eone.base.model.MUser;
import eone.util.CLogger;
import eone.util.Env;

import org.apache.commons.codec.binary.Base64; 


public class EONEMonitorFilter implements Filter
{
	
	public EONEMonitorFilter ()
	{
		super ();
		m_authorization = Long.valueOf(System.currentTimeMillis());
	}	

	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());

	/**	Authorization ID				*/
	private static final String		AUTHORIZATION = "AdempiereAuthorization";
	/** Authorization Marker			*/
	private Long					m_authorization = null;
	
	/**
	 * 	Init
	 *	@param config configuration
	 *	@throws ServletException
	 */
	public void init (FilterConfig config)
		throws ServletException
	{
		log.info ("");
	}	//	Init

	/**
	 * 	Filter
	 *	@param request request
	 *	@param response response
	 *	@param chain chain
	 *	@throws IOException
	 *	@throws ServletException
	 */
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		String errorPage = "/error.html";
		boolean pass = false;
		try
		{
			if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse))
			{
				request.getRequestDispatcher(errorPage).forward(request, response);
				return;
			}
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse resp = (HttpServletResponse)response;
			//	Previously checked
			HttpSession session = req.getSession(true);
			Long compare = (Long)session.getAttribute(AUTHORIZATION);
			if (compare != null && compare.compareTo(m_authorization) == 0)
			{
				pass = true;
			}
			else if (checkAuthorization (req.getHeader("Authorization")))
			{
				session.setAttribute(AUTHORIZATION, m_authorization);
				pass = true;
			}
			//	--------------------------------------------
			if (pass)
			{
				chain.doFilter(request, response);
			}
			else
			{
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.setHeader("WWW-Authenticate", "BASIC realm=\"EONE Server\"");
			}
			return;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "filter", e);
		}
		request.getRequestDispatcher(errorPage).forward(request, response);
	}	//	doFilter

	/**
	 * 	Check Authorization
	 *	@param authorization authorization
	 *	@return true if authenticated
	 */
	private boolean checkAuthorization (String authorization)
	{
		if (authorization == null)
			return false;
		try
		{
			String userInfo = authorization.substring(6).trim();
			Base64 decoder = new Base64();
			String namePassword = new String (decoder.decode(userInfo.getBytes()));
		//	log.fine("checkAuthorization - Name:Password=" + namePassword);
			int index = namePassword.indexOf(':');
			String name = namePassword.substring(0, index);
			String password = namePassword.substring(index+1);
			MUser user = MUser.get(Env.getCtx(), name, password);
			if (user == null)
			{
				log.warning ("User not found: '" + name);
				return false;
			}
			if (!user.isAdministrator() && !user.hasURLFormAccess("/eoneMonitor"))
			{
				log.warning ("User doesn't have access to /eoneMonitor = " + name);
				return false;
			}
			if (log.isLoggable(Level.INFO)) log.info ("Name=" + name);
			return true;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "check", e);
		}
		return false;
	}	//	check
	
	/**
	 * 	Destroy
	 */
	public void destroy ()
	{
		log.info ("");
	}	//	destroy

}	
