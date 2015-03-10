package com.pct.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "language_experience")
public class LanguageExperience extends AbstractEntity {

	private static final long serialVersionUID = -3256839276380362358L;
	
	@Column(name = "writing")
	private boolean writing;
	
	@Column(name = "reading")
	private boolean reading;
	
	@Column(name = "pronouncing")
	private boolean pronouncing;
	
	@OneToOne
	@JoinColumn(name = "profesorId")
	private Profesor profesor;
	
	@OneToOne
	@JoinColumn(name = "languageId")
	private Language language;
	
	public LanguageExperience() {
		super();
	}

	public LanguageExperience(boolean writing, boolean reading, boolean pronouncing, Profesor profesor,
			Language language) {
		super();
		this.writing = writing;
		this.reading = reading;
		this.pronouncing = pronouncing;
		this.profesor = profesor;
		this.language = language;
	}

	public boolean isWriting() {
		return writing;
	}

	public void setWriting(boolean writing) {
		this.writing = writing;
	}

	public boolean isReading() {
		return reading;
	}

	public void setReading(boolean reading) {
		this.reading = reading;
	}

	public boolean isPronouncing() {
		return pronouncing;
	}

	public void setPronouncing(boolean pronouncing) {
		this.pronouncing = pronouncing;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
}
