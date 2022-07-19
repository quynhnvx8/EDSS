package eone.rest.api.v1.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
 * @author Client
 *
 */
@Path("v1/models")
public interface ModelResource {

	@Path("{tableName}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPO(@PathParam("tableName") String tableName, @PathParam("id") String id, @QueryParam("$expand") String details, 
			@QueryParam("$select") String select);

	@Path("{tableName}/{id}/{property}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getPOProperty(@PathParam("tableName") String tableName, @PathParam("id") String id, @PathParam("property") String propertyName);

	@Path("{tableName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getPOs(@PathParam("tableName") String tableName, @QueryParam("$expand") String details, @QueryParam("$filter") String filter, @QueryParam("$orderby") String order, 
			@QueryParam("$select") String select, @QueryParam("$top") int top, @DefaultValue("0") @QueryParam("$skip") int skip,
			@QueryParam("$valrule") String validationRuleID, @QueryParam("$context") String context);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getModels(@QueryParam("$filter") String filter);
	
	@Path("{tableName}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response create(@PathParam("tableName") String tableName, String jsonText);
	
	@Path("{tableName}/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response update(@PathParam("tableName") String tableName, @PathParam("id") String id, String jsonText);
	
	@Path("{tableName}/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response delete(@PathParam("tableName") String tableName, @PathParam("id") String id);
	
	@Path("{tableName}/{id}/attachments")
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getAttachments(@PathParam("tableName") String tableName, @PathParam("id") String id);
	
	@Path("{tableName}/{id}/attachments/zip")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getAttachmentsAsZip(@PathParam("tableName") String tableName, @PathParam("id") String id);
	
	@Path("{tableName}/{id}/attachments/zip")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response createAttachmentsFromZip(@PathParam("tableName") String tableName, @PathParam("id") String id, String jsonText);
	
	@Path("{tableName}/{id}/attachments/{fileName}")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getAttachmentEntry(@PathParam("tableName") String tableName, @PathParam("id") String id, @PathParam("fileName") String fileName);
	
	@Path("{tableName}/{id}/attachments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response addAttachmentEntry(@PathParam("tableName") String tableName, @PathParam("id") String id, String jsonText);
	
	@Path("{tableName}/{id}/attachments")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response deleteAttachments(@PathParam("tableName") String tableName, @PathParam("id") String id);
	
	@Path("{tableName}/{id}/attachments/{fileName}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response deleteAttachmentEntry(@PathParam("tableName") String tableName, @PathParam("id") String id, @PathParam("fileName") String fileName);
}
