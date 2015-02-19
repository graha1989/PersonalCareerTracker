package com.pct.service;

import java.util.List;

import com.pct.domain.dto.StudentDTO;
import com.pct.validation.StudentNotFoundException;

public interface StudentService {

	List<StudentDTO> findAll();

	void deleteStudent(Long id) throws StudentNotFoundException;

	StudentDTO findStudentById(Long id) throws StudentNotFoundException;

	void saveStudent(StudentDTO studentDto);

}
