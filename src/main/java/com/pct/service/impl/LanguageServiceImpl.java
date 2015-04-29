package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Language;
import com.pct.domain.LanguageExperience;
import com.pct.domain.Professor;
import com.pct.domain.dto.LanguageDto;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.repository.LanguageExperienceRepository;
import com.pct.repository.LanguageRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.LanguageService;
import com.pct.validation.LanguageExperienceNotFoundException;
import com.pct.validation.LanguageNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageExperienceRepository languageRepository;

	@Autowired
	private LanguageRepository languageRepo;

	@Autowired
	private ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<LanguageExperienceDto> findAllLanguageExperiences(Long mentorId) {

		List<LanguageExperienceDto> languageDtoList = new ArrayList<LanguageExperienceDto>();
		try {
			List<LanguageExperience> languageList = languageRepository.findAllLanguages(mentorId);
			for (LanguageExperience l : languageList) {
				LanguageExperienceDto languageDto = new LanguageExperienceDto(l);
				languageDtoList.add(languageDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return languageDtoList;
	}

	@Override
	@Transactional
	public void saveLanguageExperience(LanguageExperienceDto languageExperienceDto) throws LanguageNotFoundException,
			ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(languageExperienceDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}
		Language language = languageRepo.findOne(languageExperienceDto.getLanguageId());
		if (language == null) {
			throw new LanguageNotFoundException();
		}

		languageRepository.saveAndFlush(createOrUpdateLanguageExperienceInstanceFromLanguageExperienceDto(
				languageExperienceDto, professor, language));
	}

	public LanguageExperience createOrUpdateLanguageExperienceInstanceFromLanguageExperienceDto(
			@Nonnull LanguageExperienceDto languageExperienceDto, @Nonnull Professor professor,
			@Nonnull Language language) {

		LanguageExperience languageExperience = null;
		if (languageExperienceDto.getId() == null) {
			languageExperience = new LanguageExperience();
			languageExperience.setProfessor(professor);
			languageExperience.setLanguage(language);
		} else {
			languageExperience = languageRepository.findOne(languageExperienceDto.getId());
		}
		languageExperience.setReading(languageExperienceDto.isReading());
		languageExperience.setWriting(languageExperienceDto.isWriting());
		languageExperience.setPronouncing(languageExperienceDto.isPronouncing());

		return languageExperience;
	}

	@Override
	@Transactional
	public Language findLanguageById(Long id) throws LanguageNotFoundException {

		Language language;

		if (id == null || languageRepo.findOne(id) == null) {
			throw new LanguageNotFoundException();
		} else {
			language = languageRepo.findOne(id);
		}

		return language;
	}

	@Override
	@Transactional
	public List<LanguageDto> findAllNotListedLanguages(List<Long> languageExperienceIdsList)
			throws LanguageNotFoundException {

		List<LanguageDto> languageDtoList = new ArrayList<LanguageDto>();

		List<Language> languageList = languageRepo.findAllNotListedLanguages(languageExperienceIdsList);
		for (Language l : languageList) {
			LanguageDto languageDto = new LanguageDto(l);
			languageDto.setId(l.getId());
			languageDtoList.add(languageDto);
		}

		return languageDtoList;
	}

	@Override
	@Transactional
	public void deleteLanguageExperience(@Nonnull Long id) throws LanguageExperienceNotFoundException {
		
		LanguageExperience languageExperience = languageRepository.findOne(id);
		if (languageExperience == null) {
			throw new LanguageExperienceNotFoundException();
		}
		
		languageRepository.delete(languageExperience);
	}

}
