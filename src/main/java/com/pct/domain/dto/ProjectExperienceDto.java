package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.pct.domain.ProjectExperience;

public class ProjectExperienceDto implements Serializable {

	private static final long serialVersionUID = 1565666662058101537L;

	@NotNull
	private Date startDate;

	private Date endDate;

	@NotNull
	protected Long projectId;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public ProjectExperienceDto() {
	}

	public ProjectExperienceDto(Date startDate, Date endDate, Long projectId, Long professorId, Long id) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectId = projectId;
		this.professorId = professorId;
		this.id = id;
	}

	public ProjectExperienceDto(ProjectExperience projectExperience) {
		this.startDate = projectExperience.getStartDate();
		this.endDate = projectExperience.getEndDate();
		this.projectId = projectExperience.getProject().getId();
		this.professorId = projectExperience.getProfessor().getId();
		this.id = projectExperience.getId();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
