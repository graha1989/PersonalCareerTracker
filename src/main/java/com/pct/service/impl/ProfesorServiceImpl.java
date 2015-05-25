package com.pct.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Role;
import com.pct.domain.dto.ProfessorDto;
import com.pct.domain.enums.RoleNames;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.RoleRepository;
import com.pct.service.ProfessorService;
import com.pct.service.util.ProfessorUtil;
import com.pct.service.util.UserUtil;
import com.pct.validation.ProfessorNotFoundException;

@Service
public class ProfesorServiceImpl implements ProfessorService {

	private ProfesorRepository profesorRepository;
	private RoleRepository roleRepository;

	@Autowired
	public ProfesorServiceImpl(ProfesorRepository profesorRepository, RoleRepository roleRepository) {
		this.profesorRepository = profesorRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public ProfessorDto findProfesorByUserName(String userName) throws ProfessorNotFoundException {

		ProfessorDto professorDto;
		if (userName != null) {
			Professor professor = profesorRepository.findByUserName(userName);
			if (professor != null) {
				professorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(),
						professor.getName(), professor.getSurname(), professor.getFathersName(),
						professor.getDateOfBirth(), professor.getPlaceOfBirth(), professor.getCountryOfBirth(),
						professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getId());

				return professorDto;
			}
		}

		throw new ProfessorNotFoundException();
	}

	@Override
	@Transactional
	public ProfessorDto findProfesorById(Long id) throws ProfessorNotFoundException {

		ProfessorDto professorDto;
		if (id != null) {
			Professor professor = profesorRepository.findOne(id);
			if (professor != null) {
				professorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(),
						professor.getName(), professor.getSurname(), professor.getFathersName(),
						professor.getDateOfBirth(), professor.getPlaceOfBirth(), professor.getCountryOfBirth(),
						professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getId());

				return professorDto;
			}
		}
		throw new ProfessorNotFoundException();
	}

	@Override
	@Transactional
	public void saveProfesor(ProfessorDto professorDto) {
		Professor professor = convertProfessorDtoToProfessor(professorDto);

		if (professor.getId() == null && StringUtils.isNotBlank(professor.getPassword())) {
			UserUtil.encodePassword(professor);
		}
		Role role = roleRepository.findByName(RoleNames.ROLE_USER.getName());
		if (role != null) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
			professor.setRoles(roles);
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

	@Override
	@Transactional
	public List<ProfessorDto> findAllProfessors() {

		List<ProfessorDto> professorDtos = new ArrayList<ProfessorDto>();
		try {
			List<Professor> professors = profesorRepository.findAll();
			for (Professor p : professors) {
				ProfessorDto professorDto = new ProfessorDto(p);
				professorDtos.add(professorDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return professorDtos;
	}

	/**
	 * Converts professor dto to professor.
	 * 
	 * @param dto
	 * @return
	 */
	private Professor convertProfessorDtoToProfessor(ProfessorDto dto) {
		Professor professor = null;

		if (dto.getId() != null && dto.getId() > 0L) {
			professor = profesorRepository.findOne(dto.getId());
		} else {
			professor = new Professor();
		}
		ProfessorUtil.copyProfessorPropertiesFromDto(professor, dto);

		return professor;
	}

}
