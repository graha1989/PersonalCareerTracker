package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.Studies;
import com.pct.domain.enums.StudyProgram;
import com.pct.domain.enums.deserializers.StudyProgramEnumDeserializer;

public class StudiesDto implements Serializable {

	private static final long serialVersionUID = 7796970503021149480L;
	
	@Nullable
	@JsonDeserialize(using = StudyProgramEnumDeserializer.class)
	private StudyProgram studyProgram;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String studyArea;

	@NotNull
	private Date studyStartDate;

	@NotNull
	private Date studyEndDate;

	@NotNull
	private Double averageGrade;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String thesisTitle;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String acquiredTitle;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String facultyName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String universityName;

	@NotNull
	protected Long professorId;

	protected Long institutionId;

	protected Long thesisTypeId;

	public StudiesDto() {
	}

	public StudiesDto(StudyProgram studyProgram, String studyArea, Date studyStartDate, Date studyEndDate,
			Double averageGrade, String thesisTitle, String acquiredTitle, String specialisticStudiesStayPurpose,
			String facultyName, String universityName, Long professorId, Long institutionId, Long thesisTypeId) {
		super();
		this.studyProgram = studyProgram;
		this.studyArea = studyArea;
		this.studyStartDate = studyStartDate;
		this.studyEndDate = studyEndDate;
		this.averageGrade = averageGrade;
		this.thesisTitle = thesisTitle;
		this.acquiredTitle = acquiredTitle;
		this.facultyName = facultyName;
		this.universityName = universityName;
		this.professorId = professorId;
		this.institutionId = institutionId;
		this.thesisTypeId = thesisTypeId;
	}

	public StudiesDto(Studies studies) {
		super();
		this.studyProgram = studies.getStudyProgram();
		this.studyArea = studies.getStudyArea();
		this.studyStartDate = studies.getStudyStartDate();
		this.studyEndDate = studies.getStudyEndDate();
		this.averageGrade = studies.getAverageGrade();
		this.thesisTitle = studies.getThesisTitle();
		this.acquiredTitle = studies.getAcquiredTitle();
		this.facultyName = studies.getInstitution().getName();
		this.universityName = studies.getInstitution().getUniversity();
		this.professorId = studies.getProfessor().getId();
		this.institutionId = studies.getInstitution().getId();
		this.thesisTypeId = studies.getStudiesThesisType().getId();
	}

	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	public String getStudyArea() {
		return studyArea;
	}

	public void setStudyArea(String studyArea) {
		this.studyArea = studyArea;
	}

	public Date getStudyStartDate() {
		return studyStartDate;
	}

	public void setStudyStartDate(Date studyStartDate) {
		this.studyStartDate = studyStartDate;
	}

	public Date getStudyEndDate() {
		return studyEndDate;
	}

	public void setStudyEndDate(Date studyEndDate) {
		this.studyEndDate = studyEndDate;
	}

	public Double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public String getThesisTitle() {
		return thesisTitle;
	}

	public void setThesisTitle(String thesisTitle) {
		this.thesisTitle = thesisTitle;
	}

	public String getAcquiredTitle() {
		return acquiredTitle;
	}

	public void setAcquiredTitle(String acquiredTitle) {
		this.acquiredTitle = acquiredTitle;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getThesisTypeId() {
		return thesisTypeId;
	}

	public void setThesisTypeId(Long thesisTypeId) {
		this.thesisTypeId = thesisTypeId;
	}

}
