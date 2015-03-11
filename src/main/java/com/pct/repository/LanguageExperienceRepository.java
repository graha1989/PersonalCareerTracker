package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.LanguageExperience;

/**
 * Spring Data repository for LanguageExperience entity
 * 
 * @author a.grahovac
 *
 */
public interface LanguageExperienceRepository extends JpaRepository<LanguageExperience, Long> {
	
	/**
	 * Retrieves list of LanguageExperience for current professor.
	 * 
	 * @param mentorId
	 * @return list of language experience
	 */
	@Query("SELECT l FROM LanguageExperience l JOIN l.professor p WHERE p.id=:mentorId")
	List<LanguageExperience> findAllLanguages(@Param("mentorId") Long mentorId);
}
