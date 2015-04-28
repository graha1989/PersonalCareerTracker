package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SpecializationAbroadDto;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorSpecializationNotFoundException;

public interface ProfessorSpecializationService {

	List<SpecializationAbroadDto> findAllSpecializations(Long professorId)
			throws ProfessorSpecializationNotFoundException;

	void saveProfessorSpecialization(SpecializationAbroadDto specializationAbroadDto)
			throws ProfessorSpecializationNotFoundException, ProfessorNotFoundException, InstitutionNotFoundException;

	void deleteSpecialization(Long id) throws ProfessorSpecializationNotFoundException;

}
