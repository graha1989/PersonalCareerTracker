package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.WorkExperience;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.domain.enums.InstitutionType;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.WorkExperienceRepository;
import com.pct.service.WorkExperienceService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.WorkExperienceNotFoundException;

@Service
public class WorkExperiencesServiceImpl implements WorkExperienceService {

	private WorkExperienceRepository workExperienceRepository;

	private ProfesorRepository professorRepository;

	private InstitutionRepository institutionRepository;

	@Autowired
	public WorkExperiencesServiceImpl(WorkExperienceRepository workExperienceRepository,
			ProfesorRepository professorRepository, InstitutionRepository institutionRepository) {
		super();
		this.workExperienceRepository = workExperienceRepository;
		this.professorRepository = professorRepository;
		this.institutionRepository = institutionRepository;
	}

	@Override
	@Transactional
	public List<WorkExperienceDto> findAllWorkExperiences(Long professorId) throws WorkExperienceNotFoundException {

		List<WorkExperienceDto> workExperienceDtos = new ArrayList<WorkExperienceDto>();
		try {
			List<WorkExperience> workExperiencesList = workExperienceRepository.findAllWorkExperiences(professorId);
			for (WorkExperience e : workExperiencesList) {
				WorkExperienceDto workExperienceDto = new WorkExperienceDto(e);
				workExperienceDtos.add(workExperienceDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return workExperienceDtos;
	}

	@Override
	@Transactional
	public WorkExperienceDto findWorkExperienceById(Long id) throws WorkExperienceNotFoundException {

		WorkExperienceDto workExperienceDto;

		if (id == null || workExperienceRepository.findOne(id) == null) {
			throw new WorkExperienceNotFoundException();
		} else {
			WorkExperience workExperience = workExperienceRepository.findOne(id);
			workExperienceDto = new WorkExperienceDto(workExperience);
		}

		return workExperienceDto;
	}

	@Override
	@Transactional
	public void saveWorkExperience(WorkExperienceDto workExperienceDto) throws WorkExperienceNotFoundException,
			ProfessorNotFoundException, InstitutionNotFoundException {

		Professor professor = professorRepository.findOne(workExperienceDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		Institution institution = initializeInstitution(workExperienceDto);
		institutionRepository.save(institution);
		workExperienceRepository.saveAndFlush(createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(
				workExperienceDto, professor, institution));
	}

	/**
	 * Creates new Institution entity object or retrieves existing from the database and sets field values from
	 * WorkExperienceDto.
	 * 
	 * @param workExperienceDto
	 * @return
	 */
	public Institution initializeInstitution(@Nonnull WorkExperienceDto workExperienceDto) {
		Institution institution = null;
		if (workExperienceDto.getInstitutionId() != null) {
			institution = institutionRepository.findOne(workExperienceDto.getInstitutionId());
		} else {
			institution = new Institution();
		}
		institution.setCity(workExperienceDto.getInstitutionCity());
		institution.setCountry(workExperienceDto.getInstitutionCountry());
		institution.setName(workExperienceDto.getInstitutionName());
		institution.setInstitutionType(workExperienceDto.getInstitutionType());

		return institution;
	}

	@Override
	@Transactional
	public List<InstitutionType> findAllInstitutionTypes() {
		return new ArrayList<InstitutionType>(Arrays.asList(InstitutionType.values()));
	}

	@Override
	@Transactional
	public List<Institution> findInstitutionsStartsWith(String value) {
		return institutionRepository.findByNameLike(value);
	}

	@Override
	@Transactional
	public void deleteWorkExperience(@Nonnull Long id) throws WorkExperienceNotFoundException {
		WorkExperience workExperience = workExperienceRepository.findOne(id);
		if (workExperience == null) {
			throw new WorkExperienceNotFoundException();
		}
		workExperienceRepository.delete(workExperience);
	}

	public WorkExperience createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(
			@Nonnull WorkExperienceDto workExperienceDto, @Nonnull Professor professor, @Nonnull Institution institution) {

		WorkExperience workExperience = null;
		if (workExperienceDto.getId() == null) {
			workExperience = new WorkExperience();
			workExperience.setProfessor(professor);
		} else {
			workExperience = workExperienceRepository.findOne(workExperienceDto.getId());
		}
		workExperience.setInstitution(institution);
		workExperience.setTitle(workExperienceDto.getTitle());
		workExperience.setWorkStartDate(workExperienceDto.getWorkStartDate());
		workExperience.setWorkEndDate(workExperienceDto.getWorkEndDate());

		return workExperience;
	}

}
