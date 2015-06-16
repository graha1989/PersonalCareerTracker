package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "subject")
public class Subject extends AbstractEntity {

	private static final long serialVersionUID = -665761007999370325L;

	@Column(name = "name", length = 100)
	private String name;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "studyProgramId", nullable = true)
	@JsonBackReference(value = "studyProgram")
	private StudyProgram studyProgram;

	@Column(name = "numberOfTheoreticalLessons", nullable = true)
	private Integer numberOfTheoreticalLessons;

	@Column(name = "numberOfPracticalLessons", nullable = true)
	private Integer numberOfPracticalLessons;

	@Column(name = "numberOfTeachingLessons", nullable = true)
	private Integer numberOfTeachingLessons;

	@OneToOne
	@JoinColumn(name = "institutionId")
	private Institution institution;

	@OneToOne
	@JoinColumn(name = "professorId")
	private Professor professor;

	@OneToOne
	@JoinColumn(name = "studiesThesisTypeId")
	private StudiesThesisType studiesThesisType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "subject")
	@JsonIgnore
	private Set<TeachingExperience> teachingExperiences = new HashSet<TeachingExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "subject")
	@JsonIgnore
	private Set<Survey> surveys = new HashSet<Survey>();

	@Nullable
	@Column(name = "active", nullable = true)
	private Boolean active;

	@NotNull
	@Column(name = "seminarOrTeachingAbroad", nullable = true)
	private Boolean seminarOrTeachingAbroad;

	public Subject() {
	}

	public Subject(String name, StudyProgram studyProgram, Integer numberOfTheoreticalLessons,
			Integer numberOfPracticalLessons, Integer numberOfTeachingLessons, Institution institution,
			Professor professor, StudiesThesisType studiesThesisType, Set<TeachingExperience> teachingExperiences,
			Set<Survey> surveys, Boolean active, Boolean seminarOrTeachingAbroad) {
		this.name = name;
		this.studyProgram = studyProgram;
		this.numberOfTheoreticalLessons = numberOfTheoreticalLessons;
		this.numberOfPracticalLessons = numberOfPracticalLessons;
		this.numberOfTeachingLessons = numberOfTeachingLessons;
		this.institution = institution;
		this.professor = professor;
		this.studiesThesisType = studiesThesisType;
		this.teachingExperiences = teachingExperiences;
		this.surveys = surveys;
		this.active = active;
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	public Integer getNumberOfTheoreticalLessons() {
		return numberOfTheoreticalLessons;
	}

	public void setNumberOfTheoreticalLessons(Integer numberOfTheoreticalLessons) {
		this.numberOfTheoreticalLessons = numberOfTheoreticalLessons;
	}

	public Integer getNumberOfPracticalLessons() {
		return numberOfPracticalLessons;
	}

	public void setNumberOfPracticalLessons(Integer numberOfPracticalLessons) {
		this.numberOfPracticalLessons = numberOfPracticalLessons;
	}

	public Integer getNumberOfTeachingLessons() {
		return numberOfTeachingLessons;
	}

	public void setNumberOfTeachingLessons(Integer numberOfTeachingLessons) {
		this.numberOfTeachingLessons = numberOfTeachingLessons;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public StudiesThesisType getStudiesThesisType() {
		return studiesThesisType;
	}

	public void setStudiesThesisType(StudiesThesisType studiesThesisType) {
		this.studiesThesisType = studiesThesisType;
	}

	public Set<TeachingExperience> getTeachingExperiences() {
		return teachingExperiences;
	}

	public void setTeachingExperiences(Set<TeachingExperience> teachingExperiences) {
		this.teachingExperiences.clear();

		if (teachingExperiences != null) {
			this.teachingExperiences.addAll(teachingExperiences);
		}
	}

	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys.clear();

		if (surveys != null) {
			this.surveys.addAll(surveys);
		}
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getSeminarOrTeachingAbroad() {
		return seminarOrTeachingAbroad;
	}

	public void setSeminarOrTeachingAbroad(Boolean seminarOrTeachingAbroad) {
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}

}
