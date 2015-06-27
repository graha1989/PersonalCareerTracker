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

public class ProfessionalOrganizationConductionDto implements Serializable {

	private static final long serialVersionUID = 6825922530989102331L;

	@NotNull
	@JsonDeserialize(using = AcademicCommunityContributionTypeEnumDeserializer.class)
	private final AcademicCommunityContributionType type = AcademicCommunityContributionType.PROFESSIONAL_ORGANIZATIONS_CONDUCTION;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String organization;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String functionDesc;

	@NotNull
	private Date startDate;

	@Nullable
	private Date endDate;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public ProfessionalOrganizationConductionDto() {
	}

	public ProfessionalOrganizationConductionDto(String organization, String functionDesc, Date startDate, Date endDate, Long professorId, Long id) {
		this.organization = organization;
		this.functionDesc = functionDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.professorId = professorId;
		this.id = id;
	}

	public ProfessionalOrganizationConductionDto(AcademicCommunityContribution contribution) {
		this.organization = contribution.getAuthorityOrganizationOrJournal();
		this.functionDesc = contribution.getFunctionInOrganizationConferenceOrCommittee();
		this.startDate = contribution.getAuthorityOrOrganizationWorkStartDate();
		this.endDate = contribution.getAuthorityOrOrganizationWorkEndDate();
		this.professorId = contribution.getProfessor().getId();
		this.id = contribution.getId();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
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
