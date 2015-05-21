package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ProjectLeader;

/**
 * @author a.grahovac
 * 
 */
public interface ProjectLeaderRepository extends JpaRepository<ProjectLeader, Long> {

	/**
	 * Finding all project leaders who are not professors and not leaders on project with projectId
	 * 
	 * @param value
	 * @param projectId
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT p FROM ProjectLeader p JOIN p.project project WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.professor IS NULL AND project.id != :projectId")
	List<ProjectLeader> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("projectId") Long projectId);

	/**
	 * Finding all professors who are leaders on project with projectId and returns List of their Ids
	 * 
	 * @param projectId
	 * @return List<Long>
	 */
	@Query("SELECT p.id FROM ProjectLeader l JOIN l.professor p WHERE l.project.id = :projectId")
	List<Long> findProfessorIdsWhoAreLeadersOnProject(@Param("projectId") Long projectId);

}
