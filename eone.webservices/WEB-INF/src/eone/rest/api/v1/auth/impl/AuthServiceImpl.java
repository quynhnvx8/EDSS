
package eone.rest.api.v1.auth.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eone.base.model.I_AD_Preference;
import eone.base.model.MClient;
import eone.base.model.MPreference;
import eone.base.model.MSysConfig;
import eone.base.model.MTable;
import eone.base.model.MUser;
import eone.base.model.Query;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.auth.AuthService;
import eone.rest.api.v1.auth.LoginCredential;
import eone.rest.api.v1.auth.LoginParameters;
import eone.rest.api.v1.auth.filter.RequestFilter;
import eone.rest.api.v1.jwt.LoginClaims;
import eone.rest.api.v1.jwt.TokenUtils;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.Language;
import eone.util.LogAuthFailure;
import eone.util.Login;
import eone.util.Msg;
import eone.util.Util;

/**
 *
 */
public class AuthServiceImpl implements AuthService {

	private static LogAuthFailure logAuthFailure = new LogAuthFailure();

	private @Context HttpServletRequest request = null;

	/**
	 * default constructor
	 */
	public AuthServiceImpl() {
	}

	
	@Override
	public Response authenticate(LoginCredential credential) {
		Login login = new Login(Env.getCtx());
		KeyNamePair[] clients = login.getClients(credential.getUserName(), credential.getPassword());
		if (clients == null || clients.length == 0) {
        	String loginErrMsg = login.getLoginErrMsg();
        	if (Util.isEmpty(loginErrMsg))
        		loginErrMsg = Msg.getMsg(Env.getCtx(),"FailedLogin", true);
        	String x_Forward_IP = request.getHeader("X-Forwarded-For");
            if (x_Forward_IP == null) {
            	 x_Forward_IP = request.getRemoteAddr();
            }
        	logAuthFailure.log(x_Forward_IP, "/api", credential.getUserName(), loginErrMsg);

			return Response.status(Status.UNAUTHORIZED)
					.entity(new ErrorBuilder().status(Status.UNAUTHORIZED).title("Authenticate error").append(loginErrMsg).build().toString())
					.build();
		} else {
			JsonObject responseNode = new JsonObject();
			JsonArray clientNodes = new JsonArray(); 
			responseNode.add("clients", clientNodes);
			for(KeyNamePair client : clients) {
				JsonObject node = new JsonObject();
				node.addProperty("id", client.getKey());
				node.addProperty("name", client.getName());
				clientNodes.add(node);
			}
			Builder builder = JWT.create().withSubject(credential.getUserName());
			if (credential.getParameters() != null) {
				LoginParameters parameters = credential.getParameters();
				if (parameters.getClientId() >= 0) {
					builder.withClaim(LoginClaims.AD_Client_ID.name(), parameters.getClientId());
					
					Env.setContext(Env.getCtx(), Env.AD_CLIENT_ID, parameters.getClientId());
					MUser user = MUser.get(Env.getCtx(), credential.getUserName());
					builder.withClaim(LoginClaims.AD_User_ID.name(), user.getAD_User_ID());
				}
				if (parameters.getLanguage() != null) {
					builder.withClaim(LoginClaims.AD_Language.name(), parameters.getLanguage());
				}
			}
			Timestamp expiresAt = TokenUtils.getTokenExpiresAt();
			builder.withIssuer(TokenUtils.getTokenIssuer()).withExpiresAt(expiresAt).withKeyId(TokenUtils.getTokenKeyId());
			try {
				String token = builder.sign(Algorithm.HMAC512(TokenUtils.getTokenSecret()));
				responseNode.addProperty("token", token);
			} catch (IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
			return Response.ok(responseNode.toString()).build();
		}
	}

	
	@Override
	public Response getClientLanguage(int clientId) {
		try {
			MClient client = MClient.get(Env.getCtx(), clientId);
			JsonObject node = new JsonObject();
			node.addProperty("AD_Language", client.getAD_Language());
			return Response.ok(node.toString()).build();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	
	@Override
	public Response changeLoginParameters(LoginParameters parameters) {
		JsonObject responseNode = new JsonObject();
		String userName = Env.getContext(Env.getCtx(), RequestFilter.LOGIN_NAME);
		Builder builder = JWT.create().withSubject(userName);
		String defaultLanguage = Language.getBaseAD_Language();
		if (parameters.getClientId() >= 0) {
			builder.withClaim(LoginClaims.AD_Client_ID.name(), parameters.getClientId());
			
			Env.setContext(Env.getCtx(), Env.AD_CLIENT_ID, parameters.getClientId());
			MUser user = MUser.get(Env.getCtx(), userName);
			builder.withClaim(LoginClaims.AD_User_ID.name(), user.getAD_User_ID());
			responseNode.addProperty("userId", user.getAD_User_ID());
			defaultLanguage = getPreferenceUserLanguage(user.getAD_User_ID());
		}
		if (parameters.getLanguage() != null) {
			defaultLanguage = parameters.getLanguage();
		}

		builder.withClaim(LoginClaims.AD_Language.name(), defaultLanguage);
		responseNode.addProperty("language", defaultLanguage);

		
		Timestamp expiresAt = TokenUtils.getTokenExpiresAt();
		builder.withIssuer(TokenUtils.getTokenIssuer()).withExpiresAt(expiresAt).withKeyId(TokenUtils.getTokenKeyId());
		try {
			String token = builder.sign(Algorithm.HMAC512(TokenUtils.getTokenSecret()));
			responseNode.addProperty("token", token);
		} catch (IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(responseNode.toString()).build();
	}
	
	/**
	 * Returns the user preference language
	 * if non exist - returns the client language
	 * */
	private String getPreferenceUserLanguage(int AD_User_ID) {
		Query query = new Query(Env.getCtx(), 
    			MTable.get(Env.getCtx(), I_AD_Preference.Table_ID), 
    			" Attribute=? AND AD_User_ID=? AND PreferenceFor = 'W'",
    			null);

    	MPreference preference = query.setOnlyActiveRecords(true)
    			.setParameters("Language", AD_User_ID)
    			.first();
    	
    	return preference != null ? Language.getAD_Language(preference.getValue()) : MClient.get(Env.getCtx()).getAD_Language();
	}

	
	@Override
	public Response getJWK() {
		JsonObject jwks = new JsonObject();
		if (MSysConfig.getBooleanValue("IDEMPIERE_REST_EXPOSE_JWK", false)) {
			JsonArray keys = new JsonArray();
			JsonObject key = new JsonObject();
			try {
				key.addProperty("alg", Algorithm.HMAC512(TokenUtils.getTokenSecret()).getName());
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
			key.addProperty("k", DatatypeConverter.printBase64Binary(TokenUtils.getTokenSecret().getBytes()));
			key.addProperty("kid", TokenUtils.getTokenKeyId());
			key.addProperty("kty", "oct");
			keys.add(key);

			jwks.add("keys", keys);
		}

		return Response.ok(jwks.toString()).build();
	}

}
