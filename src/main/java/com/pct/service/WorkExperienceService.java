package com.pct.service;

import java.util.List;

import com.pct.domain.Institution;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.domain.enums.InstitutionType;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.WorkExperienceNotFoundException;

public interface WorkExperienceService {

	List<WorkExperienceDto> findAllWorkExperiences(Long professorId) throws WorkExperienceNotFoundException;

	WorkExperienceDto findWorkExperienceById(Long id) throws WorkExperienceNotFoundException;

	void saveWorkExperience(WorkExperienceDto workExperienceDto) throws WorkExperienceNotFoundException, ProfessorNotFoundException, InstitutionNotFoundException;

	List<InstitutionType> findAllInstitutionTypes();

	List<Institution> findInstitutionsStartsWith(String value, List<Long> institutionIds);

	void deleteWorkExperience(Long id) throws WorkExperienceNotFoundException;

}
