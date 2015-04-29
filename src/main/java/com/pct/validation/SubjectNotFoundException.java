package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Subject.
 * 
 * @author a.grahovac
 * 
 */
public class SubjectNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public SubjectNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public SubjectNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public SubjectNotFoundException(Throwable cause) {
		super(cause);
	}

}
