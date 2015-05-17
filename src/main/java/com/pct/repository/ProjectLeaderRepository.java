package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ProjectLeader;

public interface ProjectLeaderRepository extends JpaRepository<ProjectLeader, Long> {

	@Query("SELECT p FROM ProjectLeader p WHERE concat(p.name, ' ', p.surname) LIKE %:value% AND p.professor IS NULL")
	List<ProjectLeader> findByNameLikeOrSurnameLike(@Param("value") String value);

}
