package com.pct.service;

import java.util.List;

import com.pct.domain.dto.SubjectDto;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.SubjectDeleteException;
import com.pct.validation.SubjectNotFoundException;

public interface SubjectService {
	
	List<SubjectDto> findAvailableSubjectsStartsWith(String value, Boolean seminarOrTeachingAbroad);

	List<SubjectDto> findAllSubjectsOrSeminars(Boolean seminarOrTeachingAbroad);

	SubjectDto findSubjectById(Long id) throws SubjectNotFoundException;

	void saveSubject(SubjectDto subjectDto) throws InstitutionNotFoundException, SubjectNotFoundException;
	
	void deleteSubject(Long id) throws SubjectNotFoundException, SubjectDeleteException;

}
