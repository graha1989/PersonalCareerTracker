package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	@Query("SELECT s FROM Subject s WHERE s.id NOT IN :subjectIds AND s.name LIKE %:value%")
	List<Subject> findByNameLikeAndNotInIds(@Param("value") String value, @Param("subjectIds") List<Long> subjectIds);
	
	@Query("SELECT s FROM Subject s WHERE s.name LIKE %:value%")
	List<Subject> findByNameLike(@Param("value") String value);
	
}
