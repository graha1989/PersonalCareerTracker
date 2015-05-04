package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.pct.domain.TeachingExperience;

public class TeachingExperienceDto implements Serializable {

	private static final long serialVersionUID = 3146290914337422784L;

	@NotNull
	SubjectDto subjectDto = new SubjectDto();

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long id;

	public TeachingExperienceDto() {
	}

	public TeachingExperienceDto(SubjectDto subjectDto, Long professorId, Long subjectId, Long id) {
		super();
		this.subjectDto = subjectDto;
		this.id = id;
	}

	public TeachingExperienceDto(TeachingExperience teachingExperience) {
		this.subjectDto.setSubjectName(teachingExperience.getSubject().getName());
		this.subjectDto.setStudyProgram(teachingExperience.getSubject().getProgram());
		/*this.subjectDto.setFacultyName(teachingExperience.getSubject().getInstitution().getName());
		this.subjectDto.setUniversityName(teachingExperience.getSubject().getInstitution().getUniversity());
		this.subjectDto.setFacultyCity(teachingExperience.getSubject().getInstitution().getCity());
		this.subjectDto.setFacultyCountry(teachingExperience.getSubject().getInstitution().getCountry());*/
		this.subjectDto.setStudiesThesisType(teachingExperience.getSubject().getStudiesThesisType());
		this.subjectDto.setNumberOfTheoreticalLessons(teachingExperience.getSubject().getNumberOfTheoreticalLessons());
		this.subjectDto.setNumberOfPracticalLessons(teachingExperience.getSubject().getNumberOfPracticalLessons());
		this.subjectDto.setNumberOfTeachingLessons(teachingExperience.getSubject().getNumberOfTeachingLessons());
		this.subjectDto.setInstitutionId(teachingExperience.getSubject().getInstitution().getId());
		this.subjectDto.setId(teachingExperience.getSubject().getId());
		this.professorId = teachingExperience.getProfessor().getId();
		this.id = teachingExperience.getId();
	}

	public SubjectDto getSubjectDto() {
		return subjectDto;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
