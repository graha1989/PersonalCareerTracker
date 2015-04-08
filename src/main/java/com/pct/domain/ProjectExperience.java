package com.pct.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project_experience")
public class ProjectExperience extends AbstractEntity {

	private static final long serialVersionUID = -4439523340893948274L;

	@ManyToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "projectId")
	@JsonBackReference(value = "project")
	private Project project;
	
	private boolean professorLeader;

	public ProjectExperience() {
		super();
	}
	
	public ProjectExperience(Professor professor, Project project, boolean professorLeader) {
		super();
		this.professor = professor;
		this.project = project;
		this.professorLeader = professorLeader;
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

	public boolean isProfessorLeader() {
		return professorLeader;
	}

	public void setProfessorLeader(boolean professorLeader) {
		this.professorLeader = professorLeader;
	}

}
