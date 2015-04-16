package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.WorkExperience;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
	
	@Query("SELECT e FROM WorkExperience e JOIN e.professor p WHERE p.id=:professorId")
	List<WorkExperience> findAllWorkExperiences(@Param("professorId") Long professorId);

}
