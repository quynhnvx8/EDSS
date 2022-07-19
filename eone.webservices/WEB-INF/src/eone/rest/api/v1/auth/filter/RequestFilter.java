
package eone.rest.api.v1.auth.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import eone.rest.api.v1.jwt.LoginClaims;
import eone.rest.api.v1.jwt.TokenUtils;
import eone.util.Env;
import eone.util.ServerContext;
import eone.util.Util;

@Provider
/**
 * @author Client
 *
 */
public class RequestFilter implements ContainerRequestFilter {
	public static final String LOGIN_NAME = "#LoginName";

	public RequestFilter() {
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Properties ctx = new Properties();
		ServerContext.setCurrentInstance(ctx);
		
		if (   HttpMethod.OPTIONS.equals(requestContext.getMethod())
			|| (   HttpMethod.POST.equals(requestContext.getMethod())
				&& requestContext.getUriInfo().getPath().endsWith("v1/auth/tokens")
				)
			|| (   HttpMethod.GET.equals(requestContext.getMethod())
					&& requestContext.getUriInfo().getPath().endsWith("v1/auth/jwk")
					)
			) {
			return;
		}
		
		String authHeaderVal = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// consume JWT i.e. execute signature validation
		if (authHeaderVal != null && authHeaderVal.startsWith("Bearer")) {
			try {
				validate(authHeaderVal.split(" ")[1]);
				if (Util.isEmpty(Env.getContext(Env.getCtx(), Env.AD_USER_ID)) ||
					Util.isEmpty(Env.getContext(Env.getCtx(), Env.AD_ROLE_ID))) {
					if (!requestContext.getUriInfo().getPath().startsWith("v1/auth/")) {
						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
					}
				}
			} catch (JWTVerificationException ex) {
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			} catch (Exception ex) {
				requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
			}
		} else {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validate(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC512(TokenUtils.getTokenSecret());
		JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(TokenUtils.getTokenIssuer())
		        .build(); //Reusable verifier instance
		DecodedJWT jwt = verifier.verify(token);
		String userName = jwt.getSubject();
		ServerContext.setCurrentInstance(new Properties());
		Env.setContext(Env.getCtx(), LOGIN_NAME, userName);
		Claim claim = jwt.getClaim(LoginClaims.AD_Client_ID.name());
		int AD_Client_ID = 0;
		if (!claim.isNull()) {
			AD_Client_ID = claim.asInt();
			Env.setContext(Env.getCtx(), Env.AD_CLIENT_ID, AD_Client_ID);				
		}
		claim = jwt.getClaim(LoginClaims.AD_User_ID.name());
		if (!claim.isNull()) {
			Env.setContext(Env.getCtx(), Env.AD_USER_ID, claim.asInt());
		}
		
		claim = jwt.getClaim(LoginClaims.AD_Org_ID.name());
		int AD_Org_ID = 0;
		if (!claim.isNull()) {
			AD_Org_ID = claim.asInt();
			Env.setContext(Env.getCtx(), Env.AD_ORG_ID, AD_Org_ID);				
		}
		
		claim = jwt.getClaim(LoginClaims.AD_Language.name());
		if (!claim.isNull()) {
			String AD_Language = claim.asString();
			Env.setContext(Env.getCtx(), Env.LANGUAGE, AD_Language);
		}
		
		Env.setContext(Env.getCtx(), "#Date", new Timestamp(System.currentTimeMillis()));
		
		/** Define AcctSchema , Currency, HasAlias **/
		if (AD_Client_ID > 0) {
			
		}
	}
}
