
package eone.base;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Constants;

import eone.base.model.GridFieldVO;
import eone.base.model.Lookup;
import eone.util.CCache;

/**
 * Static helper methods for working with {@link ILookupFactory}
 * @author hengsin
 *
 */
public final class LookupFactoryHelper {

	private LookupFactoryHelper() {
	}

	private static final CCache<Long, IServiceReferenceHolder<ILookupFactory>> s_lookupFactoryCache = new CCache<Long, IServiceReferenceHolder<ILookupFactory>>(
			null, "ILookupFactory", 10, false);

	/**
	 * Get lookup from osgi factory
	 * 
	 * @param gridFieldVO
	 * @return {@link Lookup}
	 */
	public static Lookup getLookup(GridFieldVO gridFieldVO) {
		List<Long> visitedIds = new ArrayList<Long>();
		if (!s_lookupFactoryCache.isEmpty()) {
			Long[] keys = s_lookupFactoryCache.keySet().toArray(new Long[0]);
			for (Long key : keys) {
				IServiceReferenceHolder<ILookupFactory> serviceReference = s_lookupFactoryCache.get(key);
				if (serviceReference != null) {
					ILookupFactory service = serviceReference.getService();
					if (service != null) {
						visitedIds.add(key);
						Lookup lookup = service.getLookup(gridFieldVO);
						if (lookup != null)
							return lookup;
					} else {
						s_lookupFactoryCache.remove(key);
					}
				}
			}
		}
		List<IServiceReferenceHolder<ILookupFactory>> serviceReferences = Service.locator().list(ILookupFactory.class)
				.getServiceReferences();
		for (IServiceReferenceHolder<ILookupFactory> serviceReference : serviceReferences) {
			Long serviceId = (Long) serviceReference.getServiceReference().getProperty(Constants.SERVICE_ID);
			if (visitedIds.contains(serviceId))
				continue;
			ILookupFactory service = serviceReference.getService();
			if (service != null) {
				s_lookupFactoryCache.put(serviceId, serviceReference);
				Lookup lookup = service.getLookup(gridFieldVO);
				if (lookup != null)
					return lookup;
			}
		}
		return null;
	}
	
	/**
	 * Check lookup from osgi factory 
	 * 
	 * @param gridFieldVO
	 * @return true if gridFieldVO is a lookup field
	 */
	public static boolean isLookup(GridFieldVO gridFieldVO) {
		List<Long> visitedIds = new ArrayList<Long>();
		if (!s_lookupFactoryCache.isEmpty()) {
			Long[] keys = s_lookupFactoryCache.keySet().toArray(new Long[0]);
			for (Long key : keys) {
				IServiceReferenceHolder<ILookupFactory> serviceReference = s_lookupFactoryCache.get(key);
				if (serviceReference != null) {
					ILookupFactory service = serviceReference.getService();
					if (service != null) {
						visitedIds.add(key);
						if (service.isLookup(gridFieldVO))
							return true;
					} else {
						s_lookupFactoryCache.remove(key);
					}
				}
			}
		}
		List<IServiceReferenceHolder<ILookupFactory>> serviceReferences = Service.locator().list(ILookupFactory.class)
				.getServiceReferences();
		for (IServiceReferenceHolder<ILookupFactory> serviceReference : serviceReferences) {
			Long serviceId = (Long) serviceReference.getServiceReference().getProperty(Constants.SERVICE_ID);
			if (visitedIds.contains(serviceId))
				continue;
			ILookupFactory service = serviceReference.getService();
			if (service != null) {
				s_lookupFactoryCache.put(serviceId, serviceReference);
				if (service.isLookup(gridFieldVO))
					return true;
			}
		}
		return false;
	}
}
