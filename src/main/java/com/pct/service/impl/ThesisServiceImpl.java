package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Thesis;
import com.pct.domain.ThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.repository.ThesisRepository;
import com.pct.repository.ThesisTypeRepository;
import com.pct.service.ThesisService;
import com.pct.service.ThesisUtil;

@Service
public class ThesisServiceImpl implements ThesisService {

	@Autowired
	private ThesisRepository thesisRepository;
	
	@Autowired
	private ThesisTypeRepository thesisTypeRepository;

	@Override
	@Transactional
	public List<ThesisDto> findAllBachelorThesis(Long id) {

		List<ThesisDto> thesisDtoList = new ArrayList<ThesisDto>();

		List<Thesis> thesisList = thesisRepository.findAllBachelorThesis(id);
		for (Thesis t : thesisList) {
			ThesisDto thesisDto = new ThesisDto(t);
			thesisDtoList.add(thesisDto);
		}
		return thesisDtoList;
	}

	@Override
	@Transactional
	public List<ThesisType> findAllThesisType() {

		List<ThesisType> thesisTypesList = new ArrayList<ThesisType>();
		thesisTypesList = thesisTypeRepository.findAll();
		return thesisTypesList;
	}

	@Override
	@Transactional
	public ThesisDto saveThesis(ThesisDto thesisDto) {
		
		Thesis thesis = new Thesis();

		if (thesisDto.getId() != null) {
			thesis = ThesisUtil.createThesisInstanceFromThesisDto(thesisDto);
		} else {
			thesis = ThesisUtil.createNewThesisInstanceFromThesisiDto(thesisDto);
		}

		return new ThesisDto(thesisRepository.save(thesis));
	}

}
