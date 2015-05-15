package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.domain.dto.ProfessorDto;
import com.pct.service.ProfessorService;
import com.pct.service.UserService;
import com.pct.validation.ProfessorNotFoundException;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

	private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);

	@Autowired
	ProfessorService professorService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "persistProfessor", method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessor(@Valid @RequestBody ProfessorDto professorDto) {

		if (StringUtils.isBlank(professorDto.getFathersName()) && professorDto.getDateOfBirth() == null
				&& StringUtils.isBlank(professorDto.getPlaceOfBirth())
				&& StringUtils.isBlank(professorDto.getCountryOfBirth())
				&& StringUtils.isBlank(professorDto.getScientificArea())
				&& StringUtils.isBlank(professorDto.getSpecialScientificArea())) {
			userService.saveUser(professorDto);
		} else {
			professorService.saveProfesor(professorDto);
		}

		logger.debug("Professor: " + professorDto.getUserName() + " (ID " + professorDto.getId()
				+ ") successfully registrated in database.");
		return new ResponseEntity<String>("Successfully persisted user", HttpStatus.OK);
	}

	@RequestMapping(value = "selectedProfesor", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorDto> showProfesor(String userName) throws ProfessorNotFoundException {
		ProfessorDto profesor = professorService.findProfesorByUserName(userName);
		return new ResponseEntity<ProfessorDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "loadProfessorDetails", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorDto> getProfessorById(@RequestParam(value = "id", required = true) Long id) {
		ProfessorDto professorDto = new ProfessorDto();
		try {
			professorDto = professorService.findProfesorById(id);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProfessorDto>(professorDto, HttpStatus.OK);
	}

	@RequestMapping(value = "findProfessorStartsWith", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProfessorDto>> findProfessorStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "idProf", required = false) Long idProf,
			@RequestParam(value = "idMentor", required = true) Long idMentor) throws ProfessorNotFoundException {

		List<ProfessorDto> professors = new ArrayList<ProfessorDto>();
		if (value.length() >= 3) {
			professors = professorService.findProfessorsStartsWith(value, idProf, idMentor);
		}

		return new ResponseEntity<List<ProfessorDto>>(professors, HttpStatus.OK);
	}

	@Secured(value = "ROLE_ADMIN")
	@RequestMapping(value = "allProfessors", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProfessorDto>> showAllProfessors() {

		List<ProfessorDto> professorDtos = professorService.findAllProfessors();
		logger.debug("Current number of professors in database is " + professorDtos.size() + ".");

		return new ResponseEntity<List<ProfessorDto>>(professorDtos, HttpStatus.OK);
	}

}