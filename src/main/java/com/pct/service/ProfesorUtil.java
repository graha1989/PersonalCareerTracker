package com.pct.service;

import com.pct.domain.Profesor;
import com.pct.domain.dto.ProfesorFormaDTO;

/**
 * User(Profesor) utility class.
 * 
 * @author a.grahovac
 * 
 */
public class ProfesorUtil {

	// EDIT Profesor
	public static Profesor createProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO ProfesorFormaDTO) {

		Profesor profesor = createNewProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO);
		profesor.setId(ProfesorFormaDTO.getId());

		return profesor;
	}

	// CREATE new Profesor
	public static Profesor createNewProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO ProfesorFormaDTO) {

		Profesor profesor = new Profesor();
		
		profesor.setUserName(ProfesorFormaDTO.getUserName());
		profesor.setPassword(ProfesorFormaDTO.getPassword());
		profesor.setEmail(ProfesorFormaDTO.getEmail());
		
		profesor.setName(ProfesorFormaDTO.getName());
		profesor.setSurname(ProfesorFormaDTO.getSurname());
		profesor.setFathersName(ProfesorFormaDTO.getFathersName());
		profesor.setDateOfBirth(ProfesorFormaDTO.getDateOfBirth());
		profesor.setPlaceOfBirth(ProfesorFormaDTO.getPlaceOfBirth());
		profesor.setCountryOfBirth(ProfesorFormaDTO.getCountryOfBirth());
		profesor.setScientificArea(ProfesorFormaDTO.getScientificArea());
		profesor.setSpecialScientificArea(ProfesorFormaDTO.getSpecialScientificArea());

		return profesor;
	}

}
