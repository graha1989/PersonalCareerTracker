package com.pct.controller.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
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
import com.pct.domain.Language;
import com.pct.domain.dto.LanguageDto;
import com.pct.domain.dto.LanguageExperienceDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.LanguageService;
import com.pct.service.UserService;
import com.pct.validation.LanguageExperienceNotFoundException;
import com.pct.validation.LanguageNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.LANGUAGES_API)
public class LanguageController {

	private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

	@Autowired
	LanguageService languageService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSOR_LANGUAGES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<LanguageExperienceDto>> showAllProfessorLanguages(
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

		List<LanguageExperienceDto> languages = languageService.findAllLanguageExperiences(professorId);

		return new ResponseEntity<List<LanguageExperienceDto>>(languages, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_LANGUAGES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<LanguageDto>> showAllLanguages(
			@RequestParam(value = "languageExperienceIdsList", required = false) @Nullable List<Long> languageExperienceIdsList) {
		List<LanguageDto> languages = new ArrayList<LanguageDto>();
		try {
			languages = languageService.findAllNotListedLanguages(languageExperienceIdsList);
		} catch (LanguageNotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<LanguageDto>>(languages, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistLanguageExperience(
			@Valid @RequestBody LanguageExperienceDto languageExperienceDto) {

		try {
			languageService.saveLanguageExperience(languageExperienceDto);
		} catch (LanguageNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Language experience for:" + languageExperienceDto.getLanguageName()
				+ " language successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_LANGUAGE, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<Language> loadLanguage(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws LanguageNotFoundException {

		Language language = languageService.findLanguageById(id);

		return new ResponseEntity<Language>(language, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<LanguageExperienceDto> deleteLanguageExperience(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws LanguageExperienceNotFoundException {
		languageService.deleteLanguageExperience(id);

		return new ResponseEntity<LanguageExperienceDto>(HttpStatus.OK);
	}

}
