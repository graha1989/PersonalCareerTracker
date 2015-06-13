package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.AwardType;

public interface AwardTypeRepository extends JpaRepository<AwardType, Long> {

	AwardType findByTypeName(String typeName);

}
