package com.pct.validation;

public class LanguageExperienceNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public LanguageExperienceNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public LanguageExperienceNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public LanguageExperienceNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
