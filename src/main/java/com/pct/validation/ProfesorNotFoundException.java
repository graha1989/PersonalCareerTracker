package com.pct.validation;

public class ProfesorNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public ProfesorNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ProfesorNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ProfesorNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
