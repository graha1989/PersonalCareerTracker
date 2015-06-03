package com.pct.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pct.domain.Role;
import com.pct.domain.User;
import com.pct.domain.dto.UserDto;
import com.pct.domain.enums.RoleNames;
import com.pct.repository.RoleRepository;
import com.pct.repository.UserRepository;
import com.pct.service.UserService;
import com.pct.service.util.UserUtil;
import com.pct.validation.EmailExistException;
import com.pct.validation.UserNameExistException;
import com.pct.validation.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void saveUser(UserDto userDto) throws UserNameExistException, EmailExistException {
		validateUserUserName(userDto.getId(), userDto.getUserName());
		validateUserEmail(userDto.getId(), userDto.getEmail());
		User user = convertUserDtoToUser(userDto);

		if (user.getId() == null && StringUtils.isNotBlank(user.getPassword())) {
			UserUtil.encodePassword(user);
		}
		Role role = roleRepository.findByName(RoleNames.ROLE_ADMIN.getName());
		if (role != null) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
			user.setRoles(roles);
		}

		userRepository.saveAndFlush(user);
	}

	/**
	 * Validate if users userName correct.
	 * 
	 * @param user id
	 * @param user userName
	 * @throws UserNameExistException
	 */
	public void validateUserUserName(Long userId, String userName) throws UserNameExistException {
		if (userId == null || userId == 0) {
			validateNewUserUserName(userName);
		} else {
			validateExistingUserUserName(userId, userName);
		}
	}

	/**
	 * Validate if user entered existing userName.
	 * 
	 * @param user userName
	 * @throws UserNameExistException
	 */
	public void validateNewUserUserName(String userName) throws UserNameExistException {
		if (StringUtils.isNotBlank(userName)) {
			User user = userRepository.findByUserName(userName);
			if (user != null) {
				LOG.debug("User have entered existing user name!");
				throw new UserNameExistException("user name", userName);
			}
		}
	}

	/**
	 * Validate if existing user entered existing userName.
	 * 
	 * @param user id
	 * @param user userName
	 * @throws UserNameExistException
	 */
	public void validateExistingUserUserName(Long id, String userName) throws UserNameExistException {
		if (StringUtils.isNotBlank(userName)) {
			User user = userRepository.findOne(id);
			if (!StringUtils.equalsIgnoreCase(user.getUserName(), userName)) {
				User existingUser = userRepository.findByUserName(userName);
				if (existingUser != null) {
					LOG.debug("User have entered existing user name!");
					throw new UserNameExistException("user name", userName);
				}
			}
		}
	}

	/**
	 * Validate if users email correct.
	 * 
	 * @param user id
	 * @param user email
	 * @throws EmailExistException
	 */
	public void validateUserEmail(Long userId, String userEmail) throws EmailExistException {
		if (userId == null || userId == 0) {
			validateNewUserEmail(userEmail);
		} else {
			validateExistingUserEmail(userId, userEmail);
		}
	}

	/**
	 * Validate if user entered existing email.
	 * 
	 * @param email
	 * @throws EmailExistException
	 */
	public void validateNewUserEmail(String email) throws EmailExistException {
		if (StringUtils.isNotBlank(email)) {
			User user = userRepository.findByEmail(email);
			if (user != null) {
				LOG.debug("User have entered existing email!");
				throw new EmailExistException("email", email);
			}
		}
	}

	/**
	 * Validate if existing user entered existing email.
	 * 
	 * @param user id
	 * @param user email
	 * @throws EmailExistException
	 */
	public void validateExistingUserEmail(Long id, String email) throws EmailExistException {
		if (StringUtils.isNotBlank(email)) {
			User user = userRepository.findOne(id);
			if (!StringUtils.equalsIgnoreCase(user.getEmail(), email)) {
				User existingUser = userRepository.findByEmail(email);
				if (existingUser != null) {
					LOG.debug("User have entered existing email!");
					throw new EmailExistException("email", email);
				}
			}
		}
	}

	/**
	 * Converts user dto to user.
	 * 
	 * @param dto
	 * @return
	 */
	private User convertUserDtoToUser(UserDto dto) {
		User user = null;

		if (dto.getId() != null && dto.getId() > 0L) {
			user = userRepository.findOne(dto.getId());
		} else {
			user = new User();
		}
		UserUtil.copyUserPropertiesFromDto(user, dto);

		return user;
	}

	@Override
	@Transactional
	public UserDto findAdminById(Long id) throws UserNotFoundException {

		UserDto userDto;
		if (id != null) {
			User user = userRepository.findOne(id);
			if (user != null) {
				userDto = new UserDto(user.getUserName(), user.getPassword(), user.getEmail(), user.getName(),
						user.getSurname(), user.getId());

				return userDto;
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	@Transactional
	public UserDto findUserByUserName(String userName) throws UserNotFoundException {
		
		UserDto userDto;
		if (userName != null) {
			User user = userRepository.findByUserName(userName);
			if (user != null) {
				userDto = new UserDto(user.getUserName(), user.getPassword(), user.getEmail(),
						user.getName(), user.getSurname(), user.getId());

				return userDto;
			}
		}

		throw new UserNotFoundException();
	}

}
