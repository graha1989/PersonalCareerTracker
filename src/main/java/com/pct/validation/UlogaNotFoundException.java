package com.pct.validation;

/**
 * Exception to be thrown when user tries to access non-existing role(uloga).
 * 
 * @author a.grahovac
 * 
 */
public class UlogaNotFoundException extends Exception {

	private static final long serialVersionUID = 1706128400292454257L;
	
	/**
	 * Constructor.
	 */
	public UlogaNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public UlogaNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public UlogaNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
