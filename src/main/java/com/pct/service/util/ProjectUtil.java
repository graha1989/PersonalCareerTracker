package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.Project;
import com.pct.domain.dto.ProjectExperienceDto;

public class ProjectUtil {

	public static Project createOrUpdateProjectInstanceFromProjectExperienceDto(
			ProjectExperienceDto projectExperienceDto, Professor professor, Project project) {

		project.setName(projectExperienceDto.getProjectName());
		project.setFinancedBy(projectExperienceDto.getProjectFinancedBy());
		project.setProjectStartDate(projectExperienceDto.getProjectStartDate());
		project.setProjectEndDate(projectExperienceDto.getProjectEndDate());
		project.setProjectType(projectExperienceDto.getProjectType());
		project.setProjectLeader(projectExperienceDto.getProjectLeader());

		if (projectExperienceDto.getId() == null) {
			project.creatreNewProjectExperience(professor, projectExperienceDto.isProfessorLeader());
		} else {
			project.editProjectExperience(professor, projectExperienceDto.isProfessorLeader(),
					projectExperienceDto.getId());
		}

		return project;
	}

}
