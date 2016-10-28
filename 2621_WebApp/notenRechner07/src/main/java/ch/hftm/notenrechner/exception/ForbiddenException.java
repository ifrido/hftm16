package ch.hftm.notenrechner.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ForbiddenException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message, Status status) {
		super(message, status);
	}

}
