package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InstitutionType {

	COMPANY("Firma"),
	
	SCHOOL("Škola"),
	
	FACULTY("Fakultet"),

	UNIVERSITY("Univerzitet"),

	OTHER("Ostale ustanove");

	private final String title;

	private InstitutionType(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}

	public String getTitle() {
		return title;
	}

	public static InstitutionType getByTitle(String title) throws IllegalArgumentException {
		for (InstitutionType institutionType : InstitutionType.values()) {
			if (institutionType.title.equals(title)) {
				return institutionType;
			}
		}
		throw new IllegalArgumentException("Illegal institution type code");
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
