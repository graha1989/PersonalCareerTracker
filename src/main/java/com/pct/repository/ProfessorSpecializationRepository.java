package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.SpecializationAbroad;

public interface ProfessorSpecializationRepository extends JpaRepository<SpecializationAbroad, Long> {
	
	@Query("SELECT s FROM SpecializationAbroad s JOIN s.professor p WHERE p.id=:professorId")
	List<SpecializationAbroad> findAllSpecializations(@Param("professorId") Long professorId);
	
}
