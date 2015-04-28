package com.pct.service;

import java.util.List;

import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.validation.TeachingExperienceNotFoundException;

public interface TeachingExperienceService {
	
	List<TeachingExperienceDto> findAllTeachingExperiences(Long professorId) throws TeachingExperienceNotFoundException;
	
}
