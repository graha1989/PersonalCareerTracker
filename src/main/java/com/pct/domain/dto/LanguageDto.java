package com.pct.domain.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.Language;

public class LanguageDto implements Serializable {

	private static final long serialVersionUID = -2258535556718507344L;

	@NotEmpty
	@Length(max = 20)
	@SafeHtml
	private String languageName;
	
	protected Long id;

	public LanguageDto() {
	}

	public LanguageDto(String languageName, Long id) {
		super();
		this.languageName = languageName;
		this.id = id;
	}
	
	public LanguageDto(Language language) {
		this.languageName = language.getName();
		this.id = language.getId();
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
