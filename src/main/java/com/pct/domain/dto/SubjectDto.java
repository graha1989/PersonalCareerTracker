package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.StudiesThesisType;
import com.pct.domain.Subject;
import com.pct.domain.enums.InstitutionType;
import com.pct.domain.enums.deserializers.InstitutionTypeEnumDeserializer;

public class SubjectDto implements Serializable {

	private static final long serialVersionUID = 1126081919536192074L;

	@NotNull
	protected Long id;

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

	@Nullable
	protected Long institutionId;

	public SubjectDto() {
	}

	public SubjectDto(Long id, String subjectName, String studyProgram, String facultyName, String universityName,
			String facultyCity, String facultyCountry, StudiesThesisType studiesThesisType,
			Integer numberOfTheoreticalLessons, Integer numberOfPracticalLessons, Integer numberOfTeachingLessons,
			Long institutionId) {
		super();
		this.id = id;
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
		this.institutionId = institutionId;
	}

	public SubjectDto(Subject subject) {
		this.id = subject.getId();
		this.subjectName = subject.getName();
		this.studyProgram = subject.getProgram();
		this.facultyName = subject.getInstitution().getName();
		this.universityName = subject.getInstitution().getUniversity();
		this.facultyCity = subject.getInstitution().getCity();
		this.facultyCountry = subject.getInstitution().getCountry();
		this.studiesThesisType = subject.getStudiesThesisType();
		this.numberOfTheoreticalLessons = subject.getNumberOfTheoreticalLessons();
		this.numberOfPracticalLessons = subject.getNumberOfPracticalLessons();
		this.numberOfTeachingLessons = subject.getNumberOfTeachingLessons();
		this.institutionId = subject.getInstitution().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}
	
}
