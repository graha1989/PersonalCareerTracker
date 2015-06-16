package com.pct.domain;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "teaching_experience")
public class TeachingExperience extends AbstractEntity {

	private static final long serialVersionUID = 5777696461582944238L;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "subjectId")
	@JsonBackReference(value = "subject")
	private Subject subject;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	@Column(name = "teachingStartDate")
	@NotNull
	private Date teachingStartDate;

	@Column(name = "teachingEndDate")
	@Nullable
	private Date teachingEndDate;

	@Nullable
	@Column(name = "seminarOrTeachingAbroad")
	private Boolean seminarOrTeachingAbroad;

	public TeachingExperience() {
	}

	public TeachingExperience(Subject subject, Professor professor, Date teachingStartDate, Date teachingEndDate,
			Boolean seminarOrTeachingAbroad) {
		this.subject = subject;
		this.professor = professor;
		this.teachingStartDate = teachingStartDate;
		this.teachingEndDate = teachingEndDate;
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Date getTeachingStartDate() {
		return teachingStartDate;
	}

	public void setTeachingStartDate(Date teachingStartDate) {
		this.teachingStartDate = teachingStartDate;
	}

	public Date getTeachingEndDate() {
		return teachingEndDate;
	}

	public void setTeachingEndDate(Date teachingEndDate) {
		this.teachingEndDate = teachingEndDate;
	}

	public Boolean getSeminarOrTeachingAbroad() {
		return seminarOrTeachingAbroad;
	}

	public void setSeminarOrTeachingAbroad(Boolean seminarOrTeachingAbroad) {
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}
	
}
