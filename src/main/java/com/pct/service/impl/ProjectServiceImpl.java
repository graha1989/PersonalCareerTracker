package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.pct.service.util.ProjectUtil;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProjectExperienceNotFoundException;
import com.pct.validation.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectExperienceRepository projectExperienceRepository;
	
	@Autowired
	private ProfesorRepository professorRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	@Transactional
	public List<ProjectExperienceDto> findAllProjectExperiences(Long professorId) {
		
		List<ProjectExperienceDto> projectDtoList = new ArrayList<ProjectExperienceDto>();
		try {
			List<ProjectExperience> projectExperienceList = projectExperienceRepository.findAllProjectExperiences(professorId);
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

		ProjectExperienceDto projectExperienceDto;

		if (id == null || projectExperienceRepository.findOne(id) == null) {
			throw new ProjectExperienceNotFoundException();
		} else {
			ProjectExperience project = projectExperienceRepository.findOne(id);
			projectExperienceDto = new ProjectExperienceDto(project);
		}

		return projectExperienceDto;
	}

	@Override
	@Transactional
	public ProjectExperienceDto saveProjectExperience(ProjectExperienceDto projectExperienceDto)
			throws ProjectExperienceNotFoundException, ProfessorNotFoundException, ProjectNotFoundException {

		ProjectExperience projectExperience = new ProjectExperience();
		Professor professor = new Professor();
		Project project = new Project();

		if (projectExperienceDto.getProfessorId() == null || professorRepository.findOne(projectExperienceDto.getProfessorId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			professor = professorRepository.findOne(projectExperienceDto.getProfessorId());
		}
		
		if (projectExperienceDto.getProjectId() == null || projectRepository.findOne(projectExperienceDto.getProjectId()) == null) {
			throw new ProjectNotFoundException();
		} else {
			project = projectRepository.findOne(projectExperienceDto.getProjectId());
		}

		if (projectExperienceDto.getId() != null) {
			projectExperience = ProjectUtil.createProjectExperienceInstanceFromProjectExperienceDto(projectExperienceDto, professor, project);
		} else {
			projectExperience = ProjectUtil.createNewProjectExperienceInstanceFromProjectExperienceDto(projectExperienceDto, professor, project);
		}
		
		return new ProjectExperienceDto(projectExperienceRepository.save(projectExperience));
	}

}
