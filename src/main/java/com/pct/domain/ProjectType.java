package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "project_type")
public class ProjectType extends AbstractEntity {

	private static final long serialVersionUID = -6433196433131428806L;

	@Column(name = "typeName", length = 50)
	private String typeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projectType")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "projectType")
	@JsonIgnore
	private Set<Project> projects = new HashSet<Project>();

	public ProjectType() {
	}

	public ProjectType(String typeName, Set<Project> projects) {
		this.typeName = typeName;
		this.projects = projects;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

}
