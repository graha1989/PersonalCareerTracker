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

import com.pct.domain.dto.ValidationErrorDto;
import com.pct.validation.DuplicateDataException;
import com.pct.validation.EmailExistException;
import com.pct.validation.InstitutionDeleteException;
import com.pct.validation.ProjectDeleteException;
import com.pct.validation.SeminarDeleteException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.StudentDeleteException;
import com.pct.validation.SubjectDeleteException;
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

	public static String SIMILAR_TEACHING_EXPERIENCE_EXIST_CODE = "SimilarTeachingExprerienceExist.experience";
	public static String SIMILAR_TEACHING_EXPERIENCE_EXIST_DEFAULT_MESSAGE = "Teaching experience with similar(same) data already exist";
	public static String SIMILAR_TEACHING_EXPERIENCE_EXIST_OBJECT_NAME = "teachingExperienceDto";

	public static String STUDENT_CAN_NOT_BE_DELETED = "CanNotDeleteStudent.message";
	public static String STUDENT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE = "Student can not be deleted";
	public static String STUDENT_CAN_NOT_BE_PERSISTED = "CanNotPersistStudent.message";
	public static String STUDENT_CAN_NOT_BE_PERSISTED_DEFAULT_MESSAGE = "Student can not be persisted";
	public static String STUDENT_OBJECT_NAME = "student";

	public static String INSTITUTION_CAN_NOT_BE_DELETED = "CanNotDeleteInstitution.message";
	public static String INSTITUTION_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE = "Institution can not be deleted";
	public static String INSTITUTION_CAN_NOT_BE_DELETED_OBJECT_NAME = "institution";

	public static String SUBJECT_CAN_NOT_BE_DELETED = "CanNotDeleteSubject.message";
	public static String SUBJECT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE = "Subject can not be deleted";
	public static String SUBJECT_CAN_NOT_BE_DELETED_OBJECT_NAME = "subject";

	public static String SEMINAR_CAN_NOT_BE_DELETED = "CanNotDeleteSeminar.message";
	public static String SEMINAR_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE = "Seminar can not be deleted";
	public static String SEMINAR_CAN_NOT_BE_DELETED_OBJECT_NAME = "seminar";

	public static String PROJECT_CAN_NOT_BE_DELETED = "CanNotDeleteProject.message";
	public static String PROJECT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE = "Project can not be deleted";
	public static String PROJECT_CAN_NOT_BE_DELETED_OBJECT_NAME = "project";

	@Autowired
	private MessageSource messageSource;

	/**
	 * Processes situation when user or professor enter existing user name during registration.
	 * 
	 * @param ex {@link UserNameExistException}
	 * @return {@link ValidationErrorDto}
	 */
	@ExceptionHandler(UserNameExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processUserNameValidationError(UserNameExistException ex) {
		String[] codes = { USERNAME_EXIST_CODE };
		FieldError userNameError = new FieldError(USERNAME_EXIST_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false, codes, null,
				USERNAME_EXIST_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(userNameError);
		return processFieldErrors(fieldErrors);
	}

	/**
	 * Processes situation when user or professor enter existing email during registration.
	 * 
	 * @param ex {@link EmailExistException}
	 * @return {@link ValidationErrorDto}
	 */
	@ExceptionHandler(EmailExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processEmailValidationError(EmailExistException ex) {
		String[] codes = { EMAIL_EXIST_CODE };
		FieldError emailError = new FieldError(EMAIL_EXIST_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false, codes, null,
				EMAIL_EXIST_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(emailError);
		return processFieldErrors(fieldErrors);
	}

	/**
	 * Processes validation for user's input during registration process.
	 * 
	 * @param ex {@link MethodArgumentNotValidException}
	 * @return {@link ValidationErrorDto}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(SimilarDataAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ValidationErrorDto processNewTeachingExperienceValidationError(SimilarDataAlreadyExistsException ex) {
		String[] codes = { SIMILAR_TEACHING_EXPERIENCE_EXIST_CODE };
		FieldError teachingExperienceError = new FieldError(SIMILAR_TEACHING_EXPERIENCE_EXIST_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(),
				false, codes, null, SIMILAR_TEACHING_EXPERIENCE_EXIST_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(teachingExperienceError);

		return processFieldErrors(fieldErrors);
	}
	
	@ExceptionHandler(DuplicateDataException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ValidationErrorDto processNewStudentValidationError(DuplicateDataException ex) {
		String[] codes = { STUDENT_CAN_NOT_BE_PERSISTED };
		FieldError studentPersistanceError = new FieldError(STUDENT_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(),
				false, codes, null, STUDENT_CAN_NOT_BE_PERSISTED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(studentPersistanceError);

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(StudentDeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processDeleteStudentException(StudentDeleteException ex) {
		String[] codes = { STUDENT_CAN_NOT_BE_DELETED };
		FieldError studentDeleteError = new FieldError(STUDENT_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false,
				codes, null, STUDENT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(studentDeleteError);

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(InstitutionDeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processInstitutionDeleteException(InstitutionDeleteException ex) {
		String[] codes = { INSTITUTION_CAN_NOT_BE_DELETED };
		FieldError institutionDeleteError = new FieldError(INSTITUTION_CAN_NOT_BE_DELETED_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(),
				false, codes, null, INSTITUTION_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(institutionDeleteError);

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(SubjectDeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processSubjectDeleteException(SubjectDeleteException ex) {
		String[] codes = { SUBJECT_CAN_NOT_BE_DELETED };
		FieldError subjectDeleteError = new FieldError(SUBJECT_CAN_NOT_BE_DELETED_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false,
				codes, null, SUBJECT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(subjectDeleteError);

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(SeminarDeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processSeminarDeleteException(SeminarDeleteException ex) {
		String[] codes = { SEMINAR_CAN_NOT_BE_DELETED };
		FieldError seminarDeleteError = new FieldError(SEMINAR_CAN_NOT_BE_DELETED_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false,
				codes, null, SEMINAR_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(seminarDeleteError);

		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(ProjectDeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processProjectDeleteException(ProjectDeleteException ex) {
		String[] codes = { PROJECT_CAN_NOT_BE_DELETED };
		FieldError projectDeleteError = new FieldError(PROJECT_CAN_NOT_BE_DELETED_OBJECT_NAME, ex.getFieldName(), ex.getRejectedValue(), false,
				codes, null, PROJECT_CAN_NOT_BE_DELETED_DEFAULT_MESSAGE);
		List<FieldError> fieldErrors = new ArrayList<FieldError>();
		fieldErrors.add(projectDeleteError);

		return processFieldErrors(fieldErrors);
	}

	/**
	 * Adds field errors to {@link ValidationErrorDto} object.
	 * 
	 * @param fieldErrors
	 * @return
	 */
	private ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDto dto = new ValidationErrorDto();

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
