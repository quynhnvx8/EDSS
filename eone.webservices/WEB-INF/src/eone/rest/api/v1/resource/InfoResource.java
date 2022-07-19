
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
@Path("v1/infos")
public interface InfoResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoWindows(@QueryParam("$filter") String filter);
	
	@Path("{slug}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoWindowRecords(@PathParam("slug") String infoSlug, @QueryParam("$parameters") String parameters, @QueryParam("$where_clause") String whereClause, 
			@QueryParam("$order_by") String orderBy, @DefaultValue("0") @QueryParam("$page_no") int pageNo);
	

	@Path("{slug}/columns")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoWindowColumns(@PathParam("slug") String infoSlug);
	
	@Path("{slug}/processes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoWindowProcesses(@PathParam("slug") String infoSlug);
	
	@Path("{slug}/relateds")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoWindowRelateds(@PathParam("slug") String infoSlug);
}
