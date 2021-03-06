/**
 * 
 */
package eone.felix.webconsole;

import java.util.logging.Level;

import org.apache.felix.webconsole.WebConsoleSecurityProvider;

import eone.base.model.MUser;
import eone.util.CLogger;
import eone.util.Env;

/**
 * @author hengsin
 *
 */
public class SecurityProviderImpl implements WebConsoleSecurityProvider {

	/**	Logger			*/
	private final static CLogger	log = CLogger.getCLogger(SecurityProviderImpl.class);
	
	/* (non-Javadoc)
	 * @see org.apache.felix.webconsole.WebConsoleSecurityProvider#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public Object authenticate(String username, String password) {
		MUser user = MUser.get(Env.getCtx(), username, password);
		if (user == null)
		{
			log.warning ("User not found: '" + username);
			return null;
		}
		if (!user.isAdministrator() && !user.hasURLFormAccess("/osgi/system/console"))
		{
			log.warning ("User doesn't have access to /osgi/system/console = " + username);
			return null;
		}
		if (log.isLoggable(Level.INFO)) log.info ("Name=" + username);
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see org.apache.felix.webconsole.WebConsoleSecurityProvider#authorize(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean authorize(Object resource, String role) {
		return true;
	}

}
