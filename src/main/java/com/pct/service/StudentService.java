package com.pct.service;

import java.util.List;

import com.pct.domain.dto.StudentDto;
import com.pct.validation.StudentNotFoundException;

public interface StudentService {

	List<StudentDto> findAll();

	void deleteStudent(Long id) throws StudentNotFoundException;

	StudentDto findStudentById(Long id) throws StudentNotFoundException;
	
	void saveStudent(StudentDto studentDto);

	StudentDto findStudentByTranscriptNumber(String transcriptNumber) throws StudentNotFoundException;

	List<StudentDto> findStudentsStartsWith(String value) throws StudentNotFoundException;

}
