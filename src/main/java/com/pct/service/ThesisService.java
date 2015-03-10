package com.pct.service;

import java.util.List;

import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.ThesisTypeNotFoundException;

public interface ThesisService {
	
	List<ThesisDto> findAllThesis(Long mentorId, Long thesisTypeId);

	List<ThesisType> findAllThesisType();

	ThesisDto saveThesis(ThesisDto thesisDto);

	ThesisType findThesisTypeById(Long id) throws ThesisTypeNotFoundException;

	void deleteThesis(Long id) throws ThesisNotFoundException;

	ThesisDto findThesisById(Long id) throws ThesisNotFoundException;
	
}
