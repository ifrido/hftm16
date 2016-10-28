package org.example.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {

	
	private static final long serialVersionUID = 4076331482601475242L;

	public BadRequestException() {
		super(Status.BAD_REQUEST);
	}

	public BadRequestException(String message) {
		super(Response.status(Status.BAD_REQUEST).entity(message).build());
	}
}
