package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.constants.MimeTypes;
import com.pct.domain.SpecializationAbroad;

public class SpecializationAbroadDto implements Serializable {

	private static final long serialVersionUID = -3689972646050090419L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String city;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String country;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionName;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String purpose;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private final String institutionType = MimeTypes.INSTITUTION_TYPE_OTHER;
	
	@NotNull
	protected Long professorId;

	@Nullable
	protected Long institutionId;

	@Nullable
	protected Long id;

	public SpecializationAbroadDto() {
	}

	public SpecializationAbroadDto(String city, String country, String institutionName, Date startDate, Date endDate,
			String purpose, Long professorId, Long institutionId, Long id) {
		super();
		this.city = city;
		this.country = country;
		this.institutionName = institutionName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.purpose = purpose;
		this.professorId = professorId;
		this.institutionId = institutionId;
		this.id = id;
	}

	public SpecializationAbroadDto(SpecializationAbroad specialization) {
		super();
		this.city = specialization.getCity();
		this.country = specialization.getCountry();
		this.institutionName = specialization.getInstitution().getName();
		this.startDate = specialization.getStartDate();
		this.endDate = specialization.getEndDate();
		this.purpose = specialization.getPurpose();
		this.professorId = specialization.getProfessor().getId();
		this.institutionId = specialization.getInstitution().getId();
		this.id = specialization.getId();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getInstitutionType() {
		return institutionType;
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
