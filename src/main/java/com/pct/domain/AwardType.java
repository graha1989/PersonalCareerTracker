package com.pct.domain;

public enum AwardType {
	
	RECOGNITION("PRIZNANJE"),
	
	PRIZE("NAGRADA"),
	
	LAUREL("ODLIKOVANJE");
	
	private String awardType;

	private AwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardType() {
		return awardType;
	}

}
