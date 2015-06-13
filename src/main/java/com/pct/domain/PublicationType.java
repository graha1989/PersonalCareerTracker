package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "publication_type")
public class PublicationType extends AbstractEntity {

	private static final long serialVersionUID = 6161893552046730431L;

	@Column(name = "typeName", length = 50)
	private String typeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "publicationType")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "publicationType")
	@JsonIgnore
	private Set<ProfessorPublication> professorPublications = new HashSet<ProfessorPublication>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "publicationType")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "publicationType")
	@JsonIgnore
	private Set<InternationalPublication> internationalPublications = new HashSet<InternationalPublication>();

	public PublicationType() {
	}

	public PublicationType(String typeName, Set<ProfessorPublication> professorPublications,
			Set<InternationalPublication> internationalPublications) {
		this.typeName = typeName;
		this.professorPublications = professorPublications;
		this.internationalPublications = internationalPublications;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Set<InternationalPublication> getInternationalPublications() {
		return internationalPublications;
	}

	public void setInternationalPublications(Set<InternationalPublication> internationalPublications) {
		this.internationalPublications.clear();

		if (internationalPublications != null) {
			this.internationalPublications.addAll(internationalPublications);
		}
	}

}
