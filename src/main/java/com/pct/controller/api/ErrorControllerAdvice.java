package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pct.domain.dto.ValidationErrorDTO;
import com.pct.validation.EmailExistException;
import com.pct.validation.UserNameExistException;

/**
 * Controllers error handling support class.
 * 
 * @author a.grahovac
 * 
 */
@ControllerAdvice
public class ErrorControllerAdvice {

	public static String USERNAME_EXIST_CODE = "UserNameExist.userName";
	public static String USERNAME_EXIST_DEFAULT_MESSAGE = "User name already exist";
	public static String USERNAME_EXIST_OBJECT_NAME = "professorDto";

	public static String EMAIL_EXIST_CODE = "EmailExist.email";
	public static String EMAIL_EXIST_DEFAULT_MESSAGE = "Email already exist";
	public static String EMAIL_EXIST_OBJECT_NAME = "professorDto";

	@Autowired
	private MessageSource messageSource;

	/**
	 * Processes situation when user or professor enter existing user name during registration.
	 * 
	 * @param ex {@link UserNameExistException}
	 * @return {@link ValidationErrorDTO}
	 */
	@ExceptionHandler(UserNameExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO processUserNameValidationError(UserNameExistException ex) {
		String[] codes = { USERNAME_EXIST_CODE };
		FieldError userNameError = new FieldError(USERNAME_EXIST_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(),
				false, codes, null, USERNAME_EXIST_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(userNameError);
		return processFieldErrors(fieldErrors);
	}

	/**
	 * Processes situation when user or professor enter existing email during registration.
	 * 
	 * @param ex {@link EmailExistException}
	 * @return {@link ValidationErrorDTO}
	 */
	@ExceptionHandler(EmailExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO processEmailValidationError(EmailExistException ex) {
		String[] codes = { EMAIL_EXIST_CODE };
		FieldError emailError = new FieldError(EMAIL_EXIST_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(),
				false, codes, null, EMAIL_EXIST_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(emailError);
		return processFieldErrors(fieldErrors);
	}

	/**
	 * Processes validation for user's input during registration process.
	 * 
	 * @param ex {@link MethodArgumentNotValidException}
	 * @return {@link ValidationErrorDTO}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	/**
	 * Adds field errors to {@link ValidationErrorDTO} object.
	 * 
	 * @param fieldErrors
	 * @return
	 */
	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return dto;
	}

	/**
	 * Gets localized error message from message source.
	 * 
	 * @param fieldError
	 * @return localized error message
	 */
	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			String[] fieldErrorCodes = fieldError.getCodes();
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}

}
