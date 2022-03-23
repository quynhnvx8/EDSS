package eone.webui;

import java.io.File;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import eone.EOne;
import eone.base.model.ServerStateChangeEvent;
import eone.base.model.ServerStateChangeListener;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.Ini;
import eone.util.ServerContext;
import eone.util.ServerContextURLHandler;
import eone.webui.session.SessionManager;

public class LoggedSessionListener implements HttpSessionListener, ServletContextListener, ServerStateChangeListener{
	private static Hashtable<String, HttpSession> s_SessionList = new Hashtable<String, HttpSession>();
	private static final CLogger logger = CLogger.getCLogger(LoggedSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent evt) {
		s_SessionList.put(evt.getSession().getId(), evt.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession currSess = evt.getSession();
		if(s_SessionList.containsKey(currSess.getId())){
			s_SessionList.remove(currSess.getId());
		}
		
	}
	
	public static int getCountSession() {
		return s_SessionList.size();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DestroyAllSession();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		DestroyAllSession();
		
		// bring from depricate class WebUIServlet
		/** Initialise context for the current thread*/
        Properties serverContext = new Properties();
        serverContext.put(ServerContextURLHandler.SERVER_CONTEXT_URL_HANDLER, new ServerContextURLHandler() {
			public void showURL(String url) {
				SessionManager.getAppDesktop().showURL(url, true);
			}
		});
        ServerContext.setCurrentInstance(serverContext);

        String propertyFile = Ini.getFileName(false);
        File file = new File(propertyFile);
        if (!file.exists())
        {
        	throw new IllegalStateException("eone.properties is not setup. PropertyFile="+propertyFile);
        }
        if (!EOne.isStarted())
        {
	        boolean started = EOne.startup(false);
	        if(!started)
	        {
	            throw new EONEException("Could not start iDempiere");
	        }
        }

        logger.log(Level.OFF, "context initialized");
        /**
         * End iDempiere Start
         */
	}
	
	public void DestroyAllSession() {
		if (!EOne.isStarted())
		{
			EOne.addServerStateChangeListener(this);
			return;
		}

		EOne.removeServerStateChangeListener(this);
	}
	

	@Override
	public void stateChange(ServerStateChangeEvent event) {
		DestroyAllSession();
	}
}
