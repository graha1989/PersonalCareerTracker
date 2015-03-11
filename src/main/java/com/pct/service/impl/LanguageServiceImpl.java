package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Language;
import com.pct.domain.LanguageExperience;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.repository.LanguageExperienceRepository;
import com.pct.repository.LanguageRepository;
import com.pct.service.LanguageExperienceUtil;
import com.pct.service.LanguageService;
import com.pct.validation.LanguageNotFoundException;

@Service
public class LanguageServiceImpl implements LanguageService {
	
	@Autowired
	private LanguageExperienceRepository languageRepository;
	
	@Autowired
	private LanguageRepository languageRepo;
	
	@Override
	@Transactional
	public List<LanguageExperienceDto> findAllLanguageExperiences(Long mentorId) {

		List<LanguageExperienceDto> languageDtoList = new ArrayList<LanguageExperienceDto>();

		List<LanguageExperience> languageList = languageRepository.findAllLanguages(mentorId);
		for (LanguageExperience l : languageList) {
			LanguageExperienceDto languageDto = new LanguageExperienceDto(l);
			languageDtoList.add(languageDto);
		}
		return languageDtoList;

	}

	@Override
	@Transactional
	public LanguageExperienceDto saveLanguageExperience(LanguageExperienceDto languageExperienceDto) {
		
		LanguageExperience languageExperience = new LanguageExperience();

		if (languageExperienceDto.getId() != null) {
			languageExperience = LanguageExperienceUtil.createLanguageExperienceInstanceFromLanguageExperienceDto(languageExperienceDto);
		} else {
			languageExperience = LanguageExperienceUtil.createNewLanguageExperienceInstanceFromLanguageExperienceDto(languageExperienceDto);
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

}
