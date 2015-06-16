package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SubjectDto;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.SubjectNotFoundException;

public interface SubjectService {
	
	List<SubjectDto> findAvailableSubjectsStartsWith(String value, List<Long> subjectIds, Boolean seminarOrTeachingAbroad);

	List<SubjectDto> findAllSubjects();

	SubjectDto findSubjectById(Long id) throws SubjectNotFoundException;

	void saveSubject(SubjectDto subjectDto) throws InstitutionNotFoundException, SubjectNotFoundException;

}
