package eone.util;

public interface IEnvEventListener {

	public void onClearWindowContext(int windowNo);
	
	public void onReset(boolean finalCall);
}
