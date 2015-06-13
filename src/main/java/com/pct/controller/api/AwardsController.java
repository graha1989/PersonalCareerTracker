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
import com.pct.domain.AwardField;
import com.pct.domain.AwardType;
import com.pct.domain.dto.AwardDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.AwardService;
import com.pct.service.UserService;
import com.pct.validation.AwardNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.AWARDS_API)
public class AwardsController {

	private static final Logger logger = LoggerFactory.getLogger(AwardsController.class);

	@Autowired
	AwardService awardService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_AWARDS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<AwardDto>> showAllAwards() {
		List<AwardDto> awards = awardService.findAll();

		return new ResponseEntity<List<AwardDto>>(awards, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_PROFESSOR_AWARDS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<AwardDto>> showAllProfessorAwards(
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

		List<AwardDto> awards = null;
		try {
			awards = awardService.findAllAwards(professorId);
		} catch (AwardNotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<AwardDto>>(awards, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_AWARD_TYPES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<AwardType>> showAllAwardTypes() {
		List<AwardType> awardTypes = awardService.findAllAwardTypes();

		return new ResponseEntity<List<AwardType>>(awardTypes, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_AWARD_FIELDS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<AwardField>> showAllAwardFields() {
		List<AwardField> awardFields = awardService.findAllAwardFields();

		return new ResponseEntity<List<AwardField>>(awardFields, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistAward(@Valid @RequestBody AwardDto awardDto) {

		try {
			awardService.saveAward(awardDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Award:" + awardDto.getAwardName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_AWARD, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<AwardDto> showAward(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws AwardNotFoundException {
		AwardDto award = awardService.findAwardById(id);

		return new ResponseEntity<AwardDto>(award, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<AwardDto> deleteAward(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws AwardNotFoundException {
		awardService.deleteAward(id);

		return new ResponseEntity<AwardDto>(HttpStatus.OK);
	}

}
