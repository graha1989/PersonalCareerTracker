package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity bean for Role table.
 * 
 * @author a.grahovac
 * 
 */
@Entity
@Table(name = "uloga")
public class Uloga extends AbstractEntity {
	
	private static final long serialVersionUID = 6608701814928793780L;
	
	@Column(name = "name", length = 30)
	private String name;
	
	public Uloga() {
		super();
	}

	public Uloga(String name) {
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
