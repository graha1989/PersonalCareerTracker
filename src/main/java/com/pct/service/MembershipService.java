package com.pct.service;

import java.util.List;

import com.pct.domain.dto.ScientificProfessionalOrgMemDto;
import com.pct.validation.MembershipNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

public interface MembershipService {
	
	List<ScientificProfessionalOrgMemDto> findAllProfessorMemberships(Long professorId) throws MembershipNotFoundException;

	ScientificProfessionalOrgMemDto findProfessorMembershipById(Long id) throws MembershipNotFoundException;

	void saveProfessorMembershipInOrganization(ScientificProfessionalOrgMemDto memDto) throws ProfessorNotFoundException;

	void deleteProfessorMembership(Long id) throws MembershipNotFoundException;
	
}
