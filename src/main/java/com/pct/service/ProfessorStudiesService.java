package com.pct.service;

import java.util.List;

import com.pct.domain.dto.StudiesDto;
import com.pct.domain.enums.StudyProgram;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorStudiesNotFoundException;
import com.pct.validation.StudiesThesisTypeNotFoundException;

public interface ProfessorStudiesService {

	List<StudiesDto> findAllStudies(Long professorId, Long thesisTypeId) throws ProfessorStudiesNotFoundException;

	List<StudyProgram> findAllStudyPrograms();

	void saveProfessorStudies(StudiesDto studiesDto) throws ProfessorStudiesNotFoundException,
			ProfessorNotFoundException, InstitutionNotFoundException, StudiesThesisTypeNotFoundException;

	void deleteStudies(Long id) throws ProfessorStudiesNotFoundException;

}
