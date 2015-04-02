package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.ProfessorPublication;
import com.pct.domain.dto.PublicationDto;
import com.pct.repository.ProfessorPublicationsRepository;
import com.pct.service.ProfessorPublicationService;
import com.pct.validation.PublicationNotFoundException;

@Service
public class ProfessorPublicationServiceImpl implements ProfessorPublicationService {

	@Autowired
	private ProfessorPublicationsRepository professorPublicationsRepository;

	@Override
	@Transactional
	public List<PublicationDto> findAllPublications(Long professorId) throws PublicationNotFoundException {

		List<PublicationDto> publicationDtoList = new ArrayList<PublicationDto>();
		try {
			List<ProfessorPublication> publicationsList = professorPublicationsRepository
					.findAllPublications(professorId);
			for (ProfessorPublication p : publicationsList) {
				PublicationDto publicationDto = new PublicationDto(p);
				publicationDtoList.add(publicationDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicationDtoList;
	}

}
