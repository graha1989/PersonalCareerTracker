package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
	
	/**
	 * Retrieves list of Languages which are not listed in table for professor.
	 * 
	 * @param languageExperienceIdslist
	 * @return list of languages
	 */
	@Query("SELECT l FROM Language l WHERE l.id NOT IN :languageExperienceIdsList")
	List<Language> findAllNotListedLanguages(@Param("languageExperienceIdsList") List<Long> languageExperienceIdsList);

}
