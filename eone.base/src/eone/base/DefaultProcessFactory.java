
package eone.base;

import java.util.logging.Level;

import eone.base.equinox.EquinoxExtensionLocator;
import eone.base.process.ProcessCall;
import eone.util.CLogger;

/**
 * @author hengsin
 *
 */
public class DefaultProcessFactory implements IProcessFactory {

	private final static CLogger log = CLogger.getCLogger(DefaultProcessFactory.class);
	
	/**
	 * default constructor
	 */
	public DefaultProcessFactory() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IProcessFactory#newProcessInstance(java.lang.String)
	 */
	@Override
	public ProcessCall newProcessInstance(String className) {
		ProcessCall process = null;
		process = EquinoxExtensionLocator.instance().locate(ProcessCall.class, "org.adempiere.base.Process", className, null).getExtension();
		if (process == null) {
			//Get Class
			Class<?> processClass = null;
			//use context classloader if available
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader != null)
			{
				try
				{
					processClass = classLoader.loadClass(className);
				}
				catch (ClassNotFoundException ex)
				{
					if (log.isLoggable(Level.FINE))log.log(Level.FINE, className, ex);
				}
			}
			if (processClass == null)
			{
				classLoader = this.getClass().getClassLoader();
				try
				{
					processClass = classLoader.loadClass(className);
				}
				catch (ClassNotFoundException ex)
				{
					log.log(Level.WARNING, className, ex);
					return null;
				}
			}

			if (processClass == null) {
				return null;
			}

			//Get Process
			try
			{
				process = (ProcessCall)processClass.getDeclaredConstructor().newInstance();
			}
			catch (Exception ex)
			{
				log.log(Level.WARNING, "Instance for " + className, ex);
				return null;
			}
		}
		return process;
	}

}
