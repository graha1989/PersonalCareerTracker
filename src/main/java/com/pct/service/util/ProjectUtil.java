package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.Project;
import com.pct.domain.ProjectExperience;
import com.pct.domain.dto.ProjectExperienceDto;

public class ProjectUtil {
	
	public static ProjectExperience createProjectExperienceInstanceFromProjectExperienceDto(
			ProjectExperienceDto projectExperienceDto, Professor professor, Project project) {
		
		ProjectExperience projectExperience = createNewProjectExperienceInstanceFromProjectExperienceDto(projectExperienceDto, professor, project);
		projectExperience.setId(projectExperienceDto.getId());

		return projectExperience;
	}
	
	public static ProjectExperience createNewProjectExperienceInstanceFromProjectExperienceDto(
			ProjectExperienceDto projectExperienceDto, Professor professor, Project project) {
		
		ProjectExperience projectExperience = new ProjectExperience();
		
		project.setName(projectExperienceDto.getProjectName());
		project.setFinancedBy(projectExperienceDto.getProjectFinancedBy());
		project.setProjectStartDate(projectExperienceDto.getProjectStartDate());
		project.setProjectEndDate(projectExperienceDto.getProjectEndDate());
		project.setProjectType(projectExperienceDto.getProjectType());
		project.setProjectLeader(project.getProjectLeader());
		
		projectExperience.setProfessor(professor);
		projectExperience.setProject(project);
		
		return projectExperience;
	}

}
