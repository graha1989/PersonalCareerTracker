package com.pct.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "award")
public class Award extends AbstractEntity {

	private static final long serialVersionUID = 5896764622047317204L;
	
	@Column(name = "awardName", length = 200)
	private String awardName;
	
	@Column(name = "awardedBy", length = 200)
	private String awardedBy;
	
	@Column(name = "dateOfAward")
	private Date dateOfAward;
	
	@OneToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference
	private Profesor professor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "awardType")
	private AwardType awardType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "awardField")
	private AwardField awardField;

	public Award() {
		super();
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardedBy() {
		return awardedBy;
	}

	public void setAwardedBy(String awardedBy) {
		this.awardedBy = awardedBy;
	}

	public Date getDateOfAward() {
		return dateOfAward;
	}

	public void setDateOfAward(Date dateOfAward) {
		this.dateOfAward = dateOfAward;
	}

	public Profesor getProfessor() {
		return professor;
	}

	public void setProfessor(Profesor professor) {
		this.professor = professor;
	}

	public AwardType getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardType awardType) {
		this.awardType = awardType;
	}

	public AwardField getAwardField() {
		return awardField;
	}

	public void setAwardField(AwardField awardField) {
		this.awardField = awardField;
	}
}
