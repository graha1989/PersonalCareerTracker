package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.ProjectLeader;
import com.pct.domain.dto.PersonDto;
import com.pct.repository.ProjectLeaderRepository;
import com.pct.service.ProjectLeaderService;

@Service
public class ProjectLeaderServiceImpl implements ProjectLeaderService {
	
	@Autowired
	private ProjectLeaderRepository projectLeaderRepository;
	
	@Override
	@Transactional
	public List<PersonDto> findProjectLeaderStartsWith(String value) {
		
		List<PersonDto> personDtos = new ArrayList<PersonDto>();
		List<ProjectLeader> projectLeaders = projectLeaderRepository.findByNameLikeOrSurnameLike(value);
		for (ProjectLeader p : projectLeaders) {
			PersonDto personDto = new PersonDto();
			personDto.setName(p.getName());
			personDto.setSurname(p.getSurname());
			personDtos.add(personDto);
			personDto.setLeaderId(p.getId());
		}
		
		return personDtos;
	}

}
