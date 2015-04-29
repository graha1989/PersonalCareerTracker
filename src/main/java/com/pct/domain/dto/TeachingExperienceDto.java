package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.StudiesThesisType;
import com.pct.domain.TeachingExperience;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;

public class TeachingExperienceDto implements Serializable {

	private static final long serialVersionUID = 3146290914337422784L;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String subjectName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String studyProgram;

	@JsonDeserialize(using = InstitutionTypeEnumDeserializer.class)
	private final InstitutionType institutionType = InstitutionType.FACULTY;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String facultyName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String universityName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String facultyCity;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String facultyCountry;

	@NotNull
	private StudiesThesisType studiesThesisType;

	@NotNull
	private Integer numberOfTheoreticalLessons;

	@NotNull
	private Integer numberOfPracticalLessons;

	@NotNull
	private Integer numberOfTeachingLessons;

	@NotNull
	protected Long professorId;

	@Nullable
	protected Long institutionId;

	@Nullable
	protected Long subjectId;

	@Nullable
	protected Long id;

	public TeachingExperienceDto() {
	}

	public TeachingExperienceDto(String subjectName, String studyProgram, String facultyName, String universityName,
			String facultyCity, String facultyCountry, StudiesThesisType studiesThesisType,
			Integer numberOfTheoreticalLessons, Integer numberOfPracticalLessons, Integer numberOfTeachingLessons,
			Long professorId, Long institutionId, Long subjectId, Long id) {
		super();
		this.subjectName = subjectName;
		this.studyProgram = studyProgram;
		this.facultyName = facultyName;
		this.universityName = universityName;
		this.facultyCity = facultyCity;
		this.facultyCountry = facultyCountry;
		this.studiesThesisType = studiesThesisType;
		this.numberOfTheoreticalLessons = numberOfTheoreticalLessons;
		this.numberOfPracticalLessons = numberOfPracticalLessons;
		this.numberOfTeachingLessons = numberOfTeachingLessons;
		this.professorId = professorId;
		this.institutionId = institutionId;
		this.subjectId = subjectId;
		this.id = id;
	}

	public TeachingExperienceDto(TeachingExperience teachingExperience) {
		super();
		this.subjectName = teachingExperience.getSubject().getName();
		this.studyProgram = teachingExperience.getSubject().getProgram();
		this.facultyName = teachingExperience.getSubject().getInstitution().getName();
		this.universityName = teachingExperience.getSubject().getInstitution().getUniversity();
		this.facultyCity = teachingExperience.getSubject().getInstitution().getCity();
		this.facultyCountry = teachingExperience.getSubject().getInstitution().getCountry();
		this.studiesThesisType = teachingExperience.getSubject().getStudiesThesisType();
		this.numberOfTheoreticalLessons = teachingExperience.getSubject().getNumberOfTheoreticalLessons();
		this.numberOfPracticalLessons = teachingExperience.getSubject().getNumberOfPracticalLessons();
		this.numberOfTeachingLessons = teachingExperience.getSubject().getNumberOfTeachingLessons();
		this.professorId = teachingExperience.getProfessor().getId();
		this.institutionId = teachingExperience.getSubject().getInstitution().getId();
		this.subjectId = teachingExperience.getSubject().getId();
		this.id = teachingExperience.getId();
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(String studyProgram) {
		this.studyProgram = studyProgram;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getFacultyCity() {
		return facultyCity;
	}

	public void setFacultyCity(String facultyCity) {
		this.facultyCity = facultyCity;
	}

	public String getFacultyCountry() {
		return facultyCountry;
	}

	public void setFacultyCountry(String facultyCountry) {
		this.facultyCountry = facultyCountry;
	}

	public StudiesThesisType getStudiesThesisType() {
		return studiesThesisType;
	}

	public void setStudiesThesisType(StudiesThesisType studiesThesisType) {
		this.studiesThesisType = studiesThesisType;
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

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
