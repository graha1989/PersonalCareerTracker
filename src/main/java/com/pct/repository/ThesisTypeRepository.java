package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.StudiesThesisType;

public interface ThesisTypeRepository extends JpaRepository<StudiesThesisType, Long> {
	
}
