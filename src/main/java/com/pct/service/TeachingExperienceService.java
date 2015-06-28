package com.pct.service;

import java.util.List;

import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.TeachingExperienceNotFoundException;

public interface TeachingExperienceService {

	List<TeachingExperienceDto> findAllTeachingExperiences(Long professorId, Boolean seminarOrTeachingAbroad)
			throws TeachingExperienceNotFoundException;

	TeachingExperienceDto findTeachingExperienceById(Long id) throws TeachingExperienceNotFoundException;

	void saveTeachingExperience(TeachingExperienceDto teachingExperienceDto) throws TeachingExperienceNotFoundException, ProfessorNotFoundException,
			SubjectNotFoundException, SimilarDataAlreadyExistsException;

	void deleteTeachingExperience(Long id) throws TeachingExperienceNotFoundException;

}
