package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.AcademicCommunityContribution;
import com.pct.domain.Professor;
import com.pct.domain.dto.FacultyOrUniversityAuthoritiesWorkDto;
import com.pct.domain.dto.ProfessionalOrganizationConductionDto;
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
	public List<FacultyOrUniversityAuthoritiesWorkDto> findAllFacultyOrUniversityAuthoritiesWorks(Long professorId, String type) {

		List<FacultyOrUniversityAuthoritiesWorkDto> workDtos = new ArrayList<FacultyOrUniversityAuthoritiesWorkDto>();
		AcademicCommunityContributionType contributionType = AcademicCommunityContributionType.getByTitle(type);
		try {
			List<AcademicCommunityContribution> contributions = academicCommunityContributionRepository.findAllContributions(professorId,
					contributionType);
			for (AcademicCommunityContribution a : contributions) {
				FacultyOrUniversityAuthoritiesWorkDto workDto = new FacultyOrUniversityAuthoritiesWorkDto(a);
				workDtos.add(workDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workDtos;
	}

	@Override
	@Transactional
	public FacultyOrUniversityAuthoritiesWorkDto findFacultyOrUniversityAuthorityWorkById(Long id)
			throws AcademicCommunityContributionNotFoundException {

		FacultyOrUniversityAuthoritiesWorkDto workDto;
		if (id != null) {
			AcademicCommunityContribution a = academicCommunityContributionRepository.findOne(id);
			if (a != null) {
				workDto = new FacultyOrUniversityAuthoritiesWorkDto(a);
				return workDto;
			}
		}
		throw new AcademicCommunityContributionNotFoundException();
	}

	@Override
	@Transactional
	public void saveFacultyOrUniversityAuthorityWork(FacultyOrUniversityAuthoritiesWorkDto workDto) throws ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(workDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		academicCommunityContributionRepository.saveAndFlush(createOrUpdateAcademicCommunityContributionInstanceDto(workDto, professor));
	}

	private AcademicCommunityContribution createOrUpdateAcademicCommunityContributionInstanceDto(FacultyOrUniversityAuthoritiesWorkDto workDto,
			Professor professor) {

		AcademicCommunityContribution contribution = null;
		if (workDto.getId() == null) {
			contribution = new AcademicCommunityContribution();
			contribution.setProfessor(professor);
			contribution.setType(workDto.getType());
		} else {
			contribution = academicCommunityContributionRepository.findOne(workDto.getId());
		}
		contribution.setAuthorityOrganizationOrJournal(workDto.getAuthority());
		contribution.setInstitutionType(workDto.getInstitutionType());
		contribution.setAuthorityOrOrganizationWorkStartDate(workDto.getStartDate());
		contribution.setAuthorityOrOrganizationWorkEndDate(workDto.getEndDate());

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

	@Override
	@Transactional
	public List<ProfessionalOrganizationConductionDto> findAllProfessionalOrganizationConductions(Long professorId, String type) {

		List<ProfessionalOrganizationConductionDto> conductionDtos = new ArrayList<ProfessionalOrganizationConductionDto>();
		AcademicCommunityContributionType contributionType = AcademicCommunityContributionType.getByTitle(type);
		try {
			List<AcademicCommunityContribution> contributions = academicCommunityContributionRepository.findAllContributions(professorId,
					contributionType);
			for (AcademicCommunityContribution a : contributions) {
				ProfessionalOrganizationConductionDto conductionDto = new ProfessionalOrganizationConductionDto(a);
				conductionDtos.add(conductionDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conductionDtos;
	}

	@Override
	@Transactional
	public ProfessionalOrganizationConductionDto findProfessionalOrganizationConductionById(Long id)
			throws AcademicCommunityContributionNotFoundException {

		ProfessionalOrganizationConductionDto conductionDto;
		if (id != null) {
			AcademicCommunityContribution a = academicCommunityContributionRepository.findOne(id);
			if (a != null) {
				conductionDto = new ProfessionalOrganizationConductionDto(a);
				return conductionDto;
			}
		}
		throw new AcademicCommunityContributionNotFoundException();
	}

	@Override
	@Transactional
	public void saveProfessionalOrganizationConduction(ProfessionalOrganizationConductionDto conductionDto) throws ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(conductionDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		academicCommunityContributionRepository.saveAndFlush(createOrUpdateAcademicCommunityContributionInstanceDto(conductionDto, professor));
	}

	private AcademicCommunityContribution createOrUpdateAcademicCommunityContributionInstanceDto(ProfessionalOrganizationConductionDto conductionDto,
			Professor professor) {

		AcademicCommunityContribution contribution = null;
		if (conductionDto.getId() == null) {
			contribution = new AcademicCommunityContribution();
			contribution.setProfessor(professor);
			contribution.setType(conductionDto.getType());
		} else {
			contribution = academicCommunityContributionRepository.findOne(conductionDto.getId());
		}
		contribution.setAuthorityOrganizationOrJournal(conductionDto.getOrganization());
		contribution.setFunctionInOrganizationConferenceOrCommittee(conductionDto.getFunctionDesc());
		contribution.setAuthorityOrOrganizationWorkStartDate(conductionDto.getStartDate());
		contribution.setAuthorityOrOrganizationWorkEndDate(conductionDto.getEndDate());

		return contribution;
	}

}
