
package eone.rest.api.v1.jwt;

import java.util.List;
import java.util.UUID;

import eone.base.Service;
import eone.distributed.ICacheService;

/**
 * 
 * @author Client
 *
 */
public class DefaultTokenSecretProvider implements ITokenSecretProvider {
	private List<String> keyList;
	
	private DefaultTokenSecretProvider() {
		ICacheService cacheService = Service.locator().locate(ICacheService.class).getService();
		keyList = cacheService.getList(getClass().getName());
		if (keyList.isEmpty())
			keyList.add(UUID.randomUUID().toString());
	}

	@Override
	public String getSecret() {
		return keyList.get(0);
	}
	
	public final static DefaultTokenSecretProvider instance = new DefaultTokenSecretProvider();
}
