package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Award.
 * 
 * @author a.grahovac
 * 
 */
public class ProfessorStudiesNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public ProfessorStudiesNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ProfessorStudiesNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ProfessorStudiesNotFoundException(Throwable cause) {
		super(cause);
	}

}
