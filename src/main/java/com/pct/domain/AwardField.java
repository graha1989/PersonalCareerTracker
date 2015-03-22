package com.pct.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AwardField {

	SCIENTIFIC_FIELD("Naučna oblast"),

	PEDAGOGICAL_FIELD("Pedagoška oblast");

	private final String awardField;

	private AwardField(String awardField) {
		this.awardField = awardField;
	}

	public String getAwardField() {
		return awardField;
	}

	public static AwardField getByAwardField(String awardField) throws IllegalArgumentException {
		for (AwardField awardfield : AwardField.values()) {
			if (awardfield.awardField.equals(awardField)) {
				return awardfield;
			}
		}
		throw new IllegalArgumentException("Illegal award type code");
	}

}
