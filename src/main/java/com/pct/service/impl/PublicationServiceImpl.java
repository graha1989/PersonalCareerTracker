package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.InternationalPublication;
import com.pct.domain.Professor;
import com.pct.domain.ProfessorPublication;
import com.pct.domain.PublicationCategory;
import com.pct.domain.PublicationType;
import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;
import com.pct.domain.dto.PublicationCategoryDto;
import com.pct.repository.InternationalPublicationsRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.ProfessorPublicationsRepository;
import com.pct.repository.PublicationCategoryRepository;
import com.pct.repository.PublicationTypeRepository;
import com.pct.service.PublicationService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.PublicationCategoryNotFoundException;
import com.pct.validation.PublicationNotFoundException;

@Service
public class PublicationServiceImpl implements PublicationService {

	private ProfessorPublicationsRepository professorPublicationsRepository;
	private PublicationCategoryRepository publicationCategoryRepository;
	private ProfesorRepository professorRepository;
	private InternationalPublicationsRepository internationalPublicationsRepository;
	private PublicationTypeRepository publicationTypeRepository;

	@Autowired
	public PublicationServiceImpl(ProfessorPublicationsRepository professorPublicationsRepository,
			PublicationCategoryRepository publicationCategoryRepository, ProfesorRepository professorRepository,
			InternationalPublicationsRepository internationalPublicationsRepository,
			PublicationTypeRepository publicationTypeRepository) {
		this.professorPublicationsRepository = professorPublicationsRepository;
		this.publicationCategoryRepository = publicationCategoryRepository;
		this.professorRepository = professorRepository;
		this.internationalPublicationsRepository = internationalPublicationsRepository;
		this.publicationTypeRepository = publicationTypeRepository;
	}

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

		List<PublicationType> publicationTypes = null;
		try {
			publicationTypes = publicationTypeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicationTypes;
	}

	@Override
	@Transactional
	public List<PublicationCategoryDto> findAllPublicationCategories() {

		List<PublicationCategoryDto> publicationCategoryDtos = new ArrayList<PublicationCategoryDto>();
		List<PublicationCategory> publicationCategories = publicationCategoryRepository.findAll();
		for (PublicationCategory publicationCategory : publicationCategories) {
			publicationCategoryDtos.add(new PublicationCategoryDto(publicationCategory));
		}

		return publicationCategoryDtos;
	}

	@Override
	@Transactional
	public ProfessorPublicationDto findProfessorPublicationById(Long id) throws PublicationNotFoundException {

		ProfessorPublicationDto professorPublicationDto;
		if (id != null) {
			ProfessorPublication publication = professorPublicationsRepository.findOne(id);
			if (publication != null) {
				professorPublicationDto = new ProfessorPublicationDto(publication);
				return professorPublicationDto;
			}
		}

		throw new PublicationNotFoundException();
	}

	@Override
	@Transactional
	public InternationalPublicationDto findInternationalPublicationById(Long id) throws PublicationNotFoundException {

		InternationalPublicationDto internationalPublicationDto = null;
		if (id != null) {
			InternationalPublication publication = internationalPublicationsRepository.findOne(id);
			if (publication != null) {
				internationalPublicationDto = new InternationalPublicationDto(publication);
				return internationalPublicationDto;
			}
		}

		throw new PublicationNotFoundException();
	}

	@Override
	@Transactional
	public void saveProfessorPublication(ProfessorPublicationDto professorPublicationDto)
			throws PublicationNotFoundException, ProfessorNotFoundException, PublicationCategoryNotFoundException {

		Professor professor = professorRepository.findOne(professorPublicationDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		PublicationCategory category = initializeProfessorPublicationCategory(professorPublicationDto);
		professorPublicationsRepository
				.saveAndFlush(createOrUpdateProfessorPublicationInstanceFromProfessorPublicationDto(
						professorPublicationDto, professor, category));
	}

	public PublicationCategory initializeProfessorPublicationCategory(
			@Nonnull ProfessorPublicationDto professorPublicationDto) {

		PublicationCategory category = null;
		if (professorPublicationDto.getPublicationCategoryDto() != null
				&& professorPublicationDto.getPublicationCategoryDto().getId() != null) {
			category = publicationCategoryRepository.findOne(professorPublicationDto.getPublicationCategoryDto()
					.getId());
		}

		return category;
	}

	public ProfessorPublication createOrUpdateProfessorPublicationInstanceFromProfessorPublicationDto(
			@Nonnull ProfessorPublicationDto professorPublicationDto, @Nonnull Professor professor,
			@Nullable PublicationCategory category) {

		ProfessorPublication professorPublication = null;
		if (professorPublicationDto.getId() == null) {
			professorPublication = new ProfessorPublication();
			professorPublication.setProfessor(professor);
		} else {
			professorPublication = professorPublicationsRepository.findOne(professorPublicationDto.getId());
		}
		professorPublication.setPublicationCategory(category);
		professorPublication.setIsbn(professorPublicationDto.getIsbn());
		professorPublication.setTitle(professorPublicationDto.getTitle());
		professorPublication.setJournalTitle(professorPublicationDto.getJournalTitle());
		professorPublication.setAuthors(professorPublicationDto.getAuthors());
		professorPublication.setPublisher(professorPublicationDto.getPublisher());
		professorPublication.setPageRange(professorPublicationDto.getPageRange());
		professorPublication.setPublicationType(publicationTypeRepository.findByTypeName(professorPublicationDto
				.getPublicationType()));
		professorPublication.setQuoted(professorPublicationDto.getQuoted());
		professorPublication.setYear(professorPublicationDto.getYear());

		return professorPublication;
	}

	@Override
	@Transactional
	public void saveInternationalPublication(InternationalPublicationDto internationalPublicationDto)
			throws PublicationNotFoundException, ProfessorNotFoundException, PublicationCategoryNotFoundException {

		Professor professor = professorRepository.findOne(internationalPublicationDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		PublicationCategory category = initializeInternationalPublicationCategory(internationalPublicationDto);
		internationalPublicationsRepository
				.saveAndFlush(createOrUpdateInternationalPublicationInstanceFromInternationalPublicationDto(
						internationalPublicationDto, professor, category));
	}

	public PublicationCategory initializeInternationalPublicationCategory(
			@Nonnull InternationalPublicationDto internationalPublicationDto) {

		PublicationCategory category = null;
		if (internationalPublicationDto.getPublicationCategoryDto() != null
				&& internationalPublicationDto.getPublicationCategoryDto().getId() != null) {
			category = publicationCategoryRepository.findOne(internationalPublicationDto.getPublicationCategoryDto()
					.getId());
		}

		return category;
	}

	public InternationalPublication createOrUpdateInternationalPublicationInstanceFromInternationalPublicationDto(
			@Nonnull InternationalPublicationDto internationalPublicationDto, @Nonnull Professor professor,
			@Nullable PublicationCategory category) {

		InternationalPublication internationalPublication = null;
		if (internationalPublicationDto.getId() == null) {
			internationalPublication = new InternationalPublication();
			internationalPublication.setProfessor(professor);
		} else {
			internationalPublication = internationalPublicationsRepository.findOne(internationalPublicationDto.getId());
		}
		internationalPublication.setPublicationCategory(category);
		internationalPublication.setIsbn(internationalPublicationDto.getIsbn());
		internationalPublication.setTitle(internationalPublicationDto.getTitle());
		internationalPublication.setJournalTitle(internationalPublicationDto.getJournalTitle());
		internationalPublication.setAuthors(internationalPublicationDto.getAuthors());
		internationalPublication.setPublisher(internationalPublicationDto.getPublisher());
		internationalPublication.setPagesWithQuotes(internationalPublicationDto.getPagesWithQuotes());
		internationalPublication.setPublicationType(publicationTypeRepository
				.findByTypeName(internationalPublicationDto.getPublicationType()));
		internationalPublication.setYear(internationalPublicationDto.getYear());

		return internationalPublication;
	}

	@Override
	@Transactional
	public void deleteProfessorPublication(Long id) throws PublicationNotFoundException {

		ProfessorPublication professorPublication = professorPublicationsRepository.findOne(id);
		if (professorPublication == null) {
			throw new PublicationNotFoundException();
		}

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
		if (internationalPublication == null) {
			throw new PublicationNotFoundException();
		}

		internationalPublicationsRepository.delete(internationalPublication);
	}

}
