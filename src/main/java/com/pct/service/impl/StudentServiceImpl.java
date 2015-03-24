package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Student;
import com.pct.domain.dto.StudentDto;
import com.pct.repository.StudentRepository;
import com.pct.service.StudentService;
import com.pct.service.util.StudentUtil;
import com.pct.validation.StudentNotFoundException;

/**
 * Student service implementation class.
 * 
 * @author a.grahovac
 * 
 */
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	@Transactional
	public List<StudentDto> findAll() {

		List<StudentDto> studentsDTOList = new ArrayList<StudentDto>();

		List<Student> studentsList = studentRepository.findAll();
		for (Student s : studentsList) {
			StudentDto studentDto = new StudentDto(s);
			studentDto.setId(s.getId());
			studentsDTOList.add(studentDto);
		}
		return studentsDTOList;
	}

	@Override
	@Transactional
	public StudentDto findStudentById(Long id) throws StudentNotFoundException {

		StudentDto studentDto;

		if (id == null || studentRepository.findOne(id) == null) {
			throw new StudentNotFoundException();
		} else {
			Student student = studentRepository.findOne(id);
			studentDto = new StudentDto(student);
		}

		return studentDto;
	}

	@Override
	@Transactional
	public void deleteStudent(Long id) throws StudentNotFoundException {

		if (id == null || studentRepository.findOne(id) == null) {
			throw new StudentNotFoundException();
		}

		studentRepository.delete(id);
	}

	@Override
	@Transactional
	public void saveStudent(StudentDto studentDto) {
		Student student = null;

		if (studentDto.getId() != null) {
			student = StudentUtil.createStudentInstanceFromStudentDTO(studentDto);
		} else {
			student = StudentUtil.createNewStudentInstanceFromStudentDTO(studentDto);
		}

		studentRepository.saveAndFlush(student);
	}

	@Override
	@Transactional
	public StudentDto findStudentByTranscriptNumber(String transcriptNumber) throws StudentNotFoundException {
		
		StudentDto studentDto;

		if (transcriptNumber == null || studentRepository.findByTranscriptNumber(transcriptNumber) == null) {
			throw new StudentNotFoundException();
		} else {
			Student student = studentRepository.findByTranscriptNumber(transcriptNumber);
			studentDto = new StudentDto(student);
		}

		return studentDto;
	}

	@Override
	@Transactional
	public List<StudentDto> findStudentsStartsWith(String value) throws StudentNotFoundException {
		
		List<StudentDto> studentsDTOList = new ArrayList<StudentDto>();

		List<Student> studentsList = studentRepository.findByTranscriptNumberLikeOrNameLikeOrSurnameLike(value);
		for (Student s : studentsList) {
			StudentDto studentDto = new StudentDto(s);
			studentDto.setId(s.getId());
			studentsDTOList.add(studentDto);
		}
		return studentsDTOList;
	}

}
