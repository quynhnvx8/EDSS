
package eone.rest.api.v1.resource;

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
@Path("v1/forms")
public interface FormResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getForms(@QueryParam("$filter") String filter);

}

