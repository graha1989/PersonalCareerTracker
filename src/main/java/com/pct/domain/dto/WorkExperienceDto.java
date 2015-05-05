package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.WorkExperience;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;

public class WorkExperienceDto implements Serializable {

	private static final long serialVersionUID = 138813807628111267L;

	@NotNull
	@JsonDeserialize(using = InstitutionTypeEnumDeserializer.class)
	private InstitutionType institutionType;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionName;

	@Nullable
	@Length(max = 50)
	@SafeHtml
	private String universityName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionCity;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionCountry;

	@Nullable
	protected Long institutionId;

	@NotNull
	private Date workStartDate;

	@Nullable
	private Date workEndDate;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String title;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public WorkExperienceDto() {
	}

	public WorkExperienceDto(InstitutionType institutionType, String institutionName, String universityName,
			String institutionCity, String institutionCountry, Date workStartDate, Date workEndDate, String title,
			Long professorId, Long institutionId, Long id) {
		super();
		this.institutionType = institutionType;
		this.institutionName = institutionName;
		this.universityName = universityName;
		this.institutionCity = institutionCity;
		this.institutionCountry = institutionCountry;
		this.workStartDate = workStartDate;
		this.workEndDate = workEndDate;
		this.title = title;
		this.professorId = professorId;
		this.institutionId = institutionId;
		this.id = id;
	}

	public WorkExperienceDto(WorkExperience workExperience) {
		this.institutionType = workExperience.getInstitution().getInstitutionType();
		this.institutionName = workExperience.getInstitution().getName();
		this.universityName = workExperience.getInstitution().getUniversity();
		this.institutionCity = workExperience.getInstitution().getCity();
		this.institutionCountry = workExperience.getInstitution().getCountry();
		this.workStartDate = workExperience.getWorkStartDate();
		this.workEndDate = workExperience.getWorkEndDate();
		this.title = workExperience.getTitle();
		this.professorId = workExperience.getProfessor().getId();
		this.institutionId = workExperience.getInstitution().getId();
		this.id = workExperience.getId();
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getInstitutionCity() {
		return institutionCity;
	}

	public void setInstitutionCity(String institutionCity) {
		this.institutionCity = institutionCity;
	}

	public String getInstitutionCountry() {
		return institutionCountry;
	}

	public void setInstitutionCountry(String institutionCountry) {
		this.institutionCountry = institutionCountry;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
