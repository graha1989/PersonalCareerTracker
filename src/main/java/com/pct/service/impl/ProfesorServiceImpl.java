package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Profesor;
import com.pct.domain.dto.ProfesorFormaDto;
import com.pct.repository.ProfesorRepository;
import com.pct.service.ProfesorService;
import com.pct.service.ProfesorUtil;
import com.pct.validation.ProfesorNotFoundException;

@Service
public class ProfesorServiceImpl implements ProfesorService {

	@Autowired
	private ProfesorRepository profesorRepository;

	@Override
	@Transactional
	public ProfesorFormaDto findProfesorByUserName(String userName) throws ProfesorNotFoundException {

		ProfesorFormaDto profesorFormaDto;

		if (userName == null || profesorRepository.findByUserName(userName) == null) {
			throw new ProfesorNotFoundException();
		} else {
			Profesor profesor = profesorRepository.findByUserName(userName);
			profesorFormaDto = new ProfesorFormaDto(profesor.getUserName(), profesor.getPassword(),
					profesor.getEmail(), profesor.getUloga(), profesor.getName(), profesor.getSurname(),
					profesor.getFathersName(), profesor.getDateOfBirth(), profesor.getPlaceOfBirth(),
					profesor.getCountryOfBirth(), profesor.getScientificArea(), profesor.getSpecialScientificArea(),
					profesor.getId());
		}

		return profesorFormaDto;
	}

	@Override
	@Transactional
	public ProfesorFormaDto findProfesorById(Long id) throws ProfesorNotFoundException {

		ProfesorFormaDto ProfesorFormaDto;

		if (id == null || profesorRepository.findOne(id) == null) {
			throw new ProfesorNotFoundException();
		} else {
			Profesor profesor = profesorRepository.findOne(id);
			ProfesorFormaDto = new ProfesorFormaDto(profesor.getUserName(), profesor.getPassword(),
					profesor.getEmail(), profesor.getUloga(), profesor.getName(), profesor.getSurname(),
					profesor.getFathersName(), profesor.getDateOfBirth(), profesor.getPlaceOfBirth(),
					profesor.getCountryOfBirth(), profesor.getScientificArea(), profesor.getSpecialScientificArea(),
					profesor.getId());
		}

		return ProfesorFormaDto;
	}

	@Override
	@Transactional
	public void saveProfesor(ProfesorFormaDto profesorFormaDto) {

		Profesor profesor = new Profesor();

		if (profesorFormaDto.getId() != null) {
			profesor = ProfesorUtil.createProfesorInstanceFromProfesorFormaDTO(profesorFormaDto);
		} else {
			profesor = ProfesorUtil.createNewProfesorInstanceFromProfesorFormaDTO(profesorFormaDto);
		}

		profesorRepository.saveAndFlush(profesor);

	}

	@Override
	@Transactional
	public List<ProfesorFormaDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor)
			throws ProfesorNotFoundException {
		
		List<ProfesorFormaDto> professorsDtoList = new ArrayList<ProfesorFormaDto>();
		List<Profesor> professorsList = new ArrayList<Profesor>();
		
		if (idProf == null) {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idMentor);
		} else {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idProf, idMentor);
		}
		for (Profesor p : professorsList) {
			ProfesorFormaDto professorDto = new ProfesorFormaDto(p);
			professorDto.setId(p.getId());
			professorsDtoList.add(professorDto);
		}
		
		return professorsDtoList;
	}

}
