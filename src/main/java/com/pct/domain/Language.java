package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "language")
public class Language extends AbstractEntity {

	private static final long serialVersionUID = 8036136359901638926L;
	
	@Column(name = "name", length = 20)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "language")
	@JsonIgnore
	private Set<LanguageExperience> languageExperiences = new HashSet<LanguageExperience>();
	
	public Language() {
	}

	public Language(String name, Set<LanguageExperience> languageExperiences) {
		super();
		this.name = name;
		this.languageExperiences = languageExperiences;
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
	
}
