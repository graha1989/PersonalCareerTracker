package com.pct.controller.api;

import java.util.ArrayList;
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
import com.pct.domain.dto.InstitutionDto;
import com.pct.domain.dto.UserDto;
import com.pct.domain.dto.WorkExperienceDto;
import com.pct.service.UserService;
import com.pct.service.WorkExperienceService;
import com.pct.validation.InstitutionNotFoundException;
import com.pct.validation.ProfessorNotFoundException;
import com.pct.validation.SimilarDataAlreadyExistsException;
import com.pct.validation.UserNotFoundException;
import com.pct.validation.WorkExperienceNotFoundException;

@RestController
@RequestMapping(RequestMappings.WORK_EXPERIENCES_API)
public class WorkExperienceController {

	private static final Logger logger = LoggerFactory.getLogger(WorkExperienceController.class);

	@Autowired
	WorkExperienceService workExperienceService;

	@Autowired
	UserService userService;

	@RequestMapping(value = RequestMappings.LOAD_ALL_WORK_EXPERIENCES, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<WorkExperienceDto>> showAllProfessorWorkExperiences(
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

		List<WorkExperienceDto> experiences = null;
		try {
			experiences = workExperienceService.findAllWorkExperiences(professorId);
		} catch (WorkExperienceNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Professor got: " + experiences.size() + " work experiences.");

		return new ResponseEntity<List<WorkExperienceDto>>(experiences, HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_SELECTED_WORK_EXPERIENCE, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<WorkExperienceDto> showWorkExperience(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws WorkExperienceNotFoundException {
		WorkExperienceDto workExperienceDto = workExperienceService.findWorkExperienceById(id);

		return new ResponseEntity<WorkExperienceDto>(workExperienceDto, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<String> persistWorkExperience(@Valid @RequestBody WorkExperienceDto workExperienceDto)
			throws SimilarDataAlreadyExistsException {

		try {
			workExperienceService.saveWorkExperience(workExperienceDto);
		} catch (WorkExperienceNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (InstitutionNotFoundException e) {
			e.printStackTrace();
		}

		logger.debug("Work experience in:" + workExperienceDto.getInstitutionName() + " successfully saved.");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = RequestMappings.LOAD_INSTITUTIONS_STARTS_WITH, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<List<InstitutionDto>> findInstitutionStartsWith(@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "institutionType", required = false) String institutionType) throws InstitutionNotFoundException {

		List<InstitutionDto> institutions = new ArrayList<InstitutionDto>();
		if (value.length() >= 3) {
			institutions = workExperienceService.findInstitutionsStartsWith(value, institutionType);
		}

		return new ResponseEntity<List<InstitutionDto>>(institutions, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<WorkExperienceDto> deleteWorkExperience(@RequestParam(value = RequestMappings.ID, required = true) Long id)
			throws WorkExperienceNotFoundException {
		workExperienceService.deleteWorkExperience(id);

		return new ResponseEntity<WorkExperienceDto>(HttpStatus.OK);
	}

}
