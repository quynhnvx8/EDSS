package eone.soap.api.resource;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RestController;

@RestController
@WebService (serviceName = "SInvoice", targetNamespace = "http://dssvn.com")
public class SInvoice {
	
	@WebMethod(operationName = "getData")
	public String getData(String username, String password) {
		return username + password;
	}
	
	@POST
	@Path("/postData")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getRestAPI() {
		//HttpRequest request = null;
		//HttpResponse<String> response = null;
		//JSONObject obj = new JSONObject();
		try {
			//obj.put("FirstName", "Nguyễn");
			//obj.put("LastName", "Văn B");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;// Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	

}
