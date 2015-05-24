package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.ProjectLeader;
import com.pct.domain.dto.PersonDto;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProjectLeaderRepository;
import com.pct.service.ProjectLeaderService;

@Service
public class ProjectLeaderServiceImpl implements ProjectLeaderService {

	@Autowired
	private ProjectLeaderRepository projectLeaderRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<PersonDto> findProjectLeaderStartsWith(String value, @Nullable Long projectId,
			@Nullable List<Long> professorsWhoAreLeadersOnThisProject,
			@Nullable List<Long> leadersOnThisProjectWhoAreNotProfessors) {

		List<PersonDto> personDtos = new ArrayList<PersonDto>();
		List<ProjectLeader> projectLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject = new ArrayList<ProjectLeader>();
		List<Professor> professorsWhoAreNotLeadersOnSelectedProject = new ArrayList<Professor>();

		if (projectId != null && projectId > 0L) {
			if (leadersOnThisProjectWhoAreNotProfessors != null && leadersOnThisProjectWhoAreNotProfessors.size() > 0) {
				projectLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject = projectLeaderRepository
						.findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(value, projectId,
								leadersOnThisProjectWhoAreNotProfessors);
			} else {
				projectLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject = projectLeaderRepository
						.findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(value);
			}
			if (professorsWhoAreLeadersOnThisProject != null && professorsWhoAreLeadersOnThisProject.size() > 0) {
				professorsWhoAreNotLeadersOnSelectedProject = professorRepository
						.findProfessorsWhoAreNotLeadersOnSelectedProject(value, professorsWhoAreLeadersOnThisProject);
			} else {
				professorsWhoAreNotLeadersOnSelectedProject = professorRepository
						.findProfessorsWhoAreNotLeadersOnSelectedProject(value);
			}
			for (ProjectLeader p : projectLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject) {
				PersonDto personDto = new PersonDto();
				personDto.setName(p.getName());
				personDto.setSurname(p.getSurname());
				personDto.setLeaderId(p.getId());
				personDtos.add(personDto);
			}
			for (Professor p : professorsWhoAreNotLeadersOnSelectedProject) {
				PersonDto personDto = new PersonDto();
				personDto.setName(p.getName());
				personDto.setSurname(p.getSurname());
				personDto.setProfessorId(p.getId());
				personDtos.add(personDto);
			}
		} else {
			System.out.println("Waiting for inserting new Project...");
		}

		return personDtos;
	}
}
