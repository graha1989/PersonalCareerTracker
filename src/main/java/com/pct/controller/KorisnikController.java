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
import com.pct.domain.dto.KorisnikFormaDTO;
import com.pct.service.KorisnikService;

@RestController
@RequestMapping("/registerUser")
public class KorisnikController {

	private static final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

	@Autowired
	KorisnikService korisnikService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("registerUser");
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<KorisnikFormaDTO> persistKorisnik(@Valid @RequestBody KorisnikFormaDTO korisnikFormaDTO) {
		korisnikService.saveKorisnik(korisnikFormaDTO);
		logger.debug("User:" + korisnikFormaDTO.getUserName() + " successfully registrated in database.");

		return new ResponseEntity<KorisnikFormaDTO>(HttpStatus.OK);
	}

}
