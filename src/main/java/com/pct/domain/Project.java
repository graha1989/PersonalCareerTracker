package com.pct.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
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
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade= CascadeType.ALL)
	private Set<ProjectExperience> projectExperiences;
	
	public Project() {
		super();
		this.projectExperiences = new HashSet<ProjectExperience>();
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
	
	public ProjectExperience getProjectExperienceById(Long id) {
		Iterator<ProjectExperience> it = projectExperiences.iterator();
		while (it.hasNext()) {
			ProjectExperience projectExperience = (ProjectExperience) it.next();
			if (projectExperience.getId().equals(id)) {
				return projectExperience;
			}
		}
		return null;
	}
	
	public void addProjectExperiences(ProjectExperience projectExperience) {
		if (projectExperiences != null) {
			projectExperience.setProject(this);
			this.projectExperiences.add(projectExperience);
		}
	}

	public ProjectExperience creatreNewProjectExperience(Professor professor, boolean professorLeader) {
		
		ProjectExperience projectExperience = new ProjectExperience(professor, this, professorLeader);
		professor.addProjectExperiences(projectExperience);
		this.projectExperiences.add(projectExperience);
		
		return projectExperience;
	}
	
	public ProjectExperience editProjectExperience(Professor professor, boolean professorLeader, Long id) {
		
		ProjectExperience projectExperience = getProjectExperienceById(id);
		projectExperience.setProfessorLeader(professorLeader);
		professor.addProjectExperiences(projectExperience);
		
		return projectExperience;
	}
	
}
