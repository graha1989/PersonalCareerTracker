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
import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.service.ThesisService;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.ThesisTypeNotFoundException;

@RestController
@RequestMapping("/api/thesis")
public class ThesisController {

	private static final Logger logger = LoggerFactory.getLogger(ThesisController.class);

	@Autowired
	ThesisService thesisService;

	@RequestMapping(value = "allThesis", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ThesisDto>> showAllThesis(
			@RequestParam(value = "mentorId", required = true) Long mentorId,
			@RequestParam(value = "thesisTypeId", required = true) Long thesisTypeId) {
		List<ThesisDto> thesis = thesisService.findAllThesis(mentorId, thesisTypeId);

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

	@RequestMapping(value = "loadThesisTypeDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ThesisType> getThesisTypeById(@RequestParam(value = "id", required = true) Long id) {
		ThesisType thesisType = new ThesisType();
		try {
			thesisType = thesisService.findThesisTypeById(id);
		} catch (ThesisTypeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<ThesisType>(thesisType, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ThesisDto> deleteThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		thesisService.deleteThesis(id);

		return new ResponseEntity<ThesisDto>(HttpStatus.OK);
	}

	@RequestMapping(value = "selectedThesis", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ThesisDto> showThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		ThesisDto thesis = thesisService.findThesisById(id);

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}

}
