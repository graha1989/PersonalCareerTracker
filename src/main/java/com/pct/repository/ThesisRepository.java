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
	 * Retrieves list of Bachelor thesis for professor.
	 * 
	 * @param id
	 * @return list of bachelor thesis
	 */
	@Query("SELECT t FROM Thesis t JOIN t.mentor m JOIN t.thesisType tType WHERE tType.finalPaperTypeName='Bachelor thesis' AND m.id=:id")
	List<Thesis> findAllBachelorThesis(@Param("id") Long id);

}