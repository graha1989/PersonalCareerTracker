package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing Award.
 * 
 * @author a.grahovac
 * 
 */
public class AwardNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public AwardNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public AwardNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public AwardNotFoundException(Throwable cause) {
		super(cause);
	}

}
