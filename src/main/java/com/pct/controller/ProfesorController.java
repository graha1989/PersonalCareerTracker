package com.pct.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pct.constants.MimeTypes;
import com.pct.domain.dto.ProfesorFormaDTO;
import com.pct.service.ProfesorService;
import com.pct.validation.ProfesorNotFoundException;

@RestController
@RequestMapping("/registerProfesor")
public class ProfesorController {

	private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);
	
	@Autowired
	ProfesorService profesorService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("registerProfesor");
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ProfesorFormaDTO> persistProfesor(@Valid @RequestBody ProfesorFormaDTO ProfesorFormaDTO) {
		profesorService.saveProfesor(ProfesorFormaDTO);
		logger.debug("Profesor:" + ProfesorFormaDTO.getUserName() + " successfully registrated in database.");

		return new ResponseEntity<ProfesorFormaDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "selectedProfesor", method = RequestMethod.GET, produces = "application/json")
	public ProfesorFormaDTO showProfesor(String userName) throws ProfesorNotFoundException {
		ProfesorFormaDTO profesor = profesorService.findProfesorByUserName(userName);

		return profesor;
	}

}
