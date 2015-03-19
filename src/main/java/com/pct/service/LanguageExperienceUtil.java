package com.pct.service;

import com.pct.domain.Language;
import com.pct.domain.LanguageExperience;
import com.pct.domain.Profesor;
import com.pct.domain.dto.LanguageExperienceDto;

/**
 * LanguageExperience utility class.
 * 
 * @author a.grahovac
 * 
 */
public class LanguageExperienceUtil {

	// EDIT LanguageExperience
	public static LanguageExperience createLanguageExperienceInstanceFromLanguageExperienceDto(LanguageExperienceDto languageExperienceDto, Language language, Profesor professor) {

		LanguageExperience languageExperience = createNewLanguageExperienceInstanceFromLanguageExperienceDto(languageExperienceDto, language, professor);
		languageExperience.setId(languageExperienceDto.getId());

		return languageExperience;
	}

	// CREATE new LanguageExperience
	public static LanguageExperience createNewLanguageExperienceInstanceFromLanguageExperienceDto(LanguageExperienceDto languageExperienceDto, Language language, Profesor professor) {

		LanguageExperience languageExperience = new LanguageExperience();

		languageExperience.setLanguage(language);
		languageExperience.setProfesor(professor);
		languageExperience.setReading(languageExperienceDto.isReading());
		languageExperience.setWriting(languageExperienceDto.isWriting());
		languageExperience.setPronouncing(languageExperienceDto.isPronouncing());
		
		return languageExperience;
	}

}
