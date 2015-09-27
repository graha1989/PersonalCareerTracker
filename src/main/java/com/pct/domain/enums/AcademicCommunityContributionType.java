package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AcademicCommunityContributionType {

	WORK_IN_THE_INSTITUTIONS_AND_AUTHORITIES_OF_THE_FACULTY_AND_UNIVERSITY("Ucesce u radu organa i tela fakulteta i Univerziteta"),

	PROFESSIONAL_ORGANIZATIONS_CONDUCTION("Vodjenje profesionalnih i strukovnih organizacija"),

	MEETINGS_CONFERENCES_AND_EVENTS_CONDUCTION("Ucesce u organizaciji i vodjenju skupova, konferencija i manifestacija"),

	WORK_IN_COMMITTEES_AND_LEGISLATIVE_BODIES("Ucesce u radu odbora i zakonodavnih tela"),

	REVIEWS_IN_SCIENTIFIC_JOURNALS("Recenzije u naucnim casopisima");

	private final String title;

	private AcademicCommunityContributionType(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}

	public String getTitle() {
		return title;
	}

	public static AcademicCommunityContributionType getByTitle(String title) throws IllegalArgumentException {
		for (AcademicCommunityContributionType type : AcademicCommunityContributionType.values()) {
			if (type.title.equals(title)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Illegal Academic Community Contribution type code.");
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
