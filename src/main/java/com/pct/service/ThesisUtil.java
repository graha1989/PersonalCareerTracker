package com.pct.service;

import com.pct.domain.Thesis;
import com.pct.domain.dto.ThesisDto;

/**
 * Thesis utility class.
 * 
 * @author a.grahovac
 * 
 */
public class ThesisUtil {
	
	// EDIT Thesis
	public static Thesis createThesisInstanceFromThesisDto(ThesisDto thesisDto) {
		
		Thesis thesis = createNewThesisInstanceFromThesisiDto(thesisDto);
		thesis.setId(thesisDto.getId());

		return thesis;
	}
	
	// CREATE new Thesis
	public static Thesis createNewThesisInstanceFromThesisiDto(ThesisDto thesisDto) {
		
		Thesis thesis = new Thesis();
		
		thesis.setTitle(thesisDto.getTitle());
		thesis.setPaperScientificArea(thesisDto.getPaperScientificArea());
		thesis.setDateOfGraduation(thesisDto.getDateOfGraduation());
		thesis.setUniversityName(thesisDto.getUniversityName());
		thesis.setThesisType(thesisDto.getThesisType());
		thesis.setStudent(thesisDto.getStudent());
		thesis.setMentor(thesisDto.getMentor());
		thesis.setCommissionPresident(thesisDto.getCommissionPresident());
		thesis.setCommissionMember(thesisDto.getCommissionMember());
		
		return thesis;
	}

}
