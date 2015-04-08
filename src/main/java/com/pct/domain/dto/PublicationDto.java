package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.ProfessorPublication;
import com.pct.domain.PublicationCategory;
import com.pct.domain.enums.PublicationType;

public class PublicationDto implements Serializable {

	private static final long serialVersionUID = 8325577395997414231L;

	@NotNull
	private PublicationType publicationType;
	
	@NotEmpty
	@Length(max = 30)
	@SafeHtml
	private String isbn;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String title;
	
	@NotEmpty
	@Length(max = 400)
	@SafeHtml
	private String authors;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String publisher;
	
	@NotEmpty
	@Length(max = 55)
	@SafeHtml
	private String pageRange;
	
	@NotNull
	@Min(0)
	private Integer quoted;
	
	@NotNull
	private PublicationCategory publicationCategory;
	
	protected Long professorId;
	
	protected Long id;

	public PublicationDto() {
		super();
	}

	public PublicationDto(PublicationType publicationType, String isbn, String title, String authors, String publisher,
			String pageRange, Integer quoted, PublicationCategory publicationCategory, Long professorId, Long id) {
		super();
		this.publicationType = publicationType;
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.pageRange = pageRange;
		this.quoted = quoted;
		this.publicationCategory = publicationCategory;
		this.professorId = professorId;
		this.id = id;
	}
	
	public PublicationDto(ProfessorPublication p) {
		super();
		this.publicationType = p.getPublicationType();
		this.isbn = p.getIsbn();
		this.title = p.getTitle();
		this.authors = p.getAuthors();
		this.publisher = p.getPublisher();
		this.pageRange = p.getPageRange();
		this.quoted = p.getQuoted();
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
