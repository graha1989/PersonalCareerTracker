package com.pct.domain;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "work_experience")
public class WorkExperience extends AbstractEntity {

	private static final long serialVersionUID = 1803668535106768471L;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "institutionId")
	@JsonBackReference(value = "institution")
	private Institution institution;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	@Column(name = "workStartDate")
	private Date workStartDate;

	@Column(name = "workEndDate")
	@Nullable
	private Date workEndDate;

	@Column(name = "title", length = 30)
	private String title;

	public WorkExperience() {
	}

	public WorkExperience(Institution institution, Professor professor, Date workStartDate, Date workEndDate,
			String title) {
		this.institution = institution;
		this.professor = professor;
		this.workStartDate = workStartDate;
		this.workEndDate = workEndDate;
		this.title = title;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
