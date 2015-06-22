package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.ScientificProfessionalOrgMem;

public class ScientificProfessionalOrgMemDto implements Serializable {

	private static final long serialVersionUID = -893626794699535370L;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String organizationName;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public ScientificProfessionalOrgMemDto() {
	}

	public ScientificProfessionalOrgMemDto(String organizationName, Long professorId, Long id) {
		this.organizationName = organizationName;
		this.professorId = professorId;
		this.id = id;
	}

	public ScientificProfessionalOrgMemDto(ScientificProfessionalOrgMem membership) {
		this.organizationName = membership.getOrganizationName();
		this.professorId = membership.getProfessor().getId();
		this.id = membership.getId();
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
