package com.pct.service.util;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.dto.WorkExperienceDto;

public class WorkExperienceUtil {

	public static Institution createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(
			WorkExperienceDto workExperienceDto, Professor professor, Institution institution) {

		institution.setInstitutionType(workExperienceDto.getInstitutionType());
		institution.setName(workExperienceDto.getInstitutionName());
		institution.setCity(workExperienceDto.getInstitutionCity());
		institution.setCountry(workExperienceDto.getInstitutionCountry());

		if (workExperienceDto.getId() == null) {
			institution.creatreNewWorkExperience(professor, workExperienceDto.getWorkStartDate(),
					workExperienceDto.getWorkEndDate(), workExperienceDto.getTitle());
		} else {
			institution.editWorkExperience(professor, workExperienceDto.getWorkStartDate(),
					workExperienceDto.getWorkEndDate(), workExperienceDto.getTitle(), workExperienceDto.getId());
		}

		return institution;

	}
}
