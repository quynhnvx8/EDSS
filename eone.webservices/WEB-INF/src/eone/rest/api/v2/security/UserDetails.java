package eone.rest.api.v2.security;

public class UserDetails {
	
	private String clientId;
	private String username;
	private String password;
	
	public UserDetails(String clientId, String userName, String password) {
		this.clientId = clientId;
		this.username = userName;
		this.password = password;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
