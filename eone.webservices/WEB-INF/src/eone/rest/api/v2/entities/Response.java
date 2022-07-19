package eone.rest.api.v2.entities;

/**
 * Response RESTful
 * @author hungtq24
 * @since 15/08/2018
 *
 */
public class Response {
	private String status;
	private String description;
	private Object content;
	
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

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	private Response() {}
	
	public static ResponseBuilder ok() {
		return status(Status.OK);
	}
	
	private static ResponseBuilder status(String status) {
		return ResponseBuilder.newInstance().status(status);
	}
 	
	public static ResponseBuilder ok(Object entity) {
		return ok(entity, null);
	} 
	
	public static ResponseBuilder ok(Object entity, String description) {
		ResponseBuilder b = ok();
		b.entity(entity);
		b.description(description);
		return b;
	}
	public static class ResponseBuilder {
		private Object content;
		private String status;
		private String description;
		
		private ResponseBuilder() {}
		
		public static ResponseBuilder newInstance() {
			return new ResponseBuilder();
		}
		
		public  ResponseBuilder status(String status) {
			this.status = status;
			return this;
		}
		
		public ResponseBuilder description(String description) {
			this.description = description;
			return this;
		}
		public void entity(Object content) {
			this.content = content;
		}
		
		public Response build() {
			Response resp = new Response();
			resp.setContent(this.content);
			resp.setStatus(this.status);
			resp.setDescription(this.description);
			return resp;
		}
	}
	
	
	public static class Status {
		/** Success	*/
		public static final String OK = "200";
		/** Error client	*/
		public static final String INVALID_PARAMETER = "400";
		/** Error server	*/
		public static final String SAVE_ERROR = "500";
		public static final String DELETE_ERROR = "501";
		/** Internal Server Error */
		public static final String SERVER_ERROR = "500"; 
		/** Upload File Error */
		public static final String UPLOAD_ERROR = "510";
		public static final String FILE_NOT_FOUND = "401";
		
		public static final String NO_DATA_FOUND = "402";
		
		public static final String SIGNER_ERROR = "550";
		
		public static final String AUTHENTICATION_FAILED = "600";
		
	}
	

}
