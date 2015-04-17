package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

public class StudiesDto implements Serializable {

	private static final long serialVersionUID = 7796970503021149480L;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String studyProgram;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String studyArea;

	private Date studyStartDate;

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

	@Nullable
	@Length(max = 200)
	@SafeHtml
	private String specialisticStudiesStayPurpose;

	protected Long professorId;

	protected Long institutionId;

	protected Long thesisTypeId;

	public StudiesDto() {
		super();
	}

	public StudiesDto(String studyProgram, String studyArea, Date studyStartDate, Date studyEndDate,
			Double averageGrade, String thesisTitle, String acquiredTitle, String specialisticStudiesStayPurpose,
			Long professorId, Long institutionId, Long thesisTypeId) {
		super();
		this.studyProgram = studyProgram;
		this.studyArea = studyArea;
		this.studyStartDate = studyStartDate;
		this.studyEndDate = studyEndDate;
		this.averageGrade = averageGrade;
		this.thesisTitle = thesisTitle;
		this.acquiredTitle = acquiredTitle;
		this.specialisticStudiesStayPurpose = specialisticStudiesStayPurpose;
		this.professorId = professorId;
		this.institutionId = institutionId;
		this.thesisTypeId = thesisTypeId;
	}

	public String getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(String studyProgram) {
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

	public String getSpecialisticStudiesStayPurpose() {
		return specialisticStudiesStayPurpose;
	}

	public void setSpecialisticStudiesStayPurpose(String specialisticStudiesStayPurpose) {
		this.specialisticStudiesStayPurpose = specialisticStudiesStayPurpose;
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
