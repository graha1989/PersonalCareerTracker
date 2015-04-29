package com.pct.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.dto.ProfessorDto;
import com.pct.repository.ProfesorRepository;
import com.pct.service.ProfessorService;
import com.pct.service.util.ProfessorUtil;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class ProfesorServiceImpl implements ProfessorService {

	@Autowired
	private ProfesorRepository profesorRepository;

	@Override
	@Transactional
	public ProfessorDto findProfesorByUserName(String userName) throws ProfessorNotFoundException {

		ProfessorDto professorDto;

		if (userName == null || profesorRepository.findByUserName(userName) == null) {
			throw new ProfessorNotFoundException();
		} else {
			Professor professor = profesorRepository.findByUserName(userName);
			professorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(),
					professor.getName(), professor.getSurname(), professor.getFathersName(),
					professor.getDateOfBirth(), professor.getPlaceOfBirth(), professor.getCountryOfBirth(),
					professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getId());
		}

		return professorDto;
	}

	@Override
	@Transactional
	public ProfessorDto findProfesorById(Long id) throws ProfessorNotFoundException {

		ProfessorDto ProfessorDto;

		if (id == null || profesorRepository.findOne(id) == null) {
			throw new ProfessorNotFoundException();
		} else {
			Professor professor = profesorRepository.findOne(id);
			ProfessorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(),
					professor.getName(), professor.getSurname(), professor.getFathersName(),
					professor.getDateOfBirth(), professor.getPlaceOfBirth(), professor.getCountryOfBirth(),
					professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getId());
		}

		return ProfessorDto;
	}

	@Override
	@Transactional
	public void saveProfesor(ProfessorDto professorDto) {

		Professor professor = new Professor();

		if (professorDto.getId() != null) {
			professor = ProfessorUtil.createProfesorInstanceFromProfesorFormaDTO(professorDto);
		} else {
			professor = ProfessorUtil.createNewProfesorInstanceFromProfesorFormaDTO(professorDto);
		}

		profesorRepository.saveAndFlush(professor);
	}

	@Override
	@Transactional
	public List<ProfessorDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor)
			throws ProfessorNotFoundException {

		List<ProfessorDto> professorsDtoList = new ArrayList<ProfessorDto>();
		List<Professor> professorsList = new ArrayList<Professor>();

		if (idProf == null) {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idMentor);
		} else {
			professorsList = profesorRepository.findByNameLikeOrSurnameLike(value, idProf, idMentor);
		}
		for (Professor p : professorsList) {
			ProfessorDto professorDto = new ProfessorDto(p);
			professorDto.setId(p.getId());
			professorsDtoList.add(professorDto);
		}

		return professorsDtoList;
	}

}
