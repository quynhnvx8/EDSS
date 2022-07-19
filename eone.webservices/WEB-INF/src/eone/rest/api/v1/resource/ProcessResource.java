
package eone.rest.api.v1.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("v1/processes")
public interface ProcessResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProcesses(@QueryParam("$filter") String filter);
	
	@Path("{processSlug}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProcess(@PathParam("processSlug") String processSlug);
	
	@Path("{processSlug}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response runProcess(@PathParam("processSlug") String processSlug, String jsonText);
	
	@Path("jobs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobs();
	
	@Path("jobs/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJob(@PathParam("id") int id);
	
	@Path("jobs/{processSlug}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response runJob(@PathParam("processSlug") String processSlug, String jsonText);
}
