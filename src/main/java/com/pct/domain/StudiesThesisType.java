package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "studies_thesis_type")
public class StudiesThesisType extends AbstractEntity {

	private static final long serialVersionUID = -7581971465528787379L;
	
	@Column(name = "type", length = 50)
	private String type;

	public StudiesThesisType() {
		super();
	}

	public StudiesThesisType(String finalPaperTypeName) {
		super();
		this.type = finalPaperTypeName;
	}

	public String getFinalPaperTypeName() {
		return type;
	}

	public void setFinalPaperTypeName(String finalPaperTypeName) {
		this.type = finalPaperTypeName;
	}
	
}
