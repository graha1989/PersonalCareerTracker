package com.pct.service;

import java.util.List;

import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;

public interface ThesisService {
	
	List<ThesisDto> findAllBachelorThesis(Long id);

	List<ThesisType> findAllThesisType();
	
}
