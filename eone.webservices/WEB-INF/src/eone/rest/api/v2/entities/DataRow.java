package eone.rest.api.v2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataRow extends BaseAuth {


	@JsonProperty("id")
	private Integer recordId;
	
	@JsonProperty("table_name")
	private Integer tableName;
	
	@JsonProperty("table_id")
	private Integer tableId;
	
	@JsonProperty("fields")
	private DataField[] fieldArray;
	
	

	public DataField[] getFieldArray() {
		return fieldArray;
	}

	public void setFieldArray(DataField[] fieldArray) {
		this.fieldArray = fieldArray;
	}

	@JsonProperty("record_id")
	public Integer getRecordId() {
		return recordId;
	}
	
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getTableName() {
		return tableName;
	}

	public void setTableName(Integer tableName) {
		this.tableName = tableName;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
}
