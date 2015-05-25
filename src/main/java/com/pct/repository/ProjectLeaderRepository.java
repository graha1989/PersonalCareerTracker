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
	@Query("SELECT l FROM ProjectLeader l WHERE l.professor IS NULL AND l.id NOT IN :leadersOnThisProjectWhoAreNotProfessors AND concat(l.name, ' ', l.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(@Param("value") String value,
			@Param("leadersOnThisProjectWhoAreNotProfessors") List<Long> leadersOnThisProjectWhoAreNotProfessors);

	/**
	 * Finding all project leaders who are not professors.
	 * 
	 * @param value
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT l FROM ProjectLeader l WHERE l.professor IS NULL AND concat(l.name, ' ', l.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotLeadersOnSelectedProject(@Param("value") String value);

	/**
	 * Finding all project leaders who are not in the list of id-s of already entered leaders.
	 * 
	 * @param value
	 * @param leadersOnThisProjectWhoAreNotProfessors
	 * @return List<ProjectLeader>
	 */
	@Query("SELECT l FROM ProjectLeader l WHERE l.professor IS NULL AND l.id NOT IN :leadersOnThisProjectWhoAreNotProfessors AND concat(l.name, ' ', l.surname) LIKE %:value%")
	List<ProjectLeader> findLeadersWhoAreNotProfessorsAndNotAlreadyUsed(@Param("value") String value,
			@Param("leadersOnThisProjectWhoAreNotProfessors") List<Long> leadersOnThisProjectWhoAreNotProfessors);

	/**
	 * Finding project leader who is professor by professorId.
	 * 
	 * @param professorId
	 * @return ProjectLeader
	 */
	@Query("SELECT l FROM ProjectLeader l WHERE l.professor.id = :professorId")
	ProjectLeader findLeadersByProfessorId(@Param("professorId") Long professorId);

}
