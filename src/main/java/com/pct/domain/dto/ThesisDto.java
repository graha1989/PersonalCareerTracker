package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

public class ThesisDto implements Serializable {

	private static final long serialVersionUID = 7888227200149085172L;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String title;
	
	@NotEmpty
	@Length(max = 10)
	@SafeHtml
	private String studentTranscriptNumber;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String studentName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String studentSurname;
	
	@NotEmpty
	@Length(max = 152)
	@SafeHtml
	private String mergedStudentData = studentTranscriptNumber + " " + studentName + " " + studentSurname;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String mentorName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String mentorSurname;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionPesidentName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionPesidentSurname;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionMemberName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionMemberSurname;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String paperScientificArea;
	
	private Date dateOfGraduation;
	
	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String universityName;
	
	protected Long id;

	public ThesisDto() {
		super();
	}

	public ThesisDto(String title, String studentTranscriptNumber, String studentName, String studentSurname,
			String mentorName, String mentorSurname, String commissionPesidentName, String commissionPesidentSurname,
			String commissionMemberName, String commissionMemberSurname, String paperScientificArea,
			Date dateOfGraduation, String universityName) {
		super();
		this.title = title;
		this.studentTranscriptNumber = studentTranscriptNumber;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.mentorName = mentorName;
		this.mentorSurname = mentorSurname;
		this.commissionPesidentName = commissionPesidentName;
		this.commissionPesidentSurname = commissionPesidentSurname;
		this.commissionMemberName = commissionMemberName;
		this.commissionMemberSurname = commissionMemberSurname;
		this.paperScientificArea = paperScientificArea;
		this.dateOfGraduation = dateOfGraduation;
		this.universityName = universityName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudentTranscriptNumber() {
		return studentTranscriptNumber;
	}

	public void setStudentTranscriptNumber(String studentTranscriptNumber) {
		this.studentTranscriptNumber = studentTranscriptNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}

	public String getMentorName() {
		return mentorName;
	}

	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public String getMentorSurname() {
		return mentorSurname;
	}

	public void setMentorSurname(String mentorSurname) {
		this.mentorSurname = mentorSurname;
	}

	public String getCommissionPesidentName() {
		return commissionPesidentName;
	}

	public void setCommissionPesidentName(String commissionPesidentName) {
		this.commissionPesidentName = commissionPesidentName;
	}

	public String getCommissionPesidentSurname() {
		return commissionPesidentSurname;
	}

	public void setCommissionPesidentSurname(String commissionPesidentSurname) {
		this.commissionPesidentSurname = commissionPesidentSurname;
	}

	public String getCommissionMemberName() {
		return commissionMemberName;
	}

	public void setCommissionMemberName(String commissionMemberName) {
		this.commissionMemberName = commissionMemberName;
	}

	public String getCommissionMemberSurname() {
		return commissionMemberSurname;
	}

	public void setCommissionMemberSurname(String commissionMemberSurname) {
		this.commissionMemberSurname = commissionMemberSurname;
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
