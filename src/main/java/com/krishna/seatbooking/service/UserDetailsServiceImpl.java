/**
 * 
 */
package com.krishna.seatbooking.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.krishna.seatbooking.dto.Role;
import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.repository.UserRepository;

/**
 * @author seethayya.n
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

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
			LOGGER.error(e.getMessage(), e);
			throw new UsernameNotFoundException("No user found with username: " + username);
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
