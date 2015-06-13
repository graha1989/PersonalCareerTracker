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
@Table(name = "award_field")
public class AwardField extends AbstractEntity {

	private static final long serialVersionUID = -7778121316519921107L;
	
	@Column(name = "fieldName", length = 50)
	private String fieldName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "awardField")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonManagedReference(value = "awardField")
	@JsonIgnore
	private Set<Award> awards = new HashSet<Award>();

	public AwardField() {
	}

	public AwardField(String fieldName, Set<Award> awards) {
		this.fieldName = fieldName;
		this.awards = awards;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
