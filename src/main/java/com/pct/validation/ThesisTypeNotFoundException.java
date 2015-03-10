package com.pct.validation;

public class ThesisTypeNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public ThesisTypeNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ThesisTypeNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ThesisTypeNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
