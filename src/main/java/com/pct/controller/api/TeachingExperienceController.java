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
import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.service.TeachingExperienceService;
import com.pct.validation.TeachingExperienceNotFoundException;

@RestController
@RequestMapping("/api/teachingExperiences")
public class TeachingExperienceController {
	
	private static final Logger logger = LoggerFactory.getLogger(TeachingExperienceController.class);

	@Autowired
	TeachingExperienceService teachingExperienceService;

	@RequestMapping(value = "allTeachingExperiences", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<TeachingExperienceDto>> showAllProfessorTeachingExperiences(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		List<TeachingExperienceDto> experiences = null;
		try {
			experiences = teachingExperienceService.findAllTeachingExperiences(professorId);
		} catch (TeachingExperienceNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Professor got: " + experiences.size() + " teaching experiences.");

		return new ResponseEntity<List<TeachingExperienceDto>>(experiences, HttpStatus.OK);
	}
	
}
