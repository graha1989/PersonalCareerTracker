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
import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.InstitutionType;
import com.pct.service.InstitutionsService;
import com.pct.validation.InstitutionNotFoundException;

@RestController
@RequestMapping(RequestMappings.INSTITUTIONS_API)
public class InstitutionController {

	private static final Logger logger = LoggerFactory.getLogger(InstitutionController.class);

	@Autowired
	InstitutionsService institutionsService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_INSTITUTIONS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<InstitutionDto>> showAllInstitutions() {

		List<InstitutionDto> institutionDtos = institutionsService.findAllInstitutions();
		logger.debug("Current number of institutions in database is " + institutionDtos.size() + ".");

		return new ResponseEntity<List<InstitutionDto>>(institutionDtos, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_INSTITUTION_TYPES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<InstitutionType>> showAllInstitutionTypes() {
		List<InstitutionType> institutionTypes = institutionsService.findAllInstitutionTypes();

		return new ResponseEntity<List<InstitutionType>>(institutionTypes, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_INSTITUTION, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<InstitutionDto> showInstitution(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws InstitutionNotFoundException {
		InstitutionDto institutionDto = institutionsService.findInstitutionById(id);

		return new ResponseEntity<InstitutionDto>(institutionDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistInstitution(@Valid @RequestBody InstitutionDto institutionDto) {

		try {
			institutionsService.saveInstitution(institutionDto);
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Institution: " + institutionDto.getName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<InstitutionDto> deleteInstitution(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws InstitutionNotFoundException {
		institutionsService.deleteInstitution(id);

		return new ResponseEntity<InstitutionDto>(HttpStatus.OK);
	}
}