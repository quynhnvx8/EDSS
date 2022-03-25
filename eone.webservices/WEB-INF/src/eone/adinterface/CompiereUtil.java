
package eone.adinterface;


import java.util.logging.Level;

import eone.EOne;
import eone.util.CLogger;


public class CompiereUtil {

	private static final CLogger	   log = CLogger.getCLogger(CompiereUtil.class);	
	private static boolean          s_initOK    = false;

	/**
	 * @return startup idempiere environment if needed
	 */
	public static boolean initWeb()
	{
		if (s_initOK)
		{
			return true;
		}
		
		try
		{
			if (log.isLoggable(Level.INFO)) log.info("Starting webservices iDempiere session");
			s_initOK = EOne.startup(false);
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "startup", ex); 
		}
		if (!s_initOK)
			return false;

		return s_initOK;
	}
}
