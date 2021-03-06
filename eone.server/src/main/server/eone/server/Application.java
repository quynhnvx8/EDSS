package eone.server;

import java.io.File;
import java.util.Properties;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import eone.EOne;
import eone.util.Ini;
import eone.util.ServerContext;


public class Application implements IApplication {

	
	@Override
	public Object start(IApplicationContext context) throws Exception {
        Properties serverContext = new Properties();
        ServerContext.setCurrentInstance(serverContext);

        String propertyFile = Ini.getFileName(false);
        File file = new File(propertyFile);
        if (!file.exists()) {
        	throw new IllegalStateException("eone.properties file missing. Path="+file.getAbsolutePath());
        }
        if (!EOne.isStarted())
        {
	        boolean started = EOne.startup(false);
	        if(!started)
	        {
	            throw new Exception("Could not start EONE");
	        }
        }

		return IApplication.EXIT_OK;
	}

	
	@Override
	public void stop() {
	}

}
