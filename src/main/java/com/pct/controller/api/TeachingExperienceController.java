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
import com.pct.domain.dto.SubjectDto;
import com.pct.domain.dto.TeachingExperienceDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.SubjectService;
import com.pct.service.TeachingExperienceService;
import com.pct.service.UserService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SubjectNotFoundException;
import com.pct.validation.TeachingExperienceNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping("/api/teachingExperiences")
public class TeachingExperienceController {

	private static final Logger logger = LoggerFactory.getLogger(TeachingExperienceController.class);

	@Autowired
	TeachingExperienceService teachingExperienceService;
	
	@Autowired
	UserService userService;

	@Autowired
	SubjectService subjectService;

	@RequestMapping(value = "allTeachingExperiences", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<TeachingExperienceDto>> showAllProfessorTeachingExperiences(
			@RequestParam(value = "professorId", required = true) Long professorId) {

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		UserDto userDto;
		try {
			userDto = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			if(!roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && userDto.getId() != professorId){
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		
		List<TeachingExperienceDto> experiences = null;
		try {
			experiences = teachingExperienceService.findAllTeachingExperiences(professorId);
		} catch (TeachingExperienceNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Professor got: " + experiences.size() + " teaching experiences.");

		return new ResponseEntity<List<TeachingExperienceDto>>(experiences, HttpStatus.OK);
	}

	@RequestMapping(value = "selectedTeachingExperience", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<TeachingExperienceDto> showTeachingExperience(
			@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws TeachingExperienceNotFoundException {
		TeachingExperienceDto teachingExperienceDto = teachingExperienceService.findTeachingExperienceById(id);

		return new ResponseEntity<TeachingExperienceDto>(teachingExperienceDto, HttpStatus.OK);
	}

	@RequestMapping(value = "findSubjectsStartsWith", method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<SubjectDto>> findSubjectsStartsWith(
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "subjectIds", required = false) @Nullable List<Long> subjectIds)
			throws SubjectNotFoundException {

		List<SubjectDto> subjects = new ArrayList<SubjectDto>();
		if (value.length() >= 3) {
			subjects = subjectService.findAvailableSubjectsStartsWith(value, subjectIds);
		}

		return new ResponseEntity<List<SubjectDto>>(subjects, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistTeachingExperience(
			@Valid @RequestBody TeachingExperienceDto teachingExperienceDto) {

		try {
			teachingExperienceService.saveTeachingExperience(teachingExperienceDto);
		} catch (TeachingExperienceNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (SubjectNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Teaching experience for: " + teachingExperienceDto.getSubjectDto().getSubjectName()
				+ " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<TeachingExperienceDto> deleteTeachingExperience(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws TeachingExperienceNotFoundException {
		teachingExperienceService.deleteTeachingExperience(id);

		return new ResponseEntity<TeachingExperienceDto>(HttpStatus.OK);
	}

}
