package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@Service
public class AuthService {

	private MessageSource messageSource;
	private AuthenticationManager authenticationManager;
	
	public AuthService(MessageSource messageSource, AuthenticationManager authenticationManager) {
		this.messageSource = messageSource;
		this.authenticationManager = authenticationManager;
	}
	
	public Authentication login(String username, String password, Locale locale) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				
		if(!authentication.isAuthenticated()) {
			throw new BadCredentialsException(messageSource.getMessage("bad.credentials.message", null, null));
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return authentication;
	}

	
}
