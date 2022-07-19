package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VOfficeResource {

	public VOfficeResource() {}
	
	@JsonProperty("record_id")
	private Integer recordId;
	
	@JsonProperty("table_id")
	private Integer tableId;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("username")
	private String userName;
	
	@JsonProperty("window_code")
	private String windowCode;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWindowCode() {
		return windowCode;
	}

	public void setWindowCode(String windowCode) {
		this.windowCode = windowCode;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
