package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zavrsni_rad")
public class ZavrsniRad extends AbstractEntity {

	private static final long serialVersionUID = -7699926800771668693L;
	
	@Column(name = "title", length = 200)
	private String title;
	
	@Column(name = "paperScientificArea", length = 50)
	private String paperScientificArea;
	
	@Column(name = "dateOfGraduation")
	private Date dateOfGraduation;
	
	@Column(name = "universityName", length = 100)
	private String universityName;
	
	@ManyToOne
	@JoinColumn(name = "finalPaperTypeId")
	private TipZavrsnogRada tipZavrsnogRada;
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "mentorId")
	private Profesor mentor;
	
	@ManyToOne
	@JoinColumn(name = "commissionMemberId")
	private Profesor commissionMember;
	
	@ManyToOne
	@JoinColumn(name = "commissionPesidentId")
	private Profesor commissionPesident;

	public ZavrsniRad() {
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

	public TipZavrsnogRada getTipZavrsnogRada() {
		return tipZavrsnogRada;
	}

	public void setTipZavrsnogRada(TipZavrsnogRada tipZavrsnogRada) {
		this.tipZavrsnogRada = tipZavrsnogRada;
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
