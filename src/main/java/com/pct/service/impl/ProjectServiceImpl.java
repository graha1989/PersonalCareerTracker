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
import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.domain.enums.ProjectType;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProjectExperienceRepository;
import com.pct.repository.ProjectRepository;
import com.pct.service.ProjectService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	private ProjectExperienceRepository projectExperienceRepository;

	private ProfesorRepository professorRepository;

	private ProjectRepository projectRepository;

	@Autowired
	public ProjectServiceImpl(ProjectExperienceRepository projectExperienceRepository,
			ProfesorRepository professorRepository, ProjectRepository projectRepository) {
		super();
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

		Project project = initializeProject(projectExperienceDto);
		projectRepository.save(project);
		projectExperienceRepository.saveAndFlush(createOrUpdateProjectExperienceInstanceFromProjectExperienceDto(
				projectExperienceDto, professor, project));
	}

	/**
	 * Creates new Project entity object or retrieves existing from the database and sets field values from
	 * ProjectExperienceDto.
	 * 
	 * @param projectExperienceDto
	 * @return
	 */
	public Project initializeProject(@Nonnull ProjectExperienceDto projectExperienceDto) {
		Project project = null;
		if (projectExperienceDto.getProjectId() != null) {
			project = projectRepository.findOne(projectExperienceDto.getProjectId());
		} else {
			project = new Project();
		}
		project.setName(projectExperienceDto.getProjectName());
		project.setProjectType(projectExperienceDto.getProjectType());
		project.setFinancedBy(projectExperienceDto.getProjectFinancedBy());
		project.setProjectStartDate(projectExperienceDto.getProjectStartDate());
		project.setProjectEndDate(projectExperienceDto.getProjectEndDate());
		project.setProjectLeader(projectExperienceDto.getProjectLeader());

		return project;
	}

	public ProjectExperience createOrUpdateProjectExperienceInstanceFromProjectExperienceDto(
			@Nonnull ProjectExperienceDto projectExperienceDto, @Nonnull Professor professor, @Nonnull Project project) {

		ProjectExperience projectExperience = null;
		if (projectExperienceDto.getId() == null) {
			projectExperience = new ProjectExperience();
			projectExperience.setProfessor(professor);
		} else {
			projectExperience = projectExperienceRepository.findOne(projectExperienceDto.getId());
		}
		projectExperience.setProject(project);
		projectExperience.setProfessorLeader(projectExperienceDto.isProfessorLeader());

		return projectExperience;
	}

	@Override
	@Transactional
	public List<ProjectExperienceDto> findProjectsStartsWith(String value, List<Long> projectIds) {

		List<ProjectExperienceDto> projectsDtoList = new ArrayList<ProjectExperienceDto>();

		List<Project> projectsList = projectRepository.findByNameLike(value, projectIds);
		for (Project p : projectsList) {
			ProjectExperienceDto projectDto = new ProjectExperienceDto(p);
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

		projectExperienceRepository.delete(projectExperience);
	}

}
