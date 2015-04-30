package com.pct.service;

import java.util.List;

import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;
import com.pct.domain.dto.PublicationCategoryDto;
import com.pct.domain.enums.PublicationType;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.PublicationCategoryNotFoundException;
import com.pct.validation.PublicationNotFoundException;

public interface PublicationService {

	List<ProfessorPublicationDto> findAllPublications(Long professorId) throws PublicationNotFoundException;

	List<PublicationType> findAllPublicationTypes();

	List<PublicationCategoryDto> findAllPublicationCategories();

	ProfessorPublicationDto findProfessorPublicationById(Long id) throws PublicationNotFoundException;

	InternationalPublicationDto findInternationalPublicationById(Long id) throws PublicationNotFoundException;

	void saveProfessorPublication(ProfessorPublicationDto professorPublicationDto) throws PublicationNotFoundException,
			ProfessorNotFoundException, PublicationCategoryNotFoundException;

	void deleteProfessorPublication(Long id) throws PublicationNotFoundException;
	
	void deleteInternationalPublication(Long id) throws PublicationNotFoundException;
	
	List<InternationalPublicationDto> findAllInternationalPublications(Long professorId)
			throws PublicationNotFoundException;

	void saveInternationalPublication(InternationalPublicationDto internationalPublicationDto)
			throws PublicationNotFoundException, ProfessorNotFoundException, PublicationCategoryNotFoundException;

}
