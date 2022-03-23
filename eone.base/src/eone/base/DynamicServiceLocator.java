
package eone.base;

import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.ComponentConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author hengsin
 *
 */
public class DynamicServiceLocator implements IServiceLocator {

	/**
	 * 
	 */
	public DynamicServiceLocator() {
	}

	/**
	 * @see eone.base.IServiceLocator#locate(java.lang.Class)
	 */
	@Override
	public <T> IServiceHolder<T> locate(Class<T> type) {
		Filter filter = filter(type, null, null);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		return new DynamicServiceHolder<T>(tracker);
	}

	/**
	 * @see eone.base.IServiceLocator#locate(java.lang.Class, eone.base.ServiceQuery)
	 */
	@Override
	public <T> IServiceHolder<T> locate(Class<T> type, ServiceQuery query) {
		if (query == null || query.isEmpty())
			return locate(type);
		
		Filter filter = filter(type, null, query);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		return new DynamicServiceHolder<T>(tracker);
	}

	/**
	 * @see eone.base.IServiceLocator#locate(java.lang.Class, java.lang.String, eone.base.ServiceQuery)
	 */
	@Override
	public <T> IServiceHolder<T> locate(Class<T> type, String serviceId, ServiceQuery query) {
		if ((query == null || query.isEmpty()) && (serviceId == null || serviceId.trim().length() == 0))
			return locate(type);
		
		Filter filter = filter(type, serviceId, query);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		
		return new DynamicServiceHolder<T>(tracker);
	}

	/**
	 * @see eone.base.IServiceLocator#list(java.lang.Class)
	 */
	@Override
	public <T> IServicesHolder<T> list(Class<T> type) {
		Filter filter = filter(type, null, null);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		
		return new DynamicServiceHolder<T>(tracker);
	}

	/**
	 * @see eone.base.IServiceLocator#list(java.lang.Class, eone.base.ServiceQuery)
	 */
	@Override
	public <T> IServicesHolder<T> list(Class<T> type, ServiceQuery query) {
		if (query == null || query.isEmpty())
			return list(type);
		
		Filter filter = filter(type, null, query);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		return new DynamicServiceHolder<T>(tracker);
	}

	/**
	 * @see eone.base.IServiceLocator#list(java.lang.Class, java.lang.String, eone.base.ServiceQuery)
	 */
	@Override
	public <T> IServicesHolder<T> list(Class<T> type, String serviceId, ServiceQuery query) {
		if ((query == null || query.isEmpty()) && (serviceId == null || serviceId.trim().length() == 0))
			return list(type);
		
		Filter filter = filter(type, serviceId, query);
		ServiceTracker<T, T> tracker = BaseActivator.getServiceTracker(type, filter);
		return new DynamicServiceHolder<T>(tracker);
	}

	private Filter filter(Class<?> type, String serviceId, ServiceQuery query) {
		StringBuilder builder = new StringBuilder("(&(objectclass=");
		builder.append(type.getName()).append(")");
		if (query != null) {
			for(String key : query.keySet()) {
				String value = query.get(key);
				builder.append("(").append(key).append("=").append(value).append(")");
			}
		}
		if (serviceId != null && serviceId.trim().length() > 0) {
			builder.append("(").append(ComponentConstants.COMPONENT_NAME).append("=").append(serviceId.trim()).append(")");
		}
		builder.append(")");
		try {
			return BaseActivator.getBundleContext().createFilter(builder.toString());
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
