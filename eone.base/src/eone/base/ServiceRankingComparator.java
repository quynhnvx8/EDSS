
package eone.base;

import java.io.Serializable;
import java.util.Comparator;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

/**
 * Service Reference Comparator.
 * This comparator follows OSGi Ranking policy.
 * @author hengsin
 */
public final class ServiceRankingComparator implements Comparator<ServiceReference<?>>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3444598255961708618L;
	
	/**
	 * share service ranking comparator instance
	 */
	public static final ServiceRankingComparator INSTANCE = new ServiceRankingComparator();
    
	private ServiceRankingComparator() {}
	
    /**
     * Compares two service reference.
     * @param serviceReference1 service reference 1
     * @param serviceReference2 service reference 2
     * @return <code>-1</code> if service reference 1 service.ranking value
     * is higher than service reference 2, <code>1</code> otherwise. 
     * (i.e for sorting in descending order of service.ranking value)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(ServiceReference<?> serviceReference1, ServiceReference<?> serviceReference2) {
        if (serviceReference1.equals(serviceReference2)) { return 0; }

        if (serviceReference2 != null) {
            Object serviceRankingP1 = serviceReference1.getProperty(Constants.SERVICE_RANKING);
            Object serviceRankingP2 = serviceReference2.getProperty(Constants.SERVICE_RANKING);

            int serviceRanking1 = 0;
            int serviceRanking2 = 0;
            if (serviceRankingP1 instanceof Integer) {
                serviceRanking1 = (Integer) serviceRankingP1;
            }
            if (serviceRankingP2 instanceof Integer) {
                serviceRanking2 = (Integer) serviceRankingP2;
            }

            if (serviceRanking1 == serviceRanking2) {
                // Check service.id
                Object serviceIdP1 = serviceReference1.getProperty(Constants.SERVICE_ID);
                Object serviceIdP2 = serviceReference2.getProperty(Constants.SERVICE_ID);

                long serviceId1 = (Long) serviceIdP1;
                long serviceId2 = (Long) serviceIdP2;

                if (serviceId1 == serviceId2) {
                    return 0;
                } else if (serviceId1 > serviceId2) {
                    return -1;
                } else {
                    return 1;
                }

            } else if (serviceRanking1 > serviceRanking2) {
                return -1;
            } else {
                return 1;
            }

        } else {
            return 0;
        }
    }
}