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
@Table(name = "survey")
public class Survey extends AbstractEntity {

	private static final long serialVersionUID = -1927708070179366849L;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "subjectId")
	@JsonBackReference(value = "subject")
	private Subject subject;

	@Column(name = "academicYear", length = 9)
	private String academicYear;

	@Column(name = "averageGrade", columnDefinition = "Decimal(4,2)")
	private Double averageGrade;

	@Column(name = "numberOfStudents")
	private Integer numberOfStudents;

	public Survey() {
	}

	public Survey(Professor professor, Subject subject, String academicYear, Double averageGrade,
			Integer numberOfStudents) {
		super();
		this.professor = professor;
		this.subject = subject;
		this.academicYear = academicYear;
		this.averageGrade = averageGrade;
		this.numberOfStudents = numberOfStudents;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public Integer getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(Integer numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

}
