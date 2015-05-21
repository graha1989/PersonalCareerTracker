package com.pct.service;

import java.util.List;

import com.pct.domain.dto.PersonDto;
import com.pct.domain.dto.ProfessorDto;
import com.pct.validation.ProfessorNotFoundException;

public interface ProfessorService {
	
	ProfessorDto findProfesorByUserName(String userName) throws ProfessorNotFoundException;
	
	ProfessorDto findProfesorById(Long id) throws ProfessorNotFoundException;

	void saveProfesor(ProfessorDto ProfessorDto);

	List<ProfessorDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor) throws ProfessorNotFoundException;

	List<ProfessorDto> findAllProfessors();

	List<PersonDto> findProfessorsStartsWith(String value, Long projectId);
	
}
