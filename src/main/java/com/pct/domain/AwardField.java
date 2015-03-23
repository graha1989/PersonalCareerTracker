package com.pct.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AwardField {

	SCIENTIFIC_FIELD("Naučna oblast"),

	PEDAGOGICAL_FIELD("Pedagoška oblast");

	private final String title;

	private AwardField(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}
	
	public String getTitle() {
		return title;
	}

	public static AwardField getByAwardField(String title) throws IllegalArgumentException {
		for (AwardField awardfield : AwardField.values()) {
			if (awardfield.title.equals(title)) {
				return awardfield;
			}
		}
		throw new IllegalArgumentException("Illegal award field code");
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
}
