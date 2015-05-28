package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ProfessorDto;
import com.pct.validation.EmailExistException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNameExistException;

public interface ProfessorService {
	
	ProfessorDto findProfesorByUserName(String userName) throws ProfessorNotFoundException;
	
	ProfessorDto findProfesorById(Long id) throws ProfessorNotFoundException;

	void saveProfesor(ProfessorDto ProfessorDto) throws UserNameExistException, EmailExistException;

	List<ProfessorDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor) throws ProfessorNotFoundException;

	List<ProfessorDto> findAllProfessors();

}
