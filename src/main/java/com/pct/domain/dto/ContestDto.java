package com.pct.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.pct.domain.CommissionMember;
import com.pct.domain.Contest;

public class ContestDto implements Serializable {

	private static final long serialVersionUID = 5574370769112372923L;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String authority;

	@NotNull
	private Date decisionDate;

	@NotEmpty
	@Length(max = 200)
	@SafeHtml
	private String placeOfAnnouncement;

	@NotNull
	private Date announcingDate;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String titleToChoose;

	@NotEmpty
	@Length(max = 50)
	@SafeHtml
	private String specificScientificArea;

	@NotNull
	protected Long candidateId;

	List<CommissionMemberDto> commissionMemberDtos;

	@Nullable
	protected Long id;

	public ContestDto() {
	}

	public ContestDto(String authority, Date decisionDate, String placeOfAnnouncement, Date announcingDate, String titleToChoose,
			String specificScientificArea, Long candidateId, List<CommissionMemberDto> commissionMemberDtos, Long id) {
		this.authority = authority;
		this.decisionDate = decisionDate;
		this.placeOfAnnouncement = placeOfAnnouncement;
		this.announcingDate = announcingDate;
		this.titleToChoose = titleToChoose;
		this.specificScientificArea = specificScientificArea;
		this.candidateId = candidateId;
		this.commissionMemberDtos = commissionMemberDtos;
		this.id = id;
	}

	public ContestDto(Contest contest) {
		this.authority = contest.getAuthority();
		this.decisionDate = contest.getDecisionDate();
		this.placeOfAnnouncement = contest.getPlaceOfAnnouncement();
		this.announcingDate = contest.getAnnouncingDate();
		this.titleToChoose = contest.getTitleToChoose();
		this.specificScientificArea = contest.getSpecificScientificArea();
		this.candidateId = contest.getCandidate().getId();
		this.commissionMemberDtos = createCommissionMembersDtoList(contest.getCommissionMembers());
		this.id = contest.getId();
	}

	private List<CommissionMemberDto> createCommissionMembersDtoList(Set<CommissionMember> commissionMembers) {
		List<CommissionMemberDto> commissionMemberDtos = new ArrayList<CommissionMemberDto>();
		for (CommissionMember commissionMember : commissionMembers) {
			commissionMemberDtos.add(new CommissionMemberDto(commissionMember));
		}
		return commissionMemberDtos;
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

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public List<CommissionMemberDto> getCommissionMemberDtos() {
		return commissionMemberDtos;
	}

	public void setCommissionMemberDtos(List<CommissionMemberDto> commissionMemberDtos) {
		this.commissionMemberDtos = commissionMemberDtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
