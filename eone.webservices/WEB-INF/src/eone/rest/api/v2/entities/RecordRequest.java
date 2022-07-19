package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordRequest {

	public RecordRequest() {}
	
	protected Integer recordId;
	
	protected Integer tableId;
	
	protected Integer userId;
	
	protected Integer windowId;
	

	public Integer getWindowId() {
		return windowId;
	}
	
	@JsonProperty("window_id")
	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}
	public Integer getTableId() {
		return tableId;
	}
	@JsonProperty("table_id")
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	
	public Integer getRecordId() {
		return recordId;
	}
	@JsonProperty("record_id")
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	@JsonProperty("user_id")
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
