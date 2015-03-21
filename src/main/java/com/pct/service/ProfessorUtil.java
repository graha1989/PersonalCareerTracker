package com.pct.service;

import com.pct.domain.Professor;
import com.pct.domain.dto.ProfessorDto;

/**
 * User(Professor) utility class.
 * 
 * @author a.grahovac
 * 
 */
public class ProfessorUtil {

	// EDIT Professor
	public static Professor createProfesorInstanceFromProfesorFormaDTO(ProfessorDto professorDto) {

		Professor professor = createNewProfesorInstanceFromProfesorFormaDTO(professorDto);
		professor.setId(professorDto.getId());

		return professor;
	}

	// CREATE new Professor
	public static Professor createNewProfesorInstanceFromProfesorFormaDTO(ProfessorDto professorDto) {

		Professor professor = new Professor();
		
		professor.setUserName(professorDto.getUserName());
		professor.setPassword(professorDto.getPassword());
		professor.setEmail(professorDto.getEmail());
		
		professor.setName(professorDto.getName());
		professor.setSurname(professorDto.getSurname());
		professor.setFathersName(professorDto.getFathersName());
		professor.setDateOfBirth(professorDto.getDateOfBirth());
		professor.setPlaceOfBirth(professorDto.getPlaceOfBirth());
		professor.setCountryOfBirth(professorDto.getCountryOfBirth());
		professor.setScientificArea(professorDto.getScientificArea());
		professor.setSpecialScientificArea(professorDto.getSpecialScientificArea());

		return professor;
	}

}
