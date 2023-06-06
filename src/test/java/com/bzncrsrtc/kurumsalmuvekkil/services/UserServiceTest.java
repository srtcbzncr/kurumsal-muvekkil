package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private MessageSource messageSource;
	
	@Test
	public void createTest_NotExistingEmailAndNotExistingUsername_ShouldNotCauseException() {
		// Create a user
		User user = new User("", "", "", "");
		
		// Mock the repository
		when(userRepository.existsByEmailAndDeleted("", false)).thenReturn(false);
		when(userRepository.existsByUsernameAndDeleted("", false)).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> userService.create(user, Locale.US));
		
		// Verifications
		verify(userRepository.existsByEmailAndDeleted("", false), times(1));
		verify(userRepository.existsByUsernameAndDeleted("", false), times(1));
		verify(userRepository.save(user), times(1));
	}
	
	@Test
	public void createTest_ExistingEmailAndNotExistingUsername_ShouldCauseEmailAlreadyUsedException() {
		// Create a user
		User user = new User("", "", "", "");
		
		// Mock the repository
		when(userRepository.existsByEmailAndDeleted("", true)).thenReturn(false);
		when(userRepository.existsByUsernameAndDeleted("", false)).thenReturn(false);
		
		// Assertions
		assertThrows(EmailAlreadyUsedException.class, () -> userService.create(user, Locale.US));
		
		// Verifications
		verify(userRepository.existsByEmailAndDeleted("", false), times(1));
		verify(userRepository.existsByUsernameAndDeleted("", false), times(1));
		verify(userRepository.save(user), times(0));
	}
	
	@Test
	public void createTest_NotExistingEmailAndExistingUsername_ShouldCauseUsernameAlreadyUsedException() {
		// Create a user
		User user = new User("", "", "", "");
		
		// Mock the repository
		when(userRepository.existsByEmailAndDeleted("", false)).thenReturn(false);
		when(userRepository.existsByUsernameAndDeleted("", true)).thenReturn(false);
		
		// Assertions
		assertThrows(UsernameAlreadyUsedException.class, () -> userService.create(user, Locale.US));
		
		// Verifications
		verify(userRepository.existsByEmailAndDeleted("", false), times(1));
		verify(userRepository.existsByUsernameAndDeleted("", false), times(1));
		verify(userRepository.save(user), times(0));
	}

}
