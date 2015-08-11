package com.pct.validation;

public class DuplicateDataException extends Exception {

	private static final long serialVersionUID = 6444973355862296521L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor.
	 */
	public DuplicateDataException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 * @param rejectedValue
	 */
	public DuplicateDataException(String fieldName, String rejectedValue) {
		super();
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public DuplicateDataException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public DuplicateDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * Getter.
	 * 
	 * @return field name.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Getter rejected value.
	 * 
	 * @return rejected value.
	 */
	public String getRejectedValue() {
		return rejectedValue;
	}

}
