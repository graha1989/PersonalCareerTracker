package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Aleksandar Grahovac
 * Membership in scientific and professional organizations entity.
 *
 */
@Entity
@Table(name = "scientific_professional_org_mem")
public class ScientificProfessionalOrgMem extends AbstractEntity {

	private static final long serialVersionUID = -2728822919557967807L;

	@Column(name = "organizationName", length = 200)
	private String organizationName;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	public ScientificProfessionalOrgMem() {
	}

	public ScientificProfessionalOrgMem(String organizationName, Professor professor) {
		this.organizationName = organizationName;
		this.professor = professor;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
