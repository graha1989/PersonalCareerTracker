package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Survey;

public class SurveyDto implements Serializable {

	private static final long serialVersionUID = 2359287174605186845L;

	@NotEmpty
	@Length(max = 9)
	@SafeHtml
	private String academicYear;

	@NotNull
	private Double averageGrade;

	@NotNull
	@Min(1)
	private Integer numberOfStudents;

	@Nullable
	protected Long subjectId;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public SurveyDto() {
	}

	public SurveyDto(String academicYear, Double averageGrade, Integer numberOfStudents, Long subjectId,
			Long professorId, Long id) {
		super();
		this.academicYear = academicYear;
		this.averageGrade = averageGrade;
		this.numberOfStudents = numberOfStudents;
		this.subjectId = subjectId;
		this.professorId = professorId;
		this.id = id;
	}

	public SurveyDto(Survey survey) {
		this.academicYear = survey.getAcademicYear();
		this.averageGrade = survey.getAverageGrade();
		this.numberOfStudents = survey.getNumberOfStudents();
		this.subjectId = survey.getSubject().getId();
		this.professorId = survey.getProfessor().getId();
		this.id = survey.getId();
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

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
