
package eone.rest.api.v1.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("v1/windows")
public interface WindowResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWindows(@QueryParam("$filter") String filter, @QueryParam("$expand") String details,
			@QueryParam("$select") String select);
	
	@Path("{windowSlug}/tabs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTabs(@PathParam("windowSlug") String windowSlug);
	
	@Path("{windowSlug}/tabs/{tabSlug}/fields")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTabFields(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @QueryParam("$filter") String filter);
	
	@Path("{windowSlug}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWindowRecords(@PathParam("windowSlug") String windowSlug, @QueryParam("$filter") String filter, @QueryParam("$sort_column") String sortColumn, @QueryParam("$page_no") int pageNo);
	
	@Path("{windowSlug}/{recordId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWindowRecord(@PathParam("windowSlug") String windowSlug, @PathParam("recordId") int recordId, @QueryParam("$expand") String details);
	
	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}/{childTabSlug}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildTabRecords(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId, 
			@PathParam("childTabSlug") String childTabSlug, @QueryParam("$filter") String filter, @QueryParam("$sort_column") String sortColumn, @QueryParam("$page_no") int pageNo);

	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTabRecord(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId, @QueryParam("$expand") String details);
	
	@Path("{windowSlug}/{recordId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateWindowRecord(@PathParam("windowSlug") String windowSlug, @PathParam("recordId") int recordId, String jsonText);
	
	@Path("{windowSlug}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createWindowRecord(@PathParam("windowSlug") String windowSlug, String jsonText);
	
	@Path("{windowSlug}/{recordId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteWindowRecord(@PathParam("windowSlug") String windowSlug, @PathParam("recordId") int recordId);
	
	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTabRecord(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId, String jsonText);
	
	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}/{childTabSlug}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createChildTabRecord(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId, 
			@PathParam("childTabSlug") String childTabSlug, String jsonText);
	
	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTabRecord(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId);
	
	@Path("{windowSlug}/{recordId}/print")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response printWindowRecord(@PathParam("windowSlug") String windowSlug, @PathParam("recordId") int recordId, @QueryParam("$report_type") String reportType);
	
	@Path("{windowSlug}/tabs/{tabSlug}/{recordId}/print")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response printTabRecord(@PathParam("windowSlug") String windowSlug, @PathParam("tabSlug") String tabSlug, @PathParam("recordId") int recordId, @QueryParam("$report_type") String reportType);
}

