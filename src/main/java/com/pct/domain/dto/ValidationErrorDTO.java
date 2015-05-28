package com.pct.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO implements Serializable {

	private static final long serialVersionUID = 5256235156727230372L;

	/* List of field errors. */
	private List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();

	/**
	 * Constructor.
	 */
	public ValidationErrorDTO() {
		// EMPTY CONSTRUCTOR.
	}

	public void addFieldError(String path, String message) {
		FieldErrorDTO error = new FieldErrorDTO(path, message);
		fieldErrors.add(error);
	}

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
