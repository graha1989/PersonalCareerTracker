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
	public static Profesor createProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO profesorFormaDTO) {

		Profesor profesor = createNewProfesorInstanceFromProfesorFormaDTO(profesorFormaDTO);
		profesor.setId(profesorFormaDTO.getId());

		return profesor;
	}

	// CREATE new Profesor
	public static Profesor createNewProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO profesorFormaDTO) {

		Profesor profesor = new Profesor();
		
		profesor.setUserName(profesorFormaDTO.getUserName());
		profesor.setPassword(profesorFormaDTO.getPassword());
		profesor.setEmail(profesorFormaDTO.getEmail());
		
		profesor.setName(profesorFormaDTO.getName());
		profesor.setSurname(profesorFormaDTO.getSurname());
		profesor.setFathersName(profesorFormaDTO.getFathersName());
		profesor.setDateOfBirth(profesorFormaDTO.getDateOfBirth());
		profesor.setPlaceOfBirth(profesorFormaDTO.getPlaceOfBirth());
		profesor.setCountryOfBirth(profesorFormaDTO.getCountryOfBirth());
		profesor.setScientificArea(profesorFormaDTO.getScientificArea());
		profesor.setSpecialScientificArea(profesorFormaDTO.getSpecialScientificArea());

		return profesor;
	}

}
