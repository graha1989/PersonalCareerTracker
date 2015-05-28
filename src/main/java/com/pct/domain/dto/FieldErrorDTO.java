package com.pct.domain.dto;

import java.io.Serializable;

public class FieldErrorDTO implements Serializable {

	private static final long serialVersionUID = 1814768318941064464L;

	/* Field name */
	private String field;

	/* Error message */
	private String message;

	/**
	 * Constructor.
	 * 
	 * @param field
	 * @param message
	 */
	public FieldErrorDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}

	/**
	 * Getter.
	 * 
	 * @return field name
	 */
	public String getField() {
		return field;
	}

	/**
	 * Setter.
	 * 
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Getter.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
