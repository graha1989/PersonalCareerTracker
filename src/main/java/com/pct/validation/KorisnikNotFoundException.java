package com.pct.validation;

public class KorisnikNotFoundException extends Exception {
	
	private static final long serialVersionUID = -8562393512948264118L;

	/**
	 * Constructor.
	 */
	public KorisnikNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public KorisnikNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public KorisnikNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
