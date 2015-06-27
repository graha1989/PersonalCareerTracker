package com.pct.service;

import java.util.List;

import com.pct.domain.dto.AcademicCommunityContributionDto;
import com.pct.validation.AcademicCommunityContributionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

public interface AcademicCommunityContributionService {

	List<AcademicCommunityContributionDto> findAllAcademicCommunityContributions(Long professorId, String type);

	AcademicCommunityContributionDto findAcademicCommunityContributionById(Long id) throws AcademicCommunityContributionNotFoundException;

	void saveAcademicCommunityContribution(AcademicCommunityContributionDto contributionDto) throws ProfessorNotFoundException;

	void deleteAcademicCommunityContribution(Long id) throws AcademicCommunityContributionNotFoundException;

}
