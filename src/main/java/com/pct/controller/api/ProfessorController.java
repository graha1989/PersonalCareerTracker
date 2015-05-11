package com.pct.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.domain.dto.ProfessorDto;
import com.pct.service.ProfessorService;
import com.pct.validation.ProfessorNotFoundException;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

	private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);

	@Autowired
	ProfessorService professorService;

	@RequestMapping(value = "persistProfessor", method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorDto> persistProfessor(@Valid @RequestBody ProfessorDto professorDto) {
		professorService.saveProfesor(professorDto);
		ProfessorDto profesor = new ProfessorDto();
		try {
			profesor = professorService.findProfesorByUserName(professorDto.getUserName());
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Professor: " + profesor.getUserName() + " (ID " + profesor.getId()
				+ ") successfully registrated in database.");

		return new ResponseEntity<ProfessorDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedProfesor", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorDto> showProfesor(String userName) throws ProfessorNotFoundException {
		ProfessorDto profesor = professorService.findProfesorByUserName(userName);
		return new ResponseEntity<ProfessorDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "loadProfesorDetails", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfessorDto> getProfessorById(@RequestParam(value = "id", required = true) Long id) {
		ProfessorDto profesor = new ProfessorDto();
		try {
			profesor = professorService.findProfesorById(id);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProfessorDto>(profesor, HttpStatus.OK);
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

	@RequestMapping(value = "allProfessors", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ProfessorDto>> showAllProfessors() {

		List<ProfessorDto> professorDtos = professorService.findAllProfessors();
		logger.debug("Current number of professors in database is " + professorDtos.size() + ".");

		return new ResponseEntity<List<ProfessorDto>>(professorDtos, HttpStatus.OK);
	}

}
