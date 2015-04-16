package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.pct.service.util.WorkExperienceUtil;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.WorkExperienceNotFoundException;

@Service
public class WorkExperiencesServiceImpl implements WorkExperienceService {

	@Autowired
	private WorkExperienceRepository workExperienceRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

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

		Professor professor;
		Institution institution;

		if (workExperienceDto.getProfessorId() == null
				|| !professorRepository.exists(workExperienceDto.getProfessorId())) {
			throw new ProfessorNotFoundException();
		} else {
			professor = professorRepository.findOne(workExperienceDto.getProfessorId());
		}

		if (workExperienceDto.getInstitutionId() == null
				|| !institutionRepository.exists(workExperienceDto.getInstitutionId())) {
			institution = new Institution();
		} else {
			institution = institutionRepository.findOne(workExperienceDto.getInstitutionId());
		}

		workExperienceRepository.save(WorkExperienceUtil.createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(
				workExperienceDto, professor, institution));
	}

	@Override
	@Transactional
	public List<InstitutionType> findAllInstitutionTypes() {
		return new ArrayList<InstitutionType>(Arrays.asList(InstitutionType.values()));
	}

}
