package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Student;
import com.pct.domain.dto.StudentDto;
import com.pct.repository.StudentRepository;
import com.pct.repository.ThesisRepository;
import com.pct.service.StudentService;
import com.pct.service.util.StudentUtil;
import com.pct.validation.DuplicateDataException;
import com.pct.validation.StudentDeleteException;
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
	
	@Autowired
	private ThesisRepository thesisRepository;

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
		if (id != null) {
			Student student = studentRepository.findOne(id);
			if (student != null) {
				studentDto = new StudentDto(student);
				return studentDto;
			}
		}
		
		throw new StudentNotFoundException();
	}

	@Override
	@Transactional
	public void deleteStudent(Long id) throws StudentNotFoundException, StudentDeleteException {

		Student student = null;
		if (id == null || id == 0L || studentRepository.findOne(id) == null) {
			throw new StudentNotFoundException();
		}
		student = studentRepository.findOne(id);
		if (thesisRepository.countByStudent(student) > 0L) {
			throw new StudentDeleteException("studentTranscriptNumber", student.getTranscriptNumber());
		}

		studentRepository.delete(id);
	}

	@Override
	@Transactional
	public void saveStudent(StudentDto studentDto) throws DuplicateDataException {
		Student student = null;

		Student studentFromDatabase = studentRepository.findByTranscriptNumber(studentDto.getTranscriptNumber());
		
		if(studentFromDatabase != null && studentFromDatabase.getId() != studentDto.getId()) {
			throw new DuplicateDataException("student", "student.transcriptNumber");
		}
		
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
		if (transcriptNumber != null) {
			Student student = studentRepository.findByTranscriptNumber(transcriptNumber);
			if (student != null) {
				studentDto = new StudentDto(student);
				return studentDto;
			}
		}
		
		throw new StudentNotFoundException();
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
