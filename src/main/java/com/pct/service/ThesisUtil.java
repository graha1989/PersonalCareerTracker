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
		
		return thesis;
	}

}
