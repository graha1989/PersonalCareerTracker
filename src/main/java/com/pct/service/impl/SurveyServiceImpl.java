package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Survey;
import com.pct.domain.dto.SurveyDto;
import com.pct.repository.SurveyRepository;
import com.pct.service.SurveyService;

@Service
public class SurveyServiceImpl implements SurveyService {
	
	@Autowired
	private SurveyRepository surveyRepository;

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

}
