
package eone.webui.dashboard;

import eone.webui.component.Window;
import eone.webui.util.ServerPushTemplate;

/**
 * @author Client
 */
public abstract class DashboardPanel extends Window implements IDashboardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424244218250118823L;

	public DashboardPanel()
	{
		super();
	}
	
	public void refresh(ServerPushTemplate template) {
	}

	public void updateUI() {
	}
	
	public boolean isPooling() {
		return false;
	}
}
