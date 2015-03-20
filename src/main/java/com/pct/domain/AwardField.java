package com.pct.domain;

public enum AwardField {
	
	SCIENTIFIC_FIELD("NAUČNA_OBLAST"),

	PEDAGOGICAL_FIELD("PEDAGOŠKA_OBLAST");

	private String awardField;

	private AwardField(String awardField) {
		this.awardField = awardField;
	}

	public String getAwardField() {
		return awardField;
	}

}
