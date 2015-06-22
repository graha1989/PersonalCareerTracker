package com.pct.controller.api;

import java.util.Collection;
import java.util.List;

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
import com.pct.domain.dto.ScientificProfessionalOrgMemDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.MembershipService;
import com.pct.service.UserService;
import com.pct.validation.AwardNotFoundException;
import com.pct.validation.MembershipNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.SCIENTIFIC_AND_PROFESSIONAL_ORGANIZATION_MEMBERSHIP_API)
public class ScientificProfessionalOrgMemController {

	private static final Logger logger = LoggerFactory.getLogger(ScientificProfessionalOrgMemController.class);

	@Autowired
	MembershipService membershipService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSORS_SCIENTIFIC_AND_PROFESSIONAL_ORGANIZATION_MEMBERSHIPS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ScientificProfessionalOrgMemDto>> showAllProfessorMemberships(
			@RequestParam(value = "professorId", required = true) Long professorId) {

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

		List<ScientificProfessionalOrgMemDto> membershipDtos = null;
		try {
			membershipDtos = membershipService.findAllProfessorMemberships(professorId);
			logger.debug("There is " + membershipDtos.size() + " memberships in scientific and professional organizations for professor with ID "
					+ professorId);
		} catch (MembershipNotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<ScientificProfessionalOrgMemDto>>(membershipDtos, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_PROFESSORS_SCIENTIFIC_AND_PROFESSIONAL_ORGANIZATION_MEMBERSHIP, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ScientificProfessionalOrgMemDto> showMembership(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws AwardNotFoundException {
		logger.debug("Processing request for fetching professor scientific or professional membership in organization data for membership with ID "
				+ id);
		ScientificProfessionalOrgMemDto memDto = null;
		try {
			memDto = membershipService.findProfessorMembershipById(id);
		} catch (MembershipNotFoundException e) {
			logger.error("There was an exception during fetching membership with ID " + id);
			e.printStackTrace();
		}

		return new ResponseEntity<ScientificProfessionalOrgMemDto>(memDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistProfessorMembershipInOrganization(@Valid @RequestBody ScientificProfessionalOrgMemDto memDto) {

		try {
			membershipService.saveProfessorMembershipInOrganization(memDto);
		} catch (ProfessorNotFoundException e) {
			logger.error("Professor with ID " + memDto.getProfessorId() + " is not found.");
			e.printStackTrace();
		}
		logger.debug("Membership: " + memDto.getOrganizationName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ScientificProfessionalOrgMemDto> deleteMembership(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws MembershipNotFoundException {
		membershipService.deleteProfessorMembership(id);

		return new ResponseEntity<ScientificProfessionalOrgMemDto>(HttpStatus.OK);
	}

}
