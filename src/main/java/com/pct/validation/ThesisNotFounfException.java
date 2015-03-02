package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing thesis.
 * 
 * @author a.grahovac
 * 
 */
public class ThesisNotFounfException extends Exception {
	
	private static final long serialVersionUID = -3928303991077639393L;

	/**
	 * Constructor.
	 */
	public ThesisNotFounfException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ThesisNotFounfException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ThesisNotFounfException(Throwable cause) {
		super(cause);
	}
	
}
