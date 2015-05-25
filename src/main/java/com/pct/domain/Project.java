package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.enums.ProjectType;
import com.pct.domain.enums.deserializers.ProjectTypeEnumDeserializer;

@Entity
@Table(name = "project")
public class Project extends AbstractEntity {

	private static final long serialVersionUID = 6035835341873602474L;

	@Column(name = "name", length = 200)
	private String name;

	@Column(name = "financedBy", length = 200)
	private String financedBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "projectType")
	@JsonDeserialize(using = ProjectTypeEnumDeserializer.class)
	private ProjectType projectType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	@Cascade(CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "project")
	@JsonIgnore
	private Set<ProjectExperience> projectExperiences = new HashSet<ProjectExperience>();

	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL })
	@JoinTable(name = "project_leader_intersecting_table", joinColumns = @JoinColumn(name = "projectId"), inverseJoinColumns = @JoinColumn(name = "leaderId"))
	private Set<ProjectLeader> projectLeaders = new HashSet<ProjectLeader>();

	public Project() {
	}

	public Project(String name, String financedBy, ProjectType projectType, Set<ProjectExperience> projectExperiences,
			Set<ProjectLeader> projectLeaders) {
		super();
		this.name = name;
		this.financedBy = financedBy;
		this.projectType = projectType;
		this.projectExperiences = projectExperiences;
		this.projectLeaders = projectLeaders;
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

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public Set<ProjectExperience> getProjectExperiences() {
		return projectExperiences;
	}

	public void setProjectExperiences(Set<ProjectExperience> projectExperiences) {
		this.projectExperiences.clear();

		if (projectExperiences != null) {
			this.projectExperiences.addAll(projectExperiences);
		}
	}

	public Set<ProjectLeader> getProjectLeaders() {
		return projectLeaders;
	}

	public void setProjectLeaders(Set<ProjectLeader> projectLeaders) {
		this.projectLeaders.clear();

		if (projectLeaders != null) {
			this.projectLeaders.addAll(projectLeaders);
		}
	}

}
