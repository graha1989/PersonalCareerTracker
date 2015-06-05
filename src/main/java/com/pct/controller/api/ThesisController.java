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
import com.pct.domain.StudiesThesisType;
import com.pct.domain.dto.ThesisDto;
import com.pct.domain.dto.UserDto;
import com.pct.service.ThesisService;
import com.pct.service.UserService;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.StudentNotFoundException;
import com.pct.validation.StudiesThesisTypeNotFoundException;
import com.pct.validation.ThesisNotFoundException;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.THESIS_API)
public class ThesisController {

	private static final Logger logger = LoggerFactory.getLogger(ThesisController.class);

	@Autowired
	ThesisService thesisService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_THESIS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<ThesisDto>> showAllThesis(
			@RequestParam(value = "mentorId", required = true) Long mentorId,
			@RequestParam(value = "thesisTypeId", required = true) Long thesisTypeId) {

		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		UserDto userDto;
		try {
			userDto = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			if (!roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && userDto.getId() != mentorId) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

		List<ThesisDto> thesis = thesisService.findAllThesis(mentorId, thesisTypeId);

		return new ResponseEntity<List<ThesisDto>>(thesis, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_THESIS_TYPES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<StudiesThesisType>> showAllThesisTypes() {
		List<StudiesThesisType> studiesThesisTypes = thesisService.findAllThesisType();

		return new ResponseEntity<List<StudiesThesisType>>(studiesThesisTypes, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ThesisDto> persistThesis(@Valid @RequestBody ThesisDto thesisDto) {

		ThesisDto thesis = new ThesisDto();
		try {
			thesis = thesisService.saveThesis(thesisDto);
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (StudiesThesisTypeNotFoundException e) {
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Thesis:" + thesis.getTitle() + " successfully saved.");

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_ALL_THESIS_TYPE_DETAILS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<StudiesThesisType> getThesisTypeById(@RequestParam(value = "id", required = true) Long id) {
		StudiesThesisType studiesThesisType = new StudiesThesisType();
		try {
			studiesThesisType = thesisService.findThesisTypeById(id);
		} catch (StudiesThesisTypeNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StudiesThesisType>(studiesThesisType, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ThesisDto> deleteThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		thesisService.deleteThesis(id);

		return new ResponseEntity<ThesisDto>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_THESIS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<ThesisDto> showThesis(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws ThesisNotFoundException {
		ThesisDto thesis = thesisService.findThesisById(id);

		return new ResponseEntity<ThesisDto>(thesis, HttpStatus.OK);
	}

}
