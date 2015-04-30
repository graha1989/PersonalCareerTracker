package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Subject;

public class SubjectDto implements Serializable {

	private static final long serialVersionUID = 1126081919536192074L;
	
	@NotNull
	protected Long id;
	
	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String name;

	public SubjectDto() {
	}

	public SubjectDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public SubjectDto(Subject subject) {
		this.id = subject.getId();
		this.name = subject.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
