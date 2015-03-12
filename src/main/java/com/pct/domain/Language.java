package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language extends AbstractEntity {

	private static final long serialVersionUID = 8036136359901638926L;
	
	@Column(name = "name", length = 20)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	private Set<LanguageExperience> languageExperiences;
	
	public Language() {
		super();
	}

	public Language(String name) {
		super();
		this.name = name;
		this.languageExperiences = new HashSet<LanguageExperience>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<LanguageExperience> getLanguageExperiences() {
		return languageExperiences;
	}

	public void setLanguageExperiences(Set<LanguageExperience> languageExperiences) {
		
		this.languageExperiences.clear();

		if (languageExperiences != null) {
			this.languageExperiences.addAll(languageExperiences);
		}
	}
	
	public void addLanguageExperience(LanguageExperience languageExperiences) {
		if (languageExperiences != null) {
			this.languageExperiences.add(languageExperiences);
		}
	}
	
}
