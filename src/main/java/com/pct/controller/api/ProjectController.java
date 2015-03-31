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
import com.pct.service.ProjectService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "allProfessorProjecExperiences", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProjectExperienceDto>> showAllProfessorProjects(
			@RequestParam(value = "professorId", required = true) Long professorId) {
		List<ProjectExperienceDto> projects = projectService.findAllProjectExperiences(professorId);

		logger.debug("Successfully loaded:" + projects.size() + " project experiences.");

		return new ResponseEntity<List<ProjectExperienceDto>>(projects, HttpStatus.OK);
	}

	@RequestMapping(value = "allProjectTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProjectType>> showAllProjectTypes() {
		List<ProjectType> projectTypes = projectService.findAllProjectTypes();

		return new ResponseEntity<List<ProjectType>>(projectTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedProject", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProjectExperienceDto> showProject(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectExperienceNotFoundException {
		ProjectExperienceDto project = projectService.findProjectExperienceById(id);

		return new ResponseEntity<ProjectExperienceDto>(project, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProjectExperience(
			@Valid @RequestBody ProjectExperienceDto projectExperienceDto) {

		try {
			projectService.saveProjectExperience(projectExperienceDto);
		} catch (ProjectExperienceNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Project experience:" + projectExperienceDto.getProjectName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "findProjectStartsWith", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProjectExperienceDto>> findProjectStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "projectIds", required = false) List<Long> projectIds) throws ProjectExperienceNotFoundException {

		List<ProjectExperienceDto> projects = new ArrayList<ProjectExperienceDto>();
		if (value.length() >= 3) {
			projects = projectService.findProjectsStartsWith(value, projectIds);
		}

		return new ResponseEntity<List<ProjectExperienceDto>>(projects, HttpStatus.OK);
	}

}
