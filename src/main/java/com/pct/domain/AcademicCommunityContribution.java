package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pct.domain.enums.AcademicCommunityContributionType;

@Entity
@Table(name = "academic_community_contribution")
public class AcademicCommunityContribution extends AbstractEntity {

	private static final long serialVersionUID = 5542921970596466993L;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private AcademicCommunityContributionType type;

	@Column(name = "authorityOrganizationOrJournal", length = 200)
	private String authorityOrganizationOrJournal;

	@Column(name = "institutionType", length = 20, nullable = true)
	private String institutionType;

	@Column(name = "authorityOrOrganizationWorkStartDate", nullable = true)
	private Date authorityOrOrganizationWorkStartDate;

	@Column(name = "authorityOrOrganizationWorkEndDate", nullable = true)
	private Date authorityOrOrganizationWorkEndDate;

	@Column(name = "functionInOrganizationConferenceOrCommittee", length = 40, nullable = true)
	private String functionInOrganizationConferenceOrCommittee;

	@Column(name = "meetingConferenceOrEventYear", length = 4, nullable = true)
	private String meetingConferenceOrEventYear;

	@Column(name = "journalCategory", length = 3, nullable = true)
	private String journalCategory;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	public AcademicCommunityContribution() {
	}

	public AcademicCommunityContribution(AcademicCommunityContributionType type, String authorityOrganizationOrJournal, String institutionType,
			Date authorityOrOrganizationWorkStartDate, Date authorityOrOrganizationWorkEndDate, String functionInOrganizationConferenceOrCommittee,
			String meetingConferenceOrEventYear, String journalCategory, Professor professor) {
		this.type = type;
		this.authorityOrganizationOrJournal = authorityOrganizationOrJournal;
		this.institutionType = institutionType;
		this.authorityOrOrganizationWorkStartDate = authorityOrOrganizationWorkStartDate;
		this.authorityOrOrganizationWorkEndDate = authorityOrOrganizationWorkEndDate;
		this.functionInOrganizationConferenceOrCommittee = functionInOrganizationConferenceOrCommittee;
		this.meetingConferenceOrEventYear = meetingConferenceOrEventYear;
		this.journalCategory = journalCategory;
		this.professor = professor;
	}

	public AcademicCommunityContributionType getType() {
		return type;
	}

	public void setType(AcademicCommunityContributionType type) {
		this.type = type;
	}

	public String getAuthorityOrganizationOrJournal() {
		return authorityOrganizationOrJournal;
	}

	public void setAuthorityOrganizationOrJournal(String authorityOrganizationOrJournal) {
		this.authorityOrganizationOrJournal = authorityOrganizationOrJournal;
	}

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public Date getAuthorityOrOrganizationWorkStartDate() {
		return authorityOrOrganizationWorkStartDate;
	}

	public void setAuthorityOrOrganizationWorkStartDate(Date authorityOrOrganizationWorkStartDate) {
		this.authorityOrOrganizationWorkStartDate = authorityOrOrganizationWorkStartDate;
	}

	public Date getAuthorityOrOrganizationWorkEndDate() {
		return authorityOrOrganizationWorkEndDate;
	}

	public void setAuthorityOrOrganizationWorkEndDate(Date authorityOrOrganizationWorkEndDate) {
		this.authorityOrOrganizationWorkEndDate = authorityOrOrganizationWorkEndDate;
	}

	public String getFunctionInOrganizationConferenceOrCommittee() {
		return functionInOrganizationConferenceOrCommittee;
	}

	public void setFunctionInOrganizationConferenceOrCommittee(String functionInOrganizationConferenceOrCommittee) {
		this.functionInOrganizationConferenceOrCommittee = functionInOrganizationConferenceOrCommittee;
	}

	public String getMeetingConferenceOrEventYear() {
		return meetingConferenceOrEventYear;
	}

	public void setMeetingConferenceOrEventYear(String meetingConferenceOrEventYear) {
		this.meetingConferenceOrEventYear = meetingConferenceOrEventYear;
	}

	public String getJournalCategory() {
		return journalCategory;
	}

	public void setJournalCategory(String journalCategory) {
		this.journalCategory = journalCategory;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
