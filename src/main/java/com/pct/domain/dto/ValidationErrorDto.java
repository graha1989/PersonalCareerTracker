package com.pct.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto implements Serializable {

	private static final long serialVersionUID = 5256235156727230372L;

	/* List of field errors. */
	private List<FieldErrorDto> fieldErrors = new ArrayList<FieldErrorDto>();

	/**
	 * Constructor.
	 */
	public ValidationErrorDto() {
		// EMPTY CONSTRUCTOR.
	}

	public void addFieldError(String path, String message) {
		FieldErrorDto error = new FieldErrorDto(path, message);
		fieldErrors.add(error);
	}

	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
