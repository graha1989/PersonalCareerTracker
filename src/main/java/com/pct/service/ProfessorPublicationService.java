package com.pct.service;

import java.util.List;

import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.PublicationDto;
import com.pct.domain.enums.PublicationType;
import com.pct.validation.PublicationNotFoundException;

public interface ProfessorPublicationService {

	List<PublicationDto> findAllPublications(Long professorId) throws PublicationNotFoundException;

	List<PublicationType> findAllPublicationTypes();

	List<PublicationCategory> findAllPublicationCategories();

	PublicationDto findPublicationById(Long id) throws PublicationNotFoundException;
	
}
