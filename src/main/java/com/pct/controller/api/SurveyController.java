package com.pct.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.domain.dto.SurveyDto;
import com.pct.service.SurveyService;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	SurveyService surveyService;

	@RequestMapping(value = "allSurveys", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<SurveyDto>> showAllSurveys(
			@RequestParam(value = "professorId", required = true) Long professorId,
			@RequestParam(value = "subjectId", required = true) Long subjectId) {

		List<SurveyDto> surveyDtos = surveyService.findAllSurveysForSubject(professorId, subjectId);
		logger.debug("Current number of surveys for subject with ID " + subjectId + " in database is "
				+ surveyDtos.size() + ".");

		return new ResponseEntity<List<SurveyDto>>(surveyDtos, HttpStatus.OK);
	}

}