package com.pct.validation;

public class InstitutionDeleteException extends Exception {

	private static final long serialVersionUID = 4702647582737708903L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor
	 */
	public InstitutionDeleteException() {
		super();
	}

	/**
	 * @param message
	 */
	public InstitutionDeleteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InstitutionDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param fieldName
	 * @param rejectedValue
	 */
	public InstitutionDeleteException(String fieldName, String rejectedValue) {
		super();
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

}