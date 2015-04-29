package com.pct.service;

import java.util.List;

import com.pct.domain.Language;
import com.pct.domain.dto.LanguageDto;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.validation.LanguageExperienceNotFoundException;
import com.pct.validation.LanguageNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

public interface LanguageService {
	
	List<LanguageExperienceDto> findAllLanguageExperiences(Long mentorId);

	void saveLanguageExperience(LanguageExperienceDto languageExperienceDto) throws LanguageNotFoundException, ProfessorNotFoundException;

	Language findLanguageById(Long id) throws LanguageNotFoundException;

	List<LanguageDto> findAllNotListedLanguages(List<Long> languageExperienceIdsList) throws LanguageNotFoundException;
	
	void deleteLanguageExperience(Long id) throws LanguageExperienceNotFoundException;
	
}
