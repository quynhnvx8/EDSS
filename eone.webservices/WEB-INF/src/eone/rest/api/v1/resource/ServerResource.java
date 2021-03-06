
package eone.rest.api.v1.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author hengsin
 *
 */
@Path("v1/servers")
public interface ServerResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServers();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServer(@PathParam("id") String id);
	
	@GET
	@Path("{id}/logs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServerLogs(@PathParam("id") String id);
	
	@POST
	@Path("{id}/state")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeServerState(@PathParam("id") String id);
	
	@POST
	@Path("{id}/run")
	@Produces(MediaType.APPLICATION_JSON)
	public Response runServer(@PathParam("id") String id);
	
	@POST
	@Path("reload")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reloadServers();
	
	@GET
	@Path("schedulers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduler(@PathParam("id") int id);
	
	@POST
	@Path("schedulers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScheduler(@PathParam("id") int id);
	
	@DELETE
	@Path("schedulers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeScheduler(@PathParam("id") int id);
}
