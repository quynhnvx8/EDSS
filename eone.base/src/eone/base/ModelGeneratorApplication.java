
package eone.base;

import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import eone.EOne;
import eone.util.ModelClassGenerator;
import eone.util.ModelGeneratorDialog;
import eone.util.ModelInterfaceGenerator;

public class ModelGeneratorApplication implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		EOne.startup(false);
		Map<?, ?> args = context.getArguments();
		String commandlineArgs[] = (String[]) args.get("application.args");
		if (commandlineArgs.length >= 4) {
			String folder = commandlineArgs[0];
			String packageName = commandlineArgs[1];
			String tableName = commandlineArgs[3];
			
			ModelInterfaceGenerator.generateSource(folder, packageName, tableName);
			ModelClassGenerator.generateSource(folder, packageName, tableName);
		} else if (commandlineArgs.length != 0) {
			System.out.println("usage: ModelGenerator folder packageName");
		} else {
			ModelGeneratorDialog dialog = new ModelGeneratorDialog();
			dialog.setModal(true);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		}
		return IApplication.EXIT_OK;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
	}

}
