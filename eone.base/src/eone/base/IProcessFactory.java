
package eone.base;

import eone.base.process.ProcessCall;

public interface IProcessFactory {

	public ProcessCall newProcessInstance(String className);
	
}
