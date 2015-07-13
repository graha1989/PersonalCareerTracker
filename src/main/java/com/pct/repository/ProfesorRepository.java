package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Professor;

/**
 * Spring Data repository for Professor entity
 * 
 * @author a.grahovac
 * 
 */
public interface ProfesorRepository extends JpaRepository<Professor, Long> {

	/**
	 * Retrieves Professor by user name.
	 * 
	 * @param userName
	 * @return Professor
	 */
	Professor findByUserName(String userName);

	/**
	 * Retrieves Professor by e-mail.
	 * 
	 * @param email
	 * @return Professor
	 */
	Professor findByEmail(String email);

	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param name
	 * @param surname
	 * @return list of Profesors
	 */
	@Query("SELECT p FROM Professor p WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.id <> :idMentor")
	List<Professor> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("idMentor") Long idMentor);

	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param name
	 * @param surname
	 * @return list of Profesors
	 */
	@Query("SELECT p FROM Professor p WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.id <> :idProf AND p.id <> :idMentor")
	List<Professor> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("idProf") Long idProf, @Param("idMentor") Long idMentor);

	/**
	 * Retrieves list of professors with partially matching name or surname, and id not in List of professors who are
	 * leaders on project with projectId.
	 * 
	 * @param value
	 * @param professorIds
	 * @return list of Professors
	 */
	@Query("SELECT p FROM Professor p WHERE p.id NOT IN :professorsWhoAreLeadersOnThisProject AND concat(p.name, ' ', p.surname) LIKE %:value%")
	List<Professor> findProfessorsWhoAreNotLeadersOnSelectedProject(@Param("value") String value,
			@Param("professorsWhoAreLeadersOnThisProject") List<Long> professorsWhoAreLeadersOnThisProject);

	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param value
	 * @return list of Professors
	 */
	@Query("SELECT p FROM Professor p WHERE concat(p.name, ' ', p.surname) LIKE %:value%")
	List<Professor> findProfessorsWhoAreNotLeadersOnSelectedProject(@Param("value") String value);

	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param value
	 * @return list of Professors
	 */
	@Query("SELECT p FROM Professor p WHERE concat(p.name, ' ', p.surname) LIKE %:value%")
	List<Professor> findByNameLikeOrSurnameLike(@Param("value") String value);

	@Query("SELECT p FROM Professor p WHERE p.id NOT IN :professorsWhoAreCommissionMembersOnThisContest AND concat(p.name, ' ', p.surname) LIKE %:value%")
	List<Professor> findProfessorsWhoAreNotCommissionMembersOnSelectedContest(@Param("value") String value,
			@Param("professorsWhoAreCommissionMembersOnThisContest") List<Long> professorsWhoAreCommissionMembersOnThisContest);

	@Query("SELECT p FROM Professor p WHERE concat(p.name, ' ', p.surname) LIKE %:value%")
	List<Professor> findProfessorsWhoAreNotCommissionMembersOnSelectedContest(@Param("value") String value);

}
