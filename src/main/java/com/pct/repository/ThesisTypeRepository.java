package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.ThesisType;

public interface ThesisTypeRepository extends JpaRepository<ThesisType, Long> {
	
}
