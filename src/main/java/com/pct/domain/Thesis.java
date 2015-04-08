package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "thesis")
public class Thesis extends AbstractEntity {

	private static final long serialVersionUID = -7699926800771668693L;
	
	@Column(name = "title", length = 200)
	private String title;
	
	@Column(name = "paperScientificArea", length = 50)
	private String paperScientificArea;
	
	@Column(name = "dateOfGraduation")
	private Date dateOfGraduation;
	
	@Column(name = "universityName", length = 100)
	private String universityName;
	
	@OneToOne
	@JoinColumn(name = "finalPaperTypeId")
	private ThesisType thesisType;
	
	@OneToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@OneToOne
	@JoinColumn(name = "mentorId")
	@JsonBackReference(value = "mentor")
	private Professor mentor;
	
	@OneToOne
	@JoinColumn(name = "commissionMemberId")
	@JsonBackReference(value = "commissionMember")
	private Professor commissionMember;
	
	@OneToOne
	@JoinColumn(name = "commissionPresidentId")
	@JsonBackReference(value = "commissionPresident")
	private Professor commissionPresident;

	public Thesis() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaperScientificArea() {
		return paperScientificArea;
	}

	public void setPaperScientificArea(String paperScientificArea) {
		this.paperScientificArea = paperScientificArea;
	}

	public Date getDateOfGraduation() {
		return dateOfGraduation;
	}

	public void setDateOfGraduation(Date dateOfGraduation) {
		this.dateOfGraduation = dateOfGraduation;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public ThesisType getThesisType() {
		return thesisType;
	}

	public void setThesisType(ThesisType thesisType) {
		this.thesisType = thesisType;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Professor getMentor() {
		return mentor;
	}

	public void setMentor(Professor mentor) {
		this.mentor = mentor;
	}

	public Professor getCommissionMember() {
		return commissionMember;
	}

	public void setCommissionMember(Professor commissionMember) {
		this.commissionMember = commissionMember;
	}

	public Professor getCommissionPresident() {
		return commissionPresident;
	}

	public void setCommissionPresident(Professor commissionPresident) {
		this.commissionPresident = commissionPresident;
	}
	
}
