package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

}
