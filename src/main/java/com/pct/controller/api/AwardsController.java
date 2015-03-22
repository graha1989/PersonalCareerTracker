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
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.domain.AwardField;
import com.pct.domain.AwardType;
import com.pct.domain.dto.AwardDto;
import com.pct.service.AwardService;
import com.pct.validation.ProfessorNotFoundException;

@RestController
@RequestMapping("/api/awards")
public class AwardsController {
		
	private static final Logger logger = LoggerFactory.getLogger(AwardsController.class);

	@Autowired
	AwardService awardService;

	@RequestMapping(value = "allAwards", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AwardDto>> showAllAwards() {
		List<AwardDto> awards = awardService.findAll();

		return new ResponseEntity<List<AwardDto>>(awards, HttpStatus.OK);
	}

	@RequestMapping(value = "allAwardTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AwardType>> showAllAwardTypes() {
		List<AwardType> awardTypes = awardService.findAllAwardTypes();

		return new ResponseEntity<List<AwardType>>(awardTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "allAwardFields", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AwardField>> showAllAwardFields() {
		List<AwardField> awardFields = awardService.findAllAwardFields();

		return new ResponseEntity<List<AwardField>>(awardFields, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<AwardDto> persistAward(@Valid @RequestBody AwardDto awardDto) {

		AwardDto award = null;
		try {
			award = awardService.saveAward(awardDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Award:" + award.getAwardName() + " successfully saved.");

		return new ResponseEntity<AwardDto>(award, HttpStatus.OK);
	}

}
