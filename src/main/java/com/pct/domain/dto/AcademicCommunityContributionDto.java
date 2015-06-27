package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.AcademicCommunityContribution;
import com.pct.domain.enums.AcademicCommunityContributionType;
import com.pct.domain.enums.deserializers.AcademicCommunityContributionTypeEnumDeserializer;

public class AcademicCommunityContributionDto implements Serializable {

	private static final long serialVersionUID = 4216319746576590893L;

	@NotNull
	@JsonDeserialize(using = AcademicCommunityContributionTypeEnumDeserializer.class)
	private AcademicCommunityContributionType type;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String authorityOrganizationOrJournal;

	@Nullable
	@Length(max = 20)
	@SafeHtml
	private String institutionType;

	@Nullable
	private Date authorityOrOrganizationWorkStartDate;

	@Nullable
	private Date authorityOrOrganizationWorkEndDate;

	@Nullable
	@Length(max = 40)
	@SafeHtml
	private String functionInOrganizationConferenceOrCommittee;

	@Nullable
	@Length(max = 4)
	@SafeHtml
	private String meetingConferenceOrEventYear;

	@Nullable
	@Length(max = 3)
	@SafeHtml
	private String journalCategory;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public AcademicCommunityContributionDto() {
	}

	public AcademicCommunityContributionDto(AcademicCommunityContributionType type, String authorityOrganizationOrJournal, String institutionType,
			Date authorityOrOrganizationWorkStartDate, Date authorityOrOrganizationWorkEndDate, String functionInOrganizationConferenceOrCommittee,
			String meetingConferenceOrEventYear, String journalCategory, Long professorId, Long id) {
		this.type = type;
		this.authorityOrganizationOrJournal = authorityOrganizationOrJournal;
		this.institutionType = institutionType;
		this.authorityOrOrganizationWorkStartDate = authorityOrOrganizationWorkStartDate;
		this.authorityOrOrganizationWorkEndDate = authorityOrOrganizationWorkEndDate;
		this.functionInOrganizationConferenceOrCommittee = functionInOrganizationConferenceOrCommittee;
		this.meetingConferenceOrEventYear = meetingConferenceOrEventYear;
		this.journalCategory = journalCategory;
		this.professorId = professorId;
		this.id = id;
	}

	public AcademicCommunityContributionDto(AcademicCommunityContribution contribution) {
		this.type = contribution.getType();
		this.authorityOrganizationOrJournal = contribution.getAuthorityOrganizationOrJournal();
		this.institutionType = contribution.getInstitutionType();
		this.authorityOrOrganizationWorkStartDate = contribution.getAuthorityOrOrganizationWorkStartDate();
		this.authorityOrOrganizationWorkEndDate = contribution.getAuthorityOrOrganizationWorkEndDate();
		this.functionInOrganizationConferenceOrCommittee = contribution.getFunctionInOrganizationConferenceOrCommittee();
		this.meetingConferenceOrEventYear = contribution.getMeetingConferenceOrEventYear();
		this.journalCategory = contribution.getJournalCategory();
		this.professorId = contribution.getProfessor().getId();
		this.id = contribution.getId();
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

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
