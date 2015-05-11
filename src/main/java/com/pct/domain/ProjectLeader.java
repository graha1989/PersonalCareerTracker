package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project_leader")
public class ProjectLeader extends AbstractEntity {

	private static final long serialVersionUID = -8149758813572407334L;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "projectId")
	@JsonBackReference(value = "project")
	private Project project;

	/* For project leaders outside faculty (database) */
	@Column(name = "name", length = 50)
	private String name;
	
	/* For project leaders outside faculty (database) */
	@Column(name = "surname", length = 50)
	private String surname;

	public ProjectLeader() {
	}

	public ProjectLeader(Professor professor, Project project, String name, String surname) {
		super();
		this.professor = professor;
		this.project = project;
		this.name = name;
		this.surname = surname;
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

}
