package com.pct.validation;

/**
 * Exception to be thrown when user tries to update or delete non-existing professors memberships in organizations.
 * 
 * @author a.grahovac
 * 
 */
public class MembershipNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public MembershipNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public MembershipNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public MembershipNotFoundException(Throwable cause) {
		super(cause);
	}

}
