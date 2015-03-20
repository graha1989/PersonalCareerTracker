package com.pct.service;

import com.pct.domain.Profesor;
import com.pct.domain.dto.ProfesorFormaDto;

/**
 * User(Profesor) utility class.
 * 
 * @author a.grahovac
 * 
 */
public class ProfesorUtil {

	// EDIT Profesor
	public static Profesor createProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDto profesorFormaDto) {

		Profesor profesor = createNewProfesorInstanceFromProfesorFormaDTO(profesorFormaDto);
		profesor.setId(profesorFormaDto.getId());

		return profesor;
	}

	// CREATE new Profesor
	public static Profesor createNewProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDto profesorFormaDto) {

		Profesor profesor = new Profesor();
		
		profesor.setUserName(profesorFormaDto.getUserName());
		profesor.setPassword(profesorFormaDto.getPassword());
		profesor.setEmail(profesorFormaDto.getEmail());
		
		profesor.setName(profesorFormaDto.getName());
		profesor.setSurname(profesorFormaDto.getSurname());
		profesor.setFathersName(profesorFormaDto.getFathersName());
		profesor.setDateOfBirth(profesorFormaDto.getDateOfBirth());
		profesor.setPlaceOfBirth(profesorFormaDto.getPlaceOfBirth());
		profesor.setCountryOfBirth(profesorFormaDto.getCountryOfBirth());
		profesor.setScientificArea(profesorFormaDto.getScientificArea());
		profesor.setSpecialScientificArea(profesorFormaDto.getSpecialScientificArea());

		return profesor;
	}

}
