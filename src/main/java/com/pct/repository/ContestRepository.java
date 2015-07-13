package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long> {

}
