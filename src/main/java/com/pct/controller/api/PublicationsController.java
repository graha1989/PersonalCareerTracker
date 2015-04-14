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
import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;
import com.pct.domain.enums.PublicationType;
import com.pct.service.PublicationService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.PublicationCategoryNotFoundException;
import com.pct.validation.PublicationNotFoundException;

@RestController
@RequestMapping("/api/publications")
public class PublicationsController {

	private static final Logger logger = LoggerFactory.getLogger(PublicationsController.class);

	@Autowired
	PublicationService publicationService;
	
	@RequestMapping(value = "allProfessorPublications", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProfessorPublicationDto>> showAllProfessorPublications(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		List<ProfessorPublicationDto> publications = null;
		try {
			publications = publicationService.findAllPublications(professorId);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + publications.size() + " publications.");

		return new ResponseEntity<List<ProfessorPublicationDto>>(publications, HttpStatus.OK);
	}
	
	@RequestMapping(value = "allInternationalPublications", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<InternationalPublicationDto>> showAllInternationalPublications(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		List<InternationalPublicationDto> publications = null;
		try {
			publications = publicationService.findAllInternationalPublications(professorId);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + publications.size() + " international publications related.");

		return new ResponseEntity<List<InternationalPublicationDto>>(publications, HttpStatus.OK);
	}

	@RequestMapping(value = "allPublicationTypes", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PublicationType>> getAllPublicationTypes() {
		List<PublicationType> publicationTypes = publicationService.findAllPublicationTypes();

		logger.debug("Successfully  loaded: " + publicationTypes.size() + " publication types.");

		return new ResponseEntity<List<PublicationType>>(publicationTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "allPublicationCategories", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PublicationCategory>> getAllPublicationCategories() {
		List<PublicationCategory> publicationCategories = publicationService.findAllPublicationCategories();

		logger.debug("Successfully  loaded: " + publicationCategories.size() + " publication categories.");

		return new ResponseEntity<List<PublicationCategory>>(publicationCategories, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedPublication", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorPublicationDto> showPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectExperienceNotFoundException {

		ProfessorPublicationDto professorPublicationDto = null;
		try {
			professorPublicationDto = publicationService.findPublicationById(id);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Successfully loaded publication: " + professorPublicationDto.getTitle() + ".");

		return new ResponseEntity<ProfessorPublicationDto>(professorPublicationDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessorPublication(@Valid @RequestBody ProfessorPublicationDto professorPublicationDto) {

		try {
			publicationService.saveProfessorPublication(professorPublicationDto);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (PublicationCategoryNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Publication: " + professorPublicationDto.getTitle() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ProfessorPublicationDto> deleteProfessorPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws PublicationNotFoundException {
		publicationService.deleteProfessorPublication(id);

		return new ResponseEntity<ProfessorPublicationDto>(HttpStatus.OK);
	}

}
