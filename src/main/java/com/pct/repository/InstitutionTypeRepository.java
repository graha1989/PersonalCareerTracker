package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.InstitutionType;

public interface InstitutionTypeRepository extends JpaRepository<InstitutionType, Long> {
	
	InstitutionType findByTypeName(String typeName);
	
}
