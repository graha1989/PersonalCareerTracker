package com.pct.service;

import java.util.List;

import com.pct.domain.Language;
import com.pct.domain.dto.LanguageDto;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.validation.LanguageNotFoundException;
import com.pct.validation.ProfesorNotFoundException;

public interface LanguageService {
	
	List<LanguageExperienceDto> findAllLanguageExperiences(Long mentorId);

	LanguageExperienceDto saveLanguageExperience(LanguageExperienceDto languageExperienceDto) throws LanguageNotFoundException, ProfesorNotFoundException;

	Language findLanguageById(Long id) throws LanguageNotFoundException;

	List<LanguageDto> findAllNotListedLanguages(List<Long> languageExperienceIdsList) throws LanguageNotFoundException;
	
}
