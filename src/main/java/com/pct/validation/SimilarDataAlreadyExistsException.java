package com.pct.validation;

public class SimilarDataAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 6444973355862296521L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor.
	 */
	public SimilarDataAlreadyExistsException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 * @param rejectedValue
	 */
	public SimilarDataAlreadyExistsException(String fieldName, String rejectedValue) {
		super();
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public SimilarDataAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public SimilarDataAlreadyExistsException(Throwable cause) {
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
