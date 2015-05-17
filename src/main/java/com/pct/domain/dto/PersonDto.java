package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

public class PersonDto implements Serializable {

	private static final long serialVersionUID = 3042610636778025105L;
	
	@Length(max = 50)
	@SafeHtml
	private String name;

	@Length(max = 50)
	@SafeHtml
	private String surname;

	@Nullable
	protected Long leaderId;
	
	@Nullable
	protected Long professorId;

	public PersonDto() {
	}

	public PersonDto(String name, String surname, Long leaderId, Long professorId) {
		super();
		this.name = name;
		this.surname = surname;
		this.leaderId = leaderId;
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

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
	
}
