
package eone.base;

/**
 * 
 * @author hengsin
 *
 * @param <T>
 */
public interface IServiceHolder<T> {

	/**
	 * 
	 * @return service instance. null if not available or no matching service found
	 */
	public T getService();


	/**
	 * Get service reference for service with highest service.ranking value
	 * @return {@link IServiceReferenceHolder}
	 */
	public IServiceReferenceHolder<T> getServiceReference();
}
