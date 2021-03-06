package eone.distributed;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 
 * @author hengsin
 *
 */
public interface IClusterService {

	/**
	 * 
	 * @return Collection of cluster member
	 */
	public Collection<IClusterMember> getMembers();
	
	/**
	 * @return Local node
	 */
	public IClusterMember getLocalMember();
	
	/**
	 * 
	 * @param task
	 * @param member
	 * @return Future
	 */
	public <V>Future<V> execute(Callable<V> task, IClusterMember member);
	
	/**
	 * 
	 * @param task
	 * @param members
	 * @return Map of IClusterMember and Future
	 */
	public <V> Map<IClusterMember, Future<V>> execute(Callable<V> task, Collection<IClusterMember> members);
	
	public default boolean isStandAlone() {
		return false;
	}
}
