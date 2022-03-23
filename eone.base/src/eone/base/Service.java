
package eone.base;

/**
 * This is a very simple factory for service locators
 * 
 * @author viola
 * 
 */
public class Service {

	private static IServiceLocator theLocator = new DynamicServiceLocator();

	/**
	 * 
	 * @return service locator instance
	 */
	public static IServiceLocator locator() {
		return theLocator;
	}
}
