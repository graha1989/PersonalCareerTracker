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
	 * Finding all project leaders who are not professors and not leaders on the project with the projectId.
	 * 
	 * @param value
	 * @param leadersOnThisProjectWhoAreNotProfessors
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT p FROM ProjectLeader p WHERE p.professor IS NULL AND p.project.id != :projectId AND p.id NOT IN :leadersOnThisProjectWhoAreNotProfessors AND concat(p.name, ' ', p.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(@Param("value") String value,
			@Param("projectId") Long projectId,
			@Param("leadersOnThisProjectWhoAreNotProfessors") List<Long> leadersOnThisProjectWhoAreNotProfessors);

	/**
	 * Finding all project leaders who are not professors.
	 * 
	 * @param value
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT p FROM ProjectLeader p WHERE p.professor IS NULL AND concat(p.name, ' ', p.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(@Param("value") String value);

	/**
	 * Finding all project leaders who are not in the list of id-s of already entered leaders.
	 * 
	 * @param value
	 * @param leadersOnThisProjectWhoAreNotProfessors
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT p FROM ProjectLeader p WHERE p.professor IS NULL AND p.id NOT IN :leadersOnThisProjectWhoAreNotProfessors AND concat(p.name, ' ', p.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotAlreadyUsed(@Param("value") String value,
			@Param("leadersOnThisProjectWhoAreNotProfessors") List<Long> leadersOnThisProjectWhoAreNotProfessors);

	/**
	 * Finding all professors who are leaders on project with projectId and returns List of their Ids
	 * 
	 * @param projectId
	 * @return List<Long>
	 */
	@Query("SELECT p.id FROM ProjectLeader l JOIN l.professor p WHERE l.project.id = :projectId")
	List<Long> findProfessorIdsWhoAreLeadersOnProject(@Param("projectId") Long projectId);

}
