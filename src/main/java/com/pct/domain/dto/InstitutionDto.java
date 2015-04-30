package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.Institution;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;

public class InstitutionDto implements Serializable {

	private static final long serialVersionUID = 9033995876037934292L;

	@NotNull
	@JsonDeserialize(using = InstitutionTypeEnumDeserializer.class)
	private InstitutionType institutionType;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String name;

	@Nullable
	@Length(max = 50)
	@SafeHtml
	private String university;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String city;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String country;

	@Nullable
	protected Long id;

	public InstitutionDto() {
	}

	public InstitutionDto(InstitutionType institutionType, String name, String university, String city, String country,
			Long id) {
		super();
		this.institutionType = institutionType;
		this.name = name;
		this.university = university;
		this.city = city;
		this.country = country;
		this.id = id;
	}

	public InstitutionDto(Institution institution) {
		this.institutionType = institution.getInstitutionType();
		this.name = institution.getName();
		this.university = institution.getUniversity();
		this.city = institution.getCity();
		this.country = institution.getCountry();
		this.id = institution.getId();
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
