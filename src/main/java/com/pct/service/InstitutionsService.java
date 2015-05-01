package com.pct.service;

import java.util.List;

import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.enums.InstitutionType;
import com.pct.validation.InstitutionNotFoundException;

public interface InstitutionsService {
	
	List<InstitutionDto> findAllInstitutions();

	List<InstitutionType> findAllInstitutionTypes();

	InstitutionDto findInstitutionById(Long id) throws InstitutionNotFoundException;

	void saveInstitution(InstitutionDto institutionDto) throws InstitutionNotFoundException;

	void deleteInstitution(Long id) throws InstitutionNotFoundException;
	
}
