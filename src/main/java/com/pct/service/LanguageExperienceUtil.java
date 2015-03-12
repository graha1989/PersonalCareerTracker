package com.pct.service;

import com.pct.domain.LanguageExperience;
import com.pct.domain.dto.LanguageExperienceDto;

/**
 * LanguageExperience utility class.
 * 
 * @author a.grahovac
 * 
 */
public class LanguageExperienceUtil {

	// EDIT LanguageExperience
	public static LanguageExperience createLanguageExperienceInstanceFromLanguageExperienceDto(LanguageExperienceDto languageExperienceDto) {

		LanguageExperience languageExperience = createNewLanguageExperienceInstanceFromLanguageExperienceDto(languageExperienceDto);
		languageExperience.setId(languageExperienceDto.getId());

		return languageExperience;
	}

	// CREATE new LanguageExperience
	public static LanguageExperience createNewLanguageExperienceInstanceFromLanguageExperienceDto(LanguageExperienceDto languageExperienceDto) {

		LanguageExperience languageExperience = new LanguageExperience();

		/*languageExperience.setLanguage(languageExperienceDto.getLanguage());
		languageExperience.setProfesor(languageExperienceDto.getProfessor());*/
		languageExperience.setReading(languageExperienceDto.isReading());
		languageExperience.setWriting(languageExperienceDto.isWriting());
		languageExperience.setPronouncing(languageExperienceDto.isPronouncing());
		
		return languageExperience;
	}

}
