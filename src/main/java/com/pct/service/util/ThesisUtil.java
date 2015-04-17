package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.Student;
import com.pct.domain.Thesis;
import com.pct.domain.StudiesThesisType;
import com.pct.domain.dto.ThesisDto;

/**
 * Thesis utility class.
 * 
 * @author a.grahovac
 * 
 */
public class ThesisUtil {
	
	// EDIT Thesis
	public static Thesis createThesisInstanceFromThesisDto(ThesisDto thesisDto, Student student, Professor mentor, Professor commissionPresident, Professor commissionMember, StudiesThesisType studiesThesisType) {
		
		Thesis thesis = createNewThesisInstanceFromThesisiDto(thesisDto, student, mentor, commissionPresident, commissionMember, studiesThesisType);
		thesis.setId(thesisDto.getId());

		return thesis;
	}
	
	// CREATE new Thesis
	public static Thesis createNewThesisInstanceFromThesisiDto(ThesisDto thesisDto, Student student, Professor mentor, Professor commissionPresident, Professor commissionMember, StudiesThesisType studiesThesisType) {
		
		Thesis thesis = new Thesis();
		
		thesis.setTitle(thesisDto.getTitle());
		thesis.setPaperScientificArea(thesisDto.getPaperScientificArea());
		thesis.setDateOfGraduation(thesisDto.getDateOfGraduation());
		thesis.setUniversityName(thesisDto.getUniversityName());
		thesis.setThesisType(studiesThesisType);
		thesis.setStudent(student);
		thesis.setMentor(mentor);
		thesis.setCommissionPresident(commissionPresident);
		thesis.setCommissionMember(commissionMember);
		
		return thesis;
	}

}
