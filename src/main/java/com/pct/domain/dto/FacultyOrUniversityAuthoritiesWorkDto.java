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

public class FacultyOrUniversityAuthoritiesWorkDto implements Serializable {

	private static final long serialVersionUID = -3194865033402573602L;

	@NotNull
	@JsonDeserialize(using = AcademicCommunityContributionTypeEnumDeserializer.class)
	private final AcademicCommunityContributionType type = AcademicCommunityContributionType.WORK_IN_THE_INSTITUTIONS_AND_AUTHORITIES_OF_THE_FACULTY_AND_UNIVERSITY;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String authority;

	@NotEmpty
	@Length(max = 20)
	@SafeHtml
	private String institutionType;

	@NotNull
	private Date startDate;

	@Nullable
	private Date endDate;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public FacultyOrUniversityAuthoritiesWorkDto() {
	}

	public FacultyOrUniversityAuthoritiesWorkDto(String authority, String institutionType, Date startDate, Date endDate, Long professorId, Long id) {
		this.authority = authority;
		this.institutionType = institutionType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.professorId = professorId;
		this.id = id;
	}

	public FacultyOrUniversityAuthoritiesWorkDto(AcademicCommunityContribution contribution) {
		this.authority = contribution.getAuthorityOrganizationOrJournal();
		this.institutionType = contribution.getInstitutionType();
		this.startDate = contribution.getAuthorityOrOrganizationWorkStartDate();
		this.endDate = contribution.getAuthorityOrOrganizationWorkEndDate();
		this.professorId = contribution.getProfessor().getId();
		this.id = contribution.getId();
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public AcademicCommunityContributionType getType() {
		return type;
	}

}
