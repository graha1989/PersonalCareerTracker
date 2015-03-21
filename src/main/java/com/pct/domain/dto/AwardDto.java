package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Award;

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
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String awardType;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String awardField;
	
	protected Long id;
	
	public AwardDto() {
		super();
	}

	public AwardDto(String awardName, String awardedBy, Date dateOfAward, String awardType, String awardField, Long id) {
		super();
		this.awardName = awardName;
		this.awardedBy = awardedBy;
		this.dateOfAward = dateOfAward;
		this.awardType = awardType;
		this.awardField = awardField;
		this.id = id;
	}

	public AwardDto(Award award) {
		this.awardName = award.getAwardName();
		this.awardedBy = award.getAwardedBy();
		this.dateOfAward = award.getDateOfAward();
		this.awardType = award.getAwardType().getAwardType();
		this.awardField = award.getAwardField().getAwardField();
		this.id = award.getId();
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

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
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
	
}
