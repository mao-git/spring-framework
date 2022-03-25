package com.devs4j.users.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devs4j.users.entities.User;
import com.devs4j.users.entities.UserInRole;
import com.devs4j.users.repository.UserInRoleRepository;
import com.devs4j.users.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByUsername(username);

		if (optional.isEmpty()) {
			throw new UsernameNotFoundException(String.format("Username %s not found ", username));
		}

		User user = optional.get();
		List<UserInRole> userInRoles = userInRoleRepository.findByUser(user);
		String[] roles = userInRoles.stream().map(role -> role.getRole().getName()).toArray(String[]::new);

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(passwordEncoder.encode(user.getPassword())).roles(roles).build();
	}
}
