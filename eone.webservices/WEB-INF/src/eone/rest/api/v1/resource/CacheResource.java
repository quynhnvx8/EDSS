
package eone.rest.api.v1.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Client
 *
 */
@Path("v1/caches")
public interface CacheResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCaches(@QueryParam("table_name") String tableName, @QueryParam("name") String name);
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetCache(@QueryParam("table_name") String tableName, @DefaultValue("0") @QueryParam("record_id") int recordId);
}
