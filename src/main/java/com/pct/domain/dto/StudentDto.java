package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.constants.RegexPatterns;
import com.pct.domain.Student;

public class StudentDto implements Serializable {
	
	private static final long serialVersionUID = 7330507151842329181L;

	@NotEmpty
	@Length(max = 10)
	@SafeHtml
	private String transcriptNumber;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String name;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String surname;
	
	protected Long id;
	
	public StudentDto() {
	}

	public StudentDto(Student student) {
		this.id = student.getId();
		this.transcriptNumber = student.getTranscriptNumber();
		this.name = student.getName();
		this.surname = student.getSurname();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTranscriptNumber() {
		return transcriptNumber;
	}

	public void setTranscriptNumber(String transcriptNumber) {
		this.transcriptNumber = transcriptNumber;
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
	
}
