
package eone.webui.adwindow;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Toolbarbutton;

import eone.base.IServiceHolder;
import eone.base.model.MToolBarButton;
import eone.util.Env;
import eone.util.Evaluatee;
import eone.util.Evaluator;
import eone.webui.action.Actions;
import eone.webui.action.IAction;

public class ToolbarCustomButton implements EventListener<Event>, Evaluatee { 

	private Toolbarbutton toolbarButton;
	private String actionId;
	private int windowNo;
	private int tabNo = -1;
	private MToolBarButton mToolbarButton;

	public ToolbarCustomButton(MToolBarButton mToolbarButton, Toolbarbutton btn, String actionId, int windowNo) {
		this(mToolbarButton, btn, actionId, windowNo, -1);
	}
	
	public ToolbarCustomButton(MToolBarButton mToolbarButton, Toolbarbutton btn, String actionId, int windowNo, int tabNo) {
		toolbarButton = btn;
		this.actionId = actionId;
		this.windowNo = windowNo;
		this.tabNo = tabNo;
		this.mToolbarButton = mToolbarButton;
		
		toolbarButton.addEventListener(Events.ON_CLICK, this);
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
		IServiceHolder<IAction> serviceHolder = Actions.getAction(actionId);
		if (serviceHolder != null) {
			IAction action = serviceHolder.getService();
			if (action != null) {
				action.execute(ADWindow.get(windowNo));
			}
		}
	}

	@Override
	public String get_ValueAsString(String variableName) {
		ADWindow adwindow = ADWindow.get(windowNo);
		if (adwindow == null) 
			return "";
		
		IADTabpanel adTabpanel = adwindow.getADWindowContent().getADTab().getSelectedTabpanel();
		if (adTabpanel == null)
			return "";
		
		int tabNo = this.tabNo >= 0 ? this.tabNo : adTabpanel.getTabNo();
		if( tabNo == 0)
	    	return adTabpanel.get_ValueAsString(variableName);
	    else
	    	return Env.getContext (Env.getCtx(), windowNo, tabNo, variableName, false, true);
	}
	
	public void dynamicDisplay() {
		dynamicDisplay(false);
	}
	
	public void dynamicDisplay(boolean forceValidation) {
		if (toolbarButton.getParent() == null && !forceValidation)
			return;
		
		String displayLogic = mToolbarButton.getDisplayLogic();
		if (displayLogic == null || displayLogic.trim().length() == 0)
			return;

		boolean visible = true;
		if (displayLogic.startsWith("@SQL=")) {
			ADWindow adwindow = ADWindow.get(windowNo);
			if (adwindow == null)
				return;
			
			IADTabpanel adTabpanel = adwindow.getADWindowContent().getADTab().getSelectedTabpanel();
			if (adTabpanel == null)
				return;
			
			visible = Evaluator.parseSQLLogic(displayLogic, Env.getCtx(), windowNo, adTabpanel.getTabNo(), mToolbarButton.getActionName());
		}else {
			visible = Evaluator.evaluateLogic(this, displayLogic);	
		}

		toolbarButton.setVisible(visible);
	}
	
	public Toolbarbutton getToolbarbutton() {
		return toolbarButton;
	}
}
