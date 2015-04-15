package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pct.domain.ProfessorPublication;
import com.pct.domain.PublicationCategory;
import com.pct.domain.enums.PublicationType;
import com.pct.domain.enums.deserializers.PublicationTypeEnumDeserializer;

public class ProfessorPublicationDto implements Serializable {

	private static final long serialVersionUID = 8325577395997414231L;

	@NotNull
	@JsonDeserialize(using = PublicationTypeEnumDeserializer.class)
	private PublicationType publicationType;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String isbn;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String title;

	@Length(max = 50)
	@SafeHtml
	private String journalTitle;

	@NotEmpty
	@Length(max = 400)
	@SafeHtml
	private String authors;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String publisher;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String pageRange;

	@NotNull
	@Min(0)
	private Integer quoted;

	@NotEmpty
	@Length(max = 4, min = 4)
	@SafeHtml
	private String year;

	private PublicationCategory publicationCategory;

	protected Long professorId;

	protected Long id;

	public ProfessorPublicationDto() {
		super();
	}

	public ProfessorPublicationDto(PublicationType publicationType, String isbn, String title, String journalTitle,
			String authors, String publisher, String pageRange, Integer quoted, String year,
			PublicationCategory publicationCategory, Long professorId, Long id) {
		super();
		this.publicationType = publicationType;
		this.isbn = isbn;
		this.title = title;
		this.journalTitle = journalTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.pageRange = pageRange;
		this.quoted = quoted;
		this.year = year;
		this.publicationCategory = publicationCategory;
		this.professorId = professorId;
		this.id = id;
	}

	public ProfessorPublicationDto(ProfessorPublication p) {
		super();
		this.publicationType = p.getPublicationType();
		this.isbn = p.getIsbn();
		this.title = p.getTitle();
		this.journalTitle = p.getJournalTitle();
		this.authors = p.getAuthors();
		this.publisher = p.getPublisher();
		this.pageRange = p.getPageRange();
		this.quoted = p.getQuoted();
		this.year = p.getYear();
		this.publicationCategory = p.getPublicationCategory();
		this.professorId = p.getProfessor().getId();
		this.id = p.getId();
	}

	public PublicationType getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
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

	public String getPageRange() {
		return pageRange;
	}

	public void setPageRange(String pageRange) {
		this.pageRange = pageRange;
	}

	public Integer getQuoted() {
		return quoted;
	}

	public void setQuoted(Integer quoted) {
		this.quoted = quoted;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public PublicationCategory getPublicationCategory() {
		return publicationCategory;
	}

	public void setPublicationCategory(PublicationCategory publicationCategory) {
		this.publicationCategory = publicationCategory;
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

}
