package com.pct.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AwardType {

	RECOGNITION("Priznanje"),

	PRIZE("Nagrada"),

	LAUREL("Odlikovanje");

	private final String awardType;

	private AwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardType() {
		return awardType;
	}
	
	public static AwardType getByAwardType(String awardType) throws IllegalArgumentException {
		for (AwardType awardtype : AwardType.values()) {
			if (awardtype.awardType.equals(awardType)) {
				return awardtype;
			}
		}
		throw new IllegalArgumentException("Illegal award type code");
	}

}
