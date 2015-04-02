package com.pct.service;

import java.util.List;

import com.pct.domain.dto.PublicationDto;
import com.pct.validation.PublicationNotFoundException;

public interface ProfessorPublicationService {

	List<PublicationDto> findAllPublications(Long professorId) throws PublicationNotFoundException;
	
}
