package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.Subject;
import com.pct.domain.dto.SubjectDto;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.InstitutionTypeRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.StudyProgramRepository;
import com.pct.repository.SubjectRepository;
import com.pct.service.SubjectService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.SubjectNotFoundException;

@Service
public class SubjectServiceImpl implements SubjectService {

	private SubjectRepository subjectRepository;
	private InstitutionRepository institutionRepository;
	private ProfesorRepository professorRepository;
	private InstitutionTypeRepository institutionTypeRepository;
	private StudyProgramRepository studyProgramRepository;

	@Autowired
	public SubjectServiceImpl(SubjectRepository subjectRepository, InstitutionRepository institutionRepository,
			ProfesorRepository professorRepository, InstitutionTypeRepository institutionTypeRepository,
			StudyProgramRepository studyProgramRepository) {
		this.subjectRepository = subjectRepository;
		this.institutionRepository = institutionRepository;
		this.professorRepository = professorRepository;
		this.institutionTypeRepository = institutionTypeRepository;
		this.studyProgramRepository = studyProgramRepository;
	}

	@Override
	@Transactional
	public List<SubjectDto> findAvailableSubjectsStartsWith(String value, List<Long> subjectIds,
			Boolean seminarOrTeachingAbroad) {

		List<SubjectDto> subjectDtos = new ArrayList<SubjectDto>();
		List<Subject> subjects = new ArrayList<Subject>();
		if (subjectIds != null && subjectIds.size() > 0) {
			subjects = subjectRepository.findByNameLikeAndNotInIds(value, subjectIds, seminarOrTeachingAbroad);
		} else {
			subjects = subjectRepository.findByNameLike(value, seminarOrTeachingAbroad);
		}
		for (Subject subject : subjects) {
			subjectDtos.add(new SubjectDto(subject));
		}

		return subjectDtos;
	}

	@Override
	@Transactional
	public List<SubjectDto> findAllSubjects() {

		List<SubjectDto> subjectDtos = new ArrayList<SubjectDto>();
		try {
			List<Subject> subjects = subjectRepository.findAll();
			for (Subject s : subjects) {
				SubjectDto subjectDto = new SubjectDto(s);
				subjectDtos.add(subjectDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subjectDtos;
	}

	@Override
	@Transactional
	public SubjectDto findSubjectById(Long id) throws SubjectNotFoundException {

		SubjectDto subjectDto;
		if (id != null) {
			Subject subject = subjectRepository.findOne(id);
			if (subject != null) {
				subjectDto = new SubjectDto(subject);
				return subjectDto;
			}
		}

		throw new SubjectNotFoundException();
	}

	@Override
	@Transactional
	public void saveSubject(SubjectDto subjectDto) throws InstitutionNotFoundException, SubjectNotFoundException {
		Institution institution = initializeInstitution(subjectDto);
		institutionRepository.save(institution);
		subjectRepository.saveAndFlush(createOrUpdateSubjectInstanceFromSubjectDto(subjectDto, institution));
	}

	/**
	 * Creates new Institution entity object or retrieves existing from the database and sets field values from
	 * SubjectDto.
	 * 
	 * @param subjectDto
	 * @return
	 */
	public Institution initializeInstitution(@Nonnull SubjectDto subjectDto) {
		Institution institution = null;
		if (subjectDto.getInstitutionId() != null) {
			institution = institutionRepository.findOne(subjectDto.getInstitutionId());
		} else {
			institution = new Institution();
		}
		institution.setCity(subjectDto.getInstitutionCity());
		institution.setCountry(subjectDto.getInstitutionCountry());
		institution.setName(subjectDto.getInstitutionName());
		institution.setUniversity(subjectDto.getUniversityName());
		institution.setInstitutionType(institutionTypeRepository.findByTypeName(subjectDto.getInstitutionType()));

		return institution;
	}

	public Subject createOrUpdateSubjectInstanceFromSubjectDto(@Nonnull SubjectDto subjectDto,
			@Nonnull Institution institution) {

		Professor professor = null;
		if (subjectDto.getProfessorId() != null && subjectDto.getProfessorId() > 0L) {
			professor = professorRepository.findOne(subjectDto.getProfessorId());
		}
		Subject subject = null;
		if (subjectDto.getId() == null) {
			subject = new Subject();
		} else {
			subject = subjectRepository.findOne(subjectDto.getId());
		}
		subject.setInstitution(institution);
		subject.setProfessor(professor);
		subject.setName(subjectDto.getSubjectName());
		subject.setStudyProgram(studyProgramRepository.findByName(subjectDto.getStudyProgram()));
		subject.setStudiesThesisType(subjectDto.getStudiesThesisType());
		subject.setNumberOfTeachingLessons(subjectDto.getNumberOfTeachingLessons());
		subject.setNumberOfTheoreticalLessons(subjectDto.getNumberOfTheoreticalLessons());
		subject.setNumberOfPracticalLessons(subjectDto.getNumberOfPracticalLessons());

		return subject;
	}

}
