package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.ProjectType;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
	
	ProjectType findByTypeName(String typeName);
	
}
