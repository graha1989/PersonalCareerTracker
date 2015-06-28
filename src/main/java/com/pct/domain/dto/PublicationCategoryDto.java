package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.PublicationCategory;

public class PublicationCategoryDto implements Serializable {

	private static final long serialVersionUID = 6079405832735664516L;

	@NotEmpty
	@Length(max = 5)
	@SafeHtml
	private String code;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	@Lob
	private String description;

	private Double nsmPoints;

	private Double ttbtPoints;

	private Double shPoints;

	@NotNull
	private Boolean sci;

	@Nullable
	protected Long id;

	public PublicationCategoryDto() {
	}

	public PublicationCategoryDto(String code, String description, Double nsmPoints, Double ttbtPoints, Double shPoints, Boolean sci, Long id) {
		this.code = code;
		this.description = description;
		this.nsmPoints = nsmPoints;
		this.ttbtPoints = ttbtPoints;
		this.shPoints = shPoints;
		this.sci = sci;
		this.id = id;
	}

	public PublicationCategoryDto(PublicationCategory category) {
		this.code = category.getCode();
		this.description = category.getDescription();
		this.nsmPoints = category.getNsmPoints();
		this.ttbtPoints = category.getTtbtPoints();
		this.shPoints = category.getShPoints();
		this.sci = category.getSci();
		this.id = category.getId();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getNsmPoints() {
		return nsmPoints;
	}

	public void setNsmPoints(Double nsmPoints) {
		this.nsmPoints = nsmPoints;
	}

	public Double getTtbtPoints() {
		return ttbtPoints;
	}

	public void setTtbtPoints(Double ttbtPoints) {
		this.ttbtPoints = ttbtPoints;
	}

	public Double getShPoints() {
		return shPoints;
	}

	public void setShPoints(Double shPoints) {
		this.shPoints = shPoints;
	}

	public Boolean getSci() {
		return sci;
	}

	public void setSci(Boolean sci) {
		this.sci = sci;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
