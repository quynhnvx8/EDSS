
package eone.rest.api.v1.auth;

/**
 * 
 * @author Client
 *
 */
public class LoginParameters {
	
	private int roleId;
	private int clientId;
	private String language;

	public LoginParameters() {
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}


	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
