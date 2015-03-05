package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Profesor;

/**
 * Spring Data repository for Profesor entity
 * 
 * @author a.grahovac
 *
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

	/**
	 * Retrieves Profesor by user name.
	 * 
	 * @param userName
	 * @return Profesor
	 */
	Profesor findByUserName(String userName);
	
	/**
	 * Retrieves Profesor by e-mail.
	 * 
	 * @param email
	 * @return Profesor
	 */
	Profesor findByEmail(String email);
	
	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param name
	 * @param surname
	 * @return list of Profesors
	 */
	@Query("SELECT p FROM Profesor p WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.id <> :idMentor")
	List<Profesor> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("idMentor") Long idMentor);
	
	/**
	 * Retrieves list of professors with partially matching name or surname.
	 * 
	 * @param name
	 * @param surname
	 * @return list of Profesors
	 */
	@Query("SELECT p FROM Profesor p WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.id <> :idProf AND p.id <> :idMentor")
	List<Profesor> findByNameLikeOrSurnameLike(@Param("value") String value, @Param("idProf") Long idProf, @Param("idMentor") Long idMentor);

}
