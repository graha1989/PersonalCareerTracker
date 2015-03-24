package com.pct.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project_experience")
public class ProjectExperience extends AbstractEntity {

	private static final long serialVersionUID = -353179734183526053L;
	
	@ManyToOne
	@JoinColumn(name = "profesorId")
	@JsonBackReference
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "projectId")
	@JsonBackReference
	private Project project;

	public ProjectExperience() {
		super();
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
