
package eone.base;

/**
 * A service locator looks up services.
 * This is the central authority for adempiere service definition,
 * because each service defined has to be looked up via this interface.
 * 
 * A service in adempiere is an implementation for the registered interface, expose through osgi service registry
 * 
 * @author viola
 *
 */
public interface IServiceLocator {
	/**
	 * 
	 * @param type service interface
	 * @return holder for dynamic service
	 */
	<T> IServiceHolder<T> locate(Class<T> type);
	
	/**
	 * 
	 * @param type
	 * @param query
	 * @return
	 */
	<T> IServiceHolder<T> locate(Class<T> type, ServiceQuery query);
	
	/**
	 * 
	 * @param type
	 * @param componentName service component name
	 * @param query
	 * @return holder for dynamic service
	 */
	<T> IServiceHolder<T> locate(Class<T> type, String componentName, ServiceQuery query);
	
	/**
	 * 
	 * @param type
	 * @return holder for list of dynamic service
	 */
	<T> IServicesHolder<T> list(Class<T> type);
	
	/**
	 * 
	 * @param type
	 * @param query
	 * @return holder for list of dynamic service
	 */
	<T> IServicesHolder<T> list(Class<T> type, ServiceQuery query);
	
	/**
	 * 
	 * @param type
	 * @param componentName osgi service component name
	 * @param query
	 * @return holder for list of dynamic service
	 */
	<T> IServicesHolder<T> list(Class<T> type, String componentName, ServiceQuery query);	
}
