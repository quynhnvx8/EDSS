
package eone.rest.api.v1.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1")
/**
 * authentication service
 *
 */
public interface AuthService {

	@Path("auth/tokens")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(LoginCredential credential);
	
	
	@Path("auth/language")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientLanguage(@QueryParam(value = "client") int clientId);
	
	@Path("auth/tokens")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeLoginParameters(LoginParameters loginRole);

	@Path("auth/jwk")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJWK();
}
