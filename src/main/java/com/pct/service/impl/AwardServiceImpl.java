package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Award;
import com.pct.domain.dto.AwardDto;
import com.pct.repository.AwardRepository;
import com.pct.service.AwardService;

@Service
public class AwardServiceImpl implements AwardService{
	
	@Autowired
	private AwardRepository awardRepository;
	
	@Override
	@Transactional
	public List<AwardDto> findAll() {
		
		List<AwardDto> awardsDtoList = new ArrayList<AwardDto>();

		List<Award> awardsList = awardRepository.findAll();
		for (Award a : awardsList) {
			AwardDto awardDto = new AwardDto(a);
			awardDto.setId(a.getId());
			awardsDtoList.add(awardDto);
		}
		return awardsDtoList;
	}

}
