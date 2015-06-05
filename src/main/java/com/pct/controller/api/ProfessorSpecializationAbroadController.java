package com.pct.controller.api;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.SpecializationAbroadDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.ProfessorSpecializationService;
import com.pct.service.UserService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.ProfessorSpecializationNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.SPECIALIZATION_API)
public class ProfessorSpecializationAbroadController {

	private static final Logger logger = LoggerFactory.getLogger(ProfessorSpecializationAbroadController.class);

	@Autowired
	ProfessorSpecializationService professorSpecializationService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSOR_SPECIALIZATIONS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<SpecializationAbroadDto>> showAllProfessorSpecializations(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		UserDto userDto;
		try {
			userDto = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && userDto.getId() != professorId) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

		List<SpecializationAbroadDto> specializationAbroadDtos = null;
		try {
			specializationAbroadDtos = professorSpecializationService.findAllSpecializations(professorId);
		} catch (ProfessorSpecializationNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + specializationAbroadDtos.size() + " specializations.");

		return new ResponseEntity<List<SpecializationAbroadDto>>(specializationAbroadDtos, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessorSpecialization(
			@Valid @RequestBody SpecializationAbroadDto specializationAbroadDto) {

		try {
			professorSpecializationService.saveProfessorSpecialization(specializationAbroadDto);
		} catch (ProfessorSpecializationNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Specialization abroad for professor " + specializationAbroadDto.getProfessorId() + " on "
				+ specializationAbroadDto.getInstitutionName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<SpecializationAbroadDto> deleteProfessorStudies(

	@RequestParam(value = RequestMappings.ID, required = true) Long id) throws ProfessorSpecializationNotFoundException {
		professorSpecializationService.deleteSpecialization(id);

		return new ResponseEntity<SpecializationAbroadDto>(HttpStatus.OK);
	}

}
