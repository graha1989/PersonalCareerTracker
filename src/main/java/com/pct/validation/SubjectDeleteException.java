package com.pct.validation;

public class SubjectDeleteException extends Exception {

	private static final long serialVersionUID = 4702647582737708903L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor
	 */
	public SubjectDeleteException() {
		super();
	}

	/**
	 * @param message
	 */
	public SubjectDeleteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SubjectDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param fieldName
	 * @param rejectedValue
	 */
	public SubjectDeleteException(String fieldName, String rejectedValue) {
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