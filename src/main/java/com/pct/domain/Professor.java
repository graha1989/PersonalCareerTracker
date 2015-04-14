package com.pct.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pct.domain.enums.PublicationType;

@Entity
@Table(name = "professor")
public class Professor extends AbstractEntity {

	private static final long serialVersionUID = 6438166967413025243L;

	@Column(name = "userName", nullable = true, length = 50)
	private String userName;

	@Column(name = "password", nullable = true, length = 50)
	private String password;

	@Column(name = "email", nullable = true, length = 50)
	private String email;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "surname", length = 50)
	private String surname;

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	private Set<LanguageExperience> languageExperiences = new HashSet<LanguageExperience>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
	private Set<Award> awards = new HashSet<Award>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "professor")
	private Set<ProjectExperience> projectExperiences = new HashSet<ProjectExperience>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "professor")
	private Set<ProfessorPublication> professorPublications = new HashSet<ProfessorPublication>();

	@OneToOne
	@JoinColumn(name = "ulogaId")
	private Uloga uloga;

	public Professor() {
		super();
	}

	public Professor(String userName, String password, String email, String name, String surname, String fathersName,
			Date dateOfBirth, String placeOfBirth, String countryOfBirth, String scientificArea,
			String specialScientificArea, Uloga uloga) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.fathersName = fathersName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.scientificArea = scientificArea;
		this.specialScientificArea = specialScientificArea;
		this.uloga = uloga;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public ProjectExperience getProjectExperienceById(Long id) {
		Iterator<ProjectExperience> it = projectExperiences.iterator();
		while (it.hasNext()) {
			ProjectExperience projectExperience = (ProjectExperience) it.next();
			if (projectExperience.getId().equals(id)) {
				return projectExperience;
			}
		}
		return null;
	}

	public void addProjectExperiences(ProjectExperience projectExperience) {
		if (projectExperiences != null) {
			projectExperience.setProfessor(this);
			this.projectExperiences.add(projectExperience);
		}
	}

	public ProjectExperience creatreNewProjectExperience(Project project, boolean professorLeader, Long id) {

		ProjectExperience projectExperience = new ProjectExperience(this, project, professorLeader);
		projectExperience.setId(id);
		project.addProjectExperiences(projectExperience);
		this.projectExperiences.add(projectExperience);

		return projectExperience;
	}

	public ProjectExperience editProjectExperience(Project project, boolean professorLeader, Long id) {

		ProjectExperience projectExperience = getProjectExperienceById(id);
		projectExperience.setProfessorLeader(professorLeader);
		project.addProjectExperiences(projectExperience);

		return projectExperience;
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

	public ProfessorPublication getProfessorPublicationById(Long id) {
		Iterator<ProfessorPublication> it = professorPublications.iterator();
		while (it.hasNext()) {
			ProfessorPublication professorPublication = (ProfessorPublication) it.next();
			if (professorPublication.getId().equals(id)) {
				return professorPublication;
			}
		}
		return null;
	}

	public void addProfessorPublications(ProfessorPublication professorPublication) {
		if (professorPublication != null) {
			professorPublication.setProfessor(this);
			this.professorPublications.add(professorPublication);
		}
	}

	public ProfessorPublication creatreNewProfessorPublication(PublicationCategory category, String isbn, String title,
			String journalTitle, String authors, String publisher, String pageRange, PublicationType publicationType,
			Integer quoted) {

		ProfessorPublication professorPublication = new ProfessorPublication(isbn, title, journalTitle, authors,
				publisher, pageRange, publicationType, quoted, category, this);
		if (category != null) {
			category.addProfessorPublication(professorPublication);
		}
		this.professorPublications.add(professorPublication);

		return professorPublication;
	}

	public ProfessorPublication editProfessorPublication(PublicationCategory category, String isbn, String title,
			String journalTitle, String authors, String publisher, String pageRange, Integer quoted,
			PublicationType publicationType, Long id) {

		ProfessorPublication professorPublication = getProfessorPublicationById(id);
		professorPublication.setIsbn(isbn);
		professorPublication.setTitle(title);
		professorPublication.setJournalTitle(journalTitle);
		professorPublication.setAuthors(authors);
		professorPublication.setPublisher(publisher);
		professorPublication.setPageRange(pageRange);
		professorPublication.setPublicationType(publicationType);
		professorPublication.setQuoted(quoted);
		professorPublication.setPublicationCategory(category);

		if (category != null) {
			category.addProfessorPublication(professorPublication);
		}

		return professorPublication;
	}

}
