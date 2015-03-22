package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Award;
import com.pct.domain.AwardField;
import com.pct.domain.AwardType;

public class AwardDto implements Serializable {

	private static final long serialVersionUID = -1007098220318687716L;
	
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
	
	@NotNull
	private AwardField awardField;
	
	protected Long mentorId;
	
	protected Long id;
	
	public AwardDto() {
		super();
	}

	public AwardDto(String awardName, String awardedBy, Date dateOfAward, AwardType awardType, AwardField awardField, Long id, Long mentorId) {
		super();
		this.awardName = awardName;
		this.awardedBy = awardedBy;
		this.dateOfAward = dateOfAward;
		this.awardType = awardType;
		this.awardField = awardField;
		this.id = id;
		this.mentorId = mentorId;
	}

	public AwardDto(Award award) {
		this.awardName = award.getAwardName();
		this.awardedBy = award.getAwardedBy();
		this.dateOfAward = award.getDateOfAward();
		this.awardType = award.getAwardType();
		this.awardField = award.getAwardField();
		this.id = award.getId();
		this.mentorId = award.getProfessor().getId();
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

	public AwardField getAwardField() {
		return awardField;
	}

	public void setAwardField(AwardField awardField) {
		this.awardField = awardField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
	}
	
}
