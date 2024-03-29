package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UserNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private RoleService roleService;
	private UserRepository userRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(RoleService roleService, UserRepository userRepository, MessageSource messageSource) {
		this.roleService = roleService;
		this.userRepository = userRepository;
		this.messageSource = messageSource;
	}
	
	public List<User> findAllAdmins(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.findAllByRoleIdAndDeleted(adminRoleId, false);
	}
	
	public List<User> findActiveAdmins(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.findAllByRoleIdAndDeletedAndActive(adminRoleId, false, true);
	}
	
	public List<User> findPassiveAdmins(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.findAllByRoleIdAndDeletedAndActive(adminRoleId, false, false);
	}
	
	public List<User> findDeletedAdmins(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.findAllByRoleIdAndDeleted(adminRoleId, true);
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
	
	public User createAdmin(User user, Locale locale) {
		if(userRepository.existsByEmailAndDeleted(user.getEmail(), false)) {
			throw new EmailAlreadyUsedException(messageSource.getMessage("email.already.used.message", null, locale));
		}
		
		if(userRepository.existsByUsernameAndDeleted(user.getUsername(), false)) {
			throw new UsernameAlreadyUsedException(messageSource.getMessage("username.already.used.message", null, locale));
		}
		
		Role role = roleService.findByName("ROLE_ADMIN", locale);
		
		user.setRole(role);
		
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
	
	public int allAdminsCount(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.countByRoleIdAndDeleted(adminRoleId, false);
	}
	
	public int activeAdminsCount(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.countByRoleIdAndActiveAndDeleted(adminRoleId, true, false);
	}
	
	public int passiveAdminsCount(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.countByRoleIdAndActiveAndDeleted(adminRoleId, false, false);
	}
	
	public int deletedAdminsCount(Locale locale) {
		UUID adminRoleId = roleService.findIdByName("ROLE_ADMIN", locale);
		
		return userRepository.countByRoleIdAndDeleted(adminRoleId, true);
	}
	
	public User setActive(UUID id, Locale locale) {
		Optional<User> optionalUser = userRepository.findByIdAndDeleted(id, false);
		
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null, locale));
		}
		
		User user = optionalUser.get();
		user.setActive(true);
		
		return userRepository.save(user);
	}
	
	public User setPassive(UUID id, Locale locale) {
		Optional<User> optionalUser = userRepository.findByIdAndDeleted(id, false);
		
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null, locale));
		}
		
		User user = optionalUser.get();
		user.setActive(false);
		
		return userRepository.save(user);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<User> optionalUser = userRepository.findByIdAndDeleted(id, false);
		
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null, locale));
		}
		
		User user = optionalUser.get();
		user.setDeleted(true);
		user.setDeletedAt(LocalDateTime.now());
		
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
