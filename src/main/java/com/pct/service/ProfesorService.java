package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ProfesorFormaDto;
import com.pct.validation.ProfesorNotFoundException;

public interface ProfesorService {
	
	ProfesorFormaDto findProfesorByUserName(String userName) throws ProfesorNotFoundException;
	
	ProfesorFormaDto findProfesorById(Long id) throws ProfesorNotFoundException;

	void saveProfesor(ProfesorFormaDto ProfesorFormaDto);

	List<ProfesorFormaDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor) throws ProfesorNotFoundException;
	
}
