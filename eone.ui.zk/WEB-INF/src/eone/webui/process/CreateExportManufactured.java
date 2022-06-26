

package eone.webui.process;

import eone.base.process.SvrProcess;
import eone.webui.apps.AEnv;

/**
 * 
 * @author Deepak Pansheriya
 *
 */
public class CreateExportManufactured extends SvrProcess {


	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {

		AEnv.zoom(319, 516);
		
		return "Session Time Out Initiated";
	}
}
