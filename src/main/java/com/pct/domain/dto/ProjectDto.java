package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.Project;
import com.pct.domain.enums.ProjectType;
import com.pct.domain.enums.deserializers.ProjectTypeEnumDeserializer;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 7801612436888907315L;

	@NotNull
	@JsonDeserialize(using = ProjectTypeEnumDeserializer.class)
	private ProjectType projectType;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String name;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String financedBy;
	
	@NotEmpty
	@Length(max = 400)
	@SafeHtml
	private String projectLeader;
	
	@Nullable
	protected Long id;

	public ProjectDto() {
	}

	public ProjectDto(ProjectType projectType, String name, String financedBy, String projectLeader, Long id) {
		super();
		this.projectType = projectType;
		this.name = name;
		this.financedBy = financedBy;
		this.projectLeader = projectLeader;
		this.id = id;
	}
	
	public ProjectDto(Project project) {
		this.projectType = project.getProjectType();
		this.name = project.getName();
		this.financedBy = project.getFinancedBy();
		this.projectLeader = project.getProjectLeader();
		this.id = project.getId();
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
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

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
