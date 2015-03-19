package com.pct.service;

import com.pct.domain.Student;
import com.pct.domain.dto.StudentDTO;

/**
 * Student utility class.
 * 
 * @author a.grahovac
 * 
 */
public class StudentUtil {

	/**
	 * Converts dto from StudentDTO to new {@link Student} instance.
	 * 
	 * @param dto StudentDTO
	 * @return student instance
	 */
	public static Student createNewStudentInstanceFromStudentDTO(StudentDTO studentDTO) {
		Student student = new Student();

		student.setTranscriptNumber(studentDTO.getTranscriptNumber());
		student.setName(studentDTO.getName());
		student.setSurname(studentDTO.getSurname());

		return student;
	}

	/**
	 * Converts dto from StudentDTO to {@link Student} instance.
	 * 
	 * @param dto StudentDTO
	 * @return student instance
	 */
	public static Student createStudentInstanceFromStudentDTO(StudentDTO studentDTO) {
		Student student = createNewStudentInstanceFromStudentDTO(studentDTO);
		student.setId(studentDTO.getId());

		return student;
	}

}
