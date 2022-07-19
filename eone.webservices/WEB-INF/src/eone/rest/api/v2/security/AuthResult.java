package eone.rest.api.v2.security;

public class AuthResult {

	private boolean access;
	private String description;
	public boolean isAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public AuthResult(boolean isAccess, String desc) {
		this.access = isAccess;
		this.description = desc;
	}

}
