package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing student.
 * 
 * @author a.grahovac
 * 
 */
public class StudentNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public StudentNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public StudentNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public StudentNotFoundException(Throwable cause) {
		super(cause);
	}

}
