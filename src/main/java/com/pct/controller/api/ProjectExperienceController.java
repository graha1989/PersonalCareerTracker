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

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.domain.enums.ProjectType;
import com.pct.service.ProjectExperienceService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

@RestController
@RequestMapping("/api/projectExperiences")
public class ProjectExperienceController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectExperienceController.class);

	@Autowired
	ProjectExperienceService projectExperienceService;

	@RequestMapping(value = "allProfessorProjecExperiences", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProjectExperienceDto>> showAllProfessorProjectExperiencess(
			@RequestParam(value = "professorId", required = true) Long professorId) {
		List<ProjectExperienceDto> projectExperienceDtos = projectExperienceService.findAllProjectExperiences(professorId);

		logger.debug("Successfully loaded:" + projectExperienceDtos.size() + " project experiences.");

		return new ResponseEntity<List<ProjectExperienceDto>>(projectExperienceDtos, HttpStatus.OK);
	}

	@RequestMapping(value = "allProjectTypes", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProjectType>> showAllProjectTypes() {
		List<ProjectType> projectTypes = projectExperienceService.findAllProjectTypes();

		return new ResponseEntity<List<ProjectType>>(projectTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedProjectExperience", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProjectExperienceDto> showProject(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectExperienceNotFoundException {
		ProjectExperienceDto project = projectExperienceService.findProjectExperienceById(id);

		return new ResponseEntity<ProjectExperienceDto>(project, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProjectExperience(@Valid @RequestBody ProjectExperienceDto projectExperienceDto) {

		try {
			projectExperienceService.saveProjectExperience(projectExperienceDto);
		} catch (ProjectExperienceNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Project experience: " + projectExperienceDto.getId() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "findProjectStartsWith", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProjectExperienceDto>> findProjectStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "projectIds", required = false) List<Long> projectIds)
			throws ProjectExperienceNotFoundException {

		List<ProjectExperienceDto> projects = new ArrayList<ProjectExperienceDto>();
		if (value.length() >= 3) {
			projects = projectExperienceService.findProjectsStartsWith(value, projectIds);
		}

		return new ResponseEntity<List<ProjectExperienceDto>>(projects, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ProjectExperienceDto> deleteProjectExperience(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectExperienceNotFoundException {
		projectExperienceService.deleteProjectExperience(id);

		return new ResponseEntity<ProjectExperienceDto>(HttpStatus.OK);
	}

}