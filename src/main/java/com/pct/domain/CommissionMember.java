package com.pct.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "commission_member")
public class CommissionMember extends AbstractEntity {

	private static final long serialVersionUID = -5782741888811219238L;

	@OneToOne(optional = true)
	@JoinColumn(name = "professorId", nullable = true)
	@JsonBackReference(value = "professor")
	private Professor professor;

	/* For commission members outside faculty (database) */
	@Column(name = "name", length = 50)
	private String name;

	/* For commission members outside faculty (database) */
	@Column(name = "surname", length = 50)
	private String surname;

	/* For commission members outside faculty (database) */
	@Column(name = "title", length = 50)
	private String title;

	/* For commission members outside faculty (database) */
	@Column(name = "specificScientificArea", length = 50)
	private String specificScientificArea;

	/* For commission members outside faculty (database) */
	@Column(name = "institution", length = 100)
	private String institution;

	@Column(name = "function", length = 20)
	private String function;

	@ManyToMany(mappedBy = "commissionMembers", fetch = FetchType.EAGER)
	@JsonBackReference(value = "contest")
	private Set<Contest> contests = new HashSet<Contest>();

	public CommissionMember() {
	}

	public CommissionMember(Professor professor, String name, String surname, String title, String specificScientificArea, String institution,
			String function, Set<Contest> contests) {
		this.professor = professor;
		this.name = name;
		this.surname = surname;
		this.title = title;
		this.specificScientificArea = specificScientificArea;
		this.institution = institution;
		this.function = function;
		this.contests = contests;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpecificScientificArea() {
		return specificScientificArea;
	}

	public void setSpecificScientificArea(String specificScientificArea) {
		this.specificScientificArea = specificScientificArea;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Set<Contest> getContests() {
		return contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests.clear();

		if (contests != null) {
			this.contests.addAll(contests);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CommissionMember) {
			CommissionMember commissionMember = (CommissionMember) object;
			return new EqualsBuilder().append(professor, commissionMember.getProfessor()).append(contests, commissionMember.getContests())
					.append(name, commissionMember.getName()).append(surname, commissionMember.getSurname())
					.append(title, commissionMember.getTitle()).append(specificScientificArea, commissionMember.getSpecificScientificArea())
					.append(institution, commissionMember.getInstitution()).append(function, commissionMember.getFunction()).isEquals()
					&& super.equals(object);
		}
		return false;
	}

}
