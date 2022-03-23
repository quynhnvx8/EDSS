
package eone.base;

import java.util.logging.Level;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import eone.EOne;
import eone.base.process.ProcessInfo;
import eone.util.CLogMgt;
import eone.util.Env;
import eone.util.ProcessUtil;

/**
 * @author hengsin
 *
 */
public class PostMigrationApplication implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) throws Exception {
		EOne.startup(false);
		CLogMgt.setLevel(Level.FINE);
		addMissingTranslation();
		roleAccessUpdate();
		checkSequence();
		
		return IApplication.EXIT_OK;
	}

	private void checkSequence() {
		ProcessInfo pi = new ProcessInfo("Sequence Check", 258);
		pi.setAD_Client_ID(0);
		pi.setAD_User_ID(100);
		pi.setClassName("eone.base.process.SequenceCheck");
		ProcessUtil.startJavaProcess(Env.getCtx(), pi, null, null);
	}

	private void roleAccessUpdate() {
		ProcessInfo pi = new ProcessInfo("Role Access Update", 295);
		pi.setAD_Client_ID(0);
		pi.setAD_User_ID(100);
		pi.setClassName("eone.base.process.RoleAccessUpdate");
		ProcessUtil.startJavaProcess(Env.getCtx(), pi, null, null);
	}

	private void addMissingTranslation() {
		ProcessInfo pi = new ProcessInfo("Synchronize Terminology", 172);
		pi.setAD_Client_ID(0);
		pi.setAD_User_ID(100);
		pi.setClassName("eone.base.process.SynchronizeTerminology");
		ProcessUtil.startJavaProcess(Env.getCtx(), pi, null, null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
	}

}
