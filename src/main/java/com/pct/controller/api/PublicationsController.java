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
import com.pct.domain.dto.PublicationDto;
import com.pct.service.ProfessorPublicationService;
import com.pct.validation.PublicationNotFoundException;

@RestController
@RequestMapping("/api/publications")
public class PublicationsController {

	private static final Logger logger = LoggerFactory.getLogger(PublicationsController.class);

	@Autowired
	ProfessorPublicationService professorPublicationService;

	@RequestMapping(value = "allProfessorPublications", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PublicationDto>> showAllProfessorPublications(
			@RequestParam(value = "professorId", required = true) Long professorId) {
		List<PublicationDto> publications = null;
		try {
			publications = professorPublicationService.findAllPublications(professorId);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + publications.size() + " publications.");

		return new ResponseEntity<List<PublicationDto>>(publications, HttpStatus.OK);
	}

}
