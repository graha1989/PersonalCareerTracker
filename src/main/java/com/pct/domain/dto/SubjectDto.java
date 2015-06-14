package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.constants.MimeTypes;
import com.pct.domain.StudiesThesisType;
import com.pct.domain.Subject;

public class SubjectDto implements Serializable {

	private static final long serialVersionUID = 1126081919536192074L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private final String institutionType = MimeTypes.INSTITUTION_TYPE_FACULTY;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionName;

	@Nullable
	@Length(max = 50)
	@SafeHtml
	private String universityName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionCity;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String institutionCountry;

	@Nullable
	protected Long id;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String subjectName;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String studyProgram;

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

	@Nullable
	protected Long professorId;

	public SubjectDto() {
	}

	public SubjectDto(String institutionName, String universityName, String institutionCity, String institutionCountry,
			Long id, String subjectName, String studyProgram, StudiesThesisType studiesThesisType,
			Integer numberOfTheoreticalLessons, Integer numberOfPracticalLessons, Integer numberOfTeachingLessons,
			Long institutionId, Long professorId) {
		super();
		this.institutionName = institutionName;
		this.universityName = universityName;
		this.institutionCity = institutionCity;
		this.institutionCountry = institutionCountry;
		this.id = id;
		this.subjectName = subjectName;
		this.studyProgram = studyProgram;
		this.studiesThesisType = studiesThesisType;
		this.numberOfTheoreticalLessons = numberOfTheoreticalLessons;
		this.numberOfPracticalLessons = numberOfPracticalLessons;
		this.numberOfTeachingLessons = numberOfTeachingLessons;
		this.institutionId = institutionId;
		this.professorId = professorId;
	}

	public SubjectDto(Subject subject) {
		this.institutionName = subject.getInstitution().getName();
		this.universityName = subject.getInstitution().getUniversity();
		this.institutionCity = subject.getInstitution().getCity();
		this.institutionCountry = subject.getInstitution().getCountry();
		this.id = subject.getId();
		this.subjectName = subject.getName();
		this.studyProgram = subject.getStudyProgram().getName();
		this.studiesThesisType = subject.getStudiesThesisType();
		this.numberOfTheoreticalLessons = subject.getNumberOfTheoreticalLessons();
		this.numberOfPracticalLessons = subject.getNumberOfPracticalLessons();
		this.numberOfTeachingLessons = subject.getNumberOfTeachingLessons();
		this.institutionId = subject.getInstitution().getId();
		this.professorId = subject.getProfessor().getId();
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getInstitutionCity() {
		return institutionCity;
	}

	public void setInstitutionCity(String institutionCity) {
		this.institutionCity = institutionCity;
	}

	public String getInstitutionCountry() {
		return institutionCountry;
	}

	public void setInstitutionCountry(String institutionCountry) {
		this.institutionCountry = institutionCountry;
	}

	public String getInstitutionType() {
		return institutionType;
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

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

}
