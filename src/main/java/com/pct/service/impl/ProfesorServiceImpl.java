package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Profesor;
import com.pct.domain.dto.ProfesorFormaDTO;
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
	public ProfesorFormaDTO findProfesorByUserName(String userName) throws ProfesorNotFoundException {

		ProfesorFormaDTO profesorFormaDTO;

		if (userName == null || profesorRepository.findByUserName(userName) == null) {
			throw new ProfesorNotFoundException();
		} else {
			Profesor profesor = profesorRepository.findByUserName(userName);
			profesorFormaDTO = new ProfesorFormaDTO(profesor.getUserName(), profesor.getPassword(),
					profesor.getEmail(), profesor.getUloga(), profesor.getName(), profesor.getSurname(),
					profesor.getFathersName(), profesor.getDateOfBirth(), profesor.getPlaceOfBirth(),
					profesor.getCountryOfBirth(), profesor.getScientificArea(), profesor.getSpecialScientificArea(),
					profesor.getId());
		}

		return profesorFormaDTO;
	}

	@Override
	@Transactional
	public ProfesorFormaDTO findProfesorById(Long id) throws ProfesorNotFoundException {

		ProfesorFormaDTO ProfesorFormaDTO;

		if (id == null || profesorRepository.findOne(id) == null) {
			throw new ProfesorNotFoundException();
		} else {
			Profesor profesor = profesorRepository.findOne(id);
			ProfesorFormaDTO = new ProfesorFormaDTO(profesor.getUserName(), profesor.getPassword(),
					profesor.getEmail(), profesor.getUloga(), profesor.getName(), profesor.getSurname(),
					profesor.getFathersName(), profesor.getDateOfBirth(), profesor.getPlaceOfBirth(),
					profesor.getCountryOfBirth(), profesor.getScientificArea(), profesor.getSpecialScientificArea(),
					profesor.getId());
		}

		return ProfesorFormaDTO;
	}

	@Override
	@Transactional
	public void saveProfesor(ProfesorFormaDTO ProfesorFormaDTO) {

		Profesor profesor = new Profesor();

		if (ProfesorFormaDTO.getId() != null) {
			profesor = ProfesorUtil.createProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO);
		} else {
			profesor = ProfesorUtil.createNewProfesorInstanceFromProfesorFormaDTO(ProfesorFormaDTO);
		}

		profesorRepository.saveAndFlush(profesor);

	}

	@Override
	@Transactional
	public List<ProfesorFormaDTO> findProfessorsStartsWith(String value, Long idProf, Long idMentor)
			throws ProfesorNotFoundException {
		
		List<ProfesorFormaDTO> professorsDtoList = new ArrayList<ProfesorFormaDTO>();
		List<Profesor> professorsList = new ArrayList<Profesor>();
		
		if (idProf == null) {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idMentor);
		} else {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idProf, idMentor);
		}
		for (Profesor p : professorsList) {
			ProfesorFormaDTO professorDto = new ProfesorFormaDTO(p);
			professorDto.setId(p.getId());
			professorsDtoList.add(professorDto);
		}
		
		return professorsDtoList;
	}

}
