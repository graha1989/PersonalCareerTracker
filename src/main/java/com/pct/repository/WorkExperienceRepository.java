package com.pct.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Institution;
import com.pct.domain.WorkExperience;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

	@Query("SELECT e FROM WorkExperience e JOIN e.professor p WHERE p.id=:professorId")
	List<WorkExperience> findAllWorkExperiences(@Param("professorId") Long professorId);

	@Query("SELECT COUNT(e.id) FROM WorkExperience e JOIN e.professor p JOIN e.institution i JOIN i.institutionType it "
			+ "WHERE p.id=:professorId AND i.id=:institutionId AND (it.typeName='Fakultet' OR (it.typeName != 'Fakultet' AND e.title=:title))  "
			+ "AND (:from BETWEEN e.workStartDate AND IFNULL(e.workEndDate, NOW()) "
			+ "OR :to BETWEEN e.workStartDate AND IFNULL(e.workEndDate, NOW()) " + "OR e.workStartDate BETWEEN :from AND :to "
			+ "OR IFNULL(e.workEndDate, NOW()) BETWEEN :from AND :to)")
	int isThereFacultyWorkExperienceWithSimilarPeriod(@Param("institutionId") Long institutionId, @Param("professorId") Long professorId,
			@Param("title") String title, @Param("from") Date from, @Param("to") Date to);

	Long countByInstitution(Institution institution);

}
