package com.pct.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tip_zavrsnog_rada")
public class TipZavrsnogRada extends AbstractEntity{

	private static final long serialVersionUID = -7581971465528787379L;
	
	@Column(name = "finalPaperTypeName", length = 50)
	private String finalPaperTypeName;

	public TipZavrsnogRada() {
		super();
	}

	public TipZavrsnogRada(String finalPaperTypeName) {
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
