package com.pct.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*
import com.levi9.rvr.tolkvertaler.domain.security.Role;
import com.levi9.rvr.tolkvertaler.domain.security.User;
import com.levi9.rvr.tolkvertaler.repository.UserRepository;
import com.levi9.rvr.tolkvertaler.service.UserUtil;
*/
/**
 * Customized authentication provider.
 * 
 * @author m.radojcic
 * 
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
//	TODO Remove 
	@Override
	public Authentication authenticate(Authentication arg0)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}
//	TODO Remove 
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
/*
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private UserRepository userRepository;

	*//**
	 * {@inheritDoc}
	 *//*
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = (String) authentication.getPrincipal();
		String credentials = (String) authentication.getCredentials();

		User user = userRepository.findByUsername(username);

		validateUser(user, credentials);

		List<GrantedAuthority> authorities = loadAuthorities(user);

		 Update user 
		int loginCount = user.getLoginCount() + 1;
		user.setLoginCount(loginCount);
		user.setLastLogin(new Date());
		userRepository.saveAndFlush(user);

		LOGGER.info("CustomAuthenticationProvider#authenticate user:" + username);

		return new UsernamePasswordAuthenticationToken(username, credentials, authorities);
	}

	*//**
	 * {@inheritDoc}
	 *//*
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	*//**
	 * Validates user. Throws UsernameNotFoundException if user doesn't exist.
	 * 
	 * @param user
	 * @param credentials
	 * @throws {@link UsernameNotFoundException} if user doesn't exist.
	 * @throws {@link BadCredentialsException} if user credentials are incorrect
	 *//*
	private void validateUser(User user, String credentials) {
		if (user == null) {
			LOGGER.debug("Login failed");
			throw new UsernameNotFoundException("Username not found");
		}

		if (!isPasswordValid(credentials, user)) {
			LOGGER.debug("Login failed");
			throw new BadCredentialsException("Login failed");
		}

		if (user.getExpirationDate() == null || user.getExpirationDate().before(new Date())) {
			LOGGER.debug("Login failed");
			throw new BadCredentialsException("Password expired");
		}

	}

	*//**
	 * Checks if passwords match.
	 * 
	 * @param credentials
	 * @param login
	 * @return
	 *//*
	private boolean isPasswordValid(String credentials, User user) {
		// String encodePassword = new ShaPasswordEncoder(256).encodePassword(credentials, user.getSalt());
		// return encodePassword.equals(login.getPassword());
		String encodedCredentials = UserUtil.encodePassword(credentials, user.getSalt());
		return encodedCredentials.equals(user.getPassword());
	}

	*//**
	 * Loads authorities from user.
	 * 
	 * @param userBean.
	 * @return user's authorities.
	 *//*
	private List<GrantedAuthority> loadAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Collection<Role> roles = user.getRoles();
		for (Role r : roles) {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
		}
		return authorities;
	}

	*//**
	 * Setter for userRepository. Used for tests exclusively.
	 * 
	 * @param userRepository
	 *//*
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
*/
}
