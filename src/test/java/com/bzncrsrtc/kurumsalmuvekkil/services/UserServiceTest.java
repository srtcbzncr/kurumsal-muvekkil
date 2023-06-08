package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UserNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
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
		verify(userRepository, times(1)).existsByEmailAndDeleted("", false);
		verify(userRepository, times(1)).existsByUsernameAndDeleted("", false);
		verify(userRepository, times(1)).save(user);
	}
	
	@Test
	public void createTest_ExistingEmailAndNotExistingUsername_ShouldCauseEmailAlreadyUsedException() {
		// Create a user
		User user = new User("", "", "", "");
		
		// Mock the repository
		when(userRepository.existsByEmailAndDeleted("", false)).thenReturn(true);
		
		// Assertions
		assertThrows(EmailAlreadyUsedException.class, () -> userService.create(user, Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsByEmailAndDeleted("", false);
		verify(userRepository, times(0)).existsByUsernameAndDeleted("", false);
		verify(userRepository, times(0)).save(user);
	}
	
	@Test
	public void createTest_NotExistingEmailAndExistingUsername_ShouldCauseUsernameAlreadyUsedException() {
		// Create a user
		User user = new User("", "", "", "");
		
		// Mock the repository
		when(userRepository.existsByEmailAndDeleted("", false)).thenReturn(false);
		when(userRepository.existsByUsernameAndDeleted("", false)).thenReturn(true);
		
		// Assertions
		assertThrows(UsernameAlreadyUsedException.class, () -> userService.create(user, Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsByEmailAndDeleted("", false);
		verify(userRepository, times(1)).existsByUsernameAndDeleted("", false);
		verify(userRepository, times(0)).save(user);
	}
	
	@Test
	public void updateTest_NotException_ShouldNotCauseException() {
		// Mock the repository
		when(userRepository.existsById(any())).thenReturn(true);
		when(userRepository.existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		when(userRepository.existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> userService.update(new User(), Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsById(any());
		verify(userRepository, times(1)).existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(1)).existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	public void updateTest_NotExistingUser_ShouldNotCauseException() {
		// Mock the repository
		when(userRepository.existsById(any())).thenReturn(false);
		
		// Assertions
		assertThrows(UserNotFoundException.class, () -> userService.update(new User(), Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsById(any());
		verify(userRepository, times(0)).existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(0)).existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(0)).save(any());
	}
	
	@Test
	public void updateTest_AlreadyUsedEmail_ShouldCauseEmailAlreadyUsedException() {
		// Mock the repository
		when(userRepository.existsById(any())).thenReturn(true);
		when(userRepository.existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(true);
		
		// Assertions
		assertThrows(EmailAlreadyUsedException.class, () -> userService.update(new User(), Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsById(any());
		verify(userRepository, times(1)).existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(0)).existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(0)).save(any());
	}
	
	@Test
	public void updateTest_AlreadyUsedUsername_ShouldCauseUsernameAlreadyUsedException() {
		// Mock the repository
		when(userRepository.existsById(any())).thenReturn(true);
		when(userRepository.existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		when(userRepository.existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(true);
		
		// Assertions
		assertThrows(UsernameAlreadyUsedException.class, () -> userService.update(new User(), Locale.US));
		
		// Verifications
		verify(userRepository, times(1)).existsById(any());
		verify(userRepository, times(1)).existsByEmailAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(1)).existsByUsernameAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(userRepository, times(0)).save(any());
	}

}
