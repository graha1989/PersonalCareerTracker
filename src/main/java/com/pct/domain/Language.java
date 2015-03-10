package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language extends AbstractEntity {

	private static final long serialVersionUID = 8036136359901638926L;
	
	@Column(name = "name", length = 20)
	private String name;

	public Language() {
		super();
	}

	public Language(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
