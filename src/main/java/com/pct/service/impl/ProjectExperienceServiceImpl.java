package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Project;
import com.pct.domain.ProjectExperience;
import com.pct.domain.dto.ProjectDto;
import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.domain.enums.ProjectType;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProjectExperienceRepository;
import com.pct.repository.ProjectRepository;
import com.pct.service.ProjectExperienceService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

@Service
public class ProjectExperienceServiceImpl implements ProjectExperienceService {

	private ProjectExperienceRepository projectExperienceRepository;
	private ProfesorRepository professorRepository;
	private ProjectRepository projectRepository;

	@Autowired
	public ProjectExperienceServiceImpl(ProjectExperienceRepository projectExperienceRepository,
			ProfesorRepository professorRepository, ProjectRepository projectRepository) {
		this.projectExperienceRepository = projectExperienceRepository;
		this.professorRepository = professorRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	@Transactional
	public List<ProjectExperienceDto> findAllProjectExperiences(Long professorId) {

		List<ProjectExperienceDto> projectDtoList = new ArrayList<ProjectExperienceDto>();
		try {
			List<ProjectExperience> projectExperienceList = projectExperienceRepository
					.findAllProjectExperiences(professorId);
			for (ProjectExperience p : projectExperienceList) {
				ProjectExperienceDto projectExperienceDto = new ProjectExperienceDto(p);
				projectDtoList.add(projectExperienceDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return projectDtoList;
	}

	@Override
	@Transactional
	public List<ProjectType> findAllProjectTypes() {
		return new ArrayList<ProjectType>(Arrays.asList(ProjectType.values()));
	}

	@Override
	@Transactional
	public ProjectExperienceDto findProjectExperienceById(Long id) throws ProjectExperienceNotFoundException {

		ProjectExperienceDto projectExperienceDto = null;
		if (id != null) {
			ProjectExperience projectExperience = projectExperienceRepository.findOne(id);
			if (projectExperience != null) {
				projectExperienceDto = new ProjectExperienceDto(projectExperience);
				return projectExperienceDto;
			}
		}

		throw new ProjectExperienceNotFoundException();
	}

	@Override
	@Transactional
	public void saveProjectExperience(ProjectExperienceDto projectExperienceDto)
			throws ProjectExperienceNotFoundException, ProfessorNotFoundException, ProjectNotFoundException {

		Professor professor = professorRepository.findOne(projectExperienceDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}
		Project project = projectRepository.findOne(projectExperienceDto.getProjectId());
		if (project == null) {
			throw new ProjectNotFoundException();
		}

		projectExperienceRepository.saveAndFlush(createOrUpdateProjectExperienceInstanceFromProjectExperienceDto(
				projectExperienceDto, professor, project));
	}

	public ProjectExperience createOrUpdateProjectExperienceInstanceFromProjectExperienceDto(
			@Nonnull ProjectExperienceDto projectExperienceDto, @Nonnull Professor professor, @Nonnull Project project) {

		ProjectExperience projectExperience = null;
		if (projectExperienceDto.getId() == null) {
			projectExperience = new ProjectExperience();
		} else {
			projectExperience = projectExperienceRepository.findOne(projectExperienceDto.getId());
		}
		projectExperience.setProfessor(professor);
		projectExperience.setProject(project);
		projectExperience.setStartDate(projectExperienceDto.getStartDate());
		projectExperience.setEndDate(projectExperienceDto.getEndDate());

		return projectExperience;
	}

	@Override
	@Transactional
	public List<ProjectDto> findProjectsStartsWith(String value, List<Long> projectIds) {

		List<ProjectDto> projectsDtoList = new ArrayList<ProjectDto>();
		List<Project> projectsList = new ArrayList<Project>();
		if (projectIds != null && projectIds.size() > 0) {
			projectsList = projectRepository.findByNameLike(value, projectIds);
		} else {
			projectsList = projectRepository.findByNameLike(value);
		}
		for (Project p : projectsList) {
			ProjectDto projectDto = new ProjectDto(p);
			projectsDtoList.add(projectDto);
		}

		return projectsDtoList;
	}

	@Override
	@Transactional
	public void deleteProjectExperience(Long id) throws ProjectExperienceNotFoundException {

		ProjectExperience projectExperience = projectExperienceRepository.findOne(id);
		if (projectExperience == null) {
			throw new ProjectExperienceNotFoundException();
		}

		projectExperienceRepository.delete(id);
	}

}
