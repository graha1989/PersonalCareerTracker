package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.ScientificProfessionalOrgMem;
import com.pct.domain.dto.ScientificProfessionalOrgMemDto;
import com.pct.repository.MembershipRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.MembershipService;
import com.pct.validation.MembershipNotFoundException;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class MembershipServiceImpl implements MembershipService {

	@Autowired
	MembershipRepository membershipRepository;

	@Autowired
	ProfesorRepository professorRepository;

	@Override
	@Transactional
	public List<ScientificProfessionalOrgMemDto> findAllProfessorMemberships(Long professorId) throws MembershipNotFoundException {

		List<ScientificProfessionalOrgMemDto> membershipDtoList = new ArrayList<ScientificProfessionalOrgMemDto>();
		try {
			List<ScientificProfessionalOrgMem> professionalOrgMems = membershipRepository.findAllMemberships(professorId);
			for (ScientificProfessionalOrgMem m : professionalOrgMems) {
				ScientificProfessionalOrgMemDto memDto = new ScientificProfessionalOrgMemDto(m);
				membershipDtoList.add(memDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return membershipDtoList;
	}

	@Override
	@Transactional
	public ScientificProfessionalOrgMemDto findProfessorMembershipById(Long id) throws MembershipNotFoundException {

		ScientificProfessionalOrgMemDto orgMemDto;
		if (id != null) {
			ScientificProfessionalOrgMem m = membershipRepository.findOne(id);
			if (m != null) {
				orgMemDto = new ScientificProfessionalOrgMemDto(m);
				return orgMemDto;
			}
		}
		throw new MembershipNotFoundException();
	}

	@Override
	@Transactional
	public void saveProfessorMembershipInOrganization(ScientificProfessionalOrgMemDto memDto) throws ProfessorNotFoundException {

		Professor professor = professorRepository.findOne(memDto.getProfessorId());
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}

		membershipRepository.saveAndFlush(createOrUpdateProfessorMembershipInstanceFromDto(memDto, professor));
	}

	public ScientificProfessionalOrgMem createOrUpdateProfessorMembershipInstanceFromDto(@Nonnull ScientificProfessionalOrgMemDto memDto,
			@Nonnull Professor professor) {

		ScientificProfessionalOrgMem mem = null;
		if (memDto.getId() == null) {
			mem = new ScientificProfessionalOrgMem();
			mem.setProfessor(professor);
		} else {
			mem = membershipRepository.findOne(memDto.getId());
		}
		mem.setOrganizationName(memDto.getOrganizationName());

		return mem;
	}

	@Override
	@Transactional
	public void deleteProfessorMembership(Long id) throws MembershipNotFoundException {

		ScientificProfessionalOrgMem mem = membershipRepository.findOne(id);
		if (mem == null) {
			throw new MembershipNotFoundException();
		}

		membershipRepository.delete(mem);
	}

}
