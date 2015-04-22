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

import org.hibernate.annotations.Cascade;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	private Set<InternationalPublication> internationalPublications = new HashSet<InternationalPublication>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "professor")
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>();

	@OneToOne
	@JoinColumn(name = "ulogaId")
	private Uloga uloga;

	public Professor() {
	}

	public Professor(String userName, String password, String email, String name, String surname, String fathersName,
			Date dateOfBirth, String placeOfBirth, String countryOfBirth, String scientificArea,
			String specialScientificArea, Set<LanguageExperience> languageExperiences, Set<Award> awards,
			Set<ProjectExperience> projectExperiences, Set<ProfessorPublication> professorPublications,
			Set<InternationalPublication> internationalPublications, Set<WorkExperience> workExperiences, Uloga uloga) {
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
		this.languageExperiences = languageExperiences;
		this.awards = awards;
		this.projectExperiences = projectExperiences;
		this.professorPublications = professorPublications;
		this.internationalPublications = internationalPublications;
		this.workExperiences = workExperiences;
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
			Integer quoted, String year) {

		ProfessorPublication professorPublication = new ProfessorPublication(isbn, title, journalTitle, authors,
				publisher, pageRange, publicationType, quoted, category, this, year);
		if (category != null) {
			category.addProfessorPublication(professorPublication);
		}
		this.professorPublications.add(professorPublication);

		return professorPublication;
	}

	public ProfessorPublication editProfessorPublication(PublicationCategory category, String isbn, String title,
			String journalTitle, String authors, String publisher, String pageRange, Integer quoted, String year,
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
		professorPublication.setYear(year);

		if (category != null) {
			category.addProfessorPublication(professorPublication);
		}

		return professorPublication;
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

	public InternationalPublication getInternationalPublicationById(Long id) {
		Iterator<InternationalPublication> it = internationalPublications.iterator();
		while (it.hasNext()) {
			InternationalPublication internationalPublication = (InternationalPublication) it.next();
			if (internationalPublication.getId().equals(id)) {
				return internationalPublication;
			}
		}
		return null;
	}

	public void addInternationalPublications(InternationalPublication internationalPublication) {
		if (internationalPublication != null) {
			internationalPublication.setProfessor(this);
			this.internationalPublications.add(internationalPublication);
		}
	}

	public InternationalPublication creatreNewInternationalPublication(PublicationCategory category, String isbn,
			String title, String journalTitle, String authors, String publisher, String pagesWithQuotes,
			PublicationType publicationType, String year) {

		InternationalPublication internationalPublication = new InternationalPublication(isbn, title, journalTitle,
				authors, publisher, pagesWithQuotes, year, publicationType, category, this);
		if (category != null) {
			category.addInternationalPublication(internationalPublication);
		}
		this.internationalPublications.add(internationalPublication);

		return internationalPublication;
	}

	public InternationalPublication editInternationalPublication(PublicationCategory category, String isbn,
			String title, String journalTitle, String authors, String publisher, String pagesWithQuotes, String year,
			PublicationType publicationType, Long id) {

		InternationalPublication internationalPublication = getInternationalPublicationById(id);
		internationalPublication.setIsbn(isbn);
		internationalPublication.setTitle(title);
		internationalPublication.setJournalTitle(journalTitle);
		internationalPublication.setAuthors(authors);
		internationalPublication.setPublisher(publisher);
		internationalPublication.setPagesWithQuotes(pagesWithQuotes);
		internationalPublication.setPublicationType(publicationType);
		internationalPublication.setPublicationCategory(category);
		internationalPublication.setYear(year);

		if (category != null) {
			category.addInternationalPublication(internationalPublication);
		}

		return internationalPublication;
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

}
