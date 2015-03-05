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
import com.pct.domain.dto.ProfesorFormaDTO;
import com.pct.service.ProfesorService;
import com.pct.validation.ProfesorNotFoundException;

@RestController
@RequestMapping("/api/professor")
public class ProfesorController {

	private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);

	@Autowired
	ProfesorService profesorService;

	@RequestMapping(value = "persistProfessor", method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfesorFormaDTO> persistProfessor(@Valid @RequestBody ProfesorFormaDTO profesorFormaDTO) {
		profesorService.saveProfesor(profesorFormaDTO);
		ProfesorFormaDTO profesor = new ProfesorFormaDTO();
		try {
			profesor = profesorService.findProfesorByUserName(profesorFormaDTO.getUserName());
		} catch (ProfesorNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Profesor: " + profesor.getUserName() + " (ID " + profesor.getId()
				+ ") successfully registrated in database.");

		return new ResponseEntity<ProfesorFormaDTO>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "/selectedProfesor", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProfesorFormaDTO> showProfesor(String userName) throws ProfesorNotFoundException {
		ProfesorFormaDTO profesor = profesorService.findProfesorByUserName(userName);
		return new ResponseEntity<ProfesorFormaDTO>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "loadProfesorDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProfesorFormaDTO> getProfessorById(@RequestParam(value = "id", required = true) Long id)
			throws ProfesorNotFoundException {
		ProfesorFormaDTO profesor = profesorService.findProfesorById(id);
		return new ResponseEntity<ProfesorFormaDTO>(profesor, HttpStatus.OK);
	}

	@RequestMapping(value = "findProfessorStartsWith", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProfesorFormaDTO>> findProfessorStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "idProf", required = false) Long idProf,
			@RequestParam(value = "idMentor", required = true) Long idMentor) throws ProfesorNotFoundException {

		List<ProfesorFormaDTO> professors = new ArrayList<ProfesorFormaDTO>();
		if (value.length() >= 3) {
			professors = profesorService.findProfessorsStartsWith(value, idProf, idMentor);
		}

		return new ResponseEntity<List<ProfesorFormaDTO>>(professors, HttpStatus.OK);
	}

}
