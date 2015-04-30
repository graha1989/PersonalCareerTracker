package com.pct.service;

import java.util.List;

import com.pct.domain.dto.InstitutionDto;

public interface InstitutionsService {
	
	List<InstitutionDto> findAllInstitutions();
	
}
