package com.pct.domain.dto;

import java.io.Serializable;
import java.util.Date;

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

	@NotNull
	private Date teachingStartDate;

	@Nullable
	private Date teachingEndDate;

	@NotNull
	private Boolean seminarOrTeachingAbroad;

	public TeachingExperienceDto() {
	}
	
	public TeachingExperienceDto(SubjectDto subjectDto, Long professorId, Long id, Date teachingStartDate,
			Date teachingEndDate, Boolean seminarOrTeachingAbroad) {
		this.subjectDto = subjectDto;
		this.professorId = professorId;
		this.id = id;
		this.teachingStartDate = teachingStartDate;
		this.teachingEndDate = teachingEndDate;
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}

	public TeachingExperienceDto(TeachingExperience teachingExperience) {
		this.subjectDto.setSubjectName(teachingExperience.getSubject().getName());
		this.subjectDto.setStudyProgram((teachingExperience.getSubject().getStudyProgram() !=null ? teachingExperience.getSubject().getStudyProgram().getName() : null));
		this.subjectDto.setInstitutionName(teachingExperience.getSubject().getInstitution().getName());
		this.subjectDto.setUniversityName(teachingExperience.getSubject().getInstitution().getUniversity());
		this.subjectDto.setInstitutionCity(teachingExperience.getSubject().getInstitution().getCity());
		this.subjectDto.setInstitutionCountry(teachingExperience.getSubject().getInstitution().getCountry());
		this.subjectDto.setStudiesThesisType(teachingExperience.getSubject().getStudiesThesisType());
		this.subjectDto.setNumberOfTheoreticalLessons(teachingExperience.getSubject().getNumberOfTheoreticalLessons());
		this.subjectDto.setNumberOfPracticalLessons(teachingExperience.getSubject().getNumberOfPracticalLessons());
		this.subjectDto.setNumberOfTeachingLessons(teachingExperience.getSubject().getNumberOfTeachingLessons());
		this.subjectDto.setInstitutionId(teachingExperience.getSubject().getInstitution().getId());
		this.subjectDto.setId(teachingExperience.getSubject().getId());
		this.professorId = teachingExperience.getProfessor().getId();
		this.id = teachingExperience.getId();
		this.teachingStartDate = teachingExperience.getTeachingStartDate();
		this.teachingEndDate = teachingExperience.getTeachingEndDate();
		this.seminarOrTeachingAbroad = teachingExperience.getSeminarOrTeachingAbroad();
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

	public Date getTeachingStartDate() {
		return teachingStartDate;
	}

	public void setTeachingStartDate(Date teachingStartDate) {
		this.teachingStartDate = teachingStartDate;
	}

	public Date getTeachingEndDate() {
		return teachingEndDate;
	}

	public void setTeachingEndDate(Date teachingEndDate) {
		this.teachingEndDate = teachingEndDate;
	}

	public Boolean getSeminarOrTeachingAbroad() {
		return seminarOrTeachingAbroad;
	}

	public void setSeminarOrTeachingAbroad(Boolean seminarOrTeachingAbroad) {
		this.seminarOrTeachingAbroad = seminarOrTeachingAbroad;
	}

}
