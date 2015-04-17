package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Thesis;

/**
 * Spring Data repository for Thesis entity
 * 
 * @author a.grahovac
 * 
 */
public interface ThesisRepository extends JpaRepository<Thesis, Long> {

	/**
	 * Retrieves list of thesis for professor and thesis type.
	 * 
	 * @param mentorId
	 * @param thesisTypeId
	 * @return list of bachelor thesis
	 */
	@Query("SELECT t FROM Thesis t JOIN t.mentor m JOIN t.studiesThesisType tType WHERE tType.id=:thesisTypeId AND m.id=:mentorId")
	List<Thesis> findAllThesis(@Param("mentorId") Long mentorId, @Param("thesisTypeId") Long thesisTypeId);

}