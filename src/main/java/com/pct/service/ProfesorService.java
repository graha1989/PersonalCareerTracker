package com.pct.service;

import com.pct.domain.dto.ProfesorFormaDTO;
import com.pct.validation.ProfesorNotFoundException;

public interface ProfesorService {
	
	ProfesorFormaDTO findProfesorByUserName(String userName) throws ProfesorNotFoundException;
	
	ProfesorFormaDTO findProfesorById(Long id) throws ProfesorNotFoundException;

	void saveProfesor(ProfesorFormaDTO ProfesorFormaDTO);
	
}
