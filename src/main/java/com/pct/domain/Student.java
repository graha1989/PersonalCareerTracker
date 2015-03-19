package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity bean for Student table.
 * 
 * @author a.grahovac
 * 
 */
@Entity
@Table(name = "student")
public class Student extends AbstractEntity {

	private static final long serialVersionUID = 5293157098981693931L;
	
	
	@Column(name = "transcriptNumber", unique=true, length = 10)
	private String transcriptNumber;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "surname", length = 50)
	private String surname;

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
