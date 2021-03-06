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
@Table(name = "study_program")
public class StudyProgram extends AbstractEntity {

	private static final long serialVersionUID = 3557974265999594784L;

	@Column(name = "name", length = 50)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studyProgram")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "studyProgram")
	@JsonIgnore
	private Set<Studies> studies = new HashSet<Studies>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studyProgram")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "studyProgram")
	@JsonIgnore
	private Set<Subject> subjects = new HashSet<Subject>();

	public StudyProgram() {
	}

	public StudyProgram(String name, Set<Studies> studies, Set<Subject> subjects) {
		this.name = name;
		this.studies = studies;
		this.subjects = subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Studies> getStudies() {
		return studies;
	}

	public void setStudies(Set<Studies> studies) {
		this.studies.clear();

		if (studies != null) {
			this.studies.addAll(studies);
		}
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects.clear();

		if (subjects != null) {
			this.subjects.addAll(subjects);
		}
	}
	
}
