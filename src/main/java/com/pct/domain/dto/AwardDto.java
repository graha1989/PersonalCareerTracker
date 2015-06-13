package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Award;
import com.pct.domain.enums.AwardType;

public class AwardDto implements Serializable {

	private static final long serialVersionUID = -4169829583298510729L;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String awardName;
	
	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String awardedBy;
	
	private Date dateOfAward;
	
	@NotNull
	private AwardType awardType;
	
	@NotEmpty
	private String awardField;
	
	protected Long professorId;
	
	protected Long id;
	
	public AwardDto() {
	}

	public AwardDto(String awardName, String awardedBy, Date dateOfAward, AwardType awardType, String awardField, Long id, Long professorId) {
		super();
		this.awardName = awardName;
		this.awardedBy = awardedBy;
		this.dateOfAward = dateOfAward;
		this.awardType = awardType;
		this.awardField = awardField;
		this.id = id;
		this.professorId = professorId;
	}

	public AwardDto(Award award) {
		this.awardName = award.getAwardName();
		this.awardedBy = award.getAwardedBy();
		this.dateOfAward = award.getDateOfAward();
		this.awardType = award.getAwardType();
		this.awardField = award.getAwardField().getFieldName();
		this.id = award.getId();
		this.professorId = award.getProfessor().getId();
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

	public AwardType getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardType awardType) {
		this.awardType = awardType;
	}

	public String getAwardField() {
		return awardField;
	}

	public void setAwardField(String awardField) {
		this.awardField = awardField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
	
}
