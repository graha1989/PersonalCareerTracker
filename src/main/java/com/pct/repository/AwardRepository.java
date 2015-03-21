package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {

}
