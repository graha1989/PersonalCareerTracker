package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Award.
 * 
 * @author a.grahovac
 * 
 */
public class PublicationNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public PublicationNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public PublicationNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public PublicationNotFoundException(Throwable cause) {
		super(cause);
	}

}
