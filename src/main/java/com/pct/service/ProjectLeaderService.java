package com.pct.service;

import java.util.List;

import com.pct.domain.dto.PersonDto;

public interface ProjectLeaderService {

	List<PersonDto> findProjectLeaderStartsWith(String value, Long projectId,
			List<Long> professorsWhoAreLeadersOnThisProject, List<Long> leadersOnThisProjectWhoAreNotProfessors);

}
