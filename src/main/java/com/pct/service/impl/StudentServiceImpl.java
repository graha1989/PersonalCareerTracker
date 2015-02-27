package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Student;
import com.pct.domain.dto.StudentDTO;
import com.pct.repository.StudentRepository;
import com.pct.service.StudentService;
import com.pct.service.StudentUtil;
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
	public List<StudentDTO> findAll() {

		List<StudentDTO> studentsDTOList = new ArrayList<StudentDTO>();

		List<Student> studentsList = studentRepository.findAll();
		for (Student s : studentsList) {
			StudentDTO studentDTO = new StudentDTO(s);
			studentDTO.setId(s.getId());
			studentsDTOList.add(studentDTO);
		}
		return studentsDTOList;
	}

	@Override
	@Transactional
	public StudentDTO findStudentById(Long id) throws StudentNotFoundException {

		StudentDTO studentDTO;

		if (id == null || studentRepository.findOne(id) == null) {
			throw new StudentNotFoundException();
		} else {
			Student student = studentRepository.findOne(id);
			studentDTO = new StudentDTO(student);
		}

		return studentDTO;
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
	public void saveStudent(StudentDTO studentDto) {
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
	public StudentDTO findStudentByTranscriptNumber(String transcriptNumber) throws StudentNotFoundException {
		
		StudentDTO studentDTO;

		if (transcriptNumber == null || studentRepository.findByTranscriptNumber(transcriptNumber) == null) {
			throw new StudentNotFoundException();
		} else {
			Student student = studentRepository.findByTranscriptNumber(transcriptNumber);
			studentDTO = new StudentDTO(student);
		}

		return studentDTO;
	}

}
