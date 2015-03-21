package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.pct.service.LanguageExperienceUtil;
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
	public LanguageExperienceDto saveLanguageExperience(LanguageExperienceDto languageExperienceDto)
			throws LanguageNotFoundException, ProfessorNotFoundException {

		LanguageExperience languageExperience = new LanguageExperience();
		Language language = new Language();
		Professor professor = new Professor();

		if (languageExperienceDto.getLanguageId() == null
				|| languageRepo.findOne(languageExperienceDto.getLanguageId()) == null) {
			throw new LanguageNotFoundException();
		} else {
			language = languageRepo.findOne(languageExperienceDto.getLanguageId());
		}

		if (languageExperienceDto.getProfessorId() == null
				|| professorRepository.findOne(languageExperienceDto.getProfessorId()) == null) {
			throw new ProfessorNotFoundException();
		} else {
			professor = professorRepository.findOne(languageExperienceDto.getProfessorId());
		}

		if (languageExperienceDto.getId() != null) {
			languageExperience = LanguageExperienceUtil.createLanguageExperienceInstanceFromLanguageExperienceDto(
					languageExperienceDto, language, professor);
		} else {
			languageExperience = LanguageExperienceUtil.createNewLanguageExperienceInstanceFromLanguageExperienceDto(
					languageExperienceDto, language, professor);
		}

		return new LanguageExperienceDto(languageRepository.save(languageExperience));

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
	public List<LanguageDto> findAllNotListedLanguages(List<Long> languageExperienceIdsList) throws LanguageNotFoundException {

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
	public void deleteLanguageExperience(Long id) throws LanguageExperienceNotFoundException {
		
		if (id == null || languageRepository.findOne(id) == null) {
			throw new LanguageExperienceNotFoundException();
		}

		languageRepository.delete(id);
		
	}

}
