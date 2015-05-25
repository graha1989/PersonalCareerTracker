package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project_leader")
public class ProjectLeader extends AbstractEntity {

	private static final long serialVersionUID = -4242038483732113166L;

	@OneToOne(optional = true)
	@JoinColumn(name = "professorId", nullable = true)
	@JsonBackReference(value = "professor")
	private Professor professor;

	@ManyToMany(mappedBy = "projectLeaders", fetch = FetchType.EAGER)
	@JsonBackReference(value = "project")
	private Set<Project> projects = new HashSet<Project>();

	/* For project leaders outside faculty (database) */
	@Column(name = "name", length = 50)
	private String name;

	/* For project leaders outside faculty (database) */
	@Column(name = "surname", length = 50)
	private String surname;

	public ProjectLeader() {
	}

	public ProjectLeader(Professor professor, String name, String surname, Set<Project> projects) {
		super();
		this.professor = professor;
		this.name = name;
		this.surname = surname;
		this.projects = projects;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects.clear();

		if (projects != null) {
			this.projects.addAll(projects);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ProjectLeader) {
			ProjectLeader projectLeader = (ProjectLeader) object;
			return new EqualsBuilder().append(professor, projectLeader.getProfessor())
					.append(projects, projectLeader.getProjects()).append(name, projectLeader.getName())
					.append(surname, projectLeader.getSurname()).isEquals()
					&& super.equals(object);
		}
		return false;
	}

}
