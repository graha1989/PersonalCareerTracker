package com.pct.service;

import java.util.List;

import com.pct.domain.dto.FacultyOrUniversityAuthoritiesWorkDto;
import com.pct.domain.dto.ProfessionalOrganizationConductionDto;
import com.pct.validation.AcademicCommunityContributionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

public interface AcademicCommunityContributionService {

	List<FacultyOrUniversityAuthoritiesWorkDto> findAllFacultyOrUniversityAuthoritiesWorks(Long professorId, String type);

	FacultyOrUniversityAuthoritiesWorkDto findFacultyOrUniversityAuthorityWorkById(Long id) throws AcademicCommunityContributionNotFoundException;

	void saveFacultyOrUniversityAuthorityWork(FacultyOrUniversityAuthoritiesWorkDto workDto) throws ProfessorNotFoundException;

	void deleteAcademicCommunityContribution(Long id) throws AcademicCommunityContributionNotFoundException;

	List<ProfessionalOrganizationConductionDto> findAllProfessionalOrganizationConductions(Long professorId, String type);

	ProfessionalOrganizationConductionDto findProfessionalOrganizationConductionById(Long id) throws AcademicCommunityContributionNotFoundException;

	void saveProfessionalOrganizationConduction(ProfessionalOrganizationConductionDto conductionDto) throws ProfessorNotFoundException;

}
