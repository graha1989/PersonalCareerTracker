package com.pct.service.util;

import com.pct.domain.Institution;
import com.pct.domain.Professor;
import com.pct.domain.WorkExperience;
import com.pct.domain.dto.WorkExperienceDto;

public class WorkExperienceUtil {

	public static WorkExperience createOrUpdateWorkExperienceInstanceFromWorkExperienceDto(
			WorkExperienceDto workExperienceDto, Professor professor, Institution institution) {

		WorkExperience workExperience;

		institution.setInstitutionType(workExperienceDto.getInstitutionType());
		institution.setName(workExperienceDto.getInstitutionName());
		institution.setCity(workExperienceDto.getInstitutionCity());
		institution.setCountry(workExperienceDto.getInstitutionCountry());

		if (workExperienceDto.getId() == null) {
			workExperience = professor.creatreNewWorkExperience(institution, workExperienceDto.getWorkStartDate(),
					workExperienceDto.getWorkEndDate(), workExperienceDto.getTitle());
		} else {
			workExperience = professor.editWorkExperience(institution, workExperienceDto.getWorkStartDate(),
					workExperienceDto.getWorkEndDate(), workExperienceDto.getTitle(), workExperienceDto.getId());
		}

		return workExperience;

	}
}
