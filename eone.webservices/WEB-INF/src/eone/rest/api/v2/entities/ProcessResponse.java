package eone.rest.api.v2.entities;

public class ProcessResponse {

	public ProcessResponse() {}
	
	private Integer pInstanceId;
	
	private String reportPath;
	
	private String reportName;
	
	private String reportFormat;
	
	private boolean isError;
	private String error;
	
	
	
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public Integer getpInstanceId() {
		return pInstanceId;
	}
	public void setpInstanceId(Integer pInstanceId) {
		this.pInstanceId = pInstanceId;
	}
	public boolean isError() {
		return isError;
	}
	public void setIsError(boolean isError) {
		this.isError = isError;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getReportFormat() {
		return reportFormat;
	}
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	
	
}
