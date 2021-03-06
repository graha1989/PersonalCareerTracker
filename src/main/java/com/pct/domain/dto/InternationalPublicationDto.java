package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.InternationalPublication;

public class InternationalPublicationDto implements Serializable {

	private static final long serialVersionUID = -5734666020212773965L;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String publicationType;

	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String isbn;

	@NotEmpty
	@Length(max = 100)
	@SafeHtml
	private String title;

	@Length(max = 100)
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
	private String pagesWithQuotes;

	@NotEmpty
	@Length(max = 4, min = 4)
	@SafeHtml
	private String year;

	@NotNull
	@Length(max = 6)
	private String origin;

	private PublicationCategoryDto publicationCategoryDto;

	protected Long professorId;

	protected Long id;

	public InternationalPublicationDto() {
	}

	public InternationalPublicationDto(String publicationType, String isbn, String title, String journalTitle, String authors, String publisher,
			String pagesWithQuotes, String year, String origin, Integer quoted, PublicationCategoryDto publicationCategoryDto, Long professorId,
			Long id) {
		this.publicationType = publicationType;
		this.isbn = isbn;
		this.title = title;
		this.journalTitle = journalTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.pagesWithQuotes = pagesWithQuotes;
		this.year = year;
		this.origin = origin;
		this.publicationCategoryDto = publicationCategoryDto;
		this.professorId = professorId;
		this.id = id;
	}

	public InternationalPublicationDto(InternationalPublication p) {
		this.publicationType = p.getPublicationType().getTypeName();
		this.isbn = p.getIsbn();
		this.title = p.getTitle();
		this.journalTitle = p.getJournalTitle();
		this.authors = p.getAuthors();
		this.publisher = p.getPublisher();
		this.pagesWithQuotes = p.getPagesWithQuotes();
		this.year = p.getYear();
		this.origin = p.getOrigin();
		this.publicationCategoryDto = (p.getPublicationCategory() != null ? new PublicationCategoryDto(p.getPublicationCategory()) : null);
		this.professorId = p.getProfessor().getId();
		this.id = p.getId();
	}

	public String getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(String publicationType) {
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

	public String getPagesWithQuotes() {
		return pagesWithQuotes;
	}

	public void setPagesWithQuotes(String pagesWithQuotes) {
		this.pagesWithQuotes = pagesWithQuotes;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public PublicationCategoryDto getPublicationCategoryDto() {
		return publicationCategoryDto;
	}

	public void setPublicationCategoryDto(PublicationCategoryDto publicationCategoryDto) {
		this.publicationCategoryDto = publicationCategoryDto;
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
