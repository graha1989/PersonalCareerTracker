package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SurveyDto;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.SurveyNotFoundException;

public interface SurveyService {

	List<SurveyDto> findAllSurveysForSubject(Long professorId, Long subjectId);

	SurveyDto findSurveyById(Long id) throws SurveyNotFoundException;

	void saveSurvey(SurveyDto surveyDto) throws ProfessorNotFoundException, SubjectNotFoundException;

	void deleteSurvey(Long id) throws SurveyNotFoundException;

}
