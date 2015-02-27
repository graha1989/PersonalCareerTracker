package com.pct.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.constants.RegexPatterns;

public class TipZavrsnogRadaDTO implements Serializable {

	private static final long serialVersionUID = -1047138047049208662L;
	
	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	@Pattern(regexp = RegexPatterns.LETTERS_ONLY)
	private String finalPaperTypeName;

	public TipZavrsnogRadaDTO() {
		super();
	}

	public TipZavrsnogRadaDTO(String finalPaperTypeName) {
		super();
		this.finalPaperTypeName = finalPaperTypeName;
	}

	public String getFinalPaperTypeName() {
		return finalPaperTypeName;
	}

	public void setFinalPaperTypeName(String finalPaperTypeName) {
		this.finalPaperTypeName = finalPaperTypeName;
	}
	
}
