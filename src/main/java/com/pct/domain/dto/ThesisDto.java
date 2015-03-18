package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.constants.RegexPatterns;
import com.pct.domain.Thesis;

public class ThesisDto implements Serializable {

	private static final long serialVersionUID = 7888227200149085172L;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String title;

	protected Long studentId;
	
	@NotEmpty
	@Length(max = 10)
	@SafeHtml
	private String studentTranscriptNumber;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String studentName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String studentSurname;
	
	protected Long commissionPresidentId;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String commissionPresidentName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String commissionPresidentSurname;
	
	protected Long commissionMemberId;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String commissionMemberName;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String commissionMemberSurname;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String paperScientificArea;
	
	protected Long mentorId;
	
	private Date dateOfGraduation;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String universityName;
	
	protected Long thesisTypeId;
	
	protected Long id;

	public ThesisDto() {
		super();
	}
	
	public ThesisDto(String title, Long studentId, String studentTranscriptNumber, String studentName,
			String studentSurname, Long commissionPresidentId, String commissionPresidentName,
			String commissionPresidentSurname, Long commissionMemberId, String commissionMemberName,
			String commissionMemberSurname, String paperScientificArea, Long mentorId, Date dateOfGraduation,
			String universityName, Long thesisTypeId, Long id) {
		super();
		this.title = title;
		this.studentId = studentId;
		this.studentTranscriptNumber = studentTranscriptNumber;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
		this.commissionPresidentId = commissionPresidentId;
		this.commissionPresidentName = commissionPresidentName;
		this.commissionPresidentSurname = commissionPresidentSurname;
		this.commissionMemberId = commissionMemberId;
		this.commissionMemberName = commissionMemberName;
		this.commissionMemberSurname = commissionMemberSurname;
		this.paperScientificArea = paperScientificArea;
		this.mentorId = mentorId;
		this.dateOfGraduation = dateOfGraduation;
		this.universityName = universityName;
		this.thesisTypeId = thesisTypeId;
		this.id = id;
	}



	public ThesisDto(Thesis thesis) {
		this.title = thesis.getTitle();
		this.studentId = thesis.getStudent().getId();
		this.studentTranscriptNumber = thesis.getStudent().getTranscriptNumber();
		this.studentName = thesis.getStudent().getName();
		this.studentSurname = thesis.getStudent().getSurname();
		this.commissionPresidentId = thesis.getCommissionPresident().getId();
		this.commissionPresidentName = thesis.getCommissionPresident().getName();
		this.commissionPresidentSurname = thesis.getCommissionPresident().getSurname();
		this.commissionMemberId = thesis.getCommissionMember().getId();
		this.commissionMemberName = thesis.getCommissionMember().getName();
		this.commissionMemberSurname = thesis.getCommissionMember().getSurname();
		this.paperScientificArea = thesis.getPaperScientificArea();
		this.mentorId = thesis.getMentor().getId();
		this.dateOfGraduation = thesis.getDateOfGraduation();
		this.universityName = thesis.getUniversityName();
		this.thesisTypeId = thesis.getThesisType().getId();
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public Long getCommissionPresidentId() {
		return commissionPresidentId;
	}

	public void setCommissionPresidentId(Long commissionPresidentId) {
		this.commissionPresidentId = commissionPresidentId;
	}

	public String getCommissionPresidentName() {
		return commissionPresidentName;
	}

	public void setCommissionPresidentName(String commissionPresidentName) {
		this.commissionPresidentName = commissionPresidentName;
	}

	public String getCommissionPresidentSurname() {
		return commissionPresidentSurname;
	}

	public void setCommissionPresidentSurname(String commissionPresidentSurname) {
		this.commissionPresidentSurname = commissionPresidentSurname;
	}

	public Long getCommissionMemberId() {
		return commissionMemberId;
	}

	public void setCommissionMemberId(Long commissionMemberId) {
		this.commissionMemberId = commissionMemberId;
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

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
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

	public Long getThesisTypeId() {
		return thesisTypeId;
	}

	public void setThesisTypeId(Long thesisTypeId) {
		this.thesisTypeId = thesisTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
