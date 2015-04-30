package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.dto.InstitutionDto;
import com.pct.repository.InstitutionRepository;
import com.pct.service.InstitutionsService;

@Service
public class InstitutionsServiceImpl implements InstitutionsService {

	@Autowired
	InstitutionRepository institutionRepository;

	@Override
	@Transactional
	public List<InstitutionDto> findAllInstitutions() {
		
		List<InstitutionDto> institutionDtos = new ArrayList<InstitutionDto>();
		try {
			List<Institution> institutions = institutionRepository.findAll();
			for (Institution i : institutions) {
				InstitutionDto institutionDto = new InstitutionDto(i);
				institutionDtos.add(institutionDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return institutionDtos;
	}

}
