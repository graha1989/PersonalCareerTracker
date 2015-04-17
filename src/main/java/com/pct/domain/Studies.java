package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "studies")
public class Studies extends AbstractEntity {

	private static final long serialVersionUID = -4928359091482665806L;
	
	@ManyToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "institutionId")
	@JsonBackReference(value = "institution")
	private Institution institution;
	
	@OneToOne
	@JoinColumn(name = "studiesThesisTypeId")
	private StudiesThesisType studiesThesisType;
	
	@Column(name = "studyProgram", length = 100)
	private String studyProgram;
	
	@Column(name = "studyArea", length = 100)
	private String studyArea;
	
	@Column(name = "studyStartDate")
	private Date studyStartDate;
	
	@Column(name = "studyEndDate")
	private Date studyEndDate;
	
	@Column(name = "averageGrade", columnDefinition = "Decimal(4,2)")
	private Double averageGrade;
	
	@Column(name = "thesisTitle", length = 200)
	private String thesisTitle;
	
	@Column(name = "acquiredTitle", length = 30)
	private String acquiredTitle;
	
	@Column(name = "specialisticStudiesStayPurpose", length = 200)
	private String specialisticStudiesStayPurpose;

	public Studies() {
		super();
	}

	public Studies(Professor professor, Institution institution, StudiesThesisType studiesThesisType,
			String studyProgram, String studyArea, Date studyStartDate, Date studyEndDate, Double averageGrade,
			String thesisTitle, String acquiredTitle, String specialisticStudiesStayPurpose) {
		super();
		this.professor = professor;
		this.institution = institution;
		this.studiesThesisType = studiesThesisType;
		this.studyProgram = studyProgram;
		this.studyArea = studyArea;
		this.studyStartDate = studyStartDate;
		this.studyEndDate = studyEndDate;
		this.averageGrade = averageGrade;
		this.thesisTitle = thesisTitle;
		this.acquiredTitle = acquiredTitle;
		this.specialisticStudiesStayPurpose = specialisticStudiesStayPurpose;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public StudiesThesisType getStudiesThesisType() {
		return studiesThesisType;
	}

	public void setStudiesThesisType(StudiesThesisType studiesThesisType) {
		this.studiesThesisType = studiesThesisType;
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

}
