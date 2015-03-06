package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Thesis;

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

	protected Long studentId;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String mentorName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String mentorSurname;

	protected Long mentorId;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionPesidentName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionPesidentSurname;

	protected Long commissionPesidentId;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionMemberName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String commissionMemberSurname;

	protected Long commissionMemberId;

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
			Long studentId, String mentorName, String mentorSurname, Long mentorId, String commissionPesidentName,
			String commissionPesidentSurname, Long commissionPesidentId, String commissionMemberName,
			String commissionMemberSurname, Long commissionMemberId, String paperScientificArea, Date dateOfGraduation,
			String universityName, Long id) {
		super();
		this.title = title;
		this.studentTranscriptNumber = studentTranscriptNumber;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.studentId = studentId;
		this.mentorName = mentorName;
		this.mentorSurname = mentorSurname;
		this.mentorId = mentorId;
		this.commissionPesidentName = commissionPesidentName;
		this.commissionPesidentSurname = commissionPesidentSurname;
		this.commissionPesidentId = commissionPesidentId;
		this.commissionMemberName = commissionMemberName;
		this.commissionMemberSurname = commissionMemberSurname;
		this.commissionMemberId = commissionMemberId;
		this.paperScientificArea = paperScientificArea;
		this.dateOfGraduation = dateOfGraduation;
		this.universityName = universityName;
		this.id = id;
	}

	public ThesisDto(Thesis thesis) {
		this.title = thesis.getTitle();
		this.studentTranscriptNumber = thesis.getStudent().getTranscriptNumber();
		this.studentName = thesis.getStudent().getName();
		this.studentSurname = thesis.getStudent().getSurname();
		this.studentId = thesis.getStudent().getId();
		this.mentorName = thesis.getMentor().getName();
		this.mentorSurname = thesis.getMentor().getSurname();
		this.mentorId = thesis.getMentor().getId();
		this.commissionPesidentName = thesis.getCommissionPesident().getName();
		this.commissionPesidentSurname = thesis.getCommissionPesident().getSurname();
		this.commissionPesidentId = thesis.getCommissionPesident().getId();
		this.commissionMemberName = thesis.getCommissionMember().getName();
		this.commissionMemberSurname = thesis.getCommissionMember().getSurname();
		this.commissionMemberId = thesis.getCommissionMember().getId();
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
	}

	public Long getCommissionPesidentId() {
		return commissionPesidentId;
	}

	public void setCommissionPesidentId(Long commissionPesidentId) {
		this.commissionPesidentId = commissionPesidentId;
	}

	public Long getCommissionMemberId() {
		return commissionMemberId;
	}

	public void setCommissionMemberId(Long commissionMemberId) {
		this.commissionMemberId = commissionMemberId;
	}
}
