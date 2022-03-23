package eone.webui.adwindow;

import eone.util.Callback;

public interface WindowValidator {
	public void onWindowEvent(WindowValidatorEvent event, Callback<Boolean> callback);
}
