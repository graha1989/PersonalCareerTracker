package com.pct.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "id")
public class Professor extends User {

	private static final long serialVersionUID = -3604705023149327325L;

	@Column(name = "fathersName", length = 50)
	private String fathersName;

	@Column(name = "dateOfBirth")
	private Date dateOfBirth;

	@Column(name = "placeOfBirth", length = 50)
	private String placeOfBirth;

	@Column(name = "countryOfBirth", length = 50)
	private String countryOfBirth;

	@Column(name = "scientificArea", length = 50)
	private String scientificArea;

	@Column(name = "specialScientificArea", length = 50)
	private String specialScientificArea;

	@Column(name = "title", length = 30)
	private String title;

	// Institution name + university
	@Column(name = "institution", length = 100)
	private String institution;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	private Set<LanguageExperience> languageExperiences = new HashSet<LanguageExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<Award> awards = new HashSet<Award>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<ProjectExperience> projectExperiences = new HashSet<ProjectExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<ProfessorPublication> professorPublications = new HashSet<ProfessorPublication>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<InternationalPublication> internationalPublications = new HashSet<InternationalPublication>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<Studies> studies = new HashSet<Studies>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<TeachingExperience> teachingExperiences = new HashSet<TeachingExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<Survey> surveys = new HashSet<Survey>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<ScientificProfessionalOrgMem> memberships = new HashSet<ScientificProfessionalOrgMem>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<AcademicCommunityContribution> contributions = new HashSet<AcademicCommunityContribution>();

	@OneToOne(mappedBy = "professor")
	private ProjectLeader projectLeader;

	@OneToOne(mappedBy = "professor")
	private CommissionMember commissionMember;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	@JsonIgnore
	private Set<Contest> contests = new HashSet<Contest>();

	public Professor() {
	}

	public Professor(String fathersName, Date dateOfBirth, String placeOfBirth, String countryOfBirth, String scientificArea,
			String specialScientificArea, String title, String institution, Set<LanguageExperience> languageExperiences, Set<Award> awards,
			Set<ProjectExperience> projectExperiences, Set<ProfessorPublication> professorPublications,
			Set<InternationalPublication> internationalPublications, Set<WorkExperience> workExperiences, Set<Studies> studies,
			Set<TeachingExperience> teachingExperiences, Set<Survey> surveys, Set<ScientificProfessionalOrgMem> memberships,
			Set<AcademicCommunityContribution> contributions, ProjectLeader projectLeader, CommissionMember commissionMember, Set<Contest> contests) {
		this.fathersName = fathersName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.scientificArea = scientificArea;
		this.specialScientificArea = specialScientificArea;
		this.title = title;
		this.institution = institution;
		this.languageExperiences = languageExperiences;
		this.awards = awards;
		this.projectExperiences = projectExperiences;
		this.professorPublications = professorPublications;
		this.internationalPublications = internationalPublications;
		this.workExperiences = workExperiences;
		this.studies = studies;
		this.teachingExperiences = teachingExperiences;
		this.surveys = surveys;
		this.projectLeader = projectLeader;
		this.commissionMember = commissionMember;
		this.memberships = memberships;
		this.contributions = contributions;
		this.contests = contests;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(String scientificArea) {
		this.scientificArea = scientificArea;
	}

	public String getSpecialScientificArea() {
		return specialScientificArea;
	}

	public void setSpecialScientificArea(String specialScientificArea) {
		this.specialScientificArea = specialScientificArea;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public void setLanguageExperiences(Set<LanguageExperience> languageExperiences) {
		this.languageExperiences.clear();

		if (languageExperiences != null) {
			this.languageExperiences.addAll(languageExperiences);
		}
	}

	public Set<LanguageExperience> getLanguageExperiences() {
		return languageExperiences;
	}

	public void addLanguageExperience(LanguageExperience languageExperiences) {
		if (languageExperiences != null) {
			this.languageExperiences.add(languageExperiences);
		}
	}

	public void setAwards(Set<Award> awards) {
		this.awards.clear();

		if (awards != null) {
			this.awards.addAll(awards);
		}
	}

	public Set<Award> getAwards() {
		return awards;
	}

	public void addAward(Award award) {
		if (awards != null) {
			this.awards.add(award);
		}
	}

	public Set<ProjectExperience> getProjectExperiences() {
		return projectExperiences;
	}

	public void setProjectExperiences(Set<ProjectExperience> projectExperiences) {
		this.projectExperiences.clear();

		if (projectExperiences != null) {
			this.projectExperiences.addAll(projectExperiences);
		}
	}

	public Set<ProfessorPublication> getProfessorPublications() {
		return professorPublications;
	}

	public void setProfessorPublications(Set<ProfessorPublication> professorPublications) {
		this.professorPublications.clear();

		if (professorPublications != null) {
			this.professorPublications.addAll(professorPublications);
		}
	}

	public Set<InternationalPublication> getInternationalPublications() {
		return internationalPublications;
	}

	public void setInternationalPublications(Set<InternationalPublication> internationalPublications) {
		this.internationalPublications.clear();

		if (internationalPublications != null) {
			this.internationalPublications.addAll(internationalPublications);
		}
	}

	public Set<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(Set<WorkExperience> workExperiences) {
		this.workExperiences.clear();

		if (workExperiences != null) {
			this.workExperiences.addAll(workExperiences);
		}
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

	public Set<ScientificProfessionalOrgMem> getMemberships() {
		return memberships;
	}

	public void setMemberships(Set<ScientificProfessionalOrgMem> memberships) {
		this.memberships.clear();

		if (memberships != null) {
			this.memberships.addAll(memberships);
		}
	}

	public Set<AcademicCommunityContribution> getContributions() {
		return contributions;
	}

	public void setContributions(Set<AcademicCommunityContribution> contributions) {
		this.contributions.clear();

		if (contributions != null) {
			this.contributions.addAll(contributions);
		}
	}

	public ProjectLeader getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(ProjectLeader projectLeader) {
		this.projectLeader = projectLeader;
	}

	public CommissionMember getCommissionMember() {
		return commissionMember;
	}

	public void setCommissionMember(CommissionMember commissionMember) {
		this.commissionMember = commissionMember;
	}

	public Set<Contest> getContests() {
		return contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests.clear();

		if (contests != null) {
			this.contests.addAll(contests);
		}
	}

}
