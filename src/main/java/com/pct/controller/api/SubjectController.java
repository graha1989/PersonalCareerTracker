package com.pct.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.SubjectDto;
import com.pct.service.SubjectService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.SubjectNotFoundException;

@RestController
@RequestMapping(RequestMappings.SUBJECTS_API)
public class SubjectController {

	private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

	@Autowired
	SubjectService subjectService;
	
	@Secured(value = "ROLE_ADMIN")
	@RequestMapping(value = RequestMappings.LOAD_ALL_SUBJECTS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<SubjectDto>> showAllSubjects() {

		List<SubjectDto> subjectDtos = subjectService.findAllSubjects();
		logger.debug("Current number of subjects in database is " + subjectDtos.size() + ".");

		return new ResponseEntity<List<SubjectDto>>(subjectDtos, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_SUBJECT, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<SubjectDto> showSubject(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws SubjectNotFoundException {
		SubjectDto subjectDto = subjectService.findSubjectById(id);

		return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistSubject(@Valid @RequestBody SubjectDto subjectDto) {

		try {
			subjectService.saveSubject(subjectDto);
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		} catch (SubjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Subject: " + subjectDto.getSubjectName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}