package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Subject;
import com.pct.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
	
	@Query("SELECT s FROM Survey s JOIN s.professor p JOIN s.subject sub WHERE sub.id=:subjectId AND p.id=:professorId")
	List<Survey> findAllSurveysForSubject(@Param("professorId") Long professorId, @Param("subjectId") Long subjectId);
	
	List<Survey> findBySubject(Subject subject);
	
}
