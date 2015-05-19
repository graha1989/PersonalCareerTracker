package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing User.
 * 
 * @author a.grahovac
 * 
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public UserNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

}
