package com.pct.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Institution;
import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.enums.InstitutionType;
import com.pct.repository.InstitutionRepository;
import com.pct.repository.ProfesorRepository;
import com.pct.service.InstitutionsService;
import com.pct.validation.InstitutionNotFoundException;

@Service
public class InstitutionsServiceImpl implements InstitutionsService {

	@Autowired
	InstitutionRepository institutionRepository;
	
	@Autowired
	ProfesorRepository professorRepository;

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

	@Override
	@Transactional
	public List<InstitutionType> findAllInstitutionTypes() {
		return new ArrayList<InstitutionType>(Arrays.asList(InstitutionType.values()));
	}

	@Override
	@Transactional
	public InstitutionDto findInstitutionById(Long id) throws InstitutionNotFoundException {
		
		InstitutionDto institutionDto;
		if (id != null) {
			Institution  institution = institutionRepository.findOne(id);
			if (institution != null) {
				institutionDto = new InstitutionDto(institution);
				return institutionDto;
			}
		}

		throw new InstitutionNotFoundException();
	}

	@Override
	@Transactional
	public void saveInstitution(InstitutionDto institutionDto) throws InstitutionNotFoundException {
		Institution institution = initializeInstitution(institutionDto);
		institutionRepository.saveAndFlush(institution);
	}
	
	/**
	 * Creates new Institution entity object or retrieves existing from the database and sets field values from
	 * InstitutionDto.
	 * 
	 * @param institutionDto
	 * @return
	 */
	public Institution initializeInstitution(@Nonnull InstitutionDto institutionDto) {
		Institution institution = null;
		if (institutionDto.getId() != null) {
			institution = institutionRepository.findOne(institutionDto.getId());
		} else {
			institution = new Institution();
		}
		institution.setCity(institutionDto.getCity());
		institution.setCountry(institutionDto.getCountry());
		institution.setName(institutionDto.getName());
		institution.setUniversity(institutionDto.getUniversity());
		institution.setInstitutionType(institutionDto.getInstitutionType());

		return institution;
	}
	
	/* 
	 * TODO
	 * Not delete institution completely, just set it to inactive  from some reason
	 */
	@Override
	@Transactional
	public void deleteInstitution(Long id) throws InstitutionNotFoundException {
		
		Institution institution = institutionRepository.findOne(id);
		if (institution == null) {
			throw new InstitutionNotFoundException();
		}
		institutionRepository.delete(institution);
	}

}
