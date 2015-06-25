package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.AcademicCommunityContribution;
import com.pct.domain.dto.FacultyOrUniversityAuthoritiesWorkDto;
import com.pct.domain.enums.AcademicCommunityContributionType;
import com.pct.repository.AcademicCommunityContributionRepository;
import com.pct.service.AcademicCommunityContributionService;

@Service
public class AcademicCommunityContributionServiceImpl implements AcademicCommunityContributionService {

	@Autowired
	private AcademicCommunityContributionRepository academicCommunityContributionRepository;

	@Override
	@Transactional
	public List<FacultyOrUniversityAuthoritiesWorkDto> findAllFacultyOrUniversityAuthoritiesWorks(Long professorId, String type) {

		List<FacultyOrUniversityAuthoritiesWorkDto> workDtos = new ArrayList<FacultyOrUniversityAuthoritiesWorkDto>();
		AcademicCommunityContributionType contributionType = AcademicCommunityContributionType.getByTitle(type);
		try {
			List<AcademicCommunityContribution> contributions = academicCommunityContributionRepository.findAllContributions(professorId, contributionType);
			for (AcademicCommunityContribution a : contributions) {
				FacultyOrUniversityAuthoritiesWorkDto workDto = new FacultyOrUniversityAuthoritiesWorkDto(a);
				workDtos.add(workDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workDtos;
	}

}
