
package eone.base;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * IServiceReferenceHolder implementation using ServiceTracker
 * @author hengsin
 *
 * @param <T>
 */
public class DynamicServiceReference<T> implements IServiceReferenceHolder<T> {

	private ServiceTracker<T, T> serviceTracker;
	private ServiceReference<T> serviceReference;
	
	/**
	 * 
	 * @param tracker
	 * @param serviceReference
	 */
	public DynamicServiceReference(ServiceTracker<T, T> tracker, ServiceReference<T> serviceReference) {
		this.serviceTracker = tracker;
		this.serviceReference = serviceReference;
	}

	@Override
	public T getService() {
		return serviceTracker.getService(serviceReference);
	}

	@Override
	public ServiceReference<T> getServiceReference() {
		return serviceReference;
	}
}
