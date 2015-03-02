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
	@JsonBackReference
	private Profesor mentor;
	
	@OneToOne
	@JoinColumn(name = "commissionMemberId")
	@JsonBackReference
	private Profesor commissionMember;
	
	@OneToOne
	@JoinColumn(name = "commissionPesidentId")
	@JsonBackReference
	private Profesor commissionPesident;

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

	public void setTipZavrsnogRada(ThesisType thesisType) {
		this.thesisType = thesisType;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Profesor getMentor() {
		return mentor;
	}

	public void setMentor(Profesor mentor) {
		this.mentor = mentor;
	}

	public Profesor getCommissionMember() {
		return commissionMember;
	}

	public void setCommissionMember(Profesor commissionMember) {
		this.commissionMember = commissionMember;
	}

	public Profesor getCommissionPesident() {
		return commissionPesident;
	}

	public void setCommissionPesident(Profesor commissionPesident) {
		this.commissionPesident = commissionPesident;
	}
	
}
