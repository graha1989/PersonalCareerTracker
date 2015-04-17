package com.pct.service;

import java.util.List;

import com.pct.domain.StudiesThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.StudentNotFoundException;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.ThesisTypeNotFoundException;

public interface ThesisService {
	
	List<ThesisDto> findAllThesis(Long mentorId, Long thesisTypeId);

	List<StudiesThesisType> findAllThesisType();

	ThesisDto saveThesis(ThesisDto thesisDto) throws ProfessorNotFoundException, ThesisTypeNotFoundException, StudentNotFoundException;

	StudiesThesisType findThesisTypeById(Long id) throws ThesisTypeNotFoundException;

	void deleteThesis(Long id) throws ThesisNotFoundException;

	ThesisDto findThesisById(Long id) throws ThesisNotFoundException;
	
}
