
package eone.rest.api.v1.jwt;

/**
 * token secret provider interface
 * @author Client
 *
 */
public interface ITokenSecretProvider {
	/**
	 * 
	 * @return token secret
	 */
	public String getSecret();

	public default String getKeyId() {
		return "dssvn";
	}

	public default String getIssuer() {
		return "dssvn.com";
	}

}
