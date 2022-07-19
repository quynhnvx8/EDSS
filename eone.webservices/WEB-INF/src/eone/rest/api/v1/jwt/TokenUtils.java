
package eone.rest.api.v1.jwt;

import java.sql.Timestamp;

import eone.base.Service;
import eone.util.TimeUtil;

/**
 * 
 * @author Client
 *
 */
public class TokenUtils {

	private TokenUtils() {
	}

	/**
	 * 
	 * @return token secret
	 */
	public static String getTokenSecret() {
		ITokenSecretProvider provider = Service.locator().locate(ITokenSecretProvider.class).getService();
		if (provider != null) {
			return provider.getSecret();
		}
		return DefaultTokenSecretProvider.instance.getSecret();
	}

	/**
	 *
	 * @return token key id
	 */
	public static String getTokenKeyId() {
		ITokenSecretProvider provider = Service.locator().locate(ITokenSecretProvider.class).getService();
		if (provider != null) {
			return provider.getKeyId();
		}
		return DefaultTokenSecretProvider.instance.getKeyId();
	}

	/**
	 * 
	 * @return issuer of token
	 */
	public static String getTokenIssuer() {
		ITokenSecretProvider provider = Service.locator().locate(ITokenSecretProvider.class).getService();
		if (provider != null) {
			return provider.getIssuer();
		}
		return DefaultTokenSecretProvider.instance.getIssuer();
	}

	/**
	 * 
	 * @return token expire time stamp
	 */
	public static Timestamp getTokenExpiresAt() {
		Timestamp expiresAt = new Timestamp(System.currentTimeMillis());
		expiresAt = TimeUtil.addMinutess(expiresAt, 60);
		return expiresAt;
	}
}
