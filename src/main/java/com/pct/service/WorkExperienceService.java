package com.pct.service;

import java.util.List;

import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.WorkExperienceNotFoundException;

public interface WorkExperienceService {

	List<WorkExperienceDto> findAllWorkExperiences(Long professorId) throws WorkExperienceNotFoundException;

	WorkExperienceDto findWorkExperienceById(Long id) throws WorkExperienceNotFoundException;

	void saveWorkExperience(WorkExperienceDto workExperienceDto) throws WorkExperienceNotFoundException, ProfessorNotFoundException,
			InstitutionNotFoundException, SimilarDataAlreadyExistsException;

	List<InstitutionDto> findInstitutionsStartsWith(String value, String institutionType);

	void deleteWorkExperience(Long id) throws WorkExperienceNotFoundException;

}
