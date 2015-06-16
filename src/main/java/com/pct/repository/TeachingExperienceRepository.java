package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.TeachingExperience;

public interface TeachingExperienceRepository extends JpaRepository<TeachingExperience, Long> {

	@Query("SELECT e FROM TeachingExperience e JOIN e.professor p WHERE e.seminarOrTeachingAbroad=:seminarOrTeachingAbroad AND p.id=:professorId")
	List<TeachingExperience> findAllTeachingExperiences(@Param("professorId") Long professorId,
			@Param("seminarOrTeachingAbroad") Boolean seminarOrTeachingAbroad);

}
