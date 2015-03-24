package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.ProjectExperience;
import com.pct.domain.dto.ProjectExperienceDto;
import com.pct.repository.ProjectExperienceRepository;
import com.pct.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectExperienceRepository projectExperienceRepository;
	
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

}
