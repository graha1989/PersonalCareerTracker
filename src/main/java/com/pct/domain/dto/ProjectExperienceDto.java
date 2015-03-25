package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.ProjectExperience;
import com.pct.domain.enums.ProjectType;

public class ProjectExperienceDto implements Serializable {

	private static final long serialVersionUID = 1565666662058101537L;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String projectName;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String projectFinancedBy;
	
	protected Long projectId;
	
	protected Long professorId;
	
	@NotNull
	private ProjectType projectType;
	
	private Date projectStartDate;
	
	private Date projectEndDate;
	
	@NotEmpty
	@Length(max = 400)
	@SafeHtml
	private String projectLeader;
	
	protected Long id;

	public ProjectExperienceDto() {
		super();
	}

	public ProjectExperienceDto(String projectName, String projectFinancedBy, Long projectId, Long professorId,
			ProjectType projectType, Date projectStartDate, Date projectEndDate, String projectLeader, Long id) {
		super();
		this.projectName = projectName;
		this.projectFinancedBy = projectFinancedBy;
		this.projectId = projectId;
		this.professorId = professorId;
		this.projectType = projectType;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectLeader = projectLeader;
		this.id = id;
	}
	
	public ProjectExperienceDto(ProjectExperience p) {
		this.projectName = p.getProject().getName();
		this.projectFinancedBy = p.getProject().getFinancedBy();
		this.projectId = p.getProject().getId();
		this.professorId = p.getProfessor().getId();
		this.projectType = p.getProject().getProjectType();
		this.projectStartDate = p.getProject().getProjectStartDate();
		this.projectEndDate = p.getProject().getProjectEndDate();
		this.projectLeader = p.getProject().getProjectLeader();
		this.id = p.getId();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectFinancedBy() {
		return projectFinancedBy;
	}

	public void setProjectFinancedBy(String projectFinancedBy) {
		this.projectFinancedBy = projectFinancedBy;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
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
