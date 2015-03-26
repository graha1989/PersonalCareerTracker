package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.domain.enums.ProjectType;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

public interface ProjectService {
	
	List<ProjectExperienceDto> findAllProjectExperiences(Long professorId);

	List<ProjectType> findAllProjectTypes();

	ProjectExperienceDto findProjectExperienceById(Long id) throws ProjectExperienceNotFoundException;

	ProjectExperienceDto saveProjectExperience(ProjectExperienceDto projectExperienceDto) throws ProjectExperienceNotFoundException, ProfessorNotFoundException, ProjectNotFoundException;

	List<ProjectExperienceDto> findProjectsStartsWith(String value);
	
}
