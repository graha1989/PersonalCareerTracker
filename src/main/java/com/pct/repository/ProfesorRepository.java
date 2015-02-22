package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
