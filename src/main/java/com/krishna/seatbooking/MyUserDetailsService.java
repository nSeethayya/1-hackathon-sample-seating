/**
 * 
 */
package com.krishna.seatbooking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krishna.seatbooking.dto.Role;
import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.repository.RoleRepository;
import com.krishna.seatbooking.repository.UserRepository;

/**
 * @author seethayya.n
 *
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RoleRepository roleRepository;

	/*
	 * public MyUserDetailsService(UserRepository userRepository, PasswordEncoder
	 * passwordEncoder, AuthenticationManager authenticationManager) {
	 * this.userRepository = userRepository; this.passwordEncoder = passwordEncoder;
	 * this.authenticationManager = authenticationManager; }
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			final User user = userRepository.findByUserName(username);
			if (user == null) {
				throw new UsernameNotFoundException("No user found with username: " + username);
			}
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					user.getEnable(), true, true, true, getAuthorities(user.getRoles()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String findLoggedInUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		}
	}

	@Transactional
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findAll());
		userRepository.save(user);
	}

	public void autologin(String username, String password) {
		UserDetails userDetails = loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			LOGGER.debug(String.format("Auto login %s successfully!", username));
		}
	}

	private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {

		List<String> rolesList = (roles == null ? Collections.emptyList()
				: roles.stream().map(r -> r.getName()).collect(Collectors.toList()));
		return getGrantedAuthorities(rolesList);
	}

	private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
