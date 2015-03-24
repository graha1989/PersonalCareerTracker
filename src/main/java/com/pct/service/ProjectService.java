package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ProjectExperienceDto;

public interface ProjectService {
	
	List<ProjectExperienceDto> findAllProjectExperiences(Long professorId);
	
}
