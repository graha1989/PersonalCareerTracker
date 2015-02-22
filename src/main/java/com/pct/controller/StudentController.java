package com.pct.controller;

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
import com.pct.domain.dto.StudentDTO;
import com.pct.service.StudentService;
import com.pct.validation.StudentNotFoundException;

@RestController
@RequestMapping("/showAllStudents")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	StudentService studentService;

	@RequestMapping(value = "students", method = RequestMethod.GET, produces = "application/json")
	public List<StudentDTO> showAllStudents() {
		List<StudentDTO> students = studentService.findAll();

		return students;
	}

	@RequestMapping(value = "selectedStudent", method = RequestMethod.GET, produces = "application/json")
	public StudentDTO showStudent(Long id) throws StudentNotFoundException {
		StudentDTO student = studentService.findStudentById(id);

		return student;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<StudentDTO> deleteStudent(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws StudentNotFoundException {
		studentService.deleteStudent(id);

		return new ResponseEntity<StudentDTO>(HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<StudentDTO> persistStudent(@Valid @RequestBody StudentDTO studentDto) {
		studentService.saveStudent(studentDto);
		logger.debug("Student:" + studentDto.getName() + " " + studentDto.getSurname() + " successfully saved.");

		return new ResponseEntity<StudentDTO>(HttpStatus.OK);
	}

}
