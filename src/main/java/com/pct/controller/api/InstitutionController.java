package com.pct.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.domain.dto.InstitutionDto;
import com.pct.service.InstitutionsService;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

	private static final Logger logger = LoggerFactory.getLogger(InstitutionController.class);

	@Autowired
	InstitutionsService institutionsService;

	@RequestMapping(value = "allInstitutions", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<InstitutionDto>> showAllInstitutions() {

		List<InstitutionDto> institutionDtos = institutionsService.findAllInstitutions();
		logger.debug("Current numberof institutions in database is " + institutionDtos.size() + ".");

		return new ResponseEntity<List<InstitutionDto>>(institutionDtos, HttpStatus.OK);
	}
}