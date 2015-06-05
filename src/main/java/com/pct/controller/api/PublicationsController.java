package com.pct.controller.api;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;
import com.pct.domain.dto.PublicationCategoryDto;
import com.pct.domain.dto.UserDto;
import com.pct.domain.enums.PublicationType;
import com.pct.service.PublicationService;
import com.pct.service.UserService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.PublicationCategoryNotFoundException;
import com.pct.validation.PublicationNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.PUBLICATIONS_API)
public class PublicationsController {

	private static final Logger logger = LoggerFactory.getLogger(PublicationsController.class);

	@Autowired
	PublicationService publicationService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSOR_PUBLICATIONS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProfessorPublicationDto>> showAllProfessorPublications(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		UserDto userDto;
		try {
			userDto = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && userDto.getId() != professorId) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

		List<ProfessorPublicationDto> publications = null;
		try {
			publications = publicationService.findAllPublications(professorId);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + publications.size() + " publications.");

		return new ResponseEntity<List<ProfessorPublicationDto>>(publications, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_INTERNATIONAL_PUBLICATIONS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
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

	@RequestMapping(value = RequestMappings.LOAD_ALL_PUBLICATION_TYPES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PublicationType>> getAllPublicationTypes() {
		List<PublicationType> publicationTypes = publicationService.findAllPublicationTypes();

		logger.debug("Successfully  loaded: " + publicationTypes.size() + " publication types.");

		return new ResponseEntity<List<PublicationType>>(publicationTypes, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_PUBLICATION_CATEGORIES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PublicationCategoryDto>> getAllPublicationCategories() {
		List<PublicationCategoryDto> publicationCategoryDtos = publicationService.findAllPublicationCategories();

		logger.debug("Successfully  loaded: " + publicationCategoryDtos.size() + " publication categories.");

		return new ResponseEntity<List<PublicationCategoryDto>>(publicationCategoryDtos, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_PROFESSOR_PUBLICATION, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorPublicationDto> showProfessorPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) {

		ProfessorPublicationDto professorPublicationDto = null;
		try {
			professorPublicationDto = publicationService.findProfessorPublicationById(id);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Successfully loaded professor publication: " + professorPublicationDto.getTitle() + ".");

		return new ResponseEntity<ProfessorPublicationDto>(professorPublicationDto, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_INTERNATIONAL_PUBLICATION, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<InternationalPublicationDto> showInternationalPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) {

		InternationalPublicationDto internationalPublicationDto = null;
		try {
			internationalPublicationDto = publicationService.findInternationalPublicationById(id);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Successfully loaded international publication: " + internationalPublicationDto.getTitle() + ".");

		return new ResponseEntity<InternationalPublicationDto>(internationalPublicationDto, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.PROFESSOR_PUBLICATION, method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessorPublication(
			@Valid @RequestBody ProfessorPublicationDto professorPublicationDto) {

		try {
			publicationService.saveProfessorPublication(professorPublicationDto);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (PublicationCategoryNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor publication: " + professorPublicationDto.getTitle() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.INTERNATIONAL_PUBLICATION, method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistInternationalPublication(
			@Valid @RequestBody InternationalPublicationDto internationalPublicationDto) {

		try {
			publicationService.saveInternationalPublication(internationalPublicationDto);
		} catch (PublicationNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (PublicationCategoryNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("International publication: " + internationalPublicationDto.getTitle() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.PROFESSOR_PUBLICATION, method = RequestMethod.DELETE)
	public ResponseEntity<ProfessorPublicationDto> deleteProfessorPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws PublicationNotFoundException {
		publicationService.deleteProfessorPublication(id);

		return new ResponseEntity<ProfessorPublicationDto>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.INTERNATIONAL_PUBLICATION, method = RequestMethod.DELETE)
	public ResponseEntity<InternationalPublicationDto> deleteInternationalPublication(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws PublicationNotFoundException {
		publicationService.deleteInternationalPublication(id);

		return new ResponseEntity<InternationalPublicationDto>(HttpStatus.OK);
	}

}
