package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectType {

	SCIENTIFIC("Naučni"),

	OTHER("Ostali");

	private final String title;

	private ProjectType(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}

	public String getTitle() {
		return title;
	}

	public static ProjectType getByTitle(String title) throws IllegalArgumentException {
		for (ProjectType projectType : ProjectType.values()) {
			if (projectType.title.equals(title)) {
				return projectType;
			}
		}
		throw new IllegalArgumentException("Illegal project type code");
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
