package com.pct.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "institution", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "institution")
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>();

	public Institution() {
		super();
	}

	public Institution(String name, InstitutionType institutionType, String country, String city,
			Set<WorkExperience> workExperiences) {
		super();
		this.name = name;
		this.institutionType = institutionType;
		this.country = country;
		this.city = city;
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

	public Set<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(Set<WorkExperience> workExperiences) {
		this.workExperiences.clear();

		if (workExperiences != null) {
			this.workExperiences.addAll(workExperiences);
		}
	}

	public WorkExperience getWorkExperienceById(Long id) {
		Iterator<WorkExperience> it = workExperiences.iterator();
		while (it.hasNext()) {
			WorkExperience workExperience = (WorkExperience) it.next();
			if (workExperience.getId().equals(id)) {
				return workExperience;
			}
		}
		return null;
	}

	public void addWorkExperiences(WorkExperience workExperience) {
		if (workExperience != null) {
			workExperience.setInstitution(this);
			this.workExperiences.add(workExperience);
		}
	}

	public WorkExperience creatreNewWorkExperience(Professor professor, Date workStartDate, Date workEndDate,
			String title) {

		WorkExperience workExperience = new WorkExperience(this, professor, workStartDate, workEndDate, title);
		professor.addWorkExperiences(workExperience);
		this.workExperiences.add(workExperience);
		return workExperience;
	}

	public WorkExperience editWorkExperience(Professor professor, Date workStartDate, Date workEndDate, String title,
			Long id) {

		WorkExperience workExperience = getWorkExperienceById(id);
		workExperience.setWorkStartDate(workStartDate);
		workExperience.setWorkEndDate(workEndDate);
		workExperience.setTitle(title);

		professor.addWorkExperiences(workExperience);

		return workExperience;
	}

}
