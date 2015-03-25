package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
