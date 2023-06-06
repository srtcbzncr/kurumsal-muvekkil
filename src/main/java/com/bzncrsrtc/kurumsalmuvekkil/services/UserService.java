package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@Service
public class UserService {
	
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

}
