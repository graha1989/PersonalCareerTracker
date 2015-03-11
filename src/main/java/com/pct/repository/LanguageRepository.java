package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
