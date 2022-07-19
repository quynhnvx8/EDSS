package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileResource {

	public FileResource() {}
	
	@JsonProperty("path")
	private String path;
	
	@JsonProperty("file_name")
	private String fileName;
	
	@JsonProperty("alias_name")
	private String aliasName;
	
	@JsonProperty("table_id")
	private Integer tableId;
	
	@JsonProperty("record_id")
	private Integer recordId;
	
	@JsonProperty("created_by")
	private Integer createdBy;
	
	
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
}
