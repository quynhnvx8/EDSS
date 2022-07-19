package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VOffice Response
 * @author hungtq24
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VOResponse extends RecordResponse {
	@JsonProperty("vo_status")
	private String voStatus;
	
	@JsonProperty("vo_code")
	private String voCode;
	
	@JsonProperty("vo_name")
	private String voName;
	
	private String status;
	private String description;
	
	public String getVoStatus() {
		return voStatus;
	}
	public void setVoStatus(String voStatus) {
		this.voStatus = voStatus;
	}
	public String getVoCode() {
		return voCode;
	}
	public void setVoCode(String voCode) {
		this.voCode = voCode;
	}
	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
