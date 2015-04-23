package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StudyProgram {

	BIOLOGY_AND_ECOLOGY("Biologija i ekologija"),

	PHYSICS("Fizika"),

	CHEMISTRY_BIOCHEMISTRY_AND_ENVIRONMENTAL_PROTECTION("Hemija, biohemija i zaštita životne sredine"),

	GEOGRAPHY_TOURISM_AND_HOTEL_MANAGEMENT("Geografija, turizam i hotelijerstvo"),

	MATHEMATICS_AND_INFORMATICS("Matematika i informatika");

	private final String title;

	private StudyProgram(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}

	public String getTitle() {
		return title;
	}

	public static StudyProgram getByTitle(String title) throws IllegalArgumentException {
		for (StudyProgram studyProgram : StudyProgram.values()) {
			if (studyProgram.title.equals(title)) {
				return studyProgram;
			}
		}
		throw new IllegalArgumentException("Illegal study program code");
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
