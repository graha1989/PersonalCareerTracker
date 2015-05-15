package com.pct.service.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.pct.domain.User;
import com.pct.domain.dto.UserDto;

public class UserUtil {

	private static final int SALT_LENGTH = 12;

	/**
	 * Generate password hash and set it with salt.
	 * 
	 * @param user
	 */
	public static void encodePassword(User user) {
		String salt = BCrypt.gensalt(SALT_LENGTH);
		String passwordHash = BCrypt.hashpw(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(passwordHash);
	}
	
	/**
	 * Encode the password with salt 
	 * 
	 * @param user
	 * @return
	 */
	public static String encodePassword(String password, String salt) {
		return BCrypt.hashpw(password, salt);
	}
	
	public static void copyUserPropertiesFromDto(User user, UserDto userDto) {
		if(userDto.getId() == null){
			user.setPassword(userDto.getPassword());
		}
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
	}
	
	/**
	 * Private constructor. Prevents initialization.
	 */
	private UserUtil() {
		// EMPTY CONSTRUCTOR.
	}
}
