package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Project;
import com.pct.domain.ProjectLeader;
import com.pct.domain.dto.ProjectDto;
import com.pct.domain.dto.ProjectLeaderDto;
import com.pct.domain.enums.ProjectType;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProjectLeaderRepository;
import com.pct.repository.ProjectRepository;
import com.pct.service.ProjectService;
import com.pct.validation.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository projectRepository;
	ProfesorRepository professorRepository;
	ProjectLeaderRepository projectLeaderRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository, ProfesorRepository professorRepository,
			ProjectLeaderRepository projectLeaderRepository) {
		this.projectRepository = projectRepository;
		this.professorRepository = professorRepository;
		this.projectLeaderRepository = projectLeaderRepository;
	}

	@Override
	@Transactional
	public List<ProjectDto> findAllProjects() {

		List<ProjectDto> projectDtos = new ArrayList<ProjectDto>();
		try {
			List<Project> projects = projectRepository.findAll();
			for (Project p : projects) {
				for (ProjectLeader projectLeader : p.getProjectLeaders()) {
					if (projectLeader.getProfessor() != null && projectLeader.getProfessor().getId() != null) {
						Professor professor = professorRepository.findOne(projectLeader.getProfessor().getId());
						projectLeader.setName(professor.getName());
						projectLeader.setSurname(professor.getSurname());
					}
				}
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
				for (ProjectLeader projectLeader : project.getProjectLeaders()) {
					if (projectLeader.getProfessor() != null && projectLeader.getProfessor().getId() != null) {
						Professor professor = professorRepository.findOne(projectLeader.getProfessor().getId());
						projectLeader.setName(professor.getName());
						projectLeader.setSurname(professor.getSurname());
						projectLeader.setProfessor(professor);
					}
				}
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
	private Project initializeProject(@Nonnull ProjectDto projectDto) {
		Project project = null;
		if (projectDto.getId() != null) {
			project = projectRepository.findOne(projectDto.getId());
		} else {
			project = new Project();
		}
		project.setName(projectDto.getName());
		project.setProjectType(projectDto.getProjectType());
		project.setFinancedBy(projectDto.getFinancedBy());

		Set<ProjectLeader> projectLeaders = new HashSet<ProjectLeader>();

		for (ProjectLeaderDto projectLeaderDto : projectDto.getProjectLeaderDtos()) {
			ProjectLeader projectLeader = new ProjectLeader();
			Professor professor = null;
			if (projectLeaderDto.getId() != null && projectLeaderDto.getId() > 0L) {
				projectLeader = projectLeaderRepository.findOne(projectLeaderDto.getId());
			}
			if (projectLeaderDto.getProfessorId() != null && projectLeaderDto.getProfessorId() > 0L) {
				professor = professorRepository.findOne(projectLeaderDto.getProfessorId());
			}
			projectLeader.setName(projectLeaderDto.getName());
			projectLeader.setSurname(projectLeaderDto.getSurname());
			projectLeader.setProject(project);
			projectLeader.setProfessor(professor);

			projectLeaders.add(projectLeader);
		}
		// If collection from Dto miss some element from original collection, we
		// remove it from original
		Iterator<ProjectLeader> currentLeadersIterator = project.getProjectLeaders().iterator();
		while (currentLeadersIterator.hasNext()) {
			ProjectLeader projectLeader = currentLeadersIterator.next();
			if (!projectLeaders.contains(projectLeader)) {
				currentLeadersIterator.remove();
				projectLeader.setProject(null);
			}
		}
		// If original collection miss some element from Dto collection, we add
		// it to original
		Iterator<ProjectLeader> newLeadersIterator = projectLeaders.iterator();
		while (newLeadersIterator.hasNext()) {
			ProjectLeader projectLeader = newLeadersIterator.next();
			if (!project.getProjectLeaders().contains(projectLeader)) {
				project.getProjectLeaders().add(projectLeader);
				projectLeader.setProject(project);
			}
		}

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
