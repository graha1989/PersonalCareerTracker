package com.pct.validation;

/**
 * Exception to be thrown when user tries to register with an existing userName.
 * 
 * @author a.grahovac
 * 
 */
public class UserNameExistException extends Exception {

	private static final long serialVersionUID = -1409877260164677181L;

	private String fieldName;
	private String rejectedValue;

	/**
	 * Constructor.
	 */
	public UserNameExistException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 * @param rejectedValue
	 */
	public UserNameExistException(String fieldName, String rejectedValue) {
		super();
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public UserNameExistException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public UserNameExistException(Throwable cause) {
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
	 * Getter for rejected value.
	 * 
	 * @return rejected value.
	 */
	public String getRejectedValue() {
		return rejectedValue;
	}

}
