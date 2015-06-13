package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.AwardField;

public interface AwardFieldRepository extends JpaRepository<AwardField, Long> {

	AwardField findByFieldName(String fieldName);

}
