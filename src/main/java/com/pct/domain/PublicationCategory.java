package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "publication_category")
public class PublicationCategory extends AbstractEntity {

	private static final long serialVersionUID = -7712552499088434060L;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publicationCategory")
	private Set<ProfessorPublication> publications;

	public PublicationCategory() {
		super();
		this.publications = new HashSet<ProfessorPublication>();
	}

	public PublicationCategory(String code, String description, Double nsmPoints, Double ttbtPoints, Double shPoints,
			Set<ProfessorPublication> publications) {
		super();
		this.code = code;
		this.description = description;
		this.nsmPoints = nsmPoints;
		this.ttbtPoints = ttbtPoints;
		this.shPoints = shPoints;
		this.publications = new HashSet<ProfessorPublication>(publications);
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
		return publications;
	}

	public void setProfessorPublications(Set<ProfessorPublication> publications) {
		this.publications.clear();

		if (publications != null) {
			this.publications.addAll(publications);
		}
	}

	public void addProfessorPublication(ProfessorPublication professorPublication) {
		if (publications != null) {
			professorPublication.setPublicationCategory(this);
			this.publications.add(professorPublication);
		}
	}

}
