
package eone.base;

import eone.base.model.Callout;

/**
 * 
 * @author a42niem
 *
 */
public interface ICalloutFactory {

	/**
	 * 
	 * @param className
	 * @param methodName 
	 * @return matching Callout
	 */
	public Callout getCallout(String className, String methodName);
	
}
