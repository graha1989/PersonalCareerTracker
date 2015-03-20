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
import com.pct.domain.dto.ProfesorFormaDto;
import com.pct.service.ProfesorService;
import com.pct.validation.ProfesorNotFoundException;

@RestController
@RequestMapping("/api/professor")
public class ProfesorController {

	private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);

	@Autowired
	ProfesorService profesorService;

	@RequestMapping(value = "persistProfessor", method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfesorFormaDto> persistProfessor(@Valid @RequestBody ProfesorFormaDto profesorFormaDto) {
		profesorService.saveProfesor(profesorFormaDto);
		ProfesorFormaDto profesor = new ProfesorFormaDto();
		try {
			profesor = profesorService.findProfesorByUserName(profesorFormaDto.getUserName());
		} catch (ProfesorNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Profesor: " + profesor.getUserName() + " (ID " + profesor.getId()
				+ ") successfully registrated in database.");

		return new ResponseEntity<ProfesorFormaDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "/selectedProfesor", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProfesorFormaDto> showProfesor(String userName) throws ProfesorNotFoundException {
		ProfesorFormaDto profesor = profesorService.findProfesorByUserName(userName);
		return new ResponseEntity<ProfesorFormaDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "loadProfesorDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProfesorFormaDto> getProfessorById(@RequestParam(value = "id", required = true) Long id) {
		ProfesorFormaDto profesor = new ProfesorFormaDto();
		try {
			profesor = profesorService.findProfesorById(id);
		} catch (ProfesorNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProfesorFormaDto>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "findProfessorStartsWith", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProfesorFormaDto>> findProfessorStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "idProf", required = false) Long idProf,
			@RequestParam(value = "idMentor", required = true) Long idMentor) throws ProfesorNotFoundException {

		List<ProfesorFormaDto> professors = new ArrayList<ProfesorFormaDto>();
		if (value.length() >= 3) {
			professors = profesorService.findProfessorsStartsWith(value, idProf, idMentor);
		}

		return new ResponseEntity<List<ProfesorFormaDto>>(professors, HttpStatus.OK);
	}

}
