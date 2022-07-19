

package eone.base.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import eone.util.CLogger;
import eone.util.Ini;
import eone.util.Util;

/**
 * Request property service
 * @author Elaine
 *
 */
public class RequestPropertyService {

	private static final String REQUESTEVENTHANDLER_PROPERTIES = "requesteventhandler.properties";
	
	private static final CLogger logger = CLogger.getCLogger(RequestPropertyService.class);
	
	public RequestPropertyService() {
	}

	public void bindConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		readProperties(configurationAdmin);
	}
	
	public void unbindConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		
	}
	
	private void readProperties(ConfigurationAdmin service) {
		File file = new File(Ini.getEOneHome(), REQUESTEVENTHANDLER_PROPERTIES);
		if (file.exists()) {
			Properties p = new Properties();
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				p.load(is);
				String ignoreRequesTypes = p.getProperty(RequestEventHandler.IGNORE_REQUEST_TYPES);
				
				if (!Util.isEmpty(ignoreRequesTypes)) {
					Configuration configuration = service.getConfiguration(RequestEventHandler.class.getName());
					if (configuration.getProperties() == null) {
						Dictionary<String, Object> map = new Hashtable<String, Object>();
						map.put(RequestEventHandler.IGNORE_REQUEST_TYPES, ignoreRequesTypes);
						configuration.update(map);
					} else {
						Bundle bundle = FrameworkUtil.getBundle(RequestEventHandler.class);
						String bundleLocation = bundle.getLocation();
						String configLocation = configuration.getBundleLocation();
						if (!bundleLocation.equals(configLocation)) {
							configuration.setBundleLocation(bundleLocation);
							configuration.update();
						}
					}
				}
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING, REQUESTEVENTHANDLER_PROPERTIES + " not found.", e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error reading " + REQUESTEVENTHANDLER_PROPERTIES, e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception ex) {}
				}
			}
		}		
	}
}
