package com.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.User;
import com.project.repository.UserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Optional<User> userOptional = userRepository.findByEmail(email);
	    User user = userOptional.orElseThrow(() -> new CustomException(ErrorConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

	    String role = user.getRole().name();
	    return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getEmail())
	            .password(user.getPassword())
	            .roles(role)
	            .build();
	}

}