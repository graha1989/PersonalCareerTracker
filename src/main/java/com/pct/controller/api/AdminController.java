package com.pct.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.constants.MimeTypes;
import com.pct.constants.RequestMappings;
import com.pct.domain.dto.UserDto;
import com.pct.service.UserService;
import com.pct.validation.UserNotFoundException;

@RestController
@RequestMapping(RequestMappings.ADMINS_API)
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	UserService userService;
	
	@RequestMapping(value = RequestMappings.LOAD_ADMIN_DETAILS, method = RequestMethod.GET, produces = MimeTypes.APPLICATION_JSON)
	public ResponseEntity<UserDto> getAdminById(@RequestParam(value = "id", required = true) Long id) {
		UserDto userDto = new UserDto();
		try {
			userDto = userService.findAdminById(id);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		
		logger.debug("Admin details for " + userDto.getUserName() + " successfully loaded.");
		
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

}