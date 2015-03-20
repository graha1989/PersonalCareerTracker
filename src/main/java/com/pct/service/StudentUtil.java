package com.pct.service;

import com.pct.domain.Student;
import com.pct.domain.dto.StudentDto;

/**
 * Student utility class.
 * 
 * @author a.grahovac
 * 
 */
public class StudentUtil {

	/**
	 * Converts dto from StudentDto to new {@link Student} instance.
	 * 
	 * @param dto StudentDto
	 * @return student instance
	 */
	public static Student createNewStudentInstanceFromStudentDTO(StudentDto studentDto) {
		Student student = new Student();

		student.setTranscriptNumber(studentDto.getTranscriptNumber());
		student.setName(studentDto.getName());
		student.setSurname(studentDto.getSurname());

		return student;
	}

	/**
	 * Converts dto from StudentDto to {@link Student} instance.
	 * 
	 * @param dto StudentDto
	 * @return student instance
	 */
	public static Student createStudentInstanceFromStudentDTO(StudentDto studentDto) {
		Student student = createNewStudentInstanceFromStudentDTO(studentDto);
		student.setId(studentDto.getId());

		return student;
	}

}
