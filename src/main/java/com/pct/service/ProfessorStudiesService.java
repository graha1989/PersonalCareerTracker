package com.pct.service;

import java.util.List;

import com.pct.domain.dto.StudiesDto;
import com.pct.validation.ProfessorStudiesNotFoundException;

public interface ProfessorStudiesService {

	List<StudiesDto> findAllStudies(Long professorId, Long thesisTypeId) throws ProfessorStudiesNotFoundException;

}
