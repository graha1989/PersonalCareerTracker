package com.pct.controller.api;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.FacultyOrUniversityAuthoritiesWorkDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.AcademicCommunityContributionService;
import com.pct.service.UserService;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.ACADEMIC_COMMUNITY_CONTRIBUTION_API)
public class AcademicCommunityContributionController {

	private static final Logger logger = LoggerFactory.getLogger(AcademicCommunityContributionController.class);

	@Autowired
	UserService userService;

	@Autowired
	AcademicCommunityContributionService academicCommunityContributionService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_FACULTY_OR_UNIVERSITY_WORK, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<FacultyOrUniversityAuthoritiesWorkDto>> showAllFacultyOrUniversityAuthorityWork(
			@RequestParam(value = "professorId", required = true) Long professorId,
			@RequestParam(value = "type", required = true) String type) {

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		UserDto userDto;
		try {
			userDto = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && userDto.getId() != professorId) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		logger.debug("Recived request for professor with (ID {}) faculty or university authorities work.", professorId);
		List<FacultyOrUniversityAuthoritiesWorkDto> works = null;
		works = academicCommunityContributionService.findAllFacultyOrUniversityAuthoritiesWorks(professorId, type);
		logger.debug("Found " + works.size() + " faculty or university authorities works for professor with ID {}." , professorId);
		return new ResponseEntity<List<FacultyOrUniversityAuthoritiesWorkDto>>(works, HttpStatus.OK);
	}

}
