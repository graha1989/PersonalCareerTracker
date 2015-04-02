package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ProfessorPublication;

public interface ProfessorPublicationsRepository extends JpaRepository<ProfessorPublication, Long> {
	
	@Query("SELECT p FROM ProfessorPublication p JOIN p.professor prof WHERE prof.id=:professorId")
	List<ProfessorPublication> findAllPublications(@Param("professorId") Long professorId);

}
