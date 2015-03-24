package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ProjectExperience;

public interface ProjectExperienceRepository extends JpaRepository<ProjectExperience, Long> {
	
	@Query("SELECT pe FROM ProjectExperience pe JOIN pe.professor p WHERE p.id=:professorId")
	List<ProjectExperience> findAllProjectExperiences(@Param("professorId") Long professorId);

}
