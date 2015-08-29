package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pct.domain.Institution;
import com.pct.domain.InstitutionType;
import com.pct.domain.Professor;
import com.pct.domain.WorkExperience;
import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.InstitutionTypeRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.WorkExperienceRepository;
import com.pct.service.WorkExperienceService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.WorkExperienceNotFoundException;

@Service
public class WorkExperiencesServiceImpl implements WorkExperienceService {

	private WorkExperienceRepository workExperienceRepository;
	private ProfesorRepository professorRepository;
	private InstitutionRepository institutionRepository;
	private InstitutionTypeRepository institutionTypeRepository;

	@Autowired
	public WorkExperiencesServiceImpl(WorkExperienceRepository workExperienceRepository, ProfesorRepository professorRepository,
			InstitutionRepository institutionRepository, InstitutionTypeRepository institutionTypeRepository) {
		this.workExperienceRepository = workExperienceRepository;
		this.professorRepository = professorRepository;
		this.institutionRepository = institutionRepository;
		this.institutionTypeRepository = institutionTypeRepository;
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
		if (id != null) {
			WorkExperience workExperience = workExperienceRepository.findOne(id);
			if (workExperience != null) {
				workExperienceDto = new WorkExperienceDto(workExperience);
				return workExperienceDto;
			}
		}

		throw new WorkExperienceNotFoundException();
	}

	@Override
	@Transactional
	public void saveWorkExperience(WorkExperienceDto workExperienceDto) throws WorkExperienceNotFoundException, ProfessorNotFoundException,
			InstitutionNotFoundException, SimilarDataAlreadyExistsException {

		Professor professor = professorRepository.findOne(workExperienceDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		if (workExperienceRepository.isThereFacultyWorkExperienceWithSimilarPeriod(workExperienceDto.getInstitutionId(), workExperienceDto
				.getProfessorId(), workExperienceDto.getTitle(), workExperienceDto.getWorkStartDate(),
				(workExperienceDto.getWorkEndDate() != null ? workExperienceDto.getWorkEndDate() : new Date())) == 0) {
			Institution institution = initializeInstitution(workExperienceDto);
			institutionRepository.save(institution);
			workExperienceRepository
					.saveAndFlush(createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(workExperienceDto, professor, institution));
		} else {
			throw new SimilarDataAlreadyExistsException("Work experience on faculty start date/Work experience on faculty end date",
					"workStartDate/workEndDate");
		}
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
		institution.setUniversity(workExperienceDto.getUniversityName());
		institution.setInstitutionType(institutionTypeRepository.findByTypeName(workExperienceDto.getInstitutionType()));

		return institution;
	}

	@Override
	@Transactional
	public List<InstitutionDto> findInstitutionsStartsWith(String value, String institutionType) {

		List<Institution> institutions = new ArrayList<Institution>();
		List<InstitutionDto> institutionDtos = new ArrayList<InstitutionDto>();
		if (!StringUtils.isEmpty(institutionType)) {
			InstitutionType type = institutionTypeRepository.findByTypeName(institutionType);
			institutions = institutionRepository.findByNameAndInstitutionTypeLike(value, type);
		} else {
			institutions = institutionRepository.findByNameLike(value);
		}
		for (Institution institution : institutions) {
			InstitutionDto institutionDto = new InstitutionDto(institution);
			institutionDtos.add(institutionDto);
		}
		return institutionDtos;
	}

	@Override
	@Transactional
	public void deleteWorkExperience(@Nonnull Long id) throws WorkExperienceNotFoundException {
		WorkExperience workExperience = workExperienceRepository.findOne(id);
		if (workExperience == null) {
			throw new WorkExperienceNotFoundException();
		}
		Professor professor = professorRepository.findOne(workExperience.getProfessor().getId());
		professor.setTitle(null);
		professor.setInstitution(null);
		professorRepository.save(professor);
		workExperienceRepository.delete(workExperience);
	}

	private WorkExperience createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(@Nonnull WorkExperienceDto workExperienceDto,
			@Nonnull Professor professor, @Nonnull Institution institution) {

		WorkExperience workExperience = null;
		if (workExperienceDto.getId() == null) {
			workExperience = new WorkExperience();
		} else {
			workExperience = workExperienceRepository.findOne(workExperienceDto.getId());
		}
		workExperience.setInstitution(institution);
		workExperience.setTitle(workExperienceDto.getTitle());
		workExperience.setWorkStartDate(workExperienceDto.getWorkStartDate());
		workExperience.setWorkEndDate(workExperienceDto.getWorkEndDate());

		if (workExperience.getWorkEndDate() == null && workExperience.getInstitution().getInstitutionType().getTypeName().equals("Fakultet")) {
			professor.setTitle(workExperience.getTitle());
			professor.setInstitution(workExperience.getInstitution().getName() + ", " + workExperience.getInstitution().getUniversity());
			professorRepository.saveAndFlush(professor);
		}
		workExperience.setProfessor(professor);

		return workExperience;
	}

}
