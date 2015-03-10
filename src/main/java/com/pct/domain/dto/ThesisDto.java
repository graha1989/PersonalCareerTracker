package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Profesor;
import com.pct.domain.Student;
import com.pct.domain.Thesis;
import com.pct.domain.ThesisType;

public class ThesisDto implements Serializable {

	private static final long serialVersionUID = 7888227200149085172L;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String title;

	@NotNull
	private Student student;
	
	@NotNull
	private Profesor mentor;
	
	@NotNull
	private Profesor commissionPresident;
	
	@NotNull
	private Profesor commissionMember;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String paperScientificArea;

	private Date dateOfGraduation;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String universityName;
	
	@NotNull
	private ThesisType thesisType;
	
	protected Long id;

	public ThesisDto() {
		super();
	}

	public ThesisDto(String title, Student student, Profesor mentor, Profesor commissionPresident,
			Profesor commissionMember, String paperScientificArea, Date dateOfGraduation, String universityName,
			ThesisType thesisType, Long id) {
		super();
		this.title = title;
		this.student = student;
		this.mentor = mentor;
		this.commissionPresident = commissionPresident;
		this.commissionMember = commissionMember;
		this.paperScientificArea = paperScientificArea;
		this.dateOfGraduation = dateOfGraduation;
		this.universityName = universityName;
		this.thesisType = thesisType;
		this.id = id;
	}

	public ThesisDto(Thesis thesis) {
		this.title = thesis.getTitle();
		this.student = thesis.getStudent();
		this.mentor = thesis.getMentor();
		this.commissionPresident = thesis.getCommissionPresident();
		this.commissionMember = thesis.getCommissionMember();
		this.thesisType = thesis.getThesisType();
		this.paperScientificArea = thesis.getPaperScientificArea();
		this.dateOfGraduation = thesis.getDateOfGraduation();
		this.universityName = thesis.getUniversityName();
		this.id = thesis.getId();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Profesor getCommissionPresident() {
		return commissionPresident;
	}

	public void setCommissionPresident(Profesor commissionPresident) {
		this.commissionPresident = commissionPresident;
	}

	public Profesor getCommissionMember() {
		return commissionMember;
	}

	public void setCommissionMember(Profesor commissionMember) {
		this.commissionMember = commissionMember;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
