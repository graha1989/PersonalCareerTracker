package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	@Query("SELECT p FROM Project p WHERE p.name LIKE %:value%")
	List<Project> findByNameLike(@Param("value") String value);

}
