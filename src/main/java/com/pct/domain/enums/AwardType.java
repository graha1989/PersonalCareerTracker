package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AwardType {

	RECOGNITION("Priznanje"),

	PRIZE("Nagrada"),

	LAUREL("Odlikovanje");

	private final String title;

	private AwardType(String title) {
		this.title = title;
	}
	
	public String getName() {
		return name();
	}
	
	public String getTitle() {
		return title;
	}

	public static AwardType getByTitle(String title) throws IllegalArgumentException {
		for (AwardType awardtype : AwardType.values()) {
			if (awardtype.title.equals(title)) {
				return awardtype;
			}
		}
		throw new IllegalArgumentException("Illegal award type code");
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
}
