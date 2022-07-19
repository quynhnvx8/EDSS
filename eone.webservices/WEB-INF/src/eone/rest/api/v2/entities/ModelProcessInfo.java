package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelProcessInfo {
	@JsonProperty("process_id")
	private Integer processId;
	
	@JsonProperty("record_id")
	private Integer recordId;
	
	@JsonProperty("table_id")
	private Integer tableId;
	
	@JsonProperty("author_id")
	private Integer authorId;
	

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
}
