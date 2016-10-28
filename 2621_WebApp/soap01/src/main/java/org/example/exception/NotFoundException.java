package org.example.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "name")
public class NotFoundException extends Exception {

	private String name;

	public NotFoundException(String message, String name) {
		super(message);
		this.name = name;
	}

	public String getFaultInfo() {
		return name;
	}
}
