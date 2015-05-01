package com.pct.controller.api;

import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.Institution;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;
import com.pct.service.WorkExperienceService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.WorkExperienceNotFoundException;

@RestController
@RequestMapping("/api/workExperiences")
public class WorkExperienceController {

	private static final Logger logger = LoggerFactory.getLogger(WorkExperienceController.class);

	@Autowired
	WorkExperienceService workExperienceService;

	@RequestMapping(value = "allWorkExperiences", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<WorkExperienceDto>> showAllProfessorWorkExperiences(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		List<WorkExperienceDto> experiences = null;
		try {
			experiences = workExperienceService.findAllWorkExperiences(professorId);
		} catch (WorkExperienceNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + experiences.size() + " work experiences.");

		return new ResponseEntity<List<WorkExperienceDto>>(experiences, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedWorkExperience", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<WorkExperienceDto> showWorkExperience(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws WorkExperienceNotFoundException {
		WorkExperienceDto workExperienceDto = workExperienceService.findWorkExperienceById(id);

		return new ResponseEntity<WorkExperienceDto>(workExperienceDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistWorkExperience(@Valid @RequestBody WorkExperienceDto workExperienceDto) {

		try {
			workExperienceService.saveWorkExperience(workExperienceDto);
		} catch (WorkExperienceNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Work experience in:" + workExperienceDto.getInstitutionName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "findInstitutionStartsWith", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<Institution>> findInstitutionStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "institutionType", required = false) @JsonDeserialize(using = InstitutionTypeEnumDeserializer.class) InstitutionType institutionType)
			throws InstitutionNotFoundException {

		List<Institution> institutions = new ArrayList<Institution>();
		if (value.length() >= 3) {
			institutions = workExperienceService.findInstitutionsStartsWith(value, institutionType);
		}

		return new ResponseEntity<List<Institution>>(institutions, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<WorkExperienceDto> deleteWorkExperience(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws WorkExperienceNotFoundException {
		workExperienceService.deleteWorkExperience(id);

		return new ResponseEntity<WorkExperienceDto>(HttpStatus.OK);
	}

}
