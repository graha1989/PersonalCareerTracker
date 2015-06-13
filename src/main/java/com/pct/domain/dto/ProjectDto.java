package com.pct.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Project;
import com.pct.domain.ProjectLeader;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 7801612436888907315L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String projectType;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String name;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String financedBy;

	List<ProjectLeaderDto> projectLeaderDtos;

	@Nullable
	protected Long id;

	public ProjectDto() {
	}

	public ProjectDto(String projectType, String name, String financedBy, Long id,
			List<ProjectLeaderDto> projectLeaderDtos) {
		this.projectType = projectType;
		this.name = name;
		this.financedBy = financedBy;
		this.id = id;
		this.projectLeaderDtos = projectLeaderDtos;
	}

	public ProjectDto(Project project) {
		this.projectType = project.getProjectType().getTypeName();
		this.name = project.getName();
		this.financedBy = project.getFinancedBy();
		this.id = project.getId();
		this.projectLeaderDtos = createProjectLeadersDtoList(project.getProjectLeaders());
	}

	private List<ProjectLeaderDto> createProjectLeadersDtoList(Set<ProjectLeader> projectLeaders) {
		List<ProjectLeaderDto> projectLeaderDtos = new ArrayList<ProjectLeaderDto>();
		for (ProjectLeader projectLeader : projectLeaders) {
			projectLeaderDtos.add(new ProjectLeaderDto(projectLeader));
		}
		return projectLeaderDtos;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinancedBy() {
		return financedBy;
	}

	public void setFinancedBy(String financedBy) {
		this.financedBy = financedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProjectLeaderDto> getProjectLeaderDtos() {
		return projectLeaderDtos;
	}

	public void setProjectLeaderDtos(List<ProjectLeaderDto> projectLeaderDtos) {
		this.projectLeaderDtos = projectLeaderDtos;
	}

}
