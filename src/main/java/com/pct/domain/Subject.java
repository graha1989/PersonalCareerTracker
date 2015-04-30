package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "subject")
public class Subject extends AbstractEntity {

	private static final long serialVersionUID = -665761007999370325L;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "program", length = 50)
	private String program;

	@Column(name = "numberOfTheoreticalLessons")
	private Integer numberOfTheoreticalLessons;

	@Column(name = "numberOfPracticalLessons")
	private Integer numberOfPracticalLessons;

	@Column(name = "numberOfTeachingLessons")
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

	public Subject() {
	}

	public Subject(String name, String program, Integer numberOfTheoreticalLessons, Integer numberOfPracticalLessons,
			Integer numberOfTeachingLessons, Institution institution, Professor professor,
			StudiesThesisType studiesThesisType, Set<TeachingExperience> teachingExperiences) {
		super();
		this.name = name;
		this.program = program;
		this.numberOfTheoreticalLessons = numberOfTheoreticalLessons;
		this.numberOfPracticalLessons = numberOfPracticalLessons;
		this.numberOfTeachingLessons = numberOfTeachingLessons;
		this.institution = institution;
		this.professor = professor;
		this.studiesThesisType = studiesThesisType;
		this.teachingExperiences = teachingExperiences;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
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

}
