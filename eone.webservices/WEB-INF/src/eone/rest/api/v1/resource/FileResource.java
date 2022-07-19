
package eone.rest.api.v1.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Client
 *
 */
@Path("v1/files")
public interface FileResource {

	@Path("{fileName}")
	@GET
	@Produces({MediaType.APPLICATION_OCTET_STREAM, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getFile(@PathParam("fileName") String fileName, @QueryParam("length") @DefaultValue("0") long length,
				@QueryParam("node_id") String nodeId);
}
