package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	@Query("SELECT s FROM Subject s WHERE s.id NOT IN :subjectIds AND s.seminarOrTeachingAbroad=:seminarOrTeachingAbroad AND s.name LIKE %:value%")
	List<Subject> findByNameLikeAndNotInIds(@Param("value") String value, @Param("subjectIds") List<Long> subjectIds,
			@Param("seminarOrTeachingAbroad") Boolean seminarOrTeachingAbroad);

	@Query("SELECT s FROM Subject s WHERE s.seminarOrTeachingAbroad=:seminarOrTeachingAbroad AND s.name LIKE %:value%")
	List<Subject> findByNameLike(@Param("value") String value, @Param("seminarOrTeachingAbroad") Boolean seminarOrTeachingAbroad);

	@Query("SELECT s FROM Subject s WHERE s.seminarOrTeachingAbroad=:seminarOrTeachingAbroad")
	List<Subject> findAllSubjectsOrSeminars(@Param("seminarOrTeachingAbroad") Boolean seminarOrTeachingAbroad);

}
