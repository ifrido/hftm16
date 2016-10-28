package ch.hftm.exception;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = -707318162166632389L;

	public AlreadyExistsException(String message) {
		super(message);
	}
}
