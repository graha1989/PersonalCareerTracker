package com.pct.domain.dto;

import java.io.Serializable;

import com.pct.domain.LanguageExperience;

public class LanguageExperienceDto implements Serializable {

	private static final long serialVersionUID = 7826480051632774426L;

//	@NotNull
//	private Profesor professor;
	private Long profesorId;

//	@NotNull
//	private Language language;

	private String languageName;
	
	private boolean writing;

	private boolean reading;

	private boolean pronouncing;

	protected Long id;

	public LanguageExperienceDto() {
		super();
	}

	public LanguageExperienceDto(Long professorId, String languageName, boolean writing, boolean reading,
			boolean pronouncing, Long id) {
		super();
		this.profesorId = professorId;
		this.languageName = languageName;
		this.writing = writing;
		this.reading = reading;
		this.pronouncing = pronouncing;
		this.id = id;
	}

	public LanguageExperienceDto(LanguageExperience languageExperience) {
		this.profesorId = languageExperience.getProfesor().getId();
		this.languageName = languageExperience.getLanguage().getName();
		this.writing = languageExperience.isWriting();
		this.reading = languageExperience.isReading();
		this.pronouncing = languageExperience.isPronouncing();
		this.id = languageExperience.getId();
	}
//
//	public Profesor getProfessor() {
//		return professor;
//	}
//
//	public void setProfessor(Profesor professor) {
//		this.professor = professor;
//	}
//
//	public Language getLanguage() {
//		return language;
//	}
//
//	public void setLanguage(Language language) {
//		this.language = language;
//	}
	

	public boolean isWriting() {
		return writing;
	}

	public Long getProfesorId() {
		return profesorId;
	}

	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
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
