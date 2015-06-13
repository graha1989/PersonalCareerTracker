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
@Table(name = "institution_type")
public class InstitutionType extends AbstractEntity {

	private static final long serialVersionUID = 4512544391785326993L;

	@Column(name = "typeName", length = 50)
	private String typeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "institutionType")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "institutionType")
	@JsonIgnore
	private Set<Institution> institutions = new HashSet<Institution>();

	public InstitutionType() {
	}

	public InstitutionType(String typeName, Set<Institution> institutions) {
		this.typeName = typeName;
		this.institutions = institutions;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(Set<Institution> institutions) {
		this.institutions.clear();

		if (institutions != null) {
			this.institutions.addAll(institutions);
		}
	}

}
