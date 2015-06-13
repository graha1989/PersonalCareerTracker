package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.StudyProgram;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
	
	StudyProgram findByName(String name);
	
}
