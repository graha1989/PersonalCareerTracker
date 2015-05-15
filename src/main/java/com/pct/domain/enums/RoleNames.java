package com.pct.domain.enums;

public enum RoleNames {

	ROLE_USER("ROLE_USER"),

	ROLE_ADMIN("ROLE_ADMIN");

	private String name;

	private RoleNames(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
