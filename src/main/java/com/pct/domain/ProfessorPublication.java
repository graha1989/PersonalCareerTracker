package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pct.domain.enums.PublicationType;

@Entity
@Table(name = "professor_publication")
public class ProfessorPublication extends AbstractEntity {

	private static final long serialVersionUID = -8371039188814271676L;

	@Column(name = "isbn", unique = true, length = 20)
	private String isbn;

	@Column(name = "title", length = 50)
	private String title;

	@Column(name = "authors")
	@Lob
	private String authors;

	@Column(name = "publisher", length = 50)
	private String publisher;

	@Column(name = "pageRange", length = 15)
	private String pageRange;

	@Enumerated(EnumType.STRING)
	@Column(name = "publicationType")
	private PublicationType publicationType;

	@Column(name = "quoted")
	private Integer quoted;

	@ManyToOne
	@JoinColumn(name = "publicationCategoryId")
	@JsonBackReference
	private PublicationCategory publicationCategory;

	@ManyToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference
	private Professor professor;

	public ProfessorPublication() {
		super();
	}

	public ProfessorPublication(Professor professor, PublicationCategory category, String isbn, String title,
			String authors, String publisher, String pageRange, Integer quoted) {
		this.professor = professor;
		this.publicationCategory = category;
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.pageRange = pageRange;
		this.quoted = quoted;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPageRange() {
		return pageRange;
	}

	public void setPageRange(String pageRange) {
		this.pageRange = pageRange;
	}

	public PublicationType getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
	}

	public Integer getQuoted() {
		return quoted;
	}

	public void setQuoted(Integer quoted) {
		this.quoted = quoted;
	}

	public PublicationCategory getPublicationCategory() {
		return publicationCategory;
	}

	public void setPublicationCategory(PublicationCategory publicationCategory) {
		this.publicationCategory = publicationCategory;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
