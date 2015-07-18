package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Institution;
import com.pct.domain.Studies;

public interface ProfessorStudiesRepository extends JpaRepository<Studies, Long> {
	
	@Query("SELECT s FROM Studies s JOIN s.professor p JOIN s.studiesThesisType tType WHERE tType.id=:thesisTypeId AND p.id=:professorId")
	List<Studies> findAllPublications(@Param("professorId") Long professorId, @Param("thesisTypeId") Long thesisTypeId);
	
	Long countByInstitution(Institution institution);
	
}
