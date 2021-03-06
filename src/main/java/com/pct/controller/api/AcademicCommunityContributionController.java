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
import com.pct.domain.dto.AcademicCommunityContributionDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.AcademicCommunityContributionService;
import com.pct.service.UserService;
import com.pct.validation.AcademicCommunityContributionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.ACADEMIC_COMMUNITY_CONTRIBUTION_API)
public class AcademicCommunityContributionController {

	private static final Logger logger = LoggerFactory.getLogger(AcademicCommunityContributionController.class);

	@Autowired
	UserService userService;

	@Autowired
	AcademicCommunityContributionService academicCommunityContributionService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_ACADEMIC_COMMUNITY_CONTRIBUTIONS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<AcademicCommunityContributionDto>> showAllFacultyOrUniversityAuthorityWork(
			@RequestParam(value = "professorId", required = true) Long professorId, @RequestParam(value = "type", required = true) String type) {

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
		logger.debug("Received request for professor with (ID {}) academic community contributions.", professorId);
		List<AcademicCommunityContributionDto> contributionDtos = null;
		contributionDtos = academicCommunityContributionService.findAllAcademicCommunityContributions(professorId, type);
		logger.debug("Found " + contributionDtos.size() + " academic community contribution(s) for professor with ID {}.", professorId);
		return new ResponseEntity<List<AcademicCommunityContributionDto>>(contributionDtos, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_ACADEMIC_COMMUNITY_CONTRIBUTION, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<AcademicCommunityContributionDto> showAcademicCommunityContribution(
			@RequestParam(value = RequestMappings.ID, required = true) Long id) throws AcademicCommunityContributionNotFoundException {
		logger.debug("Received request for AcademicCommunityContribution with ID {}.", id);
		AcademicCommunityContributionDto contributionDto = academicCommunityContributionService.findAcademicCommunityContributionById(id);
		logger.debug("AcademicCommunityContribution with ID {} successfully founded.", id);
		return new ResponseEntity<AcademicCommunityContributionDto>(contributionDto, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.SAVE_SELECTED_ACADEMIC_COMMUNITY_CONTRIBUTION, method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistAcademicCommunityContribution(@Valid @RequestBody AcademicCommunityContributionDto contributionDto) {

		try {
			academicCommunityContributionService.saveAcademicCommunityContribution(contributionDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("AcademicCommunityContribution: {} successfully saved.", contributionDto.getAuthorityOrganizationOrJournal());

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.DELETE_ACADEMIC_COMMUNITY_CONTRIBUTION, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAcademicCommunityContribution(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws AcademicCommunityContributionNotFoundException {
		academicCommunityContributionService.deleteAcademicCommunityContribution(id);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}