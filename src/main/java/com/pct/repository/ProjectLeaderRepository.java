package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ProjectLeader;

public interface ProjectLeaderRepository extends JpaRepository<ProjectLeader, Long> {

	@Query("SELECT p FROM ProjectLeader p JOIN p.project project WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.professor IS NULL AND project.id != :projectId")
	List<ProjectLeader> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("projectId") Long projectId);

	@Query("SELECT p.id FROM ProjectLeader l JOIN l.professor p WHERE l.project.id = :projectId")
	List<Long> findProfessorIdsWhoAreLeadersOnProject(@Param("projectId") Long projectId);

}
