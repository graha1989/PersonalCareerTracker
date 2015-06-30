package com.pct.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Professor;
import com.pct.domain.Role;
import com.pct.domain.User;
import com.pct.domain.dto.ProfessorDto;
import com.pct.domain.enums.RoleNames;
import com.pct.repository.ProfesorRepository;
import com.pct.repository.RoleRepository;
import com.pct.service.ProfessorService;
import com.pct.service.util.ProfessorUtil;
import com.pct.service.util.UserUtil;
import com.pct.validation.EmailExistException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNameExistException;

@Service
public class ProfesorServiceImpl implements ProfessorService {

	private static final Logger LOG = LoggerFactory.getLogger(ProfesorServiceImpl.class);

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
				professorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(), professor.getName(),
						professor.getSurname(), professor.getFathersName(), professor.getDateOfBirth(), professor.getPlaceOfBirth(),
						professor.getCountryOfBirth(), professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getTitle(),
						professor.getInstitution(), professor.getId());

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
				professorDto = new ProfessorDto(professor.getUserName(), professor.getPassword(), professor.getEmail(), professor.getName(),
						professor.getSurname(), professor.getFathersName(), professor.getDateOfBirth(), professor.getPlaceOfBirth(),
						professor.getCountryOfBirth(), professor.getScientificArea(), professor.getSpecialScientificArea(), professor.getTitle(),
						professor.getInstitution(), professor.getId());

				return professorDto;
			}
		}
		throw new ProfessorNotFoundException();
	}

	@Override
	@Transactional
	public void saveProfesor(ProfessorDto professorDto) throws UserNameExistException, EmailExistException {
		validateProfessorUserName(professorDto.getId(), professorDto.getUserName());
		validateProfessorEmail(professorDto.getId(), professorDto.getEmail());
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

	/**
	 * Validate if professors userName exists.
	 * 
	 * @param professor id
	 * @param professor userName
	 * @throws UserNameExistException
	 */
	public void validateProfessorUserName(Long professorId, String professorUserName) throws UserNameExistException {
		if (professorId == null || professorId == 0) {
			validateNewProfessorUserName(professorUserName);
		} else {
			validateExistingProfessorUserName(professorId, professorUserName);
		}
	}

	/**
	 * Validate if professor entered existing userName.
	 * 
	 * @param professor userName
	 * @throws UserNameExistException
	 */
	public void validateNewProfessorUserName(String professorUserName) throws UserNameExistException {
		if (StringUtils.isNotBlank(professorUserName)) {
			Professor professor = profesorRepository.findByUserName(professorUserName);
			if (professor != null) {
				LOG.debug("Professor have entered existing user name!");
				throw new UserNameExistException("user name", professorUserName);
			}
		}
	}

	/**
	 * Validate if existing professor entered existing userName.
	 * 
	 * @param professor id
	 * @param professor userName
	 * @throws UserNameExistException
	 */
	public void validateExistingProfessorUserName(Long professorId, String professorUserName) throws UserNameExistException {
		if (StringUtils.isNotBlank(professorUserName)) {
			Professor professor = profesorRepository.findOne(professorId);
			if (!StringUtils.equalsIgnoreCase(professor.getUserName(), professorUserName)) {
				Professor existingProfessor = profesorRepository.findByUserName(professorUserName);
				if (existingProfessor != null) {
					LOG.debug("Professor have entered existing user name!");
					throw new UserNameExistException("user name", professorUserName);
				}
			}
		}
	}

	/**
	 * Validate if professors email correct.
	 * 
	 * @param professor id
	 * @param professor email
	 * @throws EmailExistException
	 */
	public void validateProfessorEmail(Long professorId, String professorEmail) throws EmailExistException {
		if (professorId == null || professorId == 0) {
			validateNewProfessorEmail(professorEmail);
		} else {
			validateExistingProfessorEmail(professorId, professorEmail);
		}
	}

	/**
	 * Validate if professors entered existing email.
	 * 
	 * @param email
	 * @throws EmailExistException
	 */
	public void validateNewProfessorEmail(String email) throws EmailExistException {
		if (StringUtils.isNotBlank(email)) {
			Professor professor = profesorRepository.findByEmail(email);
			if (professor != null) {
				LOG.debug("Professor have entered existing email!");
				throw new EmailExistException("email", email);
			}
		}
	}

	/**
	 * Validate if existing professor entered existing email.
	 * 
	 * @param professor id
	 * @param professor email
	 * @throws EmailExistException
	 */
	public void validateExistingProfessorEmail(Long id, String email) throws EmailExistException {
		if (StringUtils.isNotBlank(email)) {
			User user = profesorRepository.findOne(id);
			if (!StringUtils.equalsIgnoreCase(user.getEmail(), email)) {
				Professor existingProfessor = profesorRepository.findByEmail(email);
				if (existingProfessor != null) {
					LOG.debug("Professor have entered existing email!");
					throw new EmailExistException("email", email);
				}
			}
		}
	}

	@Override
	@Transactional
	public List<ProfessorDto> findProfessorsStartsWith(String value, Long idProf, Long idMentor) throws ProfessorNotFoundException {

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
	public List<ProfessorDto> findProfessorsStartsWith(String value) throws ProfessorNotFoundException {

		List<ProfessorDto> professorsDtoList = new ArrayList<ProfessorDto>();
		List<Professor> professorsList = new ArrayList<Professor>();

		professorsList = profesorRepository.findByNameLikeOrSurnameLike(value);
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
