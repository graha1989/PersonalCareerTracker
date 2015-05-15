package com.pct.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void saveUser(UserDto userDto) {
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

}
