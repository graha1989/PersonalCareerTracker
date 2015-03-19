package com.pct.validation;

public class LanguageNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public LanguageNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public LanguageNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public LanguageNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
