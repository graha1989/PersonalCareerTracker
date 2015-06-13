package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.PublicationType;

public interface PublicationTypeRepository extends JpaRepository<PublicationType, Long> {
	
	PublicationType findByTypeName(String typeName);
	
}
