package com.pct.domain.dto;

import java.io.Serializable;

import com.pct.domain.LanguageExperience;

public class LanguageExperienceDto implements Serializable {

	private static final long serialVersionUID = 7826480051632774426L;

	protected Long professorId;

	protected Long languageId;

	private String languageName;
	
	private boolean writing;

	private boolean reading;

	private boolean pronouncing;

	protected Long id;

	public LanguageExperienceDto() {
		super();
	}

	public LanguageExperienceDto(Long professorId, Long languageId, String languageName, boolean writing, boolean reading,
			boolean pronouncing, Long id) {
		super();
		this.professorId = professorId;
		this.languageId = languageId;
		this.languageName = languageName;
		this.writing = writing;
		this.reading = reading;
		this.pronouncing = pronouncing;
		this.id = id;
	}

	public LanguageExperienceDto(LanguageExperience languageExperience) {
		this.professorId = languageExperience.getProfesor().getId();
		this.languageId = languageExperience.getLanguage().getId();
		this.languageName = languageExperience.getLanguage().getName();
		this.writing = languageExperience.isWriting();
		this.reading = languageExperience.isReading();
		this.pronouncing = languageExperience.isPronouncing();
		this.id = languageExperience.getId();
	}
	
	public Long getProfesorId() {
		return professorId;
	}

	public void setProfesorId(Long professorId) {
		this.professorId = professorId;
	}
	
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
