package com.pct.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "teaching_experience")
public class TeachingExperience extends AbstractEntity {

	private static final long serialVersionUID = 5777696461582944238L;
	
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "subjectId")
	@JsonBackReference(value = "subject")
	private Subject subject;

	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	public TeachingExperience() {
	}

	public TeachingExperience(Subject subject, Professor professor) {
		super();
		this.subject = subject;
		this.professor = professor;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
}
