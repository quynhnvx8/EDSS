package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseAuth {

	@JsonProperty("username")
	protected String userName;
	
	@JsonProperty("password")
	protected String passWord;

	protected String ip;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
