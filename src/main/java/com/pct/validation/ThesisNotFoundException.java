package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing thesis.
 * 
 * @author a.grahovac
 * 
 */
public class ThesisNotFoundException extends Exception {
	
	private static final long serialVersionUID = -3928303991077639393L;

	/**
	 * Constructor.
	 */
	public ThesisNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ThesisNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ThesisNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
