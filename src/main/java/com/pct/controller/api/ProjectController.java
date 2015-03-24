package com.pct.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "allProfessorProjecExperiences", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProjectExperienceDto>> showAllProfessorProjects(@RequestParam(value = "professorId", required = true) Long professorId) {
		List<ProjectExperienceDto> projects = projectService.findAllProjectExperiences(professorId);
		
		logger.debug("Successfully loaded:" + projects.size() + " project experiences.");
		
		return new ResponseEntity<List<ProjectExperienceDto>>(projects, HttpStatus.OK);
	}
	
}
