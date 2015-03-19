package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ProfesorFormaDTO;
import com.pct.validation.ProfesorNotFoundException;

public interface ProfesorService {
	
	ProfesorFormaDTO findProfesorByUserName(String userName) throws ProfesorNotFoundException;
	
	ProfesorFormaDTO findProfesorById(Long id) throws ProfesorNotFoundException;

	void saveProfesor(ProfesorFormaDTO ProfesorFormaDTO);

	List<ProfesorFormaDTO> findProfessorsStartsWith(String value, Long idProf, Long idMentor) throws ProfesorNotFoundException;
	
}
