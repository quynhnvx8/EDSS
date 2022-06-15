package eone.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
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
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("FirstName", "Nguyễn");
			obj.put("LastName", "Văn B");
			/*
			request = HttpRequest.newBuilder()
					.uri(new URI(""))
					.version(HttpClient.Version.HTTP_1_1)
					.GET()
					.build();
			response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	

}
