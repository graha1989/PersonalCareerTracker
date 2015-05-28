package com.pct.service;

import com.pct.domain.dto.UserDto;
import com.pct.validation.EmailExistException;
import com.pct.validation.UserNameExistException;
import com.pct.validation.UserNotFoundException;

public interface UserService {
	
	void saveUser(UserDto userDto) throws UserNameExistException, EmailExistException;

	UserDto findAdminById(Long id) throws UserNotFoundException;

}
