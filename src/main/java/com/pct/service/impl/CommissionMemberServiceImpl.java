package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.CommissionMember;
import com.pct.domain.Professor;
import com.pct.domain.dto.CommissionMemberDto;
import com.pct.repository.CommissionMemberRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.CommissionMemberService;

@Service
public class CommissionMemberServiceImpl implements CommissionMemberService {

	@Autowired
	CommissionMemberRepository commissionMemberRepository;

	@Autowired
	private ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<CommissionMemberDto> findCommissionMembersStartsWith(String value, @Nullable Long contestId,
			@Nullable List<Long> professorsWhoAreCommissionMembersOnThisContest,
			@Nullable List<Long> commissionMembersOnThisContestWhoAreNotProfessors) {

		List<CommissionMemberDto> commissionMemberDtos = new ArrayList<CommissionMemberDto>();
		List<CommissionMember> commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest = new ArrayList<CommissionMember>();
		List<Professor> professorsWhoAreNotMembersOnSelectedContest = new ArrayList<Professor>();

		if (contestId != null && contestId > 0L) {
			if (commissionMembersOnThisContestWhoAreNotProfessors != null && commissionMembersOnThisContestWhoAreNotProfessors.size() > 0) {
				commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest = commissionMemberRepository
						.findMembersWhoAreNotProfessorsAndNotCommissionMembersOnSelectedContest(value,
								commissionMembersOnThisContestWhoAreNotProfessors);
			} else {
				commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest = commissionMemberRepository
						.findMembersWhoAreNotProfessorsAndNotCommissionMembersOnSelectedContest(value);
			}
			if (professorsWhoAreCommissionMembersOnThisContest != null && professorsWhoAreCommissionMembersOnThisContest.size() > 0) {
				professorsWhoAreNotMembersOnSelectedContest = professorRepository.findProfessorsWhoAreNotCommissionMembersOnSelectedContest(value,
						professorsWhoAreCommissionMembersOnThisContest);
			} else {
				professorsWhoAreNotMembersOnSelectedContest = professorRepository.findProfessorsWhoAreNotCommissionMembersOnSelectedContest(value);
			}
		} else {
			if (commissionMembersOnThisContestWhoAreNotProfessors != null && commissionMembersOnThisContestWhoAreNotProfessors.size() > 0) {
				commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest = commissionMemberRepository
						.findMembersWhoAreNotProfessorsAndNotAlreadyUsed(value, commissionMembersOnThisContestWhoAreNotProfessors);
			} else {
				commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest = commissionMemberRepository
						.findMembersWhoAreNotProfessorsAndNotCommissionMembersOnSelectedContest(value);
			}
			if (professorsWhoAreCommissionMembersOnThisContest != null && professorsWhoAreCommissionMembersOnThisContest.size() > 0) {
				professorsWhoAreNotMembersOnSelectedContest = professorRepository.findProfessorsWhoAreNotCommissionMembersOnSelectedContest(value,
						professorsWhoAreCommissionMembersOnThisContest);
			} else {
				professorsWhoAreNotMembersOnSelectedContest = professorRepository.findProfessorsWhoAreNotCommissionMembersOnSelectedContest(value);
			}
		}

		for (CommissionMember m : commissionMembersWhoAreNotProfessorsAndNotMembersOnSelectedContest) {
			CommissionMemberDto commissionMemberDto = new CommissionMemberDto(m);
			commissionMemberDtos.add(commissionMemberDto);
		}
		for (Professor p : professorsWhoAreNotMembersOnSelectedContest) {
			CommissionMemberDto commissionMemberDto = new CommissionMemberDto();
			commissionMemberDto.setProfessorId(p.getId());
			commissionMemberDto.setName(p.getName());
			commissionMemberDto.setSurname(p.getSurname());
			commissionMemberDto.setTitle(p.getTitle());
			commissionMemberDto.setSpecificScientificArea(p.getSpecialScientificArea());
			commissionMemberDto.setInstitution(p.getInstitution());
			commissionMemberDtos.add(commissionMemberDto);
		}

		return commissionMemberDtos;
	}

}
