package com.pct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	/**
	 * Retrieves Student by transcript number.
	 * 
	 * @param transcriptNumber
	 * @return student
	 */
	Student findByTranscriptNumber(String transcriptNumber);
	
	/**
	 * Retrieves list of students with matching first name.
	 * 
	 * @param name
	 * @return list of students
	 */
	@Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
	List<Student> findByNameLike(@Param("name") String name);
	
	/**
	 * Retrieves list of students with matching surname.
	 * 
	 * @param surname
	 * @return list of students
	 */
	@Query("SELECT s FROM Student s WHERE s.surname LIKE %:surname%")
	Page<Student> findBySurnameLike(@Param("surname") String surname, Pageable page);
	
	/**
	 * Retrieves list of students with partially matching name or surname.
	 * 
	 * @param name
	 * @param surname
	 * @return list of students
	 */
	@Query("SELECT s FROM Student s WHERE s.name LIKE %:name% AND s.surname LIKE %:surname%")
	Page<Student> findByNameLikeAndSurnameLike(@Param("name") String name, @Param("surname") String surname, Pageable page);
	
	/**
	 * Retrieves list of students with partially matching name or surname or transcriptNumber.
	 * 
	 * @param name
	 * @param surname
	 * @return list of students
	 */
	@Query("SELECT s FROM Student s WHERE s.transcriptNumber LIKE %:data% OR s.name LIKE %:data% OR s.surname LIKE %:data%")
	List<Student> findByTranscriptNumberLikeOrNameLikeOrSurnameLike(@Param("data") String data);
	
}
