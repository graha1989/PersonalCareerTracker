package com.pct.validation;

public class ProfessorNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5551129679593877195L;

	/**
	 * Constructor.
	 */
	public ProfessorNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public ProfessorNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public ProfessorNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
