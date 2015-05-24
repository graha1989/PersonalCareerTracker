package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
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
import com.pct.domain.dto.PersonDto;
import com.pct.domain.dto.ProjectDto;
import com.pct.domain.enums.ProjectType;
import com.pct.service.ProfessorService;
import com.pct.service.ProjectLeaderService;
import com.pct.service.ProjectService;
import com.pct.validation.ProjectNotFoundException;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	@Autowired
	ProfessorService professorService;

	@Autowired
	ProjectLeaderService projectLeaderService;

	@RequestMapping(value = "allProjects", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProjectDto>> showAllProjects() {

		List<ProjectDto> projectDtos = projectService.findAllProjects();
		logger.debug("Current number of projects in database is " + projectDtos.size() + ".");

		return new ResponseEntity<List<ProjectDto>>(projectDtos, HttpStatus.OK);
	}

	@RequestMapping(value = "allProjectTypes", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProjectType>> showAllProjectTypes() {
		List<ProjectType> projectTypes = projectService.findAllProjectTypes();

		return new ResponseEntity<List<ProjectType>>(projectTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedProject", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProjectDto> showProject(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectNotFoundException {
		ProjectDto projectDto = projectService.findProjectById(id);

		return new ResponseEntity<ProjectDto>(projectDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProject(@Valid @RequestBody ProjectDto projectDto) {

		try {
			projectService.saveProject(projectDto);
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Project: " + projectDto.getName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ProjectDto> deleteProject(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ProjectNotFoundException {
		projectService.deleteProject(id);

		return new ResponseEntity<ProjectDto>(HttpStatus.OK);
	}

	@RequestMapping(value = "findProfessorsOrLeadersStartsWith", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<PersonDto>> findProfessorsOrLeadersStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "projectId", required = true) Long projectId,
			@RequestParam(value = "professorsWhoAreLeadersOnThisProject", required = false) @Nullable List<Long> professorsWhoAreLeadersOnThisProject,
			@RequestParam(value = "leadersOnThisProjectWhoAreNotProfessors", required = false) @Nullable List<Long> leadersOnThisProjectWhoAreNotProfessors) {

		List<PersonDto> personDtos = new ArrayList<PersonDto>();
		if (value.length() >= 3) {
			List<PersonDto> leaderDtos = projectLeaderService.findProjectLeaderStartsWith(value, projectId,
					professorsWhoAreLeadersOnThisProject, leadersOnThisProjectWhoAreNotProfessors);
			personDtos.addAll(leaderDtos);
		}

		return new ResponseEntity<List<PersonDto>>(personDtos, HttpStatus.OK);
	}
}