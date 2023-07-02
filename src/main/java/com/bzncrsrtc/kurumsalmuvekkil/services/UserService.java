package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UserNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private UserRepository userRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepository, MessageSource messageSource) {
		this.userRepository = userRepository;
		this.messageSource = messageSource;
	}
	
	public User create(User user, Locale locale) {
		if(userRepository.existsByEmailAndDeleted(user.getEmail(), false)) {
			throw new EmailAlreadyUsedException(messageSource.getMessage("email.already.used.message", null, locale));
		}
		
		if(userRepository.existsByUsernameAndDeleted(user.getUsername(), false)) {
			throw new UsernameAlreadyUsedException(messageSource.getMessage("username.already.used.message", null, locale));
		}
		
		return userRepository.save(user);
	}
	
	public void update(User user, Locale locale) {
		if(!userRepository.existsById(user.getId())) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null, locale));
		}
		
		if(userRepository.existsByEmailAndDeletedAndIdNot(user.getEmail(), false, user.getId())) {
			throw new EmailAlreadyUsedException(messageSource.getMessage("email.already.used.message", null, locale));
		}
		
		if(userRepository.existsByUsernameAndDeletedAndIdNot(user.getUsername(), false, user.getId())) {
			throw new UsernameAlreadyUsedException(messageSource.getMessage("username.already.used.message", null, locale));
		}
		
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username){
		Optional <User> user = userRepository.findByUsernameAndDeletedAndActive(username, false, true);
		
		if(user.isEmpty()) {
			throw new BadCredentialsException(messageSource.getMessage("bad.credentials.message", null, null));
		}
		
		return user.get();
	}

}
