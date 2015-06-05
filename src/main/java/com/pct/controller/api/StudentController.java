package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.StudentDto;
import com.pct.service.StudentService;
import com.pct.validation.StudentNotFoundException;

@RestController
@RequestMapping(RequestMappings.STUDENTS_API)
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	StudentService studentService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_STUDENTS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudentDto>> showAllStudents() {
		List<StudentDto> students = studentService.findAll();

		return new ResponseEntity<List<StudentDto>>(students, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_STUDENT, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<StudentDto> showStudent(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws StudentNotFoundException {
		StudentDto student = studentService.findStudentById(id);

		return new ResponseEntity<StudentDto>(student, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<StudentDto> deleteStudent(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws StudentNotFoundException {
		studentService.deleteStudent(id);

		return new ResponseEntity<StudentDto>(HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<StudentDto> persistStudent(@Valid @RequestBody StudentDto studentDto) {
		studentService.saveStudent(studentDto);
		StudentDto student = new StudentDto();
		try {
			student = studentService.findStudentByTranscriptNumber(studentDto.getTranscriptNumber());
		} catch (StudentNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Student:" + studentDto.getName() + " " + studentDto.getSurname() + " successfully saved.");

		return new ResponseEntity<StudentDto>(student, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_STUDENT, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudentDto>> findStudentStartsWith(
			@RequestParam(value = "value", required = true) String value) throws StudentNotFoundException {

		List<StudentDto> students = new ArrayList<StudentDto>();
		if (value.length() >= 3) {
			students = studentService.findStudentsStartsWith(value);
		}

		return new ResponseEntity<List<StudentDto>>(students, HttpStatus.OK);
	}

}
