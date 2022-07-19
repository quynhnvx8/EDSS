
package eone.rest.api.v1.resource.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eone.rest.api.v1.resource.CacheResource;
import eone.util.CacheInfo;
import eone.util.CacheMgt;

/**
 * @author hengsin
 *
 */
public class CacheResourceImpl implements CacheResource {

	/**
	 * default constructor
	 */
	public CacheResourceImpl() {
	}

	@Override
	public Response getCaches(String tableName, String name) {
		List<CacheInfo> cacheInfos = CacheInfo.getCacheInfos(true);
		JsonArray caches = new JsonArray();
		for(CacheInfo cacheInfo : cacheInfos) {
			if (cacheInfo.getName().endsWith("|CCacheListener"))
				continue;
			
			if (tableName != null && !tableName.equals(cacheInfo.getTableName()))
				continue;
			
			if (name != null && !name.equals(cacheInfo.getName()))
				continue;
			
			JsonObject cache = new JsonObject();
			cache.addProperty("name", cacheInfo.getName());
			cache.addProperty("tableName", cacheInfo.getTableName());
			cache.addProperty("size", cacheInfo.getSize());
			cache.addProperty("expireMinutes", cacheInfo.getExpireMinutes());
			cache.addProperty("maxSize", cacheInfo.getMaxSize());
			cache.addProperty("distributed", cacheInfo.isDistributed());
			if (cacheInfo.getNodeId() != null)
				cache.addProperty("nodeId", cacheInfo.getNodeId());
			caches.add(cache);
		}
		JsonObject json = new JsonObject();
		json.add("caches", caches);
		return Response.ok(json.toString()).build();
	}

	@Override
	public Response resetCache(String tableName, int recordId) {
		int count = 0;
		if (tableName == null) {
			count = CacheMgt.get().reset();
		} else {
			if (recordId > 0) {
				count = CacheMgt.get().reset(tableName, recordId);
			} else {
				count = CacheMgt.get().reset(tableName);
			}
		}
		return Response.ok("{entriesReset: " + count + "}").build();
	}

}
