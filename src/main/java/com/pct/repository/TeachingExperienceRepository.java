package com.pct.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Subject;
import com.pct.domain.TeachingExperience;

public interface TeachingExperienceRepository extends JpaRepository<TeachingExperience, Long> {

	@Query("SELECT e FROM TeachingExperience e JOIN e.professor p WHERE e.seminarOrTeachingAbroad=:seminarOrTeachingAbroad AND p.id=:professorId")
	List<TeachingExperience> findAllTeachingExperiences(@Param("professorId") Long professorId,
			@Param("seminarOrTeachingAbroad") Boolean seminarOrTeachingAbroad);

	@Query("SELECT COUNT(e.id) FROM TeachingExperience e JOIN e.subject s WHERE s.id=:subjectId "
			+ "AND (:from BETWEEN e.teachingStartDate AND IFNULL(e.teachingEndDate, NOW()) "
			+ "OR :to BETWEEN e.teachingStartDate AND IFNULL(e.teachingEndDate, NOW()) OR e.teachingStartDate BETWEEN :from AND :to "
			+ "OR IFNULL(e.teachingEndDate, NOW()) BETWEEN :from AND :to)")
	int isThereTeachingExperienceWithSimilarPeriod(@Param("subjectId") Long subjectId, @Param("from") Date from, @Param("to") Date to);

	Long countBySubject(Subject subject);

}
