package com.pct.validation;

public class StudentDeleteException extends Exception {

	private static final long serialVersionUID = 4702647582737708903L;
	
	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor
	 */
	public StudentDeleteException() {
		super();
	}

	/**
	 * @param message
	 */
	public StudentDeleteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public StudentDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param fieldName
	 * @param rejectedValue
	 */
	public StudentDeleteException(String fieldName, String rejectedValue) {
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