package com.pct.validation;

public class StudiesThesisTypeNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public StudiesThesisTypeNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public StudiesThesisTypeNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public StudiesThesisTypeNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
