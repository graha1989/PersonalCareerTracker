package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Award.
 * 
 * @author a.grahovac
 * 
 */
public class PublicationCategoryNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public PublicationCategoryNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public PublicationCategoryNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public PublicationCategoryNotFoundException(Throwable cause) {
		super(cause);
	}

}
