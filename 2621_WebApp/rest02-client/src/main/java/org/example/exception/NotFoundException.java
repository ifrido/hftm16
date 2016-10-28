package org.example.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotFoundException extends WebApplicationException {

	private static final long serialVersionUID = -955170823964139264L;

	public NotFoundException() {
		super(Status.NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(Response.status(Status.NOT_FOUND).entity(message).build());
	}
}
