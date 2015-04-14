package com.pct.domain;

import javax.annotation.Nullable;
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
import com.pct.domain.enums.deserializers.PublicationTypeEnumDeserializer;

@Entity
@Table(name = "international_publication")
public class InternationalPublication extends AbstractEntity {

	private static final long serialVersionUID = 7937309852122160026L;

	@Column(name = "isbn", unique = true, length = 30)
	private String isbn;

	@Column(name = "journalTitle", length = 50)
	@Nullable
	private String journalTitle;

	@Column(name = "title", length = 50)
	private String title;

	@Column(name = "authors")
	@Lob
	private String authors;

	@Column(name = "publisher", length = 50)
	private String publisher;

	@Column(name = "pagesWithQuotes", length = 100)
	private String pagesWithQuotes;

	@Enumerated(EnumType.STRING)
	@Column(name = "publicationType")
	@JsonDeserialize(using = PublicationTypeEnumDeserializer.class)
	private PublicationType publicationType;

	@ManyToOne
	@JoinColumn(name = "publicationCategoryId")
	@JsonBackReference(value = "publicationCategory")
	private PublicationCategory publicationCategory;

	@ManyToOne
	@JoinColumn(name = "professorId")
	@JsonBackReference(value = "professor")
	private Professor professor;

	public InternationalPublication() {
		super();
	}

	public InternationalPublication(String isbn, String title, String jurnalTitle, String authors, String publisher,
			String pagesWithQuotes, PublicationType publicationType, PublicationCategory publicationCategory,
			Professor professor) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.journalTitle = jurnalTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.pagesWithQuotes = pagesWithQuotes;
		this.publicationType = publicationType;
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

	public String getJournalTitle() {
		return journalTitle;
	}

	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
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

	public String getPagesWithQuotes() {
		return pagesWithQuotes;
	}

	public void setPagesWithQuotes(String pagesWithQuotes) {
		this.pagesWithQuotes = pagesWithQuotes;
	}

	public PublicationType getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
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
