package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Subject;
import com.pct.domain.TeachingExperience;
import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.SubjectRepository;
import com.pct.repository.TeachingExperienceRepository;
import com.pct.service.TeachingExperienceService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.TeachingExperienceNotFoundException;

@Service
public class TeachingExperienceServiceImpl implements TeachingExperienceService {

	@Autowired
	private TeachingExperienceRepository teachingExperienceRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Autowired
	private SubjectRepository subjectRepository;

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

	@Override
	@Transactional
	public void saveTeachingExperience(TeachingExperienceDto teachingExperienceDto)
			throws TeachingExperienceNotFoundException, ProfessorNotFoundException, SubjectNotFoundException {

		Professor professor = professorRepository.findOne(teachingExperienceDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		Subject subject = null;
		try {
			subject = subjectRepository.findOne(teachingExperienceDto.getSubjectDto().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (subject == null) {
			throw new SubjectNotFoundException();
		}

		teachingExperienceRepository.saveAndFlush(createOrUpdateTeachingExperienceInstanceFromTeachingExperienceDto(
				teachingExperienceDto, professor, subject));
	}

	private TeachingExperience createOrUpdateTeachingExperienceInstanceFromTeachingExperienceDto(
			@Nonnull TeachingExperienceDto teachingExperienceDto, @Nonnull Professor professor, @Nonnull Subject subject) {

		TeachingExperience teachingExperience = null;
		if (teachingExperienceDto.getId() == null) {
			teachingExperience = new TeachingExperience();
			teachingExperience.setProfessor(professor);
		} else {
			teachingExperience = teachingExperienceRepository.findOne(teachingExperienceDto.getId());
		}
		teachingExperience.setSubject(subject);

		return teachingExperience;
	}

}
