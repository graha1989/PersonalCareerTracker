package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.ProjectLeader;

public class ProjectLeaderDto implements Serializable {

	private static final long serialVersionUID = 4120093768265239823L;

	@Length(max = 50)
	@SafeHtml
	private String name;

	@Length(max = 50)
	@SafeHtml
	private String surname;

	@Nullable
	protected Long professorId;

	@Nullable
	protected Long projectId;

	@Nullable
	protected Long id;

	public ProjectLeaderDto() {
	}

	public ProjectLeaderDto(String name, String surname, Long professorId, Long projectId, Long id) {
		super();
		this.name = name;
		this.surname = surname;
		this.professorId = professorId;
		this.projectId = projectId;
		this.id = id;
	}

	public ProjectLeaderDto(ProjectLeader projectLeader) {
		super();
		this.name = projectLeader.getName();
		this.surname = projectLeader.getSurname();
		this.professorId = (projectLeader.getProfessor() != null ? projectLeader.getProfessor().getId() : null);
		this.projectId = projectLeader.getProject().getId();
		this.id = projectLeader.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
