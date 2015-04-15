package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "publication_category")
public class PublicationCategory extends AbstractEntity {

	private static final long serialVersionUID = -6711978289006452557L;

	@Column(name = "code", unique = true, length = 5)
	private String code;

	@Column(name = "description", length = 200)
	private String description;

	/* Natural Sciences and Medical value points */
	@Column(name = "nsmPoints", nullable = true, columnDefinition = "Decimal(4,2)")
	private Double nsmPoints;

	/* Technical-technological and biotechnical value points */
	@Column(name = "ttbtPoints", nullable = true, columnDefinition = "Decimal(4,2)")
	private Double ttbtPoints;

	/* Social and humanistic value points */
	@Column(name = "shPoints", nullable = true, columnDefinition = "Decimal(4,2)")
	private Double shPoints;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publicationCategory", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "publicationCategory")
	private Set<ProfessorPublication> professorPublications = new HashSet<ProfessorPublication>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publicationCategory", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "publicationCategory")
	private Set<InternationalPublication> internationalPublications = new HashSet<InternationalPublication>();

	public PublicationCategory() {
		super();
	}

	public PublicationCategory(String code, String description, Double nsmPoints, Double ttbtPoints, Double shPoints,
			Set<ProfessorPublication> publications) {
		super();
		this.code = code;
		this.description = description;
		this.nsmPoints = nsmPoints;
		this.ttbtPoints = ttbtPoints;
		this.shPoints = shPoints;
		this.professorPublications = publications;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getNsmPoints() {
		return nsmPoints;
	}

	public void setNsmPoints(Double nsmPoints) {
		this.nsmPoints = nsmPoints;
	}

	public Double getTtbtPoints() {
		return ttbtPoints;
	}

	public void setTtbtPoints(Double ttbtPoints) {
		this.ttbtPoints = ttbtPoints;
	}

	public Double getShPoints() {
		return shPoints;
	}

	public void setShPoints(Double shPoints) {
		this.shPoints = shPoints;
	}

	public Set<ProfessorPublication> getProfessorPublications() {
		return professorPublications;
	}

	public void setProfessorPublications(Set<ProfessorPublication> professorPublications) {
		this.professorPublications.clear();

		if (professorPublications != null) {
			this.professorPublications.addAll(professorPublications);
		}
	}

	public void addProfessorPublication(ProfessorPublication professorPublication) {
		if (professorPublication != null) {
			professorPublication.setPublicationCategory(this);
			this.professorPublications.add(professorPublication);
		}
	}

	public Set<InternationalPublication> getInternationalPublications() {
		return internationalPublications;
	}

	public void setInternationalPublications(Set<InternationalPublication> internationalPublications) {
		this.internationalPublications.clear();

		if (internationalPublications != null) {
			this.internationalPublications.addAll(internationalPublications);
		}
	}

	public void addInternationalPublication(InternationalPublication internationalPublication) {
		if (internationalPublications != null) {
			internationalPublication.setPublicationCategory(this);
			this.internationalPublications.add(internationalPublication);
		}
	}

}
