package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Project;
import com.pct.domain.dto.ProjectDto;
import com.pct.domain.enums.ProjectType;
import com.pct.repository.ProjectRepository;
import com.pct.service.ProjectService;
import com.pct.validation.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Override
	@Transactional
	public List<ProjectDto> findAllProjects() {

		List<ProjectDto> projectDtos = new ArrayList<ProjectDto>();
		try {
			List<Project> projects = projectRepository.findAll();
			for (Project p : projects) {
				ProjectDto projectDto = new ProjectDto(p);
				projectDtos.add(projectDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return projectDtos;
	}

	@Override
	@Transactional
	public List<ProjectType> findAllProjectTypes() {
		return new ArrayList<ProjectType>(Arrays.asList(ProjectType.values()));
	}

	@Override
	@Transactional
	public ProjectDto findProjectById(Long id) throws ProjectNotFoundException {

		ProjectDto projectDto;
		if (id != null) {
			Project project = projectRepository.findOne(id);
			if (project != null) {
				projectDto = new ProjectDto(project);
				return projectDto;
			}
		}

		throw new ProjectNotFoundException();
	}

	@Override
	@Transactional
	public void saveProject(ProjectDto projectDto) throws ProjectNotFoundException {
		Project project = initializeProject(projectDto);
		projectRepository.saveAndFlush(project);
	}

	/**
	 * Creates new Project entity object or retrieves existing from the database and sets field values from ProjectDto.
	 * 
	 * @param projectDto
	 * @return
	 */
	public Project initializeProject(@Nonnull ProjectDto projectDto) {
		Project project = null;
		if (projectDto.getId() != null) {
			project = projectRepository.findOne(projectDto.getId());
		} else {
			project = new Project();
		}
		project.setName(projectDto.getName());
		project.setProjectType(projectDto.getProjectType());
		project.setFinancedBy(projectDto.getFinancedBy());
		project.setProjectLeaders(projectDto.getProjectLeaders());

		return project;
	}

	@Override
	@Transactional
	public void deleteProject(Long id) throws ProjectNotFoundException {

		Project project = projectRepository.findOne(id);
		if (project == null) {
			throw new ProjectNotFoundException();
		}

		projectRepository.delete(project);
	}

}
