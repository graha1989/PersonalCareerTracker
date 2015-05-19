package com.pct.service;

import com.pct.domain.dto.UserDto;
import com.pct.validation.UserNotFoundException;

public interface UserService {
	
	void saveUser(UserDto userDto);

	UserDto findAdminById(Long id) throws UserNotFoundException;

}
