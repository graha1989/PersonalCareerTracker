package com.pct.domain;

public enum AwardField {
	
	SCIENTIFIC_FIELD("Naučna oblast"),

	PEDAGOGICAL_FIELD("Pedagoška oblast");

	private String awardField;

	private AwardField(String awardField) {
		this.awardField = awardField;
	}

	public String getAwardField() {
		return awardField;
	}

}
