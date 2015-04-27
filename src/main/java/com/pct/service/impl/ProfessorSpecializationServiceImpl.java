package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.SpecializationAbroad;
import com.pct.domain.dto.SpecializationAbroadDto;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProfessorSpecializationRepository;
import com.pct.service.ProfessorSpecializationService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorSpecializationNotFoundException;

@Service
public class ProfessorSpecializationServiceImpl implements ProfessorSpecializationService {

	@Autowired
	private ProfessorSpecializationRepository professorSpecializationRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Override
	@Transactional
	public List<SpecializationAbroadDto> findAllSpecializations(Long professorId)
			throws ProfessorSpecializationNotFoundException {

		List<SpecializationAbroadDto> specializationAbroadDtos = new ArrayList<SpecializationAbroadDto>();
		try {
			List<SpecializationAbroad> specializationAbroads = professorSpecializationRepository
					.findAllSpecializations(professorId);
			for (SpecializationAbroad s : specializationAbroads) {
				SpecializationAbroadDto specializationAbroadDto = new SpecializationAbroadDto(s);
				specializationAbroadDtos.add(specializationAbroadDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return specializationAbroadDtos;
	}

	@Override
	@Transactional
	public void saveProfessorSpecialization(SpecializationAbroadDto specializationAbroadDto)
			throws ProfessorSpecializationNotFoundException, ProfessorNotFoundException, InstitutionNotFoundException {

		Professor professor = professorRepository.findOne(specializationAbroadDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		Institution institution = initializeInstitution(specializationAbroadDto);
		institutionRepository.save(institution);
		professorSpecializationRepository
				.saveAndFlush(createOrUpdateProfessorSpecializationInstanceFromSpecializationAbroadDto(
						specializationAbroadDto, professor, institution));
	}

	/**
	 * Creates new Institution entity object or retrieves existing from the database and sets field values from
	 * SpecializationAbroadDto.
	 * 
	 * @param SpecializationAbroadDto
	 * @return
	 */
	public Institution initializeInstitution(@Nonnull SpecializationAbroadDto specializationAbroadDto) {
		Institution institution = null;
		if (specializationAbroadDto.getInstitutionId() != null) {
			institution = institutionRepository.findOne(specializationAbroadDto.getInstitutionId());
		} else {
			institution = new Institution();
		}
		/*
		 * institution.setCity(specializationAbroadDto.getFacultyCity());
		 * institution.setCountry(specializationAbroadDto.getFacultyCountry());
		 * institution.setName(specializationAbroadDto.getFacultyName());
		 * institution.setUniversity(specializationAbroadDto.getUniversityName());
		 * institution.setInstitutionType(specializationAbroadDto.getInstitutionType());
		 */
		return institution;
	}

	public SpecializationAbroad createOrUpdateProfessorSpecializationInstanceFromSpecializationAbroadDto(
			@Nonnull SpecializationAbroadDto specializationAbroadDto, @Nonnull Professor professor,
			@Nonnull Institution institution) {

		SpecializationAbroad specialization = null;
		if (specializationAbroadDto.getId() == null) {
			specialization = new SpecializationAbroad();
			specialization.setProfessor(professor);
		} else {
			specialization = professorSpecializationRepository.findOne(specializationAbroadDto.getId());
		}
		specialization.setInstitution(institution);
		specialization.setCity(specializationAbroadDto.getCity());
		specialization.setCountry(specializationAbroadDto.getCountry());
		specialization.setStartDate(specializationAbroadDto.getStartDate());
		specialization.setEndDate(specializationAbroadDto.getEndDate());
		specialization.setPurpose(specializationAbroadDto.getPurpose());

		return specialization;
	}

}
