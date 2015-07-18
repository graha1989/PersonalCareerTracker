package com.pct.service;

import java.util.List;

import com.pct.domain.ProjectType;
import com.pct.domain.dto.ProjectDto;
import com.pct.validation.ProjectDeleteException;
import com.pct.validation.ProjectNotFoundException;

public interface ProjectService {

	List<ProjectDto> findAllProjects();

	List<ProjectType> findAllProjectTypes();

	ProjectDto findProjectById(Long id) throws ProjectNotFoundException;

	void saveProject(ProjectDto projectDto) throws ProjectNotFoundException;

	void deleteProject(Long id) throws ProjectNotFoundException, ProjectDeleteException;

}
