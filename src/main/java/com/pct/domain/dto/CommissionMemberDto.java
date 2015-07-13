package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.CommissionMember;

public class CommissionMemberDto implements Serializable {

	private static final long serialVersionUID = -51470008947824729L;

	@Nullable
	protected Long professorId;

	@Length(max = 50)
	@SafeHtml
	private String name;

	@Length(max = 50)
	@SafeHtml
	private String surname;

	@Length(max = 50)
	@SafeHtml
	private String title;

	@Length(max = 50)
	@SafeHtml
	private String specificScientificArea;

	@Length(max = 100)
	@SafeHtml
	private String institution;

	@Length(max = 20)
	@SafeHtml
	private String commissionFunction;

	@Nullable
	protected Long id;

	public CommissionMemberDto() {
	}

	public CommissionMemberDto(Long professorId, String name, String surname, String title, String specificScientificArea, String institution,
			String commissionFunction, Long id) {
		this.professorId = professorId;
		this.name = name;
		this.surname = surname;
		this.title = title;
		this.specificScientificArea = specificScientificArea;
		this.institution = institution;
		this.commissionFunction = commissionFunction;
		this.id = id;
	}

	public CommissionMemberDto(CommissionMember commissionMember) {
		this.professorId = (commissionMember.getProfessor() != null ? commissionMember.getProfessor().getId() : null);
		this.name = commissionMember.getName();
		this.surname = commissionMember.getSurname();
		this.title = commissionMember.getTitle();
		this.specificScientificArea = commissionMember.getSpecificScientificArea();
		this.institution = commissionMember.getInstitution();
		this.commissionFunction = commissionMember.getFunction();
		this.id = commissionMember.getId();
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpecificScientificArea() {
		return specificScientificArea;
	}

	public void setSpecificScientificArea(String specificScientificArea) {
		this.specificScientificArea = specificScientificArea;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getCommissionFunction() {
		return commissionFunction;
	}

	public void setCommissionFunction(String commissionFunction) {
		this.commissionFunction = commissionFunction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
