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
import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.service.ThesisService;

@RestController
@RequestMapping("/api/thesis")
public class ThesisController {
	
	private static final Logger logger = LoggerFactory.getLogger(ThesisController.class);
	
	@Autowired
	ThesisService thesisService;
	
	@RequestMapping(value = "allBachelorThesis", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ThesisDto>> showAllThesis(@RequestParam(value = "id", required = true) Long id) {
		List<ThesisDto> thesis = thesisService.findAllBachelorThesis(id);

		return new ResponseEntity<List<ThesisDto>>(thesis, HttpStatus.OK);
	}
	
	@RequestMapping(value = "allThesisTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ThesisType>> showAllThesisTypes() {
		List<ThesisType> thesisTypes = thesisService.findAllThesisType();

		return new ResponseEntity<List<ThesisType>>(thesisTypes, HttpStatus.OK);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ThesisDto> persistThesis(@Valid @RequestBody ThesisDto thesisDto) {
		
		ThesisDto thesis = new ThesisDto();
		thesis = thesisService.saveThesis(thesisDto);
		
		logger.debug("Thesis:" + thesis.getTitle() + " successfully saved.");

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}
	
}
