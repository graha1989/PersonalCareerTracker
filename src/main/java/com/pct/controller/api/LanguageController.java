package com.pct.controller.api;

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
import com.pct.constants.RequestMappings;
import com.pct.domain.Language;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.service.LanguageService;
import com.pct.validation.LanguageNotFoundException;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);
	
	@Autowired
	LanguageService languageService;
	
	@RequestMapping(value = "allLanguages", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<LanguageExperienceDto>> showAllLanguages(@RequestParam(value = "mentorId", required = true) Long mentorId) {
		List<LanguageExperienceDto> languages = languageService.findAllLanguageExperiences(mentorId);

		return new ResponseEntity<List<LanguageExperienceDto>>(languages, HttpStatus.OK);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<LanguageExperienceDto> persistLanguageExperience(@Valid @RequestBody LanguageExperienceDto languageExperienceDto) {

		LanguageExperienceDto language = new LanguageExperienceDto();
		language = languageService.saveLanguageExperience(languageExperienceDto);

		logger.debug("Language experience for:" + language.getLanguageName() + " language successfully saved.");

		return new ResponseEntity<LanguageExperienceDto>(language, HttpStatus.OK);
	}
	
	@RequestMapping(value = "selectedLanguage", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Language> loadLanguage(@RequestParam(value = RequestMappings.ID, required = true) Long id) throws LanguageNotFoundException {
		Language language = languageService.findLanguageById(id);

		return new ResponseEntity<Language>(language, HttpStatus.OK);
	}
	
}
