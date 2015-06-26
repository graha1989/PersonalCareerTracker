package com.pct.validation;

import com.pct.domain.AcademicCommunityContribution;

/**
 * Exception to be thrown when user tries to update or delete non-existing {@link AcademicCommunityContribution}.
 * 
 * @author a.grahovac
 * 
 */
public class AcademicCommunityContributionNotFoundException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	/**
	 * Constructor.
	 */
	public AcademicCommunityContributionNotFoundException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public AcademicCommunityContributionNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public AcademicCommunityContributionNotFoundException(Throwable cause) {
		super(cause);
	}
}
