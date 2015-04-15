package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.InternationalPublication;
import com.pct.domain.Professor;
import com.pct.domain.ProfessorPublication;
import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;
import com.pct.domain.enums.PublicationType;
import com.pct.repository.InternationalPublicationsRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProfessorPublicationsRepository;
import com.pct.repository.PublicationCategoryRepository;
import com.pct.service.PublicationService;
import com.pct.service.util.PublicationUtil;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.PublicationCategoryNotFoundException;
import com.pct.validation.PublicationNotFoundException;

@Service
public class PublicationServiceImpl implements PublicationService {

	@Autowired
	private ProfessorPublicationsRepository professorPublicationsRepository;

	@Autowired
	private PublicationCategoryRepository publicationCategoryRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Autowired
	private InternationalPublicationsRepository internationalPublicationsRepository;

	@Override
	@Transactional
	public List<ProfessorPublicationDto> findAllPublications(Long professorId) throws PublicationNotFoundException {

		List<ProfessorPublicationDto> publicationDtoList = new ArrayList<ProfessorPublicationDto>();
		try {
			List<ProfessorPublication> publicationsList = professorPublicationsRepository
					.findAllPublications(professorId);
			for (ProfessorPublication p : publicationsList) {
				ProfessorPublicationDto professorPublicationDto = new ProfessorPublicationDto(p);
				publicationDtoList.add(professorPublicationDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicationDtoList;
	}

	@Override
	@Transactional
	public List<PublicationType> findAllPublicationTypes() {
		return new ArrayList<PublicationType>(Arrays.asList(PublicationType.values()));
	}

	@Override
	@Transactional
	public List<PublicationCategory> findAllPublicationCategories() {
		return publicationCategoryRepository.findAll();
	}

	@Override
	@Transactional
	public ProfessorPublicationDto findProfessorPublicationById(Long id) throws PublicationNotFoundException {

		ProfessorPublicationDto professorPublicationDto;

		if (id == null || professorPublicationsRepository.findOne(id) == null) {
			throw new PublicationNotFoundException();
		} else {
			ProfessorPublication publication = professorPublicationsRepository.findOne(id);
			professorPublicationDto = new ProfessorPublicationDto(publication);
		}

		return professorPublicationDto;
	}

	@Override
	@Transactional
	public InternationalPublicationDto findInternationalPublicationById(Long id) throws PublicationNotFoundException {

		InternationalPublicationDto internationalPublicationDto;

		if (id == null || internationalPublicationsRepository.findOne(id) == null) {
			throw new PublicationNotFoundException();
		} else {
			InternationalPublication publication = internationalPublicationsRepository.findOne(id);
			internationalPublicationDto = new InternationalPublicationDto(publication);
		}

		return internationalPublicationDto;
	}

	@Override
	@Transactional
	public void saveProfessorPublication(ProfessorPublicationDto professorPublicationDto)
			throws PublicationNotFoundException, ProfessorNotFoundException, PublicationCategoryNotFoundException {

		Professor professor;
		PublicationCategory category;

		if (professorPublicationDto.getProfessorId() == null
				|| !professorRepository.exists(professorPublicationDto.getProfessorId())) {
			throw new ProfessorNotFoundException();
		} else {
			professor = professorRepository.findOne(professorPublicationDto.getProfessorId());
		}

		if (professorPublicationDto.getPublicationCategory() == null
				|| professorPublicationDto.getPublicationCategory().getId() == null
				|| publicationCategoryRepository.findOne(professorPublicationDto.getPublicationCategory().getId()) == null) {
			category = null;
		} else {
			category = publicationCategoryRepository.findOne(professorPublicationDto.getPublicationCategory().getId());
		}

		professorRepository.save(PublicationUtil.createOrUpdateProfessorPublicationInstanceFromPublicationDto(
				professorPublicationDto, professor, category));
	}

	@Override
	@Transactional
	public void saveInternationalPublication(InternationalPublicationDto internationalPublicationDto)
			throws PublicationNotFoundException, ProfessorNotFoundException, PublicationCategoryNotFoundException {

		Professor professor;
		PublicationCategory category;

		if (internationalPublicationDto.getProfessorId() == null
				|| !professorRepository.exists(internationalPublicationDto.getProfessorId())) {
			throw new ProfessorNotFoundException();
		} else {
			professor = professorRepository.findOne(internationalPublicationDto.getProfessorId());
		}

		if (internationalPublicationDto.getPublicationCategory() == null
				|| internationalPublicationDto.getPublicationCategory().getId() == null
				|| publicationCategoryRepository.findOne(internationalPublicationDto.getPublicationCategory().getId()) == null) {
			category = null;
		} else {
			category = publicationCategoryRepository.findOne(internationalPublicationDto.getPublicationCategory()
					.getId());
		}

		professorRepository.save(PublicationUtil.createOrUpdateInternationalPublicationInstanceFromPublicationDto(
				internationalPublicationDto, professor, category));
	}

	@Override
	@Transactional
	public void deleteProfessorPublication(Long id) throws PublicationNotFoundException {

		ProfessorPublication professorPublication = professorPublicationsRepository.findOne(id);

		if (id == null || professorPublication == null) {
			throw new PublicationNotFoundException();
		}

		if (professorPublication.getPublicationCategory() != null) {
			professorPublication.getPublicationCategory().getProfessorPublications().remove(professorPublication);
		}

		professorPublication.getProfessor().getProfessorPublications().remove(professorPublication);

		professorPublication.setPublicationCategory(null);
		professorPublication.setProfessor(null);

		professorPublicationsRepository.delete(professorPublication);
	}

	@Override
	@Transactional
	public List<InternationalPublicationDto> findAllInternationalPublications(Long professorId)
			throws PublicationNotFoundException {

		List<InternationalPublicationDto> publicationDtoList = new ArrayList<InternationalPublicationDto>();
		try {
			List<InternationalPublication> publicationsList = internationalPublicationsRepository
					.findAllInternationalPublications(professorId);
			for (InternationalPublication p : publicationsList) {
				InternationalPublicationDto internationalPublicationDto = new InternationalPublicationDto(p);
				publicationDtoList.add(internationalPublicationDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicationDtoList;
	}

	@Override
	@Transactional
	public void deleteInternationalPublication(Long id) throws PublicationNotFoundException {

		InternationalPublication internationalPublication = internationalPublicationsRepository.findOne(id);

		if (id == null || internationalPublication == null) {
			throw new PublicationNotFoundException();
		}

		if (internationalPublication.getPublicationCategory() != null) {
			internationalPublication.getPublicationCategory().getProfessorPublications().remove(internationalPublication);
		}

		internationalPublication.getProfessor().getProfessorPublications().remove(internationalPublication);

		internationalPublication.setPublicationCategory(null);
		internationalPublication.setProfessor(null);

		internationalPublicationsRepository.delete(internationalPublication);
	}

}
