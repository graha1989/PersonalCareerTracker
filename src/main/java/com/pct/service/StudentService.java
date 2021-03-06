package com.pct.service;

import java.util.List;

import com.pct.domain.dto.StudentDto;
import com.pct.validation.DuplicateDataException;
import com.pct.validation.StudentDeleteException;
import com.pct.validation.StudentNotFoundException;

public interface StudentService {

	List<StudentDto> findAll();

	void deleteStudent(Long id) throws StudentNotFoundException, StudentDeleteException;

	StudentDto findStudentById(Long id) throws StudentNotFoundException;
	
	void saveStudent(StudentDto studentDto) throws DuplicateDataException;

	StudentDto findStudentByTranscriptNumber(String transcriptNumber) throws StudentNotFoundException;

	List<StudentDto> findStudentsStartsWith(String value) throws StudentNotFoundException;

}
