package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.InternationalPublication;

public interface InternationalPublicationsRepository extends JpaRepository<InternationalPublication, Long> {
	
	@Query("SELECT p FROM InternationalPublication p JOIN p.professor prof WHERE prof.id=:professorId")
	List<InternationalPublication> findAllInternationalPublications(@Param("professorId") Long professorId);

}
