package com.pct.domain;

public enum AwardType {
	
	RECOGNITION("Priznanje"),
	
	PRIZE("Nagrada"),
	
	LAUREL("Odlikovanje");
	
	private String awardType;

	private AwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardType() {
		return awardType;
	}

}
