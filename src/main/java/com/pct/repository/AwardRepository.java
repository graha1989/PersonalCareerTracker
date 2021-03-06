package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {
	
	@Query("SELECT a FROM Award a JOIN a.professor p WHERE p.id=:professorId")
	List<Award> findAllAwards(@Param("professorId") Long professorId);

}
