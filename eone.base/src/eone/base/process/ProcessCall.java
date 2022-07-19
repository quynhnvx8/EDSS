
package eone.base.process;

import java.util.HashMap;
import java.util.Properties;

import eone.util.IProcessUI;
import eone.util.Trx;

public interface ProcessCall
{
	
	public boolean startProcess (Properties ctx, ProcessInfo pi, Trx trx, HashMap<String, Object> params);

	public void setProcessUI(IProcessUI processUI);

}   //  ProcessCall
