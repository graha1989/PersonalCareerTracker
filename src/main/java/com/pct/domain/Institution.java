package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;

@Entity
@Table(name = "institution")
public class Institution extends AbstractEntity {

	private static final long serialVersionUID = 8497802320598563400L;

	@Enumerated(EnumType.STRING)
	@Column(name = "institutionType")
	@JsonDeserialize(using = InstitutionTypeEnumDeserializer.class)
	private InstitutionType institutionType;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "university", length = 50)
	private String university;

	@Column(name = "country", length = 50)
	private String country;

	@Column(name = "city", length = 50)
	private String city;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "institution")
	@JsonIgnore
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "institution")
	@JsonIgnore
	private Set<Studies> studies = new HashSet<Studies>();

	public Institution() {
	}

	public Institution(String name, String university, InstitutionType institutionType, String country, String city,
			Set<WorkExperience> workExperiences, Set<Studies> studies) {
		super();
		this.name = name;
		this.university = university;
		this.institutionType = institutionType;
		this.country = country;
		this.city = city;
		this.workExperiences = workExperiences;
		this.studies = studies;
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

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(Set<WorkExperience> workExperiences) {
		this.workExperiences.clear();

		if (workExperiences != null) {
			this.workExperiences.addAll(workExperiences);
		}
	}

	public Set<Studies> getStudies() {
		return studies;
	}

	public void setStudies(Set<Studies> studies) {
		this.studies.clear();

		if (studies != null) {
			this.studies.addAll(studies);
		}
	}

}
