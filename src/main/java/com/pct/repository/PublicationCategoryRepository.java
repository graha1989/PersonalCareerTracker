package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.PublicationCategory;

public interface PublicationCategoryRepository extends JpaRepository<PublicationCategory, Long> {
	
}
