
package eone.install;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * @author hengsin
 *
 */
public class ConsoleInstallApplication implements IApplication {

	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		ConfigurationConsole console = new ConfigurationConsole();
		console.doSetup();
		String path = System.getProperty("user.dir") + File.separator + "build.xml";//File.separator + "eone.install" +
		
		File file = new File(path);
		System.out.println("efile="+path+" exists="+file.exists());
		//only exists if it is running from development environment
		if (file.exists()) {
			AntRunner runner = new AntRunner();
			runner.setBuildFileLocation(path);
			runner.setMessageOutputLevel(Project.MSG_VERBOSE);
			runner.addBuildLogger(DefaultLogger.class.getName());
			runner.run();
			runner.stop();
		}
		return ConsoleInstallApplication.EXIT_OK;
	}

	
	@Override
	public void stop() {
	}
}
