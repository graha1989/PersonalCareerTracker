package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Student;
import com.pct.domain.Thesis;
import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.StudentRepository;
import com.pct.repository.ThesisRepository;
import com.pct.repository.ThesisTypeRepository;
import com.pct.service.ThesisService;
import com.pct.service.ThesisUtil;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.StudentNotFoundException;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.ThesisTypeNotFoundException;

@Service
public class ThesisServiceImpl implements ThesisService {

	@Autowired
	private ThesisRepository thesisRepository;
	
	@Autowired
	private ThesisTypeRepository thesisTypeRepository;
	
	@Autowired
	private ProfesorRepository professorRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	@Transactional
	public List<ThesisDto> findAllThesis(Long mentorId, Long thesisTypeId) {

		List<ThesisDto> thesisDtoList = new ArrayList<ThesisDto>();

		List<Thesis> thesisList = thesisRepository.findAllThesis(mentorId, thesisTypeId);
		for (Thesis t : thesisList) {
			ThesisDto thesisDto = new ThesisDto(t);
			thesisDtoList.add(thesisDto);
		}
		return thesisDtoList;
	}

	@Override
	@Transactional
	public List<ThesisType> findAllThesisType() {

		List<ThesisType> thesisTypesList = new ArrayList<ThesisType>();
		thesisTypesList = thesisTypeRepository.findAll();
		return thesisTypesList;
	}

	@Override
	@Transactional
	public ThesisDto saveThesis(ThesisDto thesisDto) throws StudentNotFoundException, ProfessorNotFoundException, ThesisTypeNotFoundException {
		
		Thesis thesis = new Thesis();
		Student student = new Student();
		Professor mentor = new Professor();
		Professor commissionPresident = new Professor();
		Professor commissionMember = new Professor();
		ThesisType thesisType = new ThesisType();
		
		if (thesisDto.getStudentId() == null || studentRepository.findOne(thesisDto.getStudentId()) == null) {
			throw new StudentNotFoundException();
		} else {
			student = studentRepository.findOne(thesisDto.getStudentId());
		}
		
		if (thesisDto.getMentorId() == null || professorRepository.findOne(thesisDto.getMentorId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			mentor = professorRepository.findOne(thesisDto.getMentorId());
		}
		
		if (thesisDto.getCommissionPresidentId() == null || professorRepository.findOne(thesisDto.getCommissionPresidentId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			commissionPresident = professorRepository.findOne(thesisDto.getCommissionPresidentId());
		}
		
		if (thesisDto.getCommissionMemberId() == null || professorRepository.findOne(thesisDto.getCommissionMemberId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			commissionMember = professorRepository.findOne(thesisDto.getCommissionMemberId());
		}
		
		if (thesisDto.getThesisTypeId() == null || thesisTypeRepository.findOne(thesisDto.getThesisTypeId()) == null) {
			throw new ThesisTypeNotFoundException();
		} else {
			thesisType = thesisTypeRepository.findOne(thesisDto.getThesisTypeId());
		}
		
		if (thesisDto.getId() != null) {
			thesis = ThesisUtil.createThesisInstanceFromThesisDto(thesisDto, student, mentor, commissionPresident, commissionMember, thesisType);
		} else {
			thesis = ThesisUtil.createNewThesisInstanceFromThesisiDto(thesisDto, student, mentor, commissionPresident, commissionMember, thesisType);
		}
		
		return new ThesisDto(thesisRepository.save(thesis));
	}

	@Override
	@Transactional
	public ThesisType findThesisTypeById(Long id) throws ThesisTypeNotFoundException {
		
		ThesisType thesisType;

		if (id == null || thesisTypeRepository.findOne(id) == null) {
			throw new ThesisTypeNotFoundException();
		} else {
			thesisType = thesisTypeRepository.findOne(id);
		}

		return thesisType;
	}

	@Override
	@Transactional
	public void deleteThesis(Long id) throws ThesisNotFoundException {
		
		if (id == null || thesisRepository.findOne(id) == null) {
			throw new ThesisNotFoundException();
		}

		thesisRepository.delete(id);
		
	}

	@Override
	@Transactional
	public ThesisDto findThesisById(Long id) throws ThesisNotFoundException {
		
		ThesisDto thesisDto;

		if (id == null || thesisRepository.findOne(id) == null) {
			throw new ThesisNotFoundException();
		} else {
			Thesis thesis = thesisRepository.findOne(id);
			thesisDto = new ThesisDto(thesis);
		}

		return thesisDto;
		
	}

}
