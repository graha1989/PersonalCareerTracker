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
import com.pct.domain.dto.StudiesDto;
import com.pct.service.ProfessorStudiesService;
import com.pct.validation.ProfessorStudiesNotFoundException;

@RestController
@RequestMapping("/api/studies")
public class ProfessorStudiesController {

	private static final Logger logger = LoggerFactory.getLogger(ProfessorStudiesController.class);

	@Autowired
	ProfessorStudiesService professorStudiesService;

	@RequestMapping(value = "professorBachelorStudies/allProfessorBachelorStudies", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudiesDto>> showAllProfessorStudies(
			@RequestParam(value = "professorId", required = true) Long professorId,
			@RequestParam(value = "thesisTypeId", required = true) Long thesisTypeId) {

		List<StudiesDto> studiesDtos = null;
		try {
			studiesDtos = professorStudiesService.findAllStudies(professorId, thesisTypeId);
		} catch (ProfessorStudiesNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + studiesDtos.size() + " studies.");

		return new ResponseEntity<List<StudiesDto>>(studiesDtos, HttpStatus.OK);
	}

}
