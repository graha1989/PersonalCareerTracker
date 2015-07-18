package com.pct.validation;

public class SeminarDeleteException extends Exception {

	private static final long serialVersionUID = 4702647582737708903L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor
	 */
	public SeminarDeleteException() {
		super();
	}

	/**
	 * @param message
	 */
	public SeminarDeleteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SeminarDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param fieldName
	 * @param rejectedValue
	 */
	public SeminarDeleteException(String fieldName, String rejectedValue) {
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