package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.AcademicCommunityContribution;
import com.pct.domain.Professor;
import com.pct.domain.dto.AcademicCommunityContributionDto;
import com.pct.domain.enums.AcademicCommunityContributionType;
import com.pct.repository.AcademicCommunityContributionRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.AcademicCommunityContributionService;
import com.pct.validation.AcademicCommunityContributionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class AcademicCommunityContributionServiceImpl implements AcademicCommunityContributionService {

	@Autowired
	private AcademicCommunityContributionRepository academicCommunityContributionRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<AcademicCommunityContributionDto> findAllAcademicCommunityContributions(Long professorId, String type) {

		List<AcademicCommunityContributionDto> workDtos = new ArrayList<AcademicCommunityContributionDto>();
		AcademicCommunityContributionType contributionType = AcademicCommunityContributionType.getByTitle(type);
		try {
			List<AcademicCommunityContribution> contributions = academicCommunityContributionRepository.findAllContributions(professorId,
					contributionType);
			for (AcademicCommunityContribution a : contributions) {
				AcademicCommunityContributionDto contributionDto = new AcademicCommunityContributionDto(a);
				workDtos.add(contributionDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workDtos;
	}

	@Override
	@Transactional
	public AcademicCommunityContributionDto findAcademicCommunityContributionById(Long id) throws AcademicCommunityContributionNotFoundException {

		AcademicCommunityContributionDto contributionDto;
		if (id != null) {
			AcademicCommunityContribution a = academicCommunityContributionRepository.findOne(id);
			if (a != null) {
				contributionDto = new AcademicCommunityContributionDto(a);
				return contributionDto;
			}
		}
		throw new AcademicCommunityContributionNotFoundException();
	}

	@Override
	@Transactional
	public void saveAcademicCommunityContribution(AcademicCommunityContributionDto contributionDto) throws ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(contributionDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		academicCommunityContributionRepository.saveAndFlush(createOrUpdateAcademicCommunityContributionInstanceDto(contributionDto, professor));
	}

	private AcademicCommunityContribution createOrUpdateAcademicCommunityContributionInstanceDto(AcademicCommunityContributionDto contributionDto,
			Professor professor) {

		AcademicCommunityContribution contribution = null;
		if (contributionDto.getId() == null) {
			contribution = new AcademicCommunityContribution();
			contribution.setProfessor(professor);
			contribution.setType(contributionDto.getType());
		} else {
			contribution = academicCommunityContributionRepository.findOne(contributionDto.getId());
		}
		contribution.setAuthorityOrganizationOrJournal(contributionDto.getAuthorityOrganizationOrJournal());
		contribution.setInstitutionType(contributionDto.getInstitutionType());
		contribution.setAuthorityOrOrganizationWorkStartDate(contributionDto.getAuthorityOrOrganizationWorkStartDate());
		contribution.setAuthorityOrOrganizationWorkEndDate(contributionDto.getAuthorityOrOrganizationWorkEndDate());
		contribution.setFunctionInOrganizationConferenceOrCommittee(contributionDto.getFunctionInOrganizationConferenceOrCommittee());
		contribution.setMeetingConferenceOrEventYear(contributionDto.getMeetingConferenceOrEventYear());
		contribution.setJournalCategory(contributionDto.getJournalCategory());

		return contribution;
	}

	@Override
	@Transactional
	public void deleteAcademicCommunityContribution(Long id) throws AcademicCommunityContributionNotFoundException {

		AcademicCommunityContribution contribution = academicCommunityContributionRepository.findOne(id);
		if (contribution == null) {
			throw new AcademicCommunityContributionNotFoundException();
		}
		academicCommunityContributionRepository.delete(contribution);

	}

}
