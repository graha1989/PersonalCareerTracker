package com.pct.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pct.domain.enums.ProjectType;

@Entity
@Table(name = "project")
public class Project extends AbstractEntity {

	private static final long serialVersionUID = -8558884874336561177L;
	
	@Column(name = "name", length = 200)
	private String name;
	
	@Column(name = "financedBy", length = 200)
	private String financedBy;
	
	@Column(name = "projectStartDate")
	private Date projectStartDate;
	
	@Column(name = "projectEndDate")
	private Date projectEndDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "projectType")
	private ProjectType projectType;
	
	@Column(name = "projectLeader")
	@Lob
	private String projectLeader;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	private Set<ProjectExperience> projectExperiences;
	
	public Project() {
		super();
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

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	
	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
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
	
	public void addProjectExperiences(ProjectExperience projectExperience) {
		if (projectExperiences != null) {
			this.projectExperiences.add(projectExperience);
		}
	}

}
