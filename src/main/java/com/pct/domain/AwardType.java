package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "award_type")
public class AwardType extends AbstractEntity {

	private static final long serialVersionUID = 1547971687698589322L;

	@Column(name = "typeName", length = 50)
	private String typeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "awardType")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "awardType")
	@JsonIgnore
	private Set<Award> awards = new HashSet<Award>();

	public AwardType() {
	}

	public AwardType(String typeName, Set<Award> awards) {
		this.typeName = typeName;
		this.awards = awards;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set<Award> getAwards() {
		return awards;
	}

	public void setAwards(Set<Award> awards) {
		this.awards.clear();

		if (awards != null) {
			this.awards.addAll(awards);
		}
	}

}
