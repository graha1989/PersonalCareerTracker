package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Award.
 * 
 * @author a.grahovac
 * 
 */
public class SurveyNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public SurveyNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public SurveyNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public SurveyNotFoundException(Throwable cause) {
		super(cause);
	}

}
