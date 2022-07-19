package eone.rest.api.v2.security;

import eone.base.model.MSysConfig;
import eone.util.CCache;
import eone.util.DB;
import eone.util.SecureEngine;

/**
 * Auth security
 * @author hungtq24
 *
 */
public class SecureAuth {
	private final static String  IP_ACCESS_WHITELIST = "IP_ACCESS_WHITELIST";
	private final static String  IP_ACCESS_LOCALHOST = "127.0.0.1";
	
	public SecureAuth() {}
	
	
	public static AuthResult auth2(UserDetails user, String ip) {
		AuthResult result = checkIP(ip);
		if (!result.isAccess()) {
			return result;
		}
		
		return checkAccount(user);
	}
	
	
	private static AuthResult checkIP(String ip) {
		String ipAccess = MSysConfig.getValue(IP_ACCESS_WHITELIST, IP_ACCESS_LOCALHOST);
		if (!ipAccess.contains(ip)) {
			return new AuthResult(false, "IP can not access");
		}
		
		return new AuthResult(true, "IP can access");
	}
	
	protected static CCache<String, String> s_cache = new CCache<String, String>("SecureAuth_s_cache", 20, CCache.DEFAULT_TIMEOUT);
	
	private static AuthResult checkAccount(UserDetails user) {
		if (user.getClientId() == null || user.getPassword() == null || user.getUsername() == null) {
			return new AuthResult(false, "Invalid parameter");
		}
		
		String key = user.getClientId() + "@@" + user.getUsername();
		String password;
		if (s_cache.containsKey(key)) {
			password = s_cache.get(key);
		} else {
			String sql = " SELECT password FROM c_service_user " +
					 	 " WHERE isactive ='Y' AND username = ? AND client_id = ? ";
		
			password = DB.getSQLValueString(null, sql, user.getUsername(), user.getClientId());	
			s_cache.put(key, password);
		}
		String passwordEncrypt = SecureEngine.encrypt(user.getPassword(), 0);
		if (passwordEncrypt.equals(password)) {
			return new AuthResult(true, "User can access");
		}
		
		return new AuthResult(false, "User can not access");
	}
	
}
