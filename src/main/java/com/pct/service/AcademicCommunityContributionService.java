package com.pct.service;

import java.util.List;

import com.pct.domain.dto.FacultyOrUniversityAuthoritiesWorkDto;

public interface AcademicCommunityContributionService {

	List<FacultyOrUniversityAuthoritiesWorkDto> findAllFacultyOrUniversityAuthoritiesWorks(Long professorId, String type);

}
