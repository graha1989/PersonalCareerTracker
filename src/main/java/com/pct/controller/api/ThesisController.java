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
import com.pct.domain.StudiesThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.service.ThesisService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.StudentNotFoundException;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.StudiesThesisTypeNotFoundException;

@RestController
@RequestMapping("/api/thesis")
public class ThesisController {

	private static final Logger logger = LoggerFactory.getLogger(ThesisController.class);

	@Autowired
	ThesisService thesisService;

	@RequestMapping(value = "allThesis", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ThesisDto>> showAllThesis(
			@RequestParam(value = "mentorId", required = true) Long mentorId,
			@RequestParam(value = "thesisTypeId", required = true) Long thesisTypeId) {
		List<ThesisDto> thesis = thesisService.findAllThesis(mentorId, thesisTypeId);

		return new ResponseEntity<List<ThesisDto>>(thesis, HttpStatus.OK);
	}

	@RequestMapping(value = "allThesisTypes", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudiesThesisType>> showAllThesisTypes() {
		List<StudiesThesisType> studiesThesisTypes = thesisService.findAllThesisType();

		return new ResponseEntity<List<StudiesThesisType>>(studiesThesisTypes, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ThesisDto> persistThesis(@Valid @RequestBody ThesisDto thesisDto) {

		ThesisDto thesis = new ThesisDto();
		try {
			thesis = thesisService.saveThesis(thesisDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (StudiesThesisTypeNotFoundException e) {
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Thesis:" + thesis.getTitle() + " successfully saved.");

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}

	@RequestMapping(value = "loadThesisTypeDetails", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<StudiesThesisType> getThesisTypeById(@RequestParam(value = "id", required = true) Long id) {
		StudiesThesisType studiesThesisType = new StudiesThesisType();
		try {
			studiesThesisType = thesisService.findThesisTypeById(id);
		} catch (StudiesThesisTypeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<StudiesThesisType>(studiesThesisType, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ThesisDto> deleteThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		thesisService.deleteThesis(id);

		return new ResponseEntity<ThesisDto>(HttpStatus.OK);
	}

	@RequestMapping(value = "selectedThesis", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ThesisDto> showThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		ThesisDto thesis = thesisService.findThesisById(id);

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}

}
