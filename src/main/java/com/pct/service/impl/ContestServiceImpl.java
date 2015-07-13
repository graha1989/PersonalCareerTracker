package com.pct.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.CommissionMember;
import com.pct.domain.Contest;
import com.pct.domain.Professor;
import com.pct.domain.dto.CommissionMemberDto;
import com.pct.domain.dto.ContestDto;
import com.pct.repository.CommissionMemberRepository;
import com.pct.repository.ContestRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.ContestService;

@Service
public class ContestServiceImpl implements ContestService {

	@Autowired
	private ContestRepository contestRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Autowired
	private CommissionMemberRepository commissionMemberRepository;

	@Override
	@Transactional
	public void saveContest(ContestDto contestDto) {
		Contest contest = initializeContest(contestDto);
		contestRepository.saveAndFlush(contest);
	}

	private Contest initializeContest(ContestDto contestDto) {
		Contest contest = null;
		if (contestDto.getId() != null) {
			contest = contestRepository.findOne(contestDto.getId());
		} else {
			contest = new Contest();
		}
		contest.setAuthority(contestDto.getAuthority());
		contest.setAnnouncingDate(contestDto.getAnnouncingDate());
		contest.setPlaceOfAnnouncement(contestDto.getPlaceOfAnnouncement());
		contest.setDecisionDate(contestDto.getDecisionDate());
		contest.setTitleToChoose(contestDto.getTitleToChoose());
		contest.setSpecificScientificArea(contestDto.getSpecificScientificArea());
		contest.setCandidate(professorRepository.findOne(contestDto.getCandidateId()));

		Set<CommissionMember> commissionMembers = new HashSet<CommissionMember>();

		for (CommissionMemberDto commissionMemberDto : contestDto.getCommissionMemberDtos()) {
			CommissionMember commissionMember = null;
			Professor professor = null;
			if (commissionMemberDto.getId() != null && commissionMemberDto.getId() > 0L) {
				commissionMember = commissionMemberRepository.findOne(commissionMemberDto.getId());
			} else if (commissionMemberDto.getProfessorId() != null && commissionMemberDto.getProfessorId() > 0L) {
				commissionMember = commissionMemberRepository.findMemberByProfessorId(commissionMemberDto.getProfessorId());
				if (commissionMember == null) {
					commissionMember = new CommissionMember();
					commissionMember.setName(commissionMemberDto.getName());
					commissionMember.setSurname(commissionMemberDto.getSurname());
					commissionMember.setTitle(commissionMemberDto.getTitle());
					commissionMember.setSpecificScientificArea(commissionMemberDto.getSpecificScientificArea());
					commissionMember.setInstitution(commissionMemberDto.getInstitution());
					commissionMember.setFunction(commissionMemberDto.getCommissionFunction());
					professor = professorRepository.findOne(commissionMemberDto.getProfessorId());
					commissionMember.setProfessor(professor);
				}
			} else {
				commissionMember = new CommissionMember();
				commissionMember.setName(commissionMemberDto.getName());
				commissionMember.setSurname(commissionMemberDto.getSurname());
				commissionMember.setTitle(commissionMemberDto.getTitle());
				commissionMember.setSpecificScientificArea(commissionMemberDto.getSpecificScientificArea());
				commissionMember.setInstitution(commissionMemberDto.getInstitution());
				commissionMember.setFunction(commissionMemberDto.getCommissionFunction());
			}

			commissionMembers.add(commissionMember);
		}

		// If collection from Dto miss some element from original collection, we
		// remove it from original
		Iterator<CommissionMember> currentMembersIterator = contest.getCommissionMembers().iterator();
		while (currentMembersIterator.hasNext()) {
			CommissionMember commissionMember = currentMembersIterator.next();
			if (!commissionMembers.contains(commissionMember)) {
				currentMembersIterator.remove();
				commissionMember.setContests(null);
			}
		}
		// If original collection miss some element from Dto collection, we add
		// it to original
		Iterator<CommissionMember> newMembersIterator = commissionMembers.iterator();
		while (newMembersIterator.hasNext()) {
			CommissionMember commissionMember = newMembersIterator.next();
			if (!contest.getCommissionMembers().contains(commissionMember)) {
				contest.getCommissionMembers().add(commissionMember);
				commissionMemberRepository.save(commissionMember);
			}
		}

		return contest;
	}

}
