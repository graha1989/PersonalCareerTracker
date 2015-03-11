package com.pct.service;

import java.util.List;

import com.pct.domain.Language;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.validation.LanguageNotFoundException;

public interface LanguageService {
	
	List<LanguageExperienceDto> findAllLanguageExperiences(Long mentorId);

	LanguageExperienceDto saveLanguageExperience(LanguageExperienceDto languageExperienceDto);

	Language findLanguageById(Long id) throws LanguageNotFoundException;
	
}
