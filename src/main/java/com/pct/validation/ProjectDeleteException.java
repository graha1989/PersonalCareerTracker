package com.pct.validation;

public class ProjectDeleteException extends Exception {

	private static final long serialVersionUID = 4702647582737708903L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor
	 */
	public ProjectDeleteException() {
		super();
	}

	/**
	 * @param message
	 */
	public ProjectDeleteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ProjectDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param fieldName
	 * @param rejectedValue
	 */
	public ProjectDeleteException(String fieldName, String rejectedValue) {
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