package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Subject;
import com.pct.domain.Survey;
import com.pct.domain.dto.SurveyDto;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.SubjectRepository;
import com.pct.repository.SurveyRepository;
import com.pct.service.SurveyService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.SurveyNotFoundException;

@Service
public class SurveyServiceImpl implements SurveyService {

	private SurveyRepository surveyRepository;

	private ProfesorRepository professorRepository;

	private SubjectRepository subjectRepository;

	@Autowired
	public SurveyServiceImpl(SurveyRepository surveyRepository, ProfesorRepository professorRepository,
			SubjectRepository subjectRepository) {
		super();
		this.surveyRepository = surveyRepository;
		this.professorRepository = professorRepository;
		this.subjectRepository = subjectRepository;
	}

	@Override
	@Transactional
	public List<SurveyDto> findAllSurveysForSubject(Long professorId, Long subjectId) {

		List<SurveyDto> surveyDtos = new ArrayList<SurveyDto>();
		try {
			List<Survey> surveys = surveyRepository.findAllSurveysForSubject(professorId, subjectId);
			for (Survey s : surveys) {
				SurveyDto surveyDto = new SurveyDto(s);
				surveyDtos.add(surveyDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return surveyDtos;
	}

	@Override
	@Transactional
	public SurveyDto findSurveyById(Long id) throws SurveyNotFoundException {

		SurveyDto surveyDto;
		if (id != null) {
			Survey survey = surveyRepository.findOne(id);
			if (survey != null) {
				surveyDto = new SurveyDto(survey);
				return surveyDto;
			}
		}

		throw new SurveyNotFoundException();
	}

	@Override
	@Transactional
	public void saveSurvey(SurveyDto surveyDto) throws ProfessorNotFoundException, SubjectNotFoundException {

		Professor professor = professorRepository.findOne(surveyDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		Subject subject = null;
		try {
			subject = subjectRepository.findOne(surveyDto.getSubjectId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (subject == null) {
			throw new SubjectNotFoundException();
		}

		surveyRepository.saveAndFlush(createOrUpdateSurveyInstanceFromSurveyDto(surveyDto, professor, subject));
	}

	private Survey createOrUpdateSurveyInstanceFromSurveyDto(@Nonnull SurveyDto surveyDto,
			@Nonnull Professor professor, @Nonnull Subject subject) {

		Survey survey = null;
		if (surveyDto.getId() == null) {
			survey = new Survey();
			survey.setProfessor(professor);
			survey.setSubject(subject);
		} else {
			survey = surveyRepository.findOne(surveyDto.getId());
		}
		survey.setAcademicYear(surveyDto.getAcademicYear());
		survey.setAverageGrade(surveyDto.getAverageGrade());
		survey.setNumberOfStudents(surveyDto.getNumberOfStudents());

		return survey;
	}

}
