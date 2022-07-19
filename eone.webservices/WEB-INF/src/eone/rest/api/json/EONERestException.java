package eone.rest.api.json;

import javax.ws.rs.core.Response.Status;

import eone.exceptions.EONEException;

public class EONERestException extends EONEException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3555412217944639699L;
	
	private Status status;
	
	/**
	 * @param message
	 * @param error -> Response status error
	 */
	public EONERestException(String message, Status error) {
		super(message);
		setErrorResponseStatus(error);
	}
	
	public void setErrorResponseStatus(Status status) {
		this.status = status;
	}
	
	public Status getErrorResponseStatus() {
		return status;
	}

}
