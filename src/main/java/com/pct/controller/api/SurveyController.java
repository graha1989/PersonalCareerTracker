package com.pct.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.SurveyDto;
import com.pct.service.SurveyService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.SurveyNotFoundException;

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

	@RequestMapping(value = "selectedSurvey", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<SurveyDto> showSurvey(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws SurveyNotFoundException {
		SurveyDto surveyDto = surveyService.findSurveyById(id);

		return new ResponseEntity<SurveyDto>(surveyDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistSurvey(@Valid @RequestBody SurveyDto surveyDto) {

		try {
			surveyService.saveSurvey(surveyDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (SubjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Survey for subject with ID: " + surveyDto.getSubjectId() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<SurveyDto> deleteSurvey(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws SurveyNotFoundException {
		surveyService.deleteSurvey(id);

		return new ResponseEntity<SurveyDto>(HttpStatus.OK);
	}

}