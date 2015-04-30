package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.TeachingExperience;
import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.repository.TeachingExperienceRepository;
import com.pct.service.TeachingExperienceService;
import com.pct.validation.TeachingExperienceNotFoundException;

@Service
public class TeachingExperienceServiceImpl implements TeachingExperienceService {

	@Autowired
	private TeachingExperienceRepository teachingExperienceRepository;

	@Override
	@Transactional
	public List<TeachingExperienceDto> findAllTeachingExperiences(Long professorId)
			throws TeachingExperienceNotFoundException {

		List<TeachingExperienceDto> teachingExperienceDtos = new ArrayList<TeachingExperienceDto>();
		try {
			List<TeachingExperience> teachingExperiences = teachingExperienceRepository
					.findAllTeachingExperiences(professorId);
			for (TeachingExperience e : teachingExperiences) {
				TeachingExperienceDto teachingExperienceDto = new TeachingExperienceDto(e);
				teachingExperienceDtos.add(teachingExperienceDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return teachingExperienceDtos;
	}

	@Override
	@Transactional
	public TeachingExperienceDto findTeachingExperienceById(Long id) throws TeachingExperienceNotFoundException {

		TeachingExperienceDto teachingExperienceDto;
		if (id != null) {
			TeachingExperience teachingExperience = teachingExperienceRepository.findOne(id);
			if (teachingExperience != null) {
				teachingExperienceDto = new TeachingExperienceDto(teachingExperience);
				return teachingExperienceDto;
			}
		}

		throw new TeachingExperienceNotFoundException();
	}

}
