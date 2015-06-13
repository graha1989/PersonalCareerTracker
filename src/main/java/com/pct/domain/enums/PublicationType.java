package com.pct.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * TODO Remove
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PublicationType {

	SCIENTIFIC("Naučna"),

	TEXTBOOK("Udžbenik"),
	
	OTHER("Ostala didaktička sredstva");
	
	@JsonProperty("title")
	private final String title;

	private PublicationType(String title) {
		this.title = title;
	}

	public String getName() {
		return name();
	}
	
	public String getTitle() {
		return title;
	}
	
	public static PublicationType getByTitle(String title) throws IllegalArgumentException {
		for (PublicationType publicationType : PublicationType.values()) {
			if (publicationType.title.equals(title)) {
				return publicationType;
			}
		}
		throw new IllegalArgumentException("Illegal publication type code");
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
