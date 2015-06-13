package com.pct.domain.dto;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.ProfessorPublication;

public class ProfessorPublicationDto implements Serializable {

	private static final long serialVersionUID = 8325577395997414231L;

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
	private String pageRange;

	@NotNull
	@Min(0)
	private Integer quoted;

	@NotEmpty
	@Length(max = 4, min = 4)
	@SafeHtml
	private String year;
	
	@Nullable
	private PublicationCategoryDto publicationCategoryDto;

	protected Long professorId;

	protected Long id;

	public ProfessorPublicationDto() {
	}

	public ProfessorPublicationDto(String publicationType, String isbn, String title, String journalTitle,
			String authors, String publisher, String pageRange, Integer quoted, String year,
			PublicationCategoryDto publicationCategoryDto, Long professorId, Long id) {
		this.publicationType = publicationType;
		this.isbn = isbn;
		this.title = title;
		this.journalTitle = journalTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.pageRange = pageRange;
		this.quoted = quoted;
		this.year = year;
		this.publicationCategoryDto = publicationCategoryDto;
		this.professorId = professorId;
		this.id = id;
	}

	public ProfessorPublicationDto(ProfessorPublication p) {
		this.publicationType = p.getPublicationType().getTypeName();
		this.isbn = p.getIsbn();
		this.title = p.getTitle();
		this.journalTitle = p.getJournalTitle();
		this.authors = p.getAuthors();
		this.publisher = p.getPublisher();
		this.pageRange = p.getPageRange();
		this.quoted = p.getQuoted();
		this.year = p.getYear();
		this.publicationCategoryDto = (p.getPublicationCategory() != null ? new PublicationCategoryDto(p.getPublicationCategory()):null);
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
