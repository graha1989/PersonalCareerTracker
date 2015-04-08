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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.enums.PublicationType;
import com.pct.domain.enums.PublicationTypeEnumDeserializer;

@Entity
@Table(name = "professor_publication")
public class ProfessorPublication extends AbstractEntity {

	private static final long serialVersionUID = -7256632218559132120L;

	@Column(name = "isbn", unique = true, length = 30)
	private String isbn;

	@Column(name = "title", length = 50)
	private String title;

	@Column(name = "authors")
	@Lob
	private String authors;

	@Column(name = "publisher", length = 50)
	private String publisher;

	@Column(name = "pageRange", length = 55)
	private String pageRange;

	@Enumerated(EnumType.STRING)
	@Column(name = "publicationType")
	@JsonDeserialize(using = PublicationTypeEnumDeserializer.class)
	private PublicationType publicationType;

	@Column(name = "quoted")
	private Integer quoted;

	@ManyToOne
	@JoinColumn(name = "publicationCategoryId")
	@JsonBackReference(value = "publicationCategory")
	private PublicationCategory publicationCategory;

	@ManyToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	public ProfessorPublication() {
		super();
	}

	public ProfessorPublication(String isbn, String title, String authors, String publisher, String pageRange,
			PublicationType publicationType, Integer quoted, PublicationCategory publicationCategory,
			Professor professor) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.pageRange = pageRange;
		this.publicationType = publicationType;
		this.quoted = quoted;
		this.publicationCategory = publicationCategory;
		this.professor = professor;
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
