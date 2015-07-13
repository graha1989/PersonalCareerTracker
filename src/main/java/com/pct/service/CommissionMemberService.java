package com.pct.service;

import java.util.List;

import com.pct.domain.dto.CommissionMemberDto;

public interface CommissionMemberService {

	List<CommissionMemberDto> findCommissionMembersStartsWith(String value, Long contestId,
			List<Long> professorsWhoAreCommissionMembersOnThisContest, List<Long> commissionMembersOnThisContestWhoAreNotProfessors);

}
