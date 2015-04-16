package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "country", length = 50)
	private String country;

	@Column(name = "city", length = 50)
	private String city;

	@Column(name = "adress", length = 50)
	private String adress;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "institution", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "institution")
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>();

	public Institution() {
		super();
	}

	public Institution(String name, InstitutionType institutionType, String country, String city, String adress,
			Set<WorkExperience> workExperiences) {
		super();
		this.name = name;
		this.institutionType = institutionType;
		this.country = country;
		this.city = city;
		this.adress = adress;
		this.workExperiences = workExperiences;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
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

	public void addWorkExperiences(WorkExperience workExperience) {
		if (workExperience != null) {
			workExperience.setInstitution(this);
			this.workExperiences.add(workExperience);
		}
	}

}
