package eone.rest.api.v2;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import eone.rest.api.v2.entities.DataRow;
import eone.rest.api.v2.entities.ModelProcessInfo;
import eone.rest.api.v2.entities.RecordRequest;
import eone.rest.api.v2.entities.RecordResponse;
import eone.rest.api.v2.entities.Response;
import eone.rest.api.v2.entities.RestResponse;
import eone.rest.api.v2.entities.VOResponse;
import eone.rest.api.v2.entities.VOfficeResource;


/**
 * 
 * @author hungtq24
 * @since 15/08/2018
 */
public interface ModelRestService {
	@POST
	@Path("/save_data")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public RecordResponse saveData(DataRow dataRow, @Context HttpServletRequest requestContext);	
	
	@POST
	@Path("/delete_data")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse deleteData(DataRow dataRow, @Context HttpServletRequest requestContext);
	
	@GET
	@Path("/get_data")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getData();	
	
	@POST
	@Path("/post")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public RestResponse postIt(RecordRequest record, @Context HttpServletRequest requestContext);
	
	@POST
	@Path("/complete")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8") 
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public RestResponse completeIt(RecordRequest record, @Context HttpServletRequest requestContext);
	
	
	@POST
	@Path("/reactive")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public RestResponse reActivateIt(RecordRequest record, @Context HttpServletRequest requestContext);
	
	
	@POST
	@Path("/process")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	public RestResponse processIt(ModelProcessInfo processInfo, @Context HttpServletRequest requestContext);	
	
	@POST
	@Path("/voffice/sign")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	public VOResponse signDocument(VOfficeResource vo, @Context HttpServletRequest requestContext);	
	
}

