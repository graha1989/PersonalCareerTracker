package com.pct.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "contest")
public class Contest extends AbstractEntity {

	private static final long serialVersionUID = 6891499165999685592L;

	@Column(name = "authority", length = 200)
	private String authority;

	@Column(name = "decisionDate")
	private Date decisionDate;

	@Column(name = "placeOfAnnouncement", length = 200)
	private String placeOfAnnouncement;

	@Column(name = "announcingDate")
	private Date announcingDate;

	@Column(name = "titleToChoose", length = 50)
	private String titleToChoose;

	@Column(name = "specificScientificArea", length = 50)
	private String specificScientificArea;

	@OneToOne(optional = false)
	@JoinColumn(name = "candidateId", nullable = false)
	@JsonBackReference(value = "candidate")
	private Professor candidate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "contest_commission_member_intersecting_table", joinColumns = @JoinColumn(name = "contestId"), inverseJoinColumns = @JoinColumn(name = "memberId"))
	private Set<CommissionMember> commissionMembers = new HashSet<CommissionMember>();

	public Contest() {
	}

	public Contest(String authority, Date decisionDate, String placeOfAnnouncement, Date announcingDate, String titleToChoose,
			String specificScientificArea, Professor candidate, Set<CommissionMember> commissionMembers) {
		this.authority = authority;
		this.decisionDate = decisionDate;
		this.placeOfAnnouncement = placeOfAnnouncement;
		this.announcingDate = announcingDate;
		this.titleToChoose = titleToChoose;
		this.specificScientificArea = specificScientificArea;
		this.candidate = candidate;
		this.commissionMembers = commissionMembers;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Date getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public String getPlaceOfAnnouncement() {
		return placeOfAnnouncement;
	}

	public void setPlaceOfAnnouncement(String placeOfAnnouncement) {
		this.placeOfAnnouncement = placeOfAnnouncement;
	}

	public Date getAnnouncingDate() {
		return announcingDate;
	}

	public void setAnnouncingDate(Date announcingDate) {
		this.announcingDate = announcingDate;
	}

	public String getTitleToChoose() {
		return titleToChoose;
	}

	public void setTitleToChoose(String titleToChoose) {
		this.titleToChoose = titleToChoose;
	}

	public String getSpecificScientificArea() {
		return specificScientificArea;
	}

	public void setSpecificScientificArea(String specificScientificArea) {
		this.specificScientificArea = specificScientificArea;
	}

	public Professor getCandidate() {
		return candidate;
	}

	public void setCandidate(Professor candidate) {
		this.candidate = candidate;
	}

	public Set<CommissionMember> getCommissionMembers() {
		return commissionMembers;
	}

	public void setCommissionMembers(Set<CommissionMember> commissionMembers) {
		this.commissionMembers.clear();

		if (commissionMembers != null) {
			this.commissionMembers.addAll(commissionMembers);
		}
	}

}
