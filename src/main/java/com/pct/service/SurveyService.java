package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SurveyDto;

public interface SurveyService {

	List<SurveyDto> findAllSurveysForSubject(Long professorId, Long subjectId);

}
